package com.ryanpang.ckeditor;

public enum EditorType {

    CLASSIC, BALLOON, INLINE, DECOUPLED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
