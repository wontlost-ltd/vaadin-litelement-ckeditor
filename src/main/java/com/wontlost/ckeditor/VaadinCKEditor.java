package com.wontlost.ckeditor;

import com.google.gson.Gson;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.PropertyChangeEvent;
import com.vaadin.flow.dom.PropertyChangeListener;
import elemental.json.JsonArray;
import elemental.json.impl.JreJsonFactory;

import java.util.*;

/**
 * Used in @VaadinCKEditorBuilder.
 */
@Tag("vaadin-ckeditor")
@JsModule("./vaadin-ckeditor.js")
public class VaadinCKEditor extends CustomField<String> {

    private String editorData;

    public static final Toolbar[] TOOLBAR = new Toolbar[]{Toolbar.heading, Toolbar.pipe, Toolbar.fontSize, Toolbar.fontFamily,
           Toolbar.fontColor, Toolbar.fontBackgroundColor, Toolbar.pipe, Toolbar.bold, Toolbar.italic,
           Toolbar.underline, Toolbar.strikethrough, Toolbar.subscript, Toolbar.superscript, Toolbar.highlight,
           Toolbar.removeFormat, Toolbar.pipe, Toolbar.horizontalLine, Toolbar.pageBreak, Toolbar.link,
           Toolbar.bulletedList, Toolbar.numberedList, Toolbar.alignment, Toolbar.todoList, Toolbar.indent,
           Toolbar.outdent, Toolbar.code, Toolbar.codeBlock, Toolbar.pipe, Toolbar.specialCharacters, Toolbar.exportPdf,
           Toolbar.imageUpload, Toolbar.blockQuote, Toolbar.insertTable, Toolbar.mediaEmbed, Toolbar.undo, Toolbar.redo};

    /**
     * Constructor of VaadinCKEditor.
     * @param editorData Content of editor.
     */
    VaadinCKEditor(String editorData) {
        this.editorData = editorData;
        getElement().setProperty("editorData", editorData==null?"":editorData);
    }

    /**
     * @param toolbar Toolbar of Editor, refer to enum @Toolbar
     * @return JsonArray
     */
    private JsonArray toJson(Toolbar[] toolbar) {
        List<String> values = new ArrayList<>();
        if(toolbar == null || toolbar.length==0) {
            toolbar = VaadinCKEditor.TOOLBAR;
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

    public void setId(String id) {
        getElement().setProperty("editorId", id==null? "editor-"+Math.abs(new Random().nextInt()): id);
    }

    public Optional<String> getId() {
        return Optional.of(getElement().getProperty("editorId"));
    }

    /**
     * Get content of editor.
     * @return Data in editor text area.
     */
    public String getValue() {
        return editorData;
    }

    /**
     * Set value of editor.
     * Together with calling updateValue method to refresh editor content
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

    /**
     * Method calls client js funtion
     * @param content editor content
     */
    private void updateValue(String content) {
        if(getId().isPresent()) {
            getElement().executeJs("this.updateData($0, $1)", getId().get(), content);
        }
    }

    public void updateValue() {
        super.updateValue();
        updateValue(this.editorData);
    }

    public void doSetUpdate(String editorContent) {
        setValue(editorContent);
        updateValue();
    }

    /**
     * @param placeHolder Place holder of Editor
     */
    void setPlaceHolder(String placeHolder){
        getElement().setProperty("placeHolder", placeHolder==null?"Type the content here!":placeHolder);
    }

    /**
     * @param editorType  Type of Editor, refer to enum @EditorType.
     */
    void setEditorType(EditorType editorType) {
        getElement().setProperty("editorType", editorType.toString());
    }

    /**
     * @param editorToolBar   Toolbar of Editor, refer to enum @Toolbar.
     */
    void setEditorToolBar(Toolbar[] editorToolBar) {
        getElement().setPropertyJson("toolBar", toJson(editorToolBar));
    }

    /**
     * @param editorWidth   Width of editor, default value is 'auto'.
     */
    public void setWidth(String editorWidth) {
        getElement().setProperty("editorWidth", editorWidth==null?"auto":editorWidth);
    }

    /**
     * @param editorHeight  Height of editor, default value is 'auto'.
     */
    public void setHeight(String editorHeight) {
        getElement().setProperty("editorHeight", editorHeight==null?"auto":editorHeight);
    }

    /**
     * @param readOnly Make editor readonly
     */
    public void setReadOnly(boolean readOnly) {
        getElement().setProperty("isReadOnly", readOnly);
    }

    /**
     * @param theme  Theme of Editor, refer to enum @ThemeType.
     */
    void setEditorTheme(ThemeType theme) {
        getElement().setProperty("themeType", theme==null?ThemeType.LIGHT.toString():theme.toString());
    }

    /**
     * @param margin Margin of editor, default value is '20px'.
     */
    void setEditorMargin(String margin) {
        getElement().getStyle().set("margin", margin==null?"20px":margin);
    }

}
