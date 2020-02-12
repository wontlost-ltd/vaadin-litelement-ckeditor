package com.vaadin.ckeditor;

import com.google.gson.Gson;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import elemental.json.JsonArray;
import elemental.json.impl.JreJsonFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag("vaadin-ckeditor")
@JsModule("./vaadin-ckeditor.js")
public class VaadinCKEditor extends Component {


    private final static Toolbar[] toolbar = new Toolbar[]{Toolbar.heading, Toolbar.pipe, Toolbar.bold,
            Toolbar.italic, Toolbar.link, Toolbar.bulletedList, Toolbar.numberedList, Toolbar.pipe, Toolbar.indent,
            Toolbar.outdent, Toolbar.pipe, Toolbar.imageUpload, Toolbar.blockQuote, Toolbar.insertTable,
            Toolbar.mediaEmbed, Toolbar.undo, Toolbar.redo};

    public VaadinCKEditor() {
        this(EditorType.CLASSIC, toolbar);
    }

    public VaadinCKEditor(EditorType editorType) {
        this(editorType, toolbar);
    }

    public VaadinCKEditor(EditorType editorType, Toolbar[] toolbar) {
        getElement().setProperty("editorType", editorType.toString());
        getElement().setPropertyJson("toolBar", toJson(toolbar));
    }

    private JsonArray toJson(Toolbar[] toolbar) {
        List<String> values = new ArrayList<>();
        if(toolbar == null || toolbar.length==0) {
            toolbar = VaadinCKEditor.toolbar;
        }
        Arrays.stream(toolbar).forEach(item -> values.add(item.getValue()));
        String toolbarJson = new Gson().toJson(values);
        return new JreJsonFactory().parse(toolbarJson);
    }

}
