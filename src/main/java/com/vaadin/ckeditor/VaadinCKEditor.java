package com.vaadin.ckeditor;

import com.google.gson.Gson;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import elemental.json.JsonArray;
import elemental.json.JsonValue;
import elemental.json.impl.JreJsonFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag("vaadin-ckeditor")
@JsModule("./vaadin-ckeditor.js")
public class VaadinCKEditor extends Component {


    private List<Toolbar> toolbar = new ArrayList<>();

    public VaadinCKEditor() {
        getElement().setProperty("editorType", EditorType.CLASSIC.toString());
    }

    public VaadinCKEditor(EditorType editorType) {
        getElement().setProperty("editorType", editorType.toString());
    }

    private JsonArray toJson(List<Toolbar> toolbar) {
        String personsJson = new Gson().toJson(toolbar);
        return new JreJsonFactory().parse(personsJson);
    }

    public void initToolbar() {
        if(getToolBar()!=null) {
            getElement().setPropertyJson("toolBar", toJson(getToolBar()));
        }
    }

    public List<Toolbar> getToolBar() {
        return toolbar;
    }

    public void setToolBar(List<Toolbar> toolbar) {
        this.toolbar = toolbar;
    }

}
