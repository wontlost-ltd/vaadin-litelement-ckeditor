package com.wontlost.ckeditor;

import java.util.function.Consumer;

public abstract class AutosaveAction implements Consumer<String>, IsAction{
    /**
     * Customise your own autosave action on ckeditor
     * @param editorData Data need to be saved.
     */
    public abstract void accept(String editorData);

}
