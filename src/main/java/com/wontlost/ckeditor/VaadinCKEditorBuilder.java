package com.wontlost.ckeditor;

import java.util.function.Consumer;

import com.wontlost.ckeditor.Constants.*;

public class VaadinCKEditorBuilder {

    public String editorId;
    public String editorData;
    public String width;
    public String height;
    public String margin;
    public Config config;
    public ThemeType theme;
    public Boolean readOnly = false;
//    public Boolean autosave = false;
    public AutosaveAction autosaveAction;
    public EditorType editorType = EditorType.CLASSIC;

    public VaadinCKEditorBuilder with(Consumer<VaadinCKEditorBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public VaadinCKEditor createVaadinCKEditor() {
        VaadinCKEditor editor = new VaadinCKEditor(editorData);
        editor.setId(editorId);
        editor.setWidth(width);
        editor.setHeight(height);
        editor.setEditorMargin(margin);
        editor.setEditorTheme(theme);
        editor.setConfig(config);
        editor.setReadOnly(readOnly);
//        editor.setAutosave(autosave);
        editor.setAutosaveAction(autosaveAction);
        editor.setEditorType(editorType);
        return editor;
    }

}
