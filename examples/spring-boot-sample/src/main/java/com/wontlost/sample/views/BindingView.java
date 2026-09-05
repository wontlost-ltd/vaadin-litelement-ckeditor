package com.wontlost.sample.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.wontlost.ckeditor.CKEditorPreset;
import com.wontlost.ckeditor.CKEditorType;
import com.wontlost.ckeditor.VaadinCKEditor;

/**
 * 值双向绑定与只读切换的 E2E 夹具，路由 /binding。
 *
 * <p>覆盖既有夹具未触及的两条核心链路：
 * <ul>
 *   <li><b>客户端 → 服务端</b>：在编辑器中输入后，Java 侧 {@code getValue()} 能读到新内容，
 *       且 {@code addValueChangeListener} 被触发（回归 issue #85：值与事件不一致）。</li>
 *   <li><b>服务端 → 客户端</b>：调用 {@code setValue()} / {@code setReadOnly()} 后，
 *       前端编辑器状态同步更新。</li>
 * </ul>
 *
 * <p>页面上的 {@code <span>} 用作断言探针——把服务端状态投影到 DOM，
 * 使 Playwright 无需依赖内部实现即可验证服务端确实收到了值。</p>
 */
@Route("binding")
public class BindingView extends VerticalLayout {

    /** 初始内容，供测试确认 setValue 生效前的基线。 */
    private static final String INITIAL = "<p>Hello from Binding</p>";

    public BindingView() {
        add(new H2("Binding"));

        VaadinCKEditor editor = VaadinCKEditor.create()
                .withPreset(CKEditorPreset.BASIC)
                .withType(CKEditorType.CLASSIC)
                .withValue(INITIAL)
                .build();
        editor.setId("ckeditor");

        // 服务端观测探针：值本身、值变更事件次数、事件是否来自客户端。
        Span serverValue = new Span(editor.getValue());
        serverValue.setId("server-value");
        Span changeCount = new Span("0");
        changeCount.setId("change-count");
        Span lastFromClient = new Span("-");
        lastFromClient.setId("last-from-client");

        // 用数组持有计数，避免 lambda 捕获非 final 局部变量。
        final int[] changes = {0};
        editor.addValueChangeListener(event -> {
            changes[0]++;
            changeCount.setText(String.valueOf(changes[0]));
            lastFromClient.setText(String.valueOf(event.isFromClient()));
            // 读取组件当前值而非 event.getValue()，以验证两者一致（issue #85 的回归点）。
            serverValue.setText(editor.getValue());
        });

        Button setValue = new Button("Set server value", e ->
                editor.setValue("<p>Set from server</p>"));
        setValue.setId("btn-set-value");

        Button toggleReadOnly = new Button("Toggle read-only", e ->
                editor.setReadOnly(!editor.isReadOnly()));
        toggleReadOnly.setId("btn-toggle-readonly");

        Button clearValue = new Button("Clear", e -> editor.setValue(null));
        clearValue.setId("btn-clear");

        add(editor, new HorizontalLayout(setValue, toggleReadOnly, clearValue),
                serverValue, changeCount, lastFromClient);
    }
}
