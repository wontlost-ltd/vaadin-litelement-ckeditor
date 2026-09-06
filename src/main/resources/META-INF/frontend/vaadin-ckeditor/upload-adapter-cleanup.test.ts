import { describe, it, expect } from 'vitest';

/**
 * 上传适配器猴补丁还原的回归测试。
 *
 * 背景：`setupCustomUploadAdapter()` 把自建工厂写到 CKEditor `FileRepository` 插件的
 * `createUploadAdapter` 上。该工厂闭包捕获了 `this`（Lit 元素），形成
 *   FileRepository 插件 -> 工厂闭包 -> 组件 -> 整棵 DOM 子树
 * 的引用链。而 `destroyEditor()` 在组件已断开时会跳过 `editor.destroy()`（注释写明交给 GC），
 * 此时 CKEditor 自身不拆卸插件，这条链会把整个编辑器钉住——每次路由往返泄漏一个实例。
 *
 * 因此补丁必须登记还原动作到 `listenerCleanups`，该数组在 `destroyEditor()` 的
 * `isDisconnected` 提前 return **之前**被无条件遍历执行。
 *
 * 本文件直接驱动生产方法 `setupCustomUploadAdapter()`，而不是复刻同构逻辑：
 * 若有人删掉那段 cleanup 登记，这里必须变红。
 */

/** 从生产类原型上取私有方法，避免在多个用例里重复样板。 */
async function getPrototypeMethod(name: string): Promise<(this: unknown, ...args: unknown[]) => unknown> {
    const mod = await import('./vaadin-ckeditor');
    const ctor = (mod as Record<string, unknown>).VaadinCKEditor as { prototype: Record<string, unknown> };
    const fn = ctor.prototype[name] as (this: unknown, ...args: unknown[]) => unknown;
    expect(typeof fn).toBe('function');
    return fn;
}

/** 构造驱动 setupCustomUploadAdapter 所需的最小上下文。 */
function makeCtx(fileRepository: Record<string, unknown>): Record<string, unknown> {
    return {
        // $server 存在才会进入补丁分支（生产方法首行守卫）
        $server: {},
        listenerCleanups: [] as Array<() => void>,
        editor: {
            plugins: { get: (): Record<string, unknown> => fileRepository },
        },
        // 生产方法通过 getUploadAdapterFactory() 取工厂；返回可辨识的哨兵便于断言
        getUploadAdapterFactory: (): (() => void) => patchedFactory,
    };
}

/** 哨兵：代表连接器写入的工厂。用引用相等判断补丁是否被写入/还原。 */
const patchedFactory = (): void => undefined;

describe('setupCustomUploadAdapter 的猴补丁还原（生产路径）', () => {
    it('补丁写入后登记还原动作，执行 cleanup 后恢复原工厂', async () => {
        const setup = await getPrototypeMethod('setupCustomUploadAdapter');

        const originalFactory = (): void => undefined;
        const fileRepository: Record<string, unknown> = { createUploadAdapter: originalFactory };
        const ctx = makeCtx(fileRepository);

        setup.call(ctx);

        // 补丁已生效
        expect(fileRepository.createUploadAdapter).toBe(patchedFactory);
        const cleanups = ctx.listenerCleanups as Array<() => void>;
        expect(cleanups).toHaveLength(1);

        // 执行 destroyEditor() 会跑的那一轮 cleanup
        for (const fn of cleanups) fn();

        // 关键断言：原工厂被还原，闭包对组件的引用链被切断
        expect(fileRepository.createUploadAdapter).toBe(originalFactory);
        expect(fileRepository.createUploadAdapter).not.toBe(patchedFactory);
    });

    it('原工厂为 undefined 时还原回 undefined，不残留补丁', async () => {
        // FileRepository 默认不定义 createUploadAdapter（CKEditor 在 createLoader() 中
        // 用 `if (!this.createUploadAdapter)` 守卫并告警，因此还原成 undefined 是安全的）。
        const setup = await getPrototypeMethod('setupCustomUploadAdapter');

        const fileRepository: Record<string, unknown> = {};
        const ctx = makeCtx(fileRepository);

        setup.call(ctx);
        expect(fileRepository.createUploadAdapter).toBe(patchedFactory);

        for (const fn of ctx.listenerCleanups as Array<() => void>) fn();

        expect(fileRepository.createUploadAdapter).toBeUndefined();
        expect('createUploadAdapter' in fileRepository).toBe(true);
    });

    it('连续两轮创建/销毁不会把补丁残留到插件上', async () => {
        // 回归重点：cleanup 闭包捕获的必须是本轮的 fileRepository 与原值，
        // 否则第二轮还原会写回第一轮的工厂，形成交叉污染。
        const setup = await getPrototypeMethod('setupCustomUploadAdapter');

        const firstOriginal = (): void => undefined;
        const repoA: Record<string, unknown> = { createUploadAdapter: firstOriginal };
        const ctxA = makeCtx(repoA);
        setup.call(ctxA);
        for (const fn of ctxA.listenerCleanups as Array<() => void>) fn();
        expect(repoA.createUploadAdapter).toBe(firstOriginal);

        // 第二轮：新的插件实例（编辑器重建后 FileRepository 是新对象）
        const secondOriginal = (): void => undefined;
        const repoB: Record<string, unknown> = { createUploadAdapter: secondOriginal };
        const ctxB = makeCtx(repoB);
        setup.call(ctxB);
        expect(repoB.createUploadAdapter).toBe(patchedFactory);
        for (const fn of ctxB.listenerCleanups as Array<() => void>) fn();

        expect(repoB.createUploadAdapter).toBe(secondOriginal);
        // 第一轮的插件不得被第二轮的还原动作波及
        expect(repoA.createUploadAdapter).toBe(firstOriginal);
    });

    it('$server 缺失时不打补丁，也不登记多余的 cleanup', async () => {
        const setup = await getPrototypeMethod('setupCustomUploadAdapter');

        const fileRepository: Record<string, unknown> = {};
        const ctx = makeCtx(fileRepository);
        delete ctx.$server;

        setup.call(ctx);

        expect(fileRepository.createUploadAdapter).toBeUndefined();
        expect(ctx.listenerCleanups as Array<() => void>).toHaveLength(0);
    });
});
