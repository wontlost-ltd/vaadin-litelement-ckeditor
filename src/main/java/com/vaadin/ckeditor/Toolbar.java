package com.vaadin.ckeditor;

public enum Toolbar {

    blockQuote("blockQuote"),
    bold("bold"),
    italic("italic"),
    ckfinder("ckfinder"),
    imageTextAlternative("imageTextAlternative"),
    imageUpload("imageUpload"),
    heading("heading"),
    imageStyleFull("imageStyle:full"),
    imageStyleSide("imageStyle:side"),
    indent("indent"),
    outdent("outdent"),
    link("link"),
    numberedList("numberedList"),
    bulletedList("bulletedList"),
    mediaEmbed("mediaEmbed"),
    undo("undo"),
    redo("redo"),
    insertTable("insertTable"),
    tableColumn("tableColumn"),
    tableRow("tableRow"),
    pipe("|"),
    mergeTableCells("mergeTableCells");

    private final String value;

    Toolbar(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
