package com.wontlost.ckeditor;

import java.util.Locale;

/**
 * Type of editor, currently four types of editor are supported.
 * They are CLASSIC, BALLOON, INLINE, DECOUPLED.
 */
public enum EditorType {

    CLASSIC, BALLOON, INLINE, DECOUPLED;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }

}
