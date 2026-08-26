import { describe, it, expect, beforeEach, afterEach } from 'vitest';

/**
 * 陈旧容器隔离的**生产路径**回归测试。
 *
 * 背景：孤儿销毁超时后放弃等待，但 destroy() 仍可能在后台完成。CKEditor 的
 * ElementApiMixin.updateSourceElement() 末尾会执行
 * `setDataInElement(this.sourceElement, ...)`，其实现是 `el.innerHTML = data`
 * （见 @ckeditor/ckeditor5-utils）。这里的 sourceElement 就是传给 Editor.create()
 * 的那个容器 —— 因此仅仅清空子节点是不够的，必须换掉容器**对象本身**，
 * 否则迟到的写回会把新编辑器的内容整片抹掉。
 *
 * 注意：本用例直接调用组件上的私有方法 detachStaleEditorContainer()，
 * 而不是复刻一份同构逻辑；先前的版本只建模了超时竞态、没有触达生产代码，
 * 因此漏掉了「sourceElement 未被替换」这个真实缺陷（review 第七轮指出）。
 */

const EDITOR_ID = 'ed-isolation-1';

// jsdom 不实现 CSS.escape，而生产代码用它转义 id 选择器。
// 浏览器原生提供该 API，这里补一个足够本用例使用的最小实现。
if (typeof (globalThis as { CSS?: unknown }).CSS === 'undefined') {
    (globalThis as { CSS?: unknown }).CSS = {
        escape: (value: string) => value.replace(/[^a-zA-Z0-9_-]/g, (c) => `\\${c}`),
    };
}

/** 复刻 CKEditor 的写回行为：对持有的 sourceElement 赋 innerHTML。 */
function simulateStaleWriteback(sourceElement: Element, data = ''): void {
    sourceElement.innerHTML = data;
}

describe('detachStaleEditorContainer（生产路径）', () => {
    let host: HTMLElement;

    beforeEach(() => {
        host = document.createElement('div');
        document.body.appendChild(host);
    });

    afterEach(() => {
        host.remove();
    });

    /**
     * 以最小依赖驱动生产方法：构造一个带 editorId / querySelector / requestUpdate
     * 的宿主，再把组件原型上的私有方法绑上去调用。
     */
    async function callDetach(hostEl: HTMLElement, editorId: string): Promise<{ updateRequested: boolean }> {
        const mod = await import('./vaadin-ckeditor');
        const ctor = (mod as Record<string, unknown>).VaadinCKEditor as { prototype: Record<string, unknown> };
        const fn = ctor.prototype['detachStaleEditorContainer'] as (this: unknown) => void;
        expect(typeof fn).toBe('function');

        let updateRequested = false;
        const ctx = {
            editorId,
            querySelector: (sel: string) => hostEl.querySelector(sel),
            requestUpdate: () => { updateRequested = true; },
        };
        fn.call(ctx);
        return { updateRequested };
    }

    it('容器对象被替换，迟到的 innerHTML 写回不影响页面上的新容器', async () => {
        const stale = document.createElement('div');
        stale.id = EDITOR_ID;
        stale.className = 'editor-content ck ck-editor__editable';   // 含 CKEditor 注入的运行时 class
        stale.innerHTML = '<div class="ck-editor">old editor</div>';
        host.appendChild(stale);

        await callDetach(host, EDITOR_ID);

        const live = host.querySelector(`#${EDITOR_ID}`) as HTMLElement;
        expect(live).not.toBeNull();
        // 关键断言：页面上的容器已不是旧对象
        expect(live).not.toBe(stale);
        expect(live.innerHTML).toBe('');
        // 运行时 class 不得被继承，只保留模板声明的 class
        expect(live.className).toBe('editor-content');

        // 模拟新编辑器已在新容器内构建
        live.innerHTML = '<div class="ck-editor">new editor</div>';

        // 迟到的销毁写回 —— 它持有的是被摘下的旧对象
        simulateStaleWriteback(stale, '');

        expect(live.innerHTML).toContain('new editor');
        expect(host.querySelector(`#${EDITOR_ID}`)).toBe(live);
    }, 30000);

    it('替换后请求 Lit 重渲染，避免陈旧节点被放回', async () => {
        const stale = document.createElement('div');
        stale.id = EDITOR_ID;
        stale.className = 'editor-content';
        host.appendChild(stale);

        const { updateRequested } = await callDetach(host, EDITOR_ID);
        expect(updateRequested).toBe(true);
    });

    it('容器不存在时安全返回，不抛异常', async () => {
        await expect(callDetach(host, 'no-such-id')).resolves.toBeDefined();
    });

    it('替换后 Lit 重新绑定新节点，动态更新不会落到游离节点', async () => {
        // review 第八轮：仅 requestUpdate() 不足以让 Lit 重新扫描被外部 replaceChild
        // 的节点 —— 其模板实例在首次克隆时就绑定了 AttributePart。
        // 生产实现因此先 render(nothing, renderRoot) 丢弃旧模板实例。
        // 该路径可达：setId() 是公开 API，editorId 可在运行期变化。
        const { LitElement, html } = await import('lit');
        const mod = await import('./vaadin-ckeditor');
        const ctor = (mod as Record<string, unknown>).VaadinCKEditor as { prototype: Record<string, unknown> };
        const detach = ctor.prototype['detachStaleEditorContainer'] as (this: unknown) => void;

        // 用一个最小 LitElement 复刻组件的渲染结构与 light DOM 约定
        class Host extends LitElement {
            static properties = { editorId: { type: String } };
            editorId = 'lit-rebind-1';
            createRenderRoot() { return this; }
            render() { return html`<div id="${this.editorId}" class="editor-content"></div>`; }
        }
        const tag = 'lit-rebind-host';
        if (!customElements.get(tag)) customElements.define(tag, Host);

        const el = new Host();
        document.body.appendChild(el);
        await el.updateComplete;

        const stale = el.querySelector('#lit-rebind-1') as HTMLElement;
        detach.call(el);                 // 调用生产方法
        await el.updateComplete;

        // 触发一次动态更新：必须落到页面上的新节点，而非游离的旧节点
        el.editorId = 'lit-rebind-2';
        await el.updateComplete;

        const live = el.querySelector('div') as HTMLElement;
        expect(live).not.toBe(stale);
        expect(live.id).toBe('lit-rebind-2');
        expect(stale.id).toBe('lit-rebind-1');   // 游离节点不再被 Lit 更新

        el.remove();
    }, 30000);
});
