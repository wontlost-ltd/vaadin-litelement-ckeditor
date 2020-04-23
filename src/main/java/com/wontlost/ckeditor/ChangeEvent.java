package com.wontlost.ckeditor;

import com.vaadin.flow.component.ComponentEvent;

public class ChangeEvent extends ComponentEvent<VaadinCKEditor> {

    /**
     * Creates a new event using the given source and indicator whether the
     * event originated from the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     */
    public ChangeEvent(VaadinCKEditor source, boolean fromClient) {
        super(source, fromClient);
    }


}
