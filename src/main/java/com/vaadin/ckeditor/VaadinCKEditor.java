package com.vaadin.ckeditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import java.lang.reflect.Type;

@Tag("vaadin-ckeditor")
@JsModule("./vaadin-ckeditor.js")
public class VaadinCKEditor extends Component {

    public VaadinCKEditor() {
        getElement().setProperty("editorType", EditorType.CLASSIC.name().toLowerCase());
    }

    public VaadinCKEditor(EditorType editorType) {
        getElement().setProperty("editorType", editorType.name().toLowerCase());
    }

}
