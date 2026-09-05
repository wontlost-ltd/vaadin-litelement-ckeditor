# E2E Tests

Playwright smoke tests for the Vaadin CKEditor addon. Drives the Spring Boot
sample app at [`examples/spring-boot-sample/`](../examples/spring-boot-sample/)
in a real browser to catch issues that unit tests miss.

## Coverage

| Spec | Verifies |
|------|----------|
| `editor-types.spec.ts` | 4 EditorTypes (classic / balloon / inline / decoupled) all mount under CKEditor 48 and render seed HTML |
| `theme-switch.spec.ts` | Lumo dark attribute applied; v48 `--ck-color-ai-*` tokens injected by `theme-manager.ts` |
| `upload-adapter.spec.ts` | Image upload via toolbar reaches Java `UploadHandler` and editor displays inserted `<img>` |
| `ai-config-migration.spec.ts` | Top-level `initialData` migrates to `root.initialData` and editor reports `state === 'ready'` |
| `collab-strip-initial-data.spec.ts` | `stripInitialDataIfChannelSeeded` writes localStorage seed key on first visit |
| `value-binding.spec.ts` | 值双向绑定（客户端输入回传服务端、`setValue` 下发、`setValue(null)` 归一化）与只读切换 |
| `visual-regression.spec.ts` | Pixel-level baselines for the 4 EditorTypes + dark theme (Linux/CI only) |

Each spec runs against Chromium and Firefox.

## 开发模式诊断工具

Playwright 套件跑的是 **production jar**，覆盖不到 dev 模式特有的前端问题
（如 [#120](https://github.com/wontlost-ltd/vaadin-ckeditor/issues/120)：
`frontendHotdeploy=true` 时 UI 无限加载）。`tools/devmode-probe.mjs` 补上这一层：
它依次以三种前端模式启动 sample，验证 4 种编辑器能否挂载并接受输入。

```bash
npm run probe:devmode                  # 依次跑 hotdeploy / prebuilt / default
npm run probe:devmode -- hotdeploy     # 只跑指定模式
npm run probe:devmode -- --json        # 机器可读输出
```

| 模式 | 含义 | 预期 Vite |
|------|------|-----------|
| `hotdeploy` | `frontendHotdeploy=true`，Vite 实时转译 TS | 启动 |
| `prebuilt` | 使用预编译 `dev.bundle` | 不启动 |
| `default` | Vaadin 25 默认行为 | 视版本而定 |

判读要点：`/VAADIN/@id/` 是 Vite dev server 专属路径前缀。

- Vite 未启动时该路径返回 **404 属于预期**，不是缺陷
- **Vite 已启动但该路径仍 404** 才是异常，说明请求没被 dev server 接住
  （常见成因：context path 代理未匹配、Vite 中途退出、前端产物与运行模式错配）

工具会显式标记这一矛盾，并在编辑器不可用时以退出码 1 结束。

> 前置条件：先在仓库根执行 `mvn -DskipTests install`。工具直接用
> `mvn spring-boot:run` 启动 sample，首次运行会触发 npm install，可能耗时数分钟。

## Local run

Prerequisites: Java 21+, Maven, Node 24+, ~300 MB of disk for Playwright browsers.

```bash
# 1. Install the addon locally so the sample can resolve it
mvn -DskipTests install

# 2. Build the sample app (frontend bundle baked into the jar)
cd examples/spring-boot-sample
mvn -Pproduction -DskipTests package

# 3. Install Playwright dependencies (one-time)
cd ../../e2e
npm install
npx playwright install chromium firefox

# 4. Run the suite
npm test
```

The sample app jar is started/stopped automatically by Playwright's `webServer`
configuration (`playwright.config.ts`). To run the server on a non-default
port, set `E2E_PORT=9090 npm test`; Playwright forwards it to Spring Boot via
`--server.port`.

## Visual regression baselines

Baselines live under [`tests/__screenshots__/`](tests/__screenshots__/) and are
pinned to **Linux x86_64** (the CI runner platform). The `visual-regression.spec.ts`
file is skipped on every other platform/arch via
`test.skip(!(process.platform === 'linux' && process.arch === 'x64'), …)` so
local development on macOS / Windows / Linux-arm64 stays green.

### Regenerate baselines

When CKEditor, Lumo, or the addon changes the rendered pixels, regenerate the
PNGs inside a Linux/amd64 container so the output matches CI:

```bash
podman run --rm --platform linux/amd64 \
    -v "$PWD":/work:Z \
    -w /work \
    mcr.microsoft.com/playwright:v1.60.0-jammy bash -c '
        apt-get update -qq && apt-get install -y -qq openjdk-21-jdk-headless curl
        curl -sL https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz | tar xz -C /opt
        export PATH=/opt/apache-maven-3.9.9/bin:$PATH
        mvn -B -ntp install -DskipTests
        cd examples/spring-boot-sample && mvn -B -ntp -DskipTests package -Pproduction
        cd ../../e2e && npm ci
        npx playwright test visual-regression --update-snapshots
    '
```

Then commit the changed PNGs.

## CI

GitHub Actions runs the full pipeline (including visual regression) on every
PR and push to `main`. See [`.github/workflows/e2e.yml`](../.github/workflows/e2e.yml).
