package com.wontlost.ckeditor.ogsi;

import com.vaadin.flow.server.VaadinServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletAsyncSupported;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import javax.servlet.Servlet;

/**
 * @author Ryan Pang (ryan.pang@wontlost.com)
 */
@Component(service = Servlet.class
        //, property = { //same as @VaadinMode
        //HttpWhiteboardConstants.HTTP_WHITEBOARD_SERVLET_INIT_PARAM_PREFIX +
        //InitParameters.SERVLET_PARAMETER_PRODUCTION_MODE + "=false"}
)
@VaadinMode
@HttpWhiteboardServletAsyncSupported
@HttpWhiteboardServletPattern("/*")
public class OGSiVaadinServlet extends VaadinServlet {

    @Override
    protected void servletInitialized() {
        getService().setClassLoader(getClass().getClassLoader());
    }

}
