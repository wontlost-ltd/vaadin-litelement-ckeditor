package com.wontlost.ckeditor;

import java.util.function.Consumer;

public class VaadinCKEditorBuilder {

    public String editorId;
    public String editorData;
    public String width;
    public String height;
    public String margin;
    public EditorType editorType = EditorType.CLASSIC;
    public ThemeType theme;
    public Boolean readOnly = false;
    public String placeHolder;
    public String uiLanguage;
    public String contentLanguage;
    public Toolbar[] toolbar = VaadinCKEditor.TOOLBAR;

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
        editor.setEditorToolBar(toolbar);
        editor.setReadOnly(readOnly);
        editor.setEditorType(editorType);
        editor.setUILanguage(uiLanguage);
        if(editorData == null) {
            editor.setPlaceHolder(placeHolder);
        }
        return editor;
    }

}
