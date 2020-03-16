package com.ryanpang.ckeditor;

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
    insertTable("insertTable");

    private final String value;

    Toolbar(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
