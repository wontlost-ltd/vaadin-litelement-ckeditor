package com.wontlost.ckeditor;

import java.util.function.BiConsumer;

public abstract class AutosaveAction implements BiConsumer<String, String>, IsAction{
    /**
     * Customise your own autosave action on ckeditor
     * @param editorId Editor id, there is a default id if not set.
     * @param editorData Data need to be saved.
     */
    public abstract void accept(String editorId, String editorData);

}
