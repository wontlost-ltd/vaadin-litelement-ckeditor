package com.vaadin.ckeditor;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

	public MainView() {
		super();
		add(new VaadinCkeditor());
		setAlignItems(Alignment.CENTER);
	}

}
