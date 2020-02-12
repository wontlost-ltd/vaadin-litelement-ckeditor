package com.vaadin.ckeditor;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

	public MainView() {
		super();
		VaadinCKEditor editor = new VaadinCKEditor();
		add(editor);
		add(new Label(""));
		Toolbar[] toolbar = new Toolbar[]{Toolbar.heading, Toolbar.pipe,
				Toolbar.bulletedList, Toolbar.outdent, Toolbar.insertTable, Toolbar.blockQuote};
		editor = new VaadinCKEditor(EditorType.BALLOON, toolbar);
		add(editor);
		add(new Label(""));
		add(new VaadinCKEditor(EditorType.INLINE, toolbar));
		add(new Label(""));
		add(new VaadinCKEditor(EditorType.DECOUPLED, toolbar));
		setAlignItems(Alignment.CENTER);
	}

}
