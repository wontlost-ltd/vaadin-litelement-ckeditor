import { expect, test } from '@playwright/test';
import { waitForCKEditorReady } from '../helpers/ck';

/**
 * 值双向绑定与只读状态的端到端回归。
 *
 * 既有夹具只验证编辑器「挂载 + 显示种子内容」，不覆盖 addon 最核心的契约：
 * 客户端编辑能否回传服务端、服务端设值能否下发客户端。这里补上这两条链路，
 * 并回归 issue #85（值变更事件里 getValue() 与事件值不一致）。
 */
test.describe('值双向绑定', () => {
    test('客户端输入回传服务端并触发一次值变更事件', async ({ page }) => {
        await page.goto('/binding');
        const editable = await waitForCKEditorReady(page);

        await expect(page.locator('#change-count')).toHaveText('0');

        await editable.click();
        await page.keyboard.type(' EDITED');

        // 值变更走 Vaadin 的服务端往返，且前端有防抖，因此用 poll 等待而非固定 sleep。
        await expect
            .poll(async () => page.locator('#server-value').innerText(), {
                timeout: 15_000,
                message: '服务端始终未收到客户端输入的内容',
            })
            .toContain('EDITED');

        // 事件必须标记为来自客户端，否则说明值是被服务端回写而非用户输入触发。
        await expect(page.locator('#last-from-client')).toHaveText('true');

        // getValue() 与事件值一致（issue #85 回归点）：探针写的是 editor.getValue()，
        // 若二者不同步，这里读到的会是旧值。
        expect(await page.locator('#server-value').innerText()).toContain('Hello from Binding');
    });

    test('服务端 setValue 下发并覆盖编辑器内容', async ({ page }) => {
        await page.goto('/binding');
        const editable = await waitForCKEditorReady(page);
        expect(await editable.innerText()).toContain('Hello from Binding');

        await page.locator('#btn-set-value').click();

        await expect
            .poll(async () => editable.innerText(), {
                timeout: 15_000,
                message: 'setValue 未下发到前端编辑器',
            })
            .toContain('Set from server');

        expect(await editable.innerText()).not.toContain('Hello from Binding');
    });

    test('setValue(null) 归一化为空串而非渲染 "null"', async ({ page }) => {
        await page.goto('/binding');
        const editable = await waitForCKEditorReady(page);

        await page.locator('#btn-clear').click();

        // 归一化失败的典型症状是编辑器里出现字面量 "null"。
        await expect
            .poll(async () => (await editable.innerText()).trim(), {
                timeout: 15_000,
                message: 'clear 后编辑器内容未清空',
            })
            .not.toContain('Hello from Binding');

        expect((await editable.innerText()).toLowerCase()).not.toContain('null');
    });
});

test.describe('只读状态', () => {
    test('切换只读后编辑器拒绝键盘输入，再次切换恢复可编辑', async ({ page }) => {
        await page.goto('/binding');
        const editable = await waitForCKEditorReady(page);
        const before = await editable.innerText();

        await page.locator('#btn-toggle-readonly').click();

        // 等只读状态真正下发到 CKEditor 实例，再尝试输入。
        await expect
            .poll(
                async () =>
                    page.evaluate(() => {
                        const el = document.querySelector('.ck-editor__editable');
                        return el?.getAttribute('contenteditable');
                    }),
                { timeout: 15_000, message: '只读状态未同步到前端' },
            )
            .toBe('false');

        await editable.click();
        await page.keyboard.type('SHOULD-NOT-APPEAR');
        await page.waitForTimeout(1_000);
        expect(await editable.innerText()).not.toContain('SHOULD-NOT-APPEAR');
        expect(await editable.innerText()).toContain(before.trim().slice(0, 10));

        // 再次切换应恢复可编辑，确认只读不是单向不可逆的。
        await page.locator('#btn-toggle-readonly').click();
        await expect
            .poll(
                async () =>
                    page.evaluate(() => {
                        const el = document.querySelector('.ck-editor__editable');
                        return el?.getAttribute('contenteditable');
                    }),
                { timeout: 15_000, message: '取消只读后编辑器未恢复可编辑' },
            )
            .toBe('true');

        await editable.click();
        await page.keyboard.type(' NOW-EDITABLE');
        await expect
            .poll(async () => editable.innerText(), { timeout: 10_000 })
            .toContain('NOW-EDITABLE');
    });
});
