package com.wontlost.ckeditor;

/**
 * Toolbar that applied to editor. Items like headin, pipe, blockQuote and etc can be used.
 * Editor can have one or more toolbar items.
 */
public enum Toolbar {

    heading("heading"),
    pipe("|"),
    blockQuote("blockQuote"),
    bold("bold"),
    italic("italic"),
    ckfinder("ckfinder"),
    imageUpload("imageUpload"),
    indent("indent"),
    outdent("outdent"),
    link("link"),
    numberedList("numberedList"),
    bulletedList("bulletedList"),
    mediaEmbed("mediaEmbed"),
    undo("undo"),
    redo("redo"),
    underline("underline"),
    strikethrough("strikethrough"),
    code("code"),
    subscript("subscript"),
    superscript("superscript"),
    removeFormat("removeFormat"),
    horizontalLine("horizontalLine"),
    pageBreak("pageBreak"),
    specialCharacters("specialCharacters"),
    alignment("alignment"),
    codeBlock("codeBlock"),
    highlight("highlight"),
    fontSize("fontSize"),
    fontFamily("fontFamily"),
    fontColor("fontColor"),
    fontBackgroundColor("fontBackgroundColor"),
    restrictedEditingException("restrictedEditingException"),
    todoList("todoList"),
    exportPdf("exportPdf"),
    exportWord("exportWord"),
    insertTable("insertTable");

    private final String value;

    Toolbar(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
