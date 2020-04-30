package com.wontlost.ckeditor;

import java.util.function.Consumer;

public class VaadinCKEditorBuilder {

    public String editorData;
    public String width;
    public String height;
    public String margin;
    public EditorType editorType = EditorType.CLASSIC;
    public ThemeType theme;
    public Boolean isReadOnly = false;
    public Toolbar[] toolbar = VaadinCKEditor.TOOLBAR;

    public VaadinCKEditorBuilder with(Consumer<VaadinCKEditorBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public VaadinCKEditor createVaadinCKEditor() {
        return new VaadinCKEditor(editorType, toolbar, theme, editorData, width, height, margin, isReadOnly);
    }

}
