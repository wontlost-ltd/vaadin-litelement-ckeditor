package com.wontlost.ckeditor;

import com.wontlost.ckeditor.Constants.EditorType;
import com.wontlost.ckeditor.Constants.ThemeType;

import java.util.function.Consumer;

public class VaadinCKEditorBuilder {

    public String editorId;
    public String editorData;

    public String overrideCssUrl;
    public String width;
    public String height;
    public String margin;
    public Config config;
    public ThemeType theme;
    public Boolean readOnly = false;
    public Boolean autosave = false;
    public Integer waitingTime = 2000;
    public Boolean sync = true;
    public Boolean ghsEnabled = false;
    public Boolean hideToolbar = false;
    public Boolean minimap = false;

    public EditorType editorType = EditorType.CLASSIC;

    public VaadinCKEditorBuilder with(Consumer<VaadinCKEditorBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public VaadinCKEditor createVaadinCKEditor() {
        VaadinCKEditor editor = new VaadinCKEditor(editorData);
        editor.setId(editorId);
        editor.setWidth(width);
        editor.setHeight(height);
        editor.setEditorMargin(margin);
        editor.setEditorTheme(theme);
        editor.setConfig(config);
        editor.setReadOnly(readOnly);
        editor.setAutosave(autosave, waitingTime);
        editor.setHideToolbar(hideToolbar);
        editor.setEditorType(editorType);
        editor.enableGHS(ghsEnabled);
        editor.isSynchronizd(sync);
        editor.setOverrideCssUrl(overrideCssUrl);
        if(editorType.equals(EditorType.DECOUPLED)) {
            editor.minimap(minimap);
        }
        return editor;
    }

}
