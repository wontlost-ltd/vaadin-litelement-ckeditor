package com.vaadin.ckeditor;

import java.util.function.Consumer;

public class VaadinCKEditorBuilder {

    public String editorData;
    public String width;
    public String height;
    public EditorType editorType = EditorType.CLASSIC;
    public Toolbar[] toolbar = new Toolbar[]{Toolbar.heading, Toolbar.pipe, Toolbar.bold,
            Toolbar.italic, Toolbar.link, Toolbar.bulletedList, Toolbar.numberedList, Toolbar.pipe, Toolbar.indent,
            Toolbar.outdent, Toolbar.pipe, Toolbar.imageUpload, Toolbar.blockQuote, Toolbar.insertTable,
            Toolbar.mediaEmbed, Toolbar.undo, Toolbar.redo};

    public VaadinCKEditorBuilder with(Consumer<VaadinCKEditorBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public VaadinCKEditor createVaadinCKEditor() {
        return new VaadinCKEditor(editorType, toolbar, editorData, width, height);
    }

}
