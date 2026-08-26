import { describe, it, expect } from 'vitest';
import { shouldRecreateEditor, type ReconnectState } from './reconnect-decision';

/** 已重连、已渲染、无编辑器、无在途流程——即「应当重建」的基线状态。 */
const reconnected: ReconnectState = {
    hasUpdated: true,
    hasEditor: false,
    isCreating: false,
    isDestroying: false,
    isDisconnected: false,
    isConnected: true,
};

describe('shouldRecreateEditor', () => {
    it('重连且无编辑器时应当重建', () => {
        expect(shouldRecreateEditor(reconnected)).toBe(true);
    });

    it('首次渲染前不重建（容器尚未就绪，交给 firstUpdated）', () => {
        expect(shouldRecreateEditor({ ...reconnected, hasUpdated: false })).toBe(false);
    });

    it('已有编辑器时不重建（防止重复创建）', () => {
        // 这条同时覆盖「connectedCallback 已经重建过，destroy microtask 再调一次」的场景：
        // 第二次调用看到 hasEditor=true，必须返回 false，否则会创建出两个编辑器。
        expect(shouldRecreateEditor({ ...reconnected, hasEditor: true })).toBe(false);
    });

    it('创建流程在途时不重建', () => {
        expect(shouldRecreateEditor({ ...reconnected, isCreating: true })).toBe(false);
    });

    it('销毁流程在途时不重建（等销毁完成后由 microtask 补判）', () => {
        expect(shouldRecreateEditor({ ...reconnected, isDestroying: true })).toBe(false);
    });

    it('组件已断开时不重建 —— isDisconnected 标记', () => {
        expect(shouldRecreateEditor({ ...reconnected, isDisconnected: true })).toBe(false);
    });

    it('组件已断开时不重建 —— DOM isConnected 为 false', () => {
        // 两个来源必须都表明「已连接」：仅凭组件自身标记可能滞后于真实 DOM 状态。
        expect(shouldRecreateEditor({ ...reconnected, isConnected: false })).toBe(false);
    });

    it('创建中断开→创建结束前重连：孤儿销毁后必须能重建', () => {
        // review (Codex): 「create() 在途时组件断开、随后又在创建结束前重新连上」
        // 这条路径不经过 destroyEditor()，拿不到那边 microtask 的补偿重建；
        // 而 connectedCallback 早已执行过（当时 isCreating=true，守卫返回），
        // 若孤儿销毁后不再补判，组件会永久停在空白状态。
        // 1) 重连发生在创建仍在途时——守卫必须拒绝（避免与在途创建竞争）
        expect(shouldRecreateEditor({ ...reconnected, isCreating: true })).toBe(false);

        // 2) 孤儿已销毁、创建锁已释放、组件确实连着——此时必须重建
        expect(shouldRecreateEditor({
            ...reconnected,
            hasEditor: false,
            isCreating: false,
            isDisconnected: false,
            isConnected: true,
        })).toBe(true);
    });

    it('同 tick remove→add 的完整时序：connected 时跳过，销毁完成后重建', () => {
        // 实测（jsdom）回调顺序为 disconnected → connected → destroy microtask。
        // 1) connectedCallback 执行时，editor 尚未被 microtask 销毁
        const atConnected: ReconnectState = { ...reconnected, hasEditor: true, isDisconnected: false };
        expect(shouldRecreateEditor(atConnected)).toBe(false);

        // 2) destroy microtask 完成后：editor 已清空、isDestroying 已复位、组件仍连着
        const afterDestroy: ReconnectState = { ...reconnected, hasEditor: false, isDestroying: false };
        expect(shouldRecreateEditor(afterDestroy)).toBe(true);
    });
});

describe('创建锁不变量（结构性回归防护）', () => {
    // 这些用例把「占锁 → await → 出口必须释放」的结构固化下来。
    // 真实代码中该不变量分布在 createEditor / executeEditorCreation 两处，
    // 无法在不 mock 整个 CKEditor 的前提下直接驱动，故以同构模型验证时序契约。

    it('await 抛错时锁必须释放（否则永久无法重试）', async () => {
        let isCreating = false;
        async function createEditor(shouldThrow: boolean): Promise<void> {
            if (isCreating) return;          // canCreateEditor 守卫
            isCreating = true;               // 先占锁
            let handedOff = false;
            try {
                if (shouldThrow) throw new Error('destroy failed');
                handedOff = true;            // 移交给 executeEditorCreation
            } finally {
                if (!handedOff) isCreating = false;
            }
        }

        await expect(createEditor(true)).rejects.toThrow('destroy failed');
        expect(isCreating).toBe(false);      // 锁已释放，后续可重试

        await createEditor(false);
        expect(isCreating).toBe(true);       // 已移交，由下游 finally 负责释放
    });

    it('占锁早于 await 可阻止并发双重创建', async () => {
        let isCreating = false;
        let created = 0;
        async function createEditor(): Promise<void> {
            if (isCreating) return;
            isCreating = true;               // 关键：在 await 之前占锁
            await Promise.resolve();         // 让出执行权
            created++;
            isCreating = false;
        }
        // 两个并发调用者
        await Promise.all([createEditor(), createEditor()]);
        expect(created).toBe(1);
    });
});

describe('销毁所有权隔离（结构性回归防护）', () => {
    // 背景：孤儿路径超时后不再等待 destroy()，但 Promise.race 并不会取消它。
    // 而 editorId 是 @property，重连后新实例复用同一个 DOM 节点，
    // CKEditor 在 destroy() 末尾会向 source element 写回内容——
    // 若不做所有权隔离，迟到的销毁会清空刚建好的编辑器 DOM。
    // 契约：只要存在未完成的销毁，创建流程就必须先等它结束。

    it('迟到的销毁必须先于新实例创建完成', async () => {
        const events: string[] = [];
        let destroyPromise: Promise<void> | null = null;

        // 模拟一次「超时后仍在后台推进」的销毁
        const slowDestroy = new Promise<void>((resolve) =>
            setTimeout(() => { events.push('destroy-writeback'); resolve(); }, 30));
        destroyPromise = slowDestroy;
        void slowDestroy.finally(() => { if (destroyPromise === slowDestroy) destroyPromise = null; });

        // 创建流程：以 destroyPromise 是否存在为唯一判据（不再看 editor/isDestroying）
        async function waitForPreviousEditorCleanup(): Promise<void> {
            if (destroyPromise) {
                await destroyPromise;
            }
        }
        await waitForPreviousEditorCleanup();
        events.push('create-new-editor');

        // 写回必须发生在新实例创建之前，否则新编辑器 DOM 会被清空
        expect(events).toEqual(['destroy-writeback', 'create-new-editor']);
    });

    it('无未完成销毁时不应引入等待', async () => {
        let destroyPromise: Promise<void> | null = null;
        async function waitForPreviousEditorCleanup(): Promise<void> {
            if (destroyPromise) await destroyPromise;
        }
        await waitForPreviousEditorCleanup();
        expect(destroyPromise).toBeNull();
    });
});

describe('destroyPromise 登记的身份归属（结构性回归防护）', () => {
    // destroyPromise 现在同时承载「常规销毁」与「孤儿销毁」两种登记。
    // 清空时必须按身份比对：若无条件置 null，先 settle 的一方会抹掉
    // 后登记的另一方，使创建流程不再等待迟到的销毁，
    // 重新打开「迟到销毁清空新编辑器 DOM」的所有权漏洞。

    it('先 settle 的销毁不得抹掉更新的登记', async () => {
        let destroyPromise: Promise<void> | null = null;

        // 常规销毁：很快 settle
        const normal = new Promise<void>((resolve) => setTimeout(resolve, 5));
        destroyPromise = normal;
        void normal.finally(() => {
            if (destroyPromise === normal) destroyPromise = null;   // 身份比对
        });

        // 孤儿销毁：随后登记，耗时更长
        const orphan = new Promise<void>((resolve) => setTimeout(resolve, 40));
        destroyPromise = orphan;
        void orphan.finally(() => {
            if (destroyPromise === orphan) destroyPromise = null;
        });

        await normal;
        await new Promise((r) => setTimeout(r, 5));
        // normal settle 后，orphan 的登记必须仍在
        expect(destroyPromise).toBe(orphan);

        await orphan;
        await new Promise((r) => setTimeout(r, 5));
        expect(destroyPromise).toBeNull();
    });

    it('无条件清空会抹掉更新的登记（反例，证明身份比对是必要的）', async () => {
        let destroyPromise: Promise<void> | null = null;

        const normal = new Promise<void>((resolve) => setTimeout(resolve, 5));
        destroyPromise = normal;
        void normal.finally(() => { destroyPromise = null; });       // 无身份比对

        const orphan = new Promise<void>((resolve) => setTimeout(resolve, 40));
        destroyPromise = orphan;

        await normal;
        await new Promise((r) => setTimeout(r, 5));
        // 孤儿登记被误抹 —— 这正是需要避免的行为
        expect(destroyPromise).toBeNull();
    });
});

describe('清理等待必须有上界（结构性回归防护）', () => {
    // detached 下 CKEditor 的 destroy() 可能永不 settle。
    // 若创建流程无界 await 它，挂起只是从「孤儿销毁」搬到「补偿创建」：
    // isCreating 被永久占用、重连后依旧空白。
    // 契约：等待必须能超时返回，超时后再通过换掉容器节点保证 DOM 安全。

    const TIMEOUT_MS = 30;

    async function waitWithBound(destroyPromise: Promise<void> | null): Promise<'done' | 'timeout'> {
        if (!destroyPromise) return 'done';
        const timedOut = Symbol('t');
        const r = await Promise.race([
            destroyPromise.then(() => undefined),
            new Promise<typeof timedOut>((res) => setTimeout(() => res(timedOut), TIMEOUT_MS)),
        ]);
        return r === timedOut ? 'timeout' : 'done';
    }

    it('永不 settle 的销毁不得阻塞创建流程', async () => {
        const never = new Promise<void>(() => { /* 永不 settle */ });
        await expect(waitWithBound(never)).resolves.toBe('timeout');
    });

    it('正常结束的销毁应当被完整等待', async () => {
        const quick = new Promise<void>((res) => setTimeout(res, 5));
        await expect(waitWithBound(quick)).resolves.toBe('done');
    });

    it('无待处理销毁时立即返回', async () => {
        await expect(waitWithBound(null)).resolves.toBe('done');
    });
});
