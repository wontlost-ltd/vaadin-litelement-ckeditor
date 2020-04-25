package com.wontlost.ckeditor;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.theme.AbstractTheme;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Theme class
 */
@JsModule("./theme/vaadin-ckeditor.js")
public class CKEditorTheme implements AbstractTheme {

    public static final String LIGHT = "light";
    public static final String DARK = "dark";

    public String getBaseUrl() {
        return "/src/";
    }


    public String getThemeUrl() {
        return "/theme/";
    }

    public List<String> getHeaderInlineContents() {
        return Collections.singletonList("<custom-style>\n"
                + "<style include=\"ckeditor-color ckeditor-typography\">"
                + "</style>\n"
                + "</custom-style>");
    }

    public Map<String, String> getHtmlAttributes(String variant) {
        switch (variant) {
            case LIGHT:
                return Collections.singletonMap("theme", LIGHT);
            case DARK:
                return Collections.singletonMap("theme", DARK);
            default:
                if (!variant.isEmpty()) {
                    LoggerFactory.getLogger(getClass().getName()).warn(
                            "Material theme variant not recognized: '{}'. Using no variant.",
                            variant);
                }
                return Collections.emptyMap();
        }
    }

}
