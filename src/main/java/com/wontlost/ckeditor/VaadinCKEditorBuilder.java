package com.wontlost.ckeditor;

import java.util.function.Consumer;

public class VaadinCKEditorBuilder {

    public String editorData;
    public String width;
    public String height;
    public EditorType editorType = EditorType.CLASSIC;
    public Theme theme = new Theme();
    public Boolean isReadOnly = false;
    public Toolbar[] toolbar = new Toolbar[]{Toolbar.heading, Toolbar.pipe, Toolbar.bold, Toolbar.italic,
           Toolbar.underline, Toolbar.strikethrough, Toolbar.subscript, Toolbar.superscript, Toolbar.highlight,
           Toolbar.removeFormat, Toolbar.pipe, Toolbar.horizontalLine, Toolbar.pageBreak, Toolbar.link,
           Toolbar.bulletedList, Toolbar.numberedList, Toolbar.alignment, Toolbar.todoList, Toolbar.indent,
           Toolbar.outdent, Toolbar.code, Toolbar.codeBlock, Toolbar.pipe, Toolbar.specialCharacters,
           Toolbar.imageUpload, Toolbar.blockQuote, Toolbar.insertTable, Toolbar.mediaEmbed, Toolbar.undo, Toolbar.redo};

    public VaadinCKEditorBuilder with(Consumer<VaadinCKEditorBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public VaadinCKEditor createVaadinCKEditor() {
        return new VaadinCKEditor(editorType, toolbar, theme, editorData, width, height, isReadOnly);
    }

}
