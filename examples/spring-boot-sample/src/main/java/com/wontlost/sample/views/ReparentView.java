package com.wontlost.sample.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import com.wontlost.ckeditor.CKEditorPreset;
import com.wontlost.ckeditor.CKEditorType;
import com.wontlost.ckeditor.VaadinCKEditor;

/**
 * 编辑器「保持连接状态下被换父容器」的 E2E 夹具，路由 /reparent。
 *
 * <p>复现 issue #122 报告的触发场景：在<b>同一次服务端往返</b>中把编辑器
 * 从一个 {@link SplitLayout} 槽位移到另一个槽位。这会让前端走
 * {@code disconnectedCallback -> connectedCallback -> recreateEditorOnReconnect()}，
 * 而不是整页导航。</p>
 *
 * <p>能否触发缺陷取决于一个精确条件：{@code disconnectedCallback} 会立即把
 * {@code isDisconnected} 置为 true，但真正的销毁被推迟到 microtask；而
 * {@code connectedCallback} 会把该标志复位为 false。因此只有当 remove 与 add
 * 落在<b>同一 tick</b>（Vaadin 的 {@code add()} 正是把两步打包进同一批 DOM 变更）时，
 * microtask 里的 {@code destroyEditor()} 才会看到 {@code isDisconnected === false}，
 * 从而真正调用 {@code editor.destroy()} 并命中递归。若变更被拆成两批下发，
 * 销毁会走 isDisconnected 提前 return 分支而跳过，测试将失去判别力。</p>
 *
 * <p>当 {@code createEditorInstance()} 在创建后覆盖了 editor 实例的 {@code .id} 时，
 * 该 destroy 会退化成 {@code Context#destroy -> editor.destroy -> ...} 的无限递归，
 * 占满主线程并卡死整个标签页。修复后本页应能反复换父而始终保持响应。</p>
 *
 * <p>页面上的 {@code <span>} 用作断言探针：把服务端记录的换父次数与当前槽位
 * 投影到 DOM，使 Playwright 无需触碰内部实现即可确认换父确实发生过。</p>
 */
@Route("reparent")
public class ReparentView extends VerticalLayout {

    /** 初始内容，便于测试确认换父后编辑器仍然可用。 */
    private static final String INITIAL = "<p>Hello from Reparent</p>";

    public ReparentView() {
        add(new H2("Reparent"));

        VaadinCKEditor editor = VaadinCKEditor.create()
                .withPreset(CKEditorPreset.BASIC)
                .withType(CKEditorType.CLASSIC)
                .withValue(INITIAL)
                .build();
        editor.setId("ckeditor");

        // 两个槽位容器：编辑器在它们之间往返移动。
        VerticalLayout primarySlot = new VerticalLayout(editor);
        primarySlot.setId("slot-primary");
        VerticalLayout secondarySlot = new VerticalLayout();
        secondarySlot.setId("slot-secondary");

        SplitLayout split = new SplitLayout(primarySlot, secondarySlot);
        split.setId("split");
        split.setHeight("400px");

        // 服务端观测探针：换父次数与编辑器当前所在槽位。
        Span moveCount = new Span("0");
        moveCount.setId("move-count");
        Span currentSlot = new Span("primary");
        currentSlot.setId("current-slot");

        // 用数组持有计数，避免 lambda 捕获非 final 局部变量。
        final int[] moves = {0};

        Button move = new Button("Move editor", e -> {
            // 关键：add() 会先把组件从原父容器移除再挂到新父容器，
            // 且两步都在同一次往返里下发——正是 issue #122 描述的槽位重分配模式。
            boolean inPrimary = editor.getParent().orElse(null) == primarySlot;
            if (inPrimary) {
                secondarySlot.add(editor);
                currentSlot.setText("secondary");
            } else {
                primarySlot.add(editor);
                currentSlot.setText("primary");
            }
            moves[0]++;
            moveCount.setText(String.valueOf(moves[0]));
        });
        move.setId("btn-move");

        add(new HorizontalLayout(move), split, moveCount, currentSlot);
    }
}
