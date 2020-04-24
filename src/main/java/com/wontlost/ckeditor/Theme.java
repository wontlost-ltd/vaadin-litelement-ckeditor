package com.wontlost.ckeditor;

import com.vaadin.flow.component.charts.model.style.Color;

/**
 * Theme class
 */
public class Theme {

    private final String background;
    private final String foreground;
    private final String border;
    private final String white;

    public Theme(){
        this("hsl(270, 1%, 29%)", "hsl(255, 3%, 18%)",
                 "hsl(300, 1%, 22%)", "hsl(0, 0%, 100%)");
    }

    public Theme(Color background, Color foreground, Color border, Color white) {
        this(background.toString(), foreground.toString(), border.toString(), white.toString());
    }

    public Theme(String background, String foreground, String border, String white) {
        this.background = "'--ck-custom-background' : '"+background+"',";
        this.foreground = "'--ck-custom-foreground' : '"+foreground+"',";
        this.border = "'--ck-custom-border' : '"+border+"',";
        this.white = "'--ck-custom-white' : '"+white+"'";
    }

    public String getStyles() {
        return "{"+background+foreground+border+white+"}";
    }

}
