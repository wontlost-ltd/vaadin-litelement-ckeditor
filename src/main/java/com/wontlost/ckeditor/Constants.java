package com.wontlost.ckeditor;

import org.jsoup.safety.Whitelist;

import java.util.Locale;

public class Constants {

    /**
     * Configuration types
     */
    public enum ConfigType {

        alignment,
        autosave,
        balloonToolbar,
        blockToolbar,
        ckfinder,
        cloudServices,
        codeBlock,
        exportPdf,
        exportWord,
        extraPlugins,
        fontBackgroundColor,
        fontColor,
        fontFamily,
        fontSize,
        heading,
        highlight,
        image,
        indentBlock,
        initialData,
        language,
        link,
        mediaEmbed,
        mention,
        placeholder,
        plugins,
        removePlugins,
        restrictedEditing,
        simpleUpload,
        table,
        title,
        htmlEmbed,
        toolbar,
        typing,
        wordCount,
        wproofreader,
        pagination

    }

    /**
     * Type of editor, currently four types of editor are supported.
     * They are CLASSIC, BALLOON, INLINE, DECOUPLED.
     */
    public enum EditorType {

        CLASSIC,
        BALLOON,
        INLINE,
        DECOUPLED;

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ENGLISH);
        }

    }

    /**
     * Language sets
     */
    public enum Language {

        af("af"),
        ar("ar"),
        ast("ast"),
        az("az"),
        bg("bg"),
        ca("ca"),
        cs("cs"),
        da("da"),
        de("de"),
        de_ch("de-ch"),
        el("el"),
        en_au("en-au"),
        en_gb("en-gb"),
        eo("eo"),
        es("es"),
        et("et"),
        eu("eu"),
        fa("fa"),
        fi("fi"),
        fr("fr"),
        gl("gl"),
        gu("gu"),
        hi("hi"),
        he("he"),
        hr("hr"),
        hu("hu"),
        id("id"),
        it("it"),
        ja("ja"),
        km("km"),
        kn("kn"),
        ko("ko"),
        ku("ku"),
        lt("lt"),
        lv("lv"),
        ms("ms"),
        nb("nb"),
        ne("ne"),
        nl("nl"),
        no("no"),
        oc("oc"),
        pl("pl"),
        pt("pt"),
        pt_br("pt-br"),
        ro("ro"),
        ru("ru"),
        si("si"),
        sk("sk"),
        sl("sl"),
        sq("sq"),
        sr("sr"),
        sr_latn("sr-latn"),
        sv("sv"),
        th("th"),
        tr("tr"),
        tt("tt"),
        ug("ug"),
        uk("uk"),
        vi("vi"),
        zh("zh"),
        zh_cn("zh-cn");

        private final String language;

        Language(String language) {
            this.language = language;
        }

        public String getLanguage(){
            return this.language;
        }

    }

    /**
     * Theme
     */
    public enum ThemeType {

        DARK,
        LIGHT;

        public String toString() {
            return name().toLowerCase(Locale.ENGLISH);
        }

    }

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
        selectAll("selectAll"),
        removeFormat("removeFormat"),
        horizontalLine("horizontalLine"),
        pageBreak("pageBreak"),
        specialCharacters("specialCharacters"),
        alignment("alignment"),
        codeBlock("codeBlock"),
        highlight("highlight"),
        htmlEmbed("htmlEmbed"),
        fontSize("fontSize"),
        fontFamily("fontFamily"),
        fontColor("fontColor"),
        fontBackgroundColor("fontBackgroundColor"),
        restrictedEditing("restrictedEditing"),
        restrictedEditingException("restrictedEditingException"),
        todoList("todoList"),
        exportPdf("exportPdf"),
        exportWord("exportWord"),
        insertTable("insertTable"),
        previousPage("previousPage"),
        nextPage("nextPage"),
        pageNavigation("pageNavigation");

        private final String value;

        Toolbar(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }

    }

    /**
     * All available plugins
     */
    public enum Plugins {

        Essentials,
        UploadAdapter,
        Autoformat,
        Autosave,
        Alignment,
        Bold,
        Italic,
        Underline,
        Strikethrough,
        Code,
        Subscript,
        Superscript,
        BlockQuote,
        CKFinder,
        Clipboard,
        CodeBlock,
        EasyImage,
        Base64UploadAdapter,
        Heading,
        Font,
        Highlight,
        HtmlEmbed,
        HorizontalLine,
        Image,
        ImageCaption,
        ImageStyle,
        ImageToolbar,
        ImageUpload,
        ImageResize,
        Indent,
        Link,
        List,
        Markdown,
        MediaEmbed,
        Mention,
        Paragraph,
        PasteFromOffice,
        PageBreak,
        PendingActions,
        RemoveFormat,
        RemoveFormatLinks,
        RestrictedEditingMode,
        SelectAll,
        StandardEditingMode,
        SpecialCharacters,
        SpecialCharactersEssentials,
        TodoList,
        Table,
        TableToolbar,
        TableProperties,
        TableCellProperties,
        TextTransformation,
        ExportPdf,
        ExportWord,
        WProofreader,
        WordCount,
        Pagination

    }

    /**
     * Sanitize type
     */
    public enum SanitizeType {

        none(Whitelist.none()),
        simpleText(Whitelist.simpleText()),
        basic(Whitelist.basic()),
        basicWithImages(Whitelist.basicWithImages()),
        relaxed(Whitelist.relaxed());

        private final Whitelist whitelist;

        SanitizeType(Whitelist value) {
            this.whitelist = value;
        }

        public Whitelist getValue(){
            return this.whitelist;
        }

    }

}
