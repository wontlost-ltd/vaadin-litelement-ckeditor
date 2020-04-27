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
            getElement().getStyle().set("--ck-custom-background", "");
            getElement().getStyle().set("--ck-custom-foreground", "");
            getElement().getStyle().set("--ck-custom-border", "");
            getElement().getStyle().set("--ck-custom-white", "");
            getElement().getStyle().set("--ck-custom-focus-border", "");
            getElement().getStyle().set("--ck-custom-color-text", "");
            getElement().getStyle().set("--ck-custom-shadow-drop", "");
            getElement().getStyle().set("--ck-custom-shadow-inner", "");
            getElement().getStyle().set("--ck-custom-default-hover-background", "");
            getElement().getStyle().set("--ck-custom-default-active-background", "");
            getElement().getStyle().set("--ck-custom-default-active-shadow", "");
            getElement().getStyle().set("--ck-custom-on-hover-background", "");
            getElement().getStyle().set("--ck-custom-on-active-background", "");
            getElement().getStyle().set("--ck-custom-on-active-shadow", "");
            getElement().getStyle().set("--ck-custom-action-background", "");
            getElement().getStyle().set("--ck-custom-action-hover-background", "");
            getElement().getStyle().set("--ck-custom-action-active-background", "");
            getElement().getStyle().set("--ck-custom-action-active-shadow", "");
            getElement().getStyle().set("--ck-custom-action-disabled-background", "");
            getElement().getStyle().set("--ck-custom-save", "");
            getElement().getStyle().set("--ck-custom-cancel", "");
            getElement().getStyle().set("--ck-custom-input-border", "");
            getElement().getStyle().set("--ck-custom-input-text", "");
            getElement().getStyle().set("--ck-custom-input-disabled-background", "");
            getElement().getStyle().set("--ck-custom-input-disabled-border", "");
            getElement().getStyle().set("--ck-custom-input-disabled-text", "");
            getElement().getStyle().set("--ck-custom-tooltip-background", "");
            getElement().getStyle().set("--ck-custom-tooltip-text", "");
            getElement().getStyle().set("--ck-custom-image-caption-background", "");
            getElement().getStyle().set("--ck-custom-image-caption-text", "");
            getElement().getStyle().set("--ck-custom-widget-blurred-border", "");
            getElement().getStyle().set("--ck-custom-widget-hover-border", "");
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
