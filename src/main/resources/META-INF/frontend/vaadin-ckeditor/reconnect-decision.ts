/**
 * 重挂载后是否需要重建编辑器的判定（纯函数，便于单测覆盖）。
 *
 * 背景：编辑器创建原先只挂在 Lit 的 `firstUpdated`（每个元素实例只调用一次），
 * 而 `disconnectedCallback` 会销毁编辑器，于是「移出 DOM 再放回」之后就再也不会
 * 重建，组件永久停在空白状态。Vaadin 中这类场景很常见：`remove()` 后 `add()`、
 * 在布局间移动组件、Tab/Accordion 切换、`@PreserveOnRefresh` 视图等。
 *
 * 判定被抽成纯函数，是因为真正难的部分不是「怎么创建」而是「什么时候该创建」——
 * 尤其销毁被延迟到 microtask 后，同一 tick 内 remove→add 的回调顺序是：
 *   disconnectedCallback → connectedCallback → destroy microtask
 * 此时 connectedCallback 看到的 editor 尚未被清空，必须等销毁真正完成后再补判一次。
 */

/** 判定所需的组件状态快照。 */
export interface ReconnectState {
    /** Lit 是否已完成首次渲染；false 时 shadow DOM 尚未就绪，创建交给 firstUpdated。 */
    hasUpdated: boolean;
    /** 当前是否已持有编辑器实例。 */
    hasEditor: boolean;
    /** 是否有创建流程正在进行。 */
    isCreating: boolean;
    /** 是否有销毁流程正在进行。 */
    isDestroying: boolean;
    /** 组件自身记录的断开标记。 */
    isDisconnected: boolean;
    /** DOM 层面的连接状态（Node.isConnected）。 */
    isConnected: boolean;
}

/**
 * 判断当前是否应当重建编辑器。
 *
 * 仅在以下条件全部满足时返回 true：
 * - 已完成首次渲染（否则容器元素还不存在，创建应由 firstUpdated 负责）；
 * - 当前没有编辑器实例（避免重复创建）；
 * - 没有创建/销毁流程在途（避免与之竞争）；
 * - 组件确实处于已连接状态（两个来源都要求为「已连接」）。
 */
export function shouldRecreateEditor(state: ReconnectState): boolean {
    if (!state.hasUpdated) {
        return false;
    }
    if (state.hasEditor || state.isCreating || state.isDestroying) {
        return false;
    }
    return !state.isDisconnected && state.isConnected;
}
