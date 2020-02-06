package com.vaadin.ckeditor;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

	public MainView() {
		super();
		add(new VaadinCKEditor());
		add(new Label(""));
		add(new VaadinCKEditor(EditorType.BALLOON));
		add(new Label(""));
		add(new VaadinCKEditor(EditorType.INLINE));
		add(new Label(""));
		add(new VaadinCKEditor(EditorType.DECOUPLED));
		setAlignItems(Alignment.CENTER);
	}

}
