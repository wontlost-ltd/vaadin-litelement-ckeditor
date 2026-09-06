import { expect, test } from '@playwright/test';
import { waitForCKEditorReady } from '../helpers/ck';

/**
 * issue #122 的端到端回归：编辑器在「保持连接」的前提下被换父容器时，
 * 不得因 CKEditor Collection 记账错乱而陷入 destroy 无限递归。
 *
 * 缺陷形态：createEditorInstance() 在 Editor.create() 之后覆盖实例的 .id，
 * 使其与所属 Context 的 Collection#_itemMap 键脱钩；destroy 时
 * `editors.has(editor)` 失配导致 remove() 从不执行，Context#destroy() 再度
 * 遍历到同一个 editor 并调用 destroy()，形成 CPU 密集的无限递归，卡死标签页。
 *
 * 断言策略：换父之后主线程必须仍能响应。这里不去窥探 CKEditor 内部，而是
 * 直接测量「浏览器还能不能干活」——递归一旦发生，主线程被占满，
 * 任何 evaluate 都无法在超时内返回。
 *
 * 失败必须是**干净的超时**而非挂起整个 runner：所有探针都套了显式 timeout，
 * 单测也设了自己的 test.setTimeout，避免复发时把 CI 拖死。
 */

/**
 * 主线程响应性探针：递归发生时事件循环被微任务占满，回到宏任务队列的探针无法返回。
 *
 * 用 expect.poll 而非手搓 Promise.race：由 Playwright 统一管理轮询、超时与失败报告。
 * 注意 expect.poll 内部仍走 runner 侧计时器，因此这并不能消除 runner 高负载对计时的影响；
 * 真正的收益是：换父期间 Vaadin 可能重建 DOM，单次 evaluate 会因执行上下文销毁而 reject，
 * poll 会重试而不是把这类噪音当成递归证据（失败消息用「疑似」措辞，不把超时断言成已证实根因）。
 */
async function expectMainThreadResponsive(
    page: import('@playwright/test').Page,
    timeout = 10_000,
): Promise<void> {
    await expect
        .poll(
            () =>
                page
                    // 回到宏任务队列跑一圈：只有事件循环仍在转动才会 resolve。
                    .evaluate(() => new Promise<string>((resolve) => setTimeout(() => resolve('alive'), 0)))
                    .catch(() => 'unavailable'),
            { timeout, message: '主线程无响应：疑似 destroy 无限递归（issue #122）' },
        )
        .toBe('alive');
}

test.describe('换父容器时的 destroy 递归', () => {
    test('单次换父后标签页保持响应且编辑器可用', async ({ page }) => {
        test.setTimeout(90_000);

        const pageErrors: Error[] = [];
        page.on('pageerror', (e) => pageErrors.push(e));

        await page.goto('/reparent');
        await waitForCKEditorReady(page);
        await expect(page.locator('#move-count')).toHaveText('0');

        await page.locator('#btn-move').click();

        // 换父确实发生（服务端探针），而不是按钮点了个寂寞。
        await expect
            .poll(async () => page.locator('#move-count').innerText(), {
                timeout: 20_000,
                message: '换父未发生：服务端计数未更新',
            })
            .toBe('1');
        await expect(page.locator('#current-slot')).toHaveText('secondary');

        // 核心断言：主线程仍在转。缺陷存在时这里会干净地超时失败。
        await expectMainThreadResponsive(page);

        // 编辑器在新槽位重建完成，且仍然可以正常输入。
        const editable = await waitForCKEditorReady(page);
        await expect(page.locator('#slot-secondary vaadin-ckeditor#ckeditor')).toBeAttached();

        await editable.click();
        await page.keyboard.type(' AFTER-MOVE');
        await expect
            .poll(async () => editable.innerText(), {
                timeout: 15_000,
                message: '换父后编辑器不再接受输入',
            })
            .toContain('AFTER-MOVE');

        // 换父全程不得有未捕获的页面错误。
        // 注意：本缺陷的递归跨越 await/Promise 边界（Context#destroy 用 Promise.all 递归），
        // 同步调用栈逐次展开，因此**不会**抛 RangeError，而是表现为微任务饥饿。
        // 所以这里不能只过滤栈溢出（那样断言恒真），必须断言「没有任何未捕获错误」。
        expect(pageErrors.map((e) => e.message), '换父过程中页面抛出未捕获错误').toEqual([]);
    });

    test('反复换父不累积卡顿，编辑器始终可重建', async ({ page }) => {
        test.setTimeout(120_000);

        await page.goto('/reparent');
        await waitForCKEditorReady(page);

        // 多次往返：单次侥幸通过掩盖不了递归——真有缺陷时第一次就会卡死。
        const ROUNDS = 4;
        for (let i = 1; i <= ROUNDS; i++) {
            await page.locator('#btn-move').click();

            await expect
                .poll(async () => page.locator('#move-count').innerText(), {
                    timeout: 20_000,
                    message: `第 ${i} 次换父未完成`,
                })
                .toBe(String(i));

            await expectMainThreadResponsive(page);

            // 每一轮都必须重新长出可用的编辑器，确认 destroy/create 循环健康。
            await waitForCKEditorReady(page);
        }

        await expect(page.locator('#move-count')).toHaveText(String(ROUNDS));
        // 偶数次往返后应回到起始槽位。
        await expect(page.locator('#current-slot')).toHaveText('primary');
        await expect(page.locator('#slot-primary vaadin-ckeditor#ckeditor')).toBeAttached();

        const editable = await waitForCKEditorReady(page);
        await editable.click();
        await page.keyboard.type(' STILL-WORKS');
        await expect
            .poll(async () => editable.innerText(), {
                timeout: 15_000,
                message: '多次换父后编辑器不再接受输入',
            })
            .toContain('STILL-WORKS');
    });
});
