package com.wontlost.ckeditor;

import org.jsoup.safety.Safelist;

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
        licenseKey,
        pagination,
        ui,
        wproofreader

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
        uz("uz"),
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
     * Language sets
     */
    public enum TextPartLanguage {

        af("Afrikaans", "af"),
        ar("Arabic","ar"),
        ast("Galician","ast"),
        az("Azerbaijani","az"),
        bg("Bulgarian","bg"),
        ca("Catalan","ca"),
        cs("Czech","cs"),
        da("Danis","da"),
        de("German","de"),
        de_ch("German Dutch","de-ch"),
        el("Greek","el"),
        en_au("English Australia","en-au"),
        en_gb("English Britain", "en-gb"),
        eo("Esperanto", "eo"),
        es("Spanish", "es"),
        et("Estonian", "et"),
        eu("Basque", "eu"),
        fa("Persian", "fa"),
        fi("Finnish", "fi"),
        fr("French", "fr"),
        gl("Galician", "gl"),
        gu("Gujarati", "gu"),
        hi("Hindi", "hi"),
        he("Hebrew", "he"),
        hr("Croatian", "hr"),
        hu("Hungarian","hu"),
        id("Indonesian", "id"),
        it("Italian", "it"),
        ja("Japanese", "ja"),
        km("Cambodian", "km"),
        kn("Kannada", "kn"),
        ko("Korean","ko"),
        ku("Kurdish", "ku"),
        lt("Lithuanian", "lt"),
        lv("Latvian", "lv"),
        ms("Malay", "ms"),
        nb("Norwegian","nb"),
        ne("Nepali", "ne"),
        nl("Dutch", "nl"),
        no("Danish", "no"),
        oc("Occitan", "oc"),
        pl("Polish", "pl"),
        pt("Portuguese", "pt"),
        pt_br("Portuguese BR", "pt-br"),
        ro("Romanian", "ro"),
        ru("Russian", "ru"),
        si("Singhalese", "si"),
        sk("Slovak", "sk"),
        sl("Slovenian", "sl"),
        sq("Albanian","sq"),
        sr("Serbian", "sr"),
        sr_latn("Latin", "sr-latn"),
        sv("Swedish", "sv"),
        th("Thai", "th"),
        tr("Turkish", "tr"),
        tt("Tatar", "tt"),
        ug("Uigur", "ug"),
        uk("Ukrainian", "uk"),
        uz("Uzbek","uz"),
        vi("Vietnamese", "vi"),
        zh("Chinese Traditional", "zh"),
        zh_cn("Chinese Simple", "zh-cn");

        private final String title;

        private final String language;

        TextPartLanguage(String title, String language) {
            this.title = title;
            this.language = language;
        }

        public String getTitle() {
            return this.title;
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

        @Override
        public String toString() {
            return name().toLowerCase(Locale.ENGLISH);
        }

    }

    /**
     * Toolbar that applied to editor. Items like headin, pipe, blockQuote and etc can be used.
     * Editor can have one or more toolbar items.
     */
    public enum Toolbar {

        textPartLanguage("textPartLanguage"),
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
        linkImage("linkImage"),
        sourceEditing("sourceEditing"),
        numberedList("numberedList"),
        bulletedList("bulletedList"),
        mediaEmbed("mediaEmbed"),
        insertImage("insertImage"),
        findAndReplace("findAndReplace"),
        previousPage("previousPage"),
        nextPage("nextPage"),
        pageNavigation("pageNavigation"),
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
        insertTable("insertTable");

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
        FindAndReplace,
        Pagination,
        Minimap,
        ImageInline,
        ImageBlock,
        LinkImage,
        ImageInsert,
        AutoImage,
        GeneralHtmlSupport,
        HtmlComment,
        SourceEditing,
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
        WordCount

    }

    /**
     * Sanitize type
     */
    public enum SanitizeType {

        none(Safelist.none()),
        simpleText(Safelist.simpleText()),
        basic(Safelist.basic()),
        basicWithImages(Safelist.basicWithImages()),
        relaxed(Safelist.relaxed());

        private final Safelist whitelist;

        SanitizeType(Safelist value) {
            this.whitelist = value;
        }

        public Safelist getValue(){
            return this.whitelist;
        }

    }

}
