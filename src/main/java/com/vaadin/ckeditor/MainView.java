package com.vaadin.ckeditor;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

	public MainView() {
		super();
		List<Toolbar> toolbar = new ArrayList<>();
		toolbar.add(Toolbar.blockQuote);
		toolbar.add(Toolbar.bold);
		VaadinCKEditor editor = new VaadinCKEditor();
		editor.setToolBar(toolbar);
		editor.initToolbar();
		add(editor);
		add(new Label(""));
		toolbar = new ArrayList<>();
		toolbar.add(Toolbar.heading);
		toolbar.add(Toolbar.imageUpload);
		editor = new VaadinCKEditor(EditorType.BALLOON);
		editor.setToolBar(toolbar);
		editor.initToolbar();
		add(editor);
//		add(new Label(""));
//		add(new VaadinCKEditor(EditorType.INLINE));
//		add(new Label(""));
//		add(new VaadinCKEditor(EditorType.DECOUPLED));
		setAlignItems(Alignment.CENTER);
	}

}
