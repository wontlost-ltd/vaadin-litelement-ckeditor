package com.wontlost.ckeditor;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.theme.AbstractTheme;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Theme class
 */
@JsModule("@vaadin/vaadin-lumo-styles/color.js")
public class CKEditorTheme implements AbstractTheme {

    public String getBaseUrl() {
        return "/src/";
    }


    public String getThemeUrl() {
        return "/theme/";
    }

    public List<String> getHeaderInlineContents() {
        return Collections.singletonList("<custom-style>\n"
                + "<style include=\"lumo-color lumo-typography\">"
                + "</style>\n"
                + "</custom-style>");
    }

    public Map<String, String> getHtmlAttributes(String variant) {
        if ("dark".equals(variant)) {
            // the <body> element will have the "theme"
            // attribute set to "dark" when the dark variant
            // is used
            return Collections.singletonMap("theme", "dark");
        }
        return Collections.emptyMap();
    }

}
