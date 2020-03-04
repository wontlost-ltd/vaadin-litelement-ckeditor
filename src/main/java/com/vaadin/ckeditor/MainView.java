package com.vaadin.ckeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@PWA(name = "CKEditor", shortName = "CK")
public class MainView extends VerticalLayout {

	public MainView() {
		super();
		VaadinCKEditor editor = new VaadinCKEditorBuilder().with(builder -> {
			builder.editorData = "<p>This is a test.</p>";
			builder.editorType = EditorType.CLASSIC;
		}).createVaadinCKEditor();
		add(editor);
		add(new Label(""));
		Button button = new Button("Print");
		Label label = new Label(editor.getEditorData());
		button.addClickListener(e->label.setText(editor.getEditorData()));
		add(button);
		add(new Label(""));
		add(label);
		add(new Label(""));
//		VaadinCKEditor editor1 = new VaadinCKEditorBuilder().with(builder->{
//			builder.editorType=EditorType.BALLOON;
//			builder.editorData="Balloon Editor test";
//		}).createVaadinCKEditor();
//		add(editor1);
//
//		add(new Label(""));
//
//		VaadinCKEditor editor2 = new VaadinCKEditorBuilder().with(builder->{
//			builder.editorType=EditorType.INLINE;
//			builder.editorData="Inline";
//		}).createVaadinCKEditor();
//		add(editor2);
//
//		add(new Label(""));
//
//		VaadinCKEditor editor3 = new VaadinCKEditorBuilder().with(builder->{
//			builder.editorType=EditorType.DECOUPLED;
//			builder.editorData="Dcoupled Editor";
//		}).createVaadinCKEditor();
//		add(editor3);


		setAlignItems(Alignment.CENTER);
	}

}
