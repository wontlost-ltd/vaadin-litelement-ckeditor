#!/usr/bin/env node
/**
 * 开发模式诊断工具（dev-mode probe）
 *
 * 用途：在三种前端运行模式下启动 examples/spring-boot-sample，逐一验证编辑器
 * 能否正常挂载并可交互，同时记录 Vite dev server 的启停状态与关键请求结果。
 *
 * 背景：仓库既有的 Playwright 套件只跑 production jar，无法覆盖 dev 模式特有的
 * 问题（issue #120：frontendHotdeploy=true 时 UI 无限加载、oxc 辅助模块 404）。
 * 本工具把之前手工排查的步骤固化下来，便于复现用户报告的环境。
 *
 * 三种模式：
 *   hotdeploy  —— frontendHotdeploy=true，Vite dev server 实时转译 TS
 *   prebuilt   —— frontendHotdeploy=false，使用预编译 dev.bundle，不启动 Vite
 *   default    —— 不显式指定，走 Vaadin 25 的默认行为
 *
 * 关键判据：`/VAADIN/@id/` 是 Vite dev server 专属路径前缀。
 *   - Vite 运行时该路径应返回 200
 *   - Vite 未运行时返回 404 属于预期，不是缺陷
 *   - 若「Vite 已启动」但该路径仍 404，说明请求没被 dev server 接住
 *     （典型成因：context path 代理未匹配、Vite 中途退出、前端产物与运行模式错配）
 *
 * 用法：
 *   node tools/devmode-probe.mjs                  # 依次跑三种模式
 *   node tools/devmode-probe.mjs hotdeploy        # 只跑指定模式
 *   node tools/devmode-probe.mjs --port 8899      # 指定端口
 *   node tools/devmode-probe.mjs --json           # 输出机器可读结果
 *
 * 退出码：0 = 全部模式编辑器可用；1 = 存在模式失败（可复现问题）。
 */

import { spawn } from 'node:child_process';
import { once } from 'node:events';
import { setTimeout as sleep } from 'node:timers/promises';
import { rm } from 'node:fs/promises';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { chromium } from '@playwright/test';

const HERE = path.dirname(fileURLToPath(import.meta.url));
const SAMPLE_DIR = path.resolve(HERE, '../../examples/spring-boot-sample');

/** issue #120 报告的确切 URL，用于判断 Vite 是否真正接管了该请求。 */
const OXC_HELPER_PATH =
    '/VAADIN/@id/__x00__@oxc-project+runtime@0.133.0/helpers/esm/decorate.js';

/**
 * 三种待测模式。`hotdeploy` 为 null 时不传该系统属性，走 Vaadin 默认行为。
 * expectVite 记录该模式下是否应当启动 Vite，用于交叉校验实际观测结果。
 */
const MODES = {
    hotdeploy: { hotdeploy: true, expectVite: true, desc: 'frontendHotdeploy=true（Vite 实时转译）' },
    prebuilt: { hotdeploy: false, expectVite: false, desc: '预编译 dev bundle（不启动 Vite）' },
    default: { hotdeploy: null, expectVite: null, desc: 'Vaadin 25 默认行为' },
};

/** 被测路由与其种子文本，用于确认编辑器确实渲染了内容而非空壳。 */
const ROUTES = [
    { path: '/classic', seed: 'Hello from Classic' },
    { path: '/balloon', seed: 'Hello from Balloon' },
    { path: '/inline', seed: 'Hello from Inline' },
    { path: '/decoupled', seed: 'Hello from Decoupled' },
];

const USAGE = `开发模式诊断工具

用法：node tools/devmode-probe.mjs [模式...] [选项]

模式（缺省全部执行）：
${Object.entries(MODES)
    .map(([k, v]) => `  ${k.padEnd(11)} ${v.desc}`)
    .join('\n')}

选项：
  --port <n>      应用监听端口（默认 8899）
  --json          输出机器可读的 JSON 结果
  --keep-bundle   保留已生成的前端产物，不在切换模式前清理
  -h, --help      显示本帮助

退出码：0 = 全部模式编辑器可用；1 = 存在模式失败。
`;

function parseArgs(argv) {
    const opts = { modes: [], port: '8899', json: false, keepBundle: false };
    for (let i = 0; i < argv.length; i++) {
        const a = argv[i];
        if (a === '-h' || a === '--help') {
            process.stdout.write(USAGE);
            process.exit(0);
        } else if (a === '--json') opts.json = true;
        else if (a === '--keep-bundle') opts.keepBundle = true;
        else if (a === '--port') opts.port = argv[++i];
        else if (MODES[a]) opts.modes.push(a);
        else throw new Error(`未知参数：${a}（可选模式：${Object.keys(MODES).join(', ')}）`);
    }
    if (opts.modes.length === 0) opts.modes = Object.keys(MODES);
    return opts;
}

/**
 * 启动 sample 应用并等待其就绪。
 *
 * 注意：这里同时监听 Vite 的启动日志。Vaadin 把 dev server 输出转发到应用日志，
 * 因此「是否出现 VITE vX ready」是判断 Vite 实际启停最可靠的信号——比探测端口更准，
 * 因为 Vite 由 Flow 以随机端口在内部托管。
 */
async function startApp({ mode, port }) {
    const { hotdeploy } = MODES[mode];
    const jvmArgs = [
        '-Dvaadin.launch-browser=false',
        `-Dserver.port=${port}`,
        ...(hotdeploy === null ? [] : [`-Dvaadin.frontend.hotdeploy=${hotdeploy}`]),
    ];

    const child = spawn(
        'mvn',
        ['spring-boot:run', `-Dspring-boot.run.jvmArguments=${jvmArgs.join(' ')}`],
        { cwd: SAMPLE_DIR, stdio: ['ignore', 'pipe', 'pipe'] },
    );

    const log = [];
    const state = { started: false, viteStarted: false, bundleRebuilt: false, reoptimized: false };

    const scan = (buf) => {
        const text = buf.toString();
        log.push(text);
        if (/Started SampleApplication/.test(text)) state.started = true;
        if (/VITE v[\d.]+\s+ready/i.test(text)) state.viteStarted = true;
        if (/Development frontend bundle built/i.test(text)) state.bundleRebuilt = true;
        if (/Re-optimizing dependencies/i.test(text)) state.reoptimized = true;
    };
    child.stdout.on('data', scan);
    child.stderr.on('data', scan);

    // 首次启动可能触发 npm install + bundle 构建，给足 8 分钟。
    const deadline = Date.now() + 480_000;
    while (Date.now() < deadline && !state.started) {
        if (child.exitCode !== null) {
            throw new Error(`应用异常退出（code=${child.exitCode}）：\n${log.join('').slice(-2000)}`);
        }
        await sleep(1000);
    }
    if (!state.started) throw new Error('应用启动超时（8 分钟）');

    return { child, state, log };
}

async function stopApp(child) {
    if (child.exitCode !== null) return;
    child.kill('SIGTERM');
    // 给 Spring 优雅关闭的机会，超时则强杀，避免残留进程占用端口。
    const timer = sleep(15_000).then(() => 'timeout');
    const exited = once(child, 'exit').then(() => 'exited');
    if ((await Promise.race([exited, timer])) === 'timeout') child.kill('SIGKILL');
}

/**
 * 逐路由验证编辑器：既要挂载，也要能接受键盘输入。
 * 只检查元素存在是不够的——issue #120 的症状是「一直加载」，
 * 组件可能已挂载但编辑器实例从未 ready。
 */
async function probeRoutes(baseUrl) {
    const browser = await chromium.launch();
    const results = [];
    const consoleErrors = [];
    const failedRequests = [];

    try {
        const page = await browser.newPage();
        page.on('console', (m) => {
            if (m.type() === 'error') consoleErrors.push(m.text());
        });
        page.on('response', (r) => {
            // 只记录资源类 4xx/5xx。Vaadin 的 push/UIDL 长连接会因导航被取消，
            // 那属于正常现象，不计入失败。
            if (r.status() >= 400) failedRequests.push(`HTTP ${r.status()} ${r.url()}`);
        });

        for (const route of ROUTES) {
            const entry = { route: route.path, mounted: false, interactive: false, seeded: false };
            try {
                await page.goto(`${baseUrl}${route.path}`, { waitUntil: 'load', timeout: 120_000 });
                const editable = page.locator('.ck-editor__editable').first();
                await editable.waitFor({ state: 'visible', timeout: 60_000 });
                entry.mounted = true;

                entry.seeded = (await editable.innerText()).includes(route.seed);

                const marker = ' PROBE-OK';
                await editable.click();
                await page.keyboard.type(marker);
                await page.waitForTimeout(800);
                entry.interactive = (await editable.innerText()).includes(marker.trim());
            } catch (err) {
                entry.error = String(err).split('\n')[0];
            }
            results.push(entry);
        }
    } finally {
        await browser.close();
    }
    return { results, consoleErrors, failedRequests };
}

/** 探测 issue #120 中的 oxc 辅助模块路径，返回状态码。 */
async function probeOxcHelper(baseUrl) {
    try {
        const res = await fetch(`${baseUrl}${OXC_HELPER_PATH}`);
        return res.status;
    } catch {
        return 0;
    }
}

async function runMode(mode, opts) {
    const baseUrl = `http://localhost:${opts.port}`;
    const meta = MODES[mode];
    process.stderr.write(`\n=== 模式：${mode} —— ${meta.desc} ===\n`);

    // prebuilt 模式依赖已存在的 dev.bundle；hotdeploy 模式则要求 Vite 接管，
    // 两者共用同一份产物容易互相污染，因此每次切换模式都清掉生成物。
    if (!opts.keepBundle) {
        await rm(path.join(SAMPLE_DIR, 'target/frontend'), { recursive: true, force: true });
        await rm(path.join(SAMPLE_DIR, 'src/main/frontend/generated'), { recursive: true, force: true });
    }

    let app;
    try {
        app = await startApp({ mode, port: opts.port });
    } catch (err) {
        process.stderr.write(`启动失败：${err.message}\n`);
        return { mode, ok: false, startupError: err.message };
    }

    try {
        const { results, consoleErrors, failedRequests } = await probeRoutes(baseUrl);
        const oxcStatus = await probeOxcHelper(baseUrl);
        const viteStarted = app.state.viteStarted;

        const allOk = results.every((r) => r.mounted && r.interactive);
        // 核心矛盾检测：Vite 已启动，但其专属路径仍 404 —— 这正是 issue #120 的形态。
        const viteRouting = viteStarted && oxcStatus === 404;

        for (const r of results) {
            const flag = r.mounted && r.interactive ? '✓' : '✗';
            process.stderr.write(
                `  ${flag} ${r.route} mounted=${r.mounted} interactive=${r.interactive} seeded=${r.seeded}` +
                    `${r.error ? ` error=${r.error}` : ''}\n`,
            );
        }
        process.stderr.write(
            `  Vite=${viteStarted ? '已启动' : '未启动'} ` +
                `oxc(${OXC_HELPER_PATH.slice(0, 24)}…)=${oxcStatus} ` +
                `bundleRebuilt=${app.state.bundleRebuilt} reoptimized=${app.state.reoptimized}\n`,
        );
        if (meta.expectVite !== null && meta.expectVite !== viteStarted) {
            process.stderr.write(
                `  ⚠ 预期 Vite ${meta.expectVite ? '启动' : '不启动'}，实际相反——模式未按配置生效\n`,
            );
        }
        if (viteRouting) {
            process.stderr.write('  ⚠ Vite 已启动但其专属路径 404 —— 复现 issue #120 的请求路由异常\n');
        }
        if (consoleErrors.length) {
            process.stderr.write(`  console 错误 ${consoleErrors.length} 条：\n`);
            for (const e of consoleErrors.slice(0, 5)) process.stderr.write(`    ${e.split('\n')[0]}\n`);
        }
        if (failedRequests.length) {
            process.stderr.write(`  失败请求 ${failedRequests.length} 条：\n`);
            for (const f of failedRequests.slice(0, 5)) process.stderr.write(`    ${f}\n`);
        }

        return {
            mode,
            ok: allOk && !viteRouting,
            viteStarted,
            viteExpected: meta.expectVite,
            oxcStatus,
            bundleRebuilt: app.state.bundleRebuilt,
            reoptimized: app.state.reoptimized,
            routes: results,
            consoleErrors,
            failedRequests,
        };
    } finally {
        await stopApp(app.child);
    }
}

const opts = parseArgs(process.argv.slice(2));
const report = [];
for (const mode of opts.modes) {
    report.push(await runMode(mode, opts));
}

const failed = report.filter((r) => !r.ok);
if (opts.json) {
    process.stdout.write(`${JSON.stringify({ report, ok: failed.length === 0 }, null, 2)}\n`);
} else {
    process.stderr.write('\n=== 汇总 ===\n');
    for (const r of report) {
        process.stderr.write(
            `${r.ok ? '✓' : '✗'} ${r.mode}` +
                `${r.startupError ? ` —— 启动失败：${r.startupError}` : ` —— Vite=${r.viteStarted} oxc=${r.oxcStatus}`}\n`,
        );
    }
}
process.exit(failed.length === 0 ? 0 : 1);
