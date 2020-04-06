package com.wontlost.ckeditor;

import com.vaadin.flow.component.charts.model.style.Color;

/**
 * Theme class
 */
public class Theme {

    private String background;
    private String foreground;
    private String border;
    private String white;

    public Theme(){
        this("hsl(270, 1%, 29%)", "hsl(255, 3%, 18%)",
                 "hsl(300, 1%, 22%)", "hsl(0, 0%, 100%)");
    }

    public Theme(Color background, Color foreground, Color border, Color white) {
        this(background.toString(), foreground.toString(), border.toString(), white.toString());
    }

    public Theme(String background, String foreground, String border, String white) {
        this.background = "css`" + background + "`";
        this.foreground = "css`" + foreground + "`";
        this.border = "css`" + border + "`";
        this.white = "css`" + white + "`";
    }

    public String getBackground() {
        return background;
    }

    public String getForeground() {
        return foreground;
    }

    public String getBorder() {
        return border;
    }

    public String getWhite() {
        return white;
    }

}
