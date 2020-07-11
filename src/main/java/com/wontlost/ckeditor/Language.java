package com.wontlost.ckeditor;

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
