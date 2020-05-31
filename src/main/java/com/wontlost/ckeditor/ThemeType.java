package com.wontlost.ckeditor;

import java.util.Locale;

/**
 * Theme Type
 */
public enum ThemeType {

    DARK, LIGHT;

    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }

}
