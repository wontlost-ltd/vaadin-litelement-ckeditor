package com.vaadin.ckeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

	public MainView() {
		super();
		VaadinCKEditor editor = new VaadinCKEditor("<p style=\"background:green;\">This is a test.</p>");
		add(editor);
		add(new Label(""));
		Button button = new Button("Print");
		Label label = new Label(editor.getEditorData());
		button.addClickListener(e->label.setText(editor.getEditorData()));
		add(button);
		add(new Label(""));
		add(label);

		setAlignItems(Alignment.CENTER);
	}

}
