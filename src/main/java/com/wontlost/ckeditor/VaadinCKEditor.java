package com.wontlost.ckeditor;

import com.google.gson.Gson;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import elemental.json.JsonArray;
import elemental.json.impl.JreJsonFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used in @VaadinCKEditorBuilder.
 */
@Tag("vaadin-ckeditor")
@JsModule("./vaadin-ckeditor.js")
public class VaadinCKEditor extends CustomField<String> {

    private String editorData="";

    private final Toolbar[] toolbar = new Toolbar[]{Toolbar.heading, Toolbar.pipe, Toolbar.fontSize, Toolbar.fontFamily,
           Toolbar.fontColor, Toolbar.fontBackgroundColor, Toolbar.pipe, Toolbar.bold, Toolbar.italic,
           Toolbar.underline, Toolbar.strikethrough, Toolbar.subscript, Toolbar.superscript, Toolbar.highlight,
           Toolbar.removeFormat, Toolbar.pipe, Toolbar.horizontalLine, Toolbar.pageBreak, Toolbar.link,
           Toolbar.bulletedList, Toolbar.numberedList, Toolbar.alignment, Toolbar.todoList, Toolbar.indent,
           Toolbar.outdent, Toolbar.code, Toolbar.codeBlock, Toolbar.pipe, Toolbar.specialCharacters,
           Toolbar.imageUpload, Toolbar.blockQuote, Toolbar.insertTable, Toolbar.mediaEmbed, Toolbar.undo, Toolbar.redo};

    /**
     * Constructor of VaadinCKEditor.
     * @param editorType  Type of Editor, refer to enum @EditorType.
     * @param toolbar   Toolbar of Editor, refer to enum @Toolbar.
     * @param editorData Content of editor.
     * @param width   Width of editor, default value is 'auto'.
     * @param height  Height of editor, default value is 'auto'.
     * @param margin Margin of editor, default value is '20px'.
     * @param isReadOnly Make editor readonly
     */
    VaadinCKEditor(EditorType editorType, Toolbar[] toolbar, Theme theme, String editorData, String width, String height, String margin, Boolean isReadOnly) {
        getElement().setProperty("editorType", editorType.toString());
        getElement().setPropertyJson("toolBar", toJson(toolbar));
        getElement().setProperty("editorData", editorData==null?"":editorData);
        getElement().setProperty("editorWidth", width==null?"auto":width);
        getElement().setProperty("editorHeight", height==null?"auto":height);
        getElement().setProperty("isReadOnly", isReadOnly==null?false:isReadOnly);
        getElement().getStyle().set("margin", margin==null?"20px":margin);
        initTheme(theme);
    }

    private void initTheme(Theme theme) {
        if(theme ==null || Theme.Type.LIGHT.equals(theme.getThemeType())) {
            getElement().getStyle().set("--ck-custom-radius", "");
            getElement().getStyle().set("--ck-custom-background", "#fafafa");
            getElement().getStyle().set("--ck-custom-foreground", "#dedede");
            getElement().getStyle().set("--ck-custom-border", "#c4c4c4");
            getElement().getStyle().set("--ck-custom-white", "#fff");
            getElement().getStyle().set("--ck-custom-focus-border", "#1f89e5");
            getElement().getStyle().set("--ck-custom-color-text", "#333");
            getElement().getStyle().set("--ck-custom-shadow-drop", "rgba(0,0,0,0.15)");
            getElement().getStyle().set("--ck-custom-shadow-inner", "rgba(0,0,0,0.1)");
            getElement().getStyle().set("--ck-custom-default-hover-background", "transparent");
            getElement().getStyle().set("--ck-custom-default-active-background", "#e6e6e6");
            getElement().getStyle().set("--ck-custom-default-active-shadow", "#bfbfbf");
            getElement().getStyle().set("--ck-custom-on-hover-background", "#c4c4c4");
            getElement().getStyle().set("--ck-custom-on-active-background", "#bababa");
            getElement().getStyle().set("--ck-custom-on-active-shadow", "#a1a1a1");
            getElement().getStyle().set("--ck-custom-action-background", "#61b045");
            getElement().getStyle().set("--ck-custom-action-hover-background", "#579e3d");
            getElement().getStyle().set("--ck-custom-action-active-background", "#53973b");
            getElement().getStyle().set("--ck-custom-action-active-shadow", "#498433");
            getElement().getStyle().set("--ck-custom-action-disabled-background", "#7ec365");
            getElement().getStyle().set("--ck-custom-save", "#008a00");
            getElement().getStyle().set("--ck-custom-cancel", "#db3700");
            getElement().getStyle().set("--ck-custom-input-border", "#c7c7c7");
            getElement().getStyle().set("--ck-custom-input-text", "#333");
            getElement().getStyle().set("--ck-custom-input-disabled-background", "#f2f2f2");
            getElement().getStyle().set("--ck-custom-input-disabled-border", "#c7c7c7");
            getElement().getStyle().set("--ck-custom-input-disabled-text", "#5c5c5c");
            getElement().getStyle().set("--ck-custom-tooltip-background", "#333");
            getElement().getStyle().set("--ck-custom-tooltip-text", "#fff");
            getElement().getStyle().set("--ck-custom-image-caption-background", "#333");
            getElement().getStyle().set("--ck-custom-image-caption-text", "#fff");
            getElement().getStyle().set("--ck-custom-widget-blurred-border", "#dedede");
            getElement().getStyle().set("--ck-custom-widget-hover-border", "#ffc83d");
            getElement().getStyle().set("--ck-custom-link-default", "");
        } else {
            getElement().getStyle().set("--ck-custom-radius", theme.getRadius());
            getElement().getStyle().set("--ck-custom-background", theme.getBackground());
            getElement().getStyle().set("--ck-custom-foreground", theme.getForeground());
            getElement().getStyle().set("--ck-custom-border", theme.getBorder());
            getElement().getStyle().set("--ck-custom-white", theme.getWhite());
            getElement().getStyle().set("--ck-custom-focus-border", theme.getFocusBorder());
            getElement().getStyle().set("--ck-custom-color-text", theme.getColorText());
            getElement().getStyle().set("--ck-custom-shadow-drop", theme.getShadowDrop());
            getElement().getStyle().set("--ck-custom-shadow-inner", theme.getShadowInner());
            getElement().getStyle().set("--ck-custom-default-hover-background", theme.getDefaultHoverBackground());
            getElement().getStyle().set("--ck-custom-default-active-background", theme.getDefaultActiveBackground());
            getElement().getStyle().set("--ck-custom-default-active-shadow", theme.getDefaultActiveShadow());
            getElement().getStyle().set("--ck-custom-on-hover-background", theme.getOnHoverBackground());
            getElement().getStyle().set("--ck-custom-on-active-background", theme.getOnActiveBackground());
            getElement().getStyle().set("--ck-custom-on-active-shadow", theme.getOnActiveShadow());
            getElement().getStyle().set("--ck-custom-action-background", theme.getActionBackground());
            getElement().getStyle().set("--ck-custom-action-hover-background", theme.getActionHoverBackground());
            getElement().getStyle().set("--ck-custom-action-active-background", theme.getActionActiveBackground());
            getElement().getStyle().set("--ck-custom-action-active-shadow", theme.getActionActiveShadow());
            getElement().getStyle().set("--ck-custom-action-disabled-background", theme.getActionDisabledBackground());
            getElement().getStyle().set("--ck-custom-save", theme.getSave());
            getElement().getStyle().set("--ck-custom-cancel", theme.getCancel());
            getElement().getStyle().set("--ck-custom-input-border", theme.getInputBorder());
            getElement().getStyle().set("--ck-custom-input-text", theme.getInputText());
            getElement().getStyle().set("--ck-custom-input-disabled-background", theme.getInputDisabledBackground());
            getElement().getStyle().set("--ck-custom-input-disabled-border", theme.getInputDisabledBorder());
            getElement().getStyle().set("--ck-custom-input-disabled-text", theme.getInputDisabledText());
            getElement().getStyle().set("--ck-custom-tooltip-background", theme.getTooltipBackground());
            getElement().getStyle().set("--ck-custom-tooltip-text", theme.getTooltipText());
            getElement().getStyle().set("--ck-custom-image-caption-background", theme.getImageCaptionBackground());
            getElement().getStyle().set("--ck-custom-image-caption-text", theme.getImageCaptionText());
            getElement().getStyle().set("--ck-custom-widget-blurred-border", theme.getWidgetBlurredBorder());
            getElement().getStyle().set("--ck-custom-widget-hover-border", theme.getWidgetHoverBorder());
            getElement().getStyle().set("--ck-custom-link-default", theme.getLinkDefault());
        }
    }

    /**
     * @param toolbar Toolbar of Editor, refer to enum @Toolbar
     * @return JsonArray
     */
    private JsonArray toJson(Toolbar[] toolbar) {
        List<String> values = new ArrayList<>();
        if(toolbar == null || toolbar.length==0) {
            toolbar = this.toolbar;
        }
        Arrays.stream(toolbar).forEach(item -> values.add(item.getValue()));
        String toolbarJson = new Gson().toJson(values);
        return new JreJsonFactory().parse(toolbarJson);
    }

    protected String generateModelValue() {
        return editorData;
    }

    protected void setPresentationValue(String newPresentationValue) {
        this.editorData = newPresentationValue;
    }

    /**
     * Get content of editor.
     * @return Data in editor text area.
     */
    public String getValue() {
        return editorData;
    }

    /**
     * Set content of editor.
     * @param value  Data in editor text area.
     */
    public void setValue(String value) {
        String oldEditorData = this.editorData;
        this.editorData = value;
        fireEvent(new ComponentValueChangeEvent<>(this, this, oldEditorData,false));
    }

    @ClientCallable
    private void setEditorData(String editorData) {
        setValue(editorData);
    }

}
