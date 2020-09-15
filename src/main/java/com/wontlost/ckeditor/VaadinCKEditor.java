package com.wontlost.ckeditor;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wontlost.ckeditor.Constants.*;

/**
 * Used in @VaadinCKEditorBuilder.
 */
@Tag("vaadin-ckeditor")
@JsModule("./vaadin-ckeditor.js")
@JsModule("./translations/af.js")
@JsModule("./translations/ar.js")
@JsModule("./translations/ast.js")
@JsModule("./translations/az.js")
@JsModule("./translations/bg.js")
@JsModule("./translations/ca.js")
@JsModule("./translations/cs.js")
@JsModule("./translations/da.js")
@JsModule("./translations/de.js")
@JsModule("./translations/de-ch.js")
@JsModule("./translations/el.js")
@JsModule("./translations/en-au.js")
@JsModule("./translations/en-gb.js")
@JsModule("./translations/eo.js")
@JsModule("./translations/es.js")
@JsModule("./translations/et.js")
@JsModule("./translations/eu.js")
@JsModule("./translations/fa.js")
@JsModule("./translations/fi.js")
@JsModule("./translations/fr.js")
@JsModule("./translations/gl.js")
@JsModule("./translations/gu.js")
@JsModule("./translations/he.js")
@JsModule("./translations/hr.js")
@JsModule("./translations/hu.js")
@JsModule("./translations/id.js")
@JsModule("./translations/it.js")
@JsModule("./translations/ja.js")
@JsModule("./translations/km.js")
@JsModule("./translations/kn.js")
@JsModule("./translations/ko.js")
@JsModule("./translations/ku.js")
@JsModule("./translations/lt.js")
@JsModule("./translations/lv.js")
@JsModule("./translations/ms.js")
@JsModule("./translations/nb.js")
@JsModule("./translations/ne.js")
@JsModule("./translations/nl.js")
@JsModule("./translations/no.js")
@JsModule("./translations/oc.js")
@JsModule("./translations/pl.js")
@JsModule("./translations/pt.js")
@JsModule("./translations/pt-br.js")
@JsModule("./translations/ro.js")
@JsModule("./translations/ru.js")
@JsModule("./translations/si.js")
@JsModule("./translations/sk.js")
@JsModule("./translations/sl.js")
@JsModule("./translations/sq.js")
@JsModule("./translations/sr.js")
@JsModule("./translations/sr-latn.js")
@JsModule("./translations/sv.js")
@JsModule("./translations/th.js")
@JsModule("./translations/tk.js")
@JsModule("./translations/tr.js")
@JsModule("./translations/tt.js")
@JsModule("./translations/ug.js")
@JsModule("./translations/uk.js")
@JsModule("./translations/vi.js")
@JsModule("./translations/zh.js")
@JsModule("./translations/zh-cn.js")
@CssImport("./ckeditor.css")
public class VaadinCKEditor extends CustomField<String> implements HasConfig {

    private String editorData;

    private BiConsumer<String, String> autosaveAction;

    Logger vaddinCKEditorLog = Logger.getLogger(this.getClass().getName());

    /**
     * Constructor of VaadinCKEditor.
     * @param editorData Content of editor.
     */
    VaadinCKEditor(String editorData) {
        this.editorData = editorData;
        getElement().setProperty("editorData", editorData==null?"":editorData);
    }

    protected String generateModelValue() {
        return editorData;
    }

    protected void setPresentationValue(String newPresentationValue) {
        this.editorData = newPresentationValue;
    }

    public void setId(String id) {
        getElement().setProperty("editorId", id==null? "editor_"+Math.abs(new Random().nextInt()): id);
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
        super.setValue(value);
        this.editorData = value;
        getElement().setProperty("editorData", editorData==null?"":editorData);
        updateValue(value);
    }

    protected void setModelValue(String value, boolean fromClient){
        super.setModelValue(value, fromClient);
        String oldEditorData = this.editorData;
        this.editorData = value;
        fireEvent(new ComponentValueChangeEvent<>(this, this, oldEditorData,fromClient));
    }

    @ClientCallable
    private void setEditorData(String editorData) {
        setModelValue(editorData, true);
    }

    @ClientCallable
    private void saveEditorData(String editorId, String editorData) {
        Optional<BiConsumer<String, String>> save = getAutosaveAction();
        save.orElse((id, data)-> vaddinCKEditorLog.log(Level.SEVERE, "Invalid consumer provided. If you have " +
                "multiple editors in one page, you need to set autosave configurations for every editor."))
                .accept(editorId, editorData);
    }

    Optional<BiConsumer<String, String>> getAutosaveAction() {
        return Optional.ofNullable(autosaveAction);
    }

    /**
     * Should be used with method setAutosave. Be aware that if you have multiple editors within one page, you need to
     * set this for every editor. Otherwise the autosave function would not work as expected.
     * @param dataConsumer Autosave Action handler
     */
    void setAutosaveAction(AutosaveAction dataConsumer) {
        this.autosaveAction = dataConsumer;
    }

    /**
     * Method calls client js funtion
     * @param content editor content
     */
    private void updateValue(String content) {
        if(getId().isPresent()) {
            getElement().executeJs("this.updateData($0, $1)", getId().get(), content==null?"":content);
        }
    }

    /**
     * @param editorType  Type of Editor, refer to enum @EditorType.
     */
    void setEditorType(EditorType editorType) {
        getElement().setProperty("editorType", editorType.toString());
    }

    /**
     * @param editorWidth   Width of editor, default value is 'auto'.
     */
    public void setWidth(String editorWidth) {
        super.setWidth(editorWidth);
        getElement().setProperty("editorWidth", editorWidth==null?"auto":editorWidth);
    }

    /**
     * @param editorHeight  Height of editor, default value is 'auto'.
     */
    public void setHeight(String editorHeight) {
        super.setHeight(editorHeight);
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

    /**
     * @param autosave enable autosave function on editor, should be used with setSaveEditorData together.
     *                 Otherwise it'll be ignored.
     */
    void setAutosave(boolean autosave) {
        getElement().setProperty("autosave", autosave);
    }

    public void clear() {
        updateValue(null);
    }

    public void setConfig(Config config) {
        getElement().setPropertyJson("config", config==null?new Config().getConfigJson():config.getConfigJson());
    }

}
