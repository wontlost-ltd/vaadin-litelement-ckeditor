package com.wontlost.ckeditor;

public class Theme {

    public enum Type {
        DARK, LIGHT
    }

    private Type themeType;
    private String radius = "2px";
    private String background = "hsl(270, 1%, 29%)";
    private String foreground = "hsl(255, 3%, 18%)";
    private String border = "hsl(300, 1%, 22%)";
    private String white = "hsl(0, 0%, 100%)";
    private String focusBorder = "hsl(208, 90%, 62%)";
    private String colorText = "hsl(0, 0%, 98%)";
    private String shadowDrop = "hsla(0, 0%, 0%, 0.2)";
    private String shadowInner = "hsla(0, 0%, 0%, 0.1)";
    private String defaultHoverBackground = "hsl(270, 1%, 22%)";
    private String defaultActiveBackground = "hsl(270, 2%, 20%)";
    private String defaultActiveShadow = "hsl(270, 2%, 23%)";
    private String onHoverBackground = "hsl(255, 4%, 16%)";
    private String onActiveBackground = "hsl(255, 4%, 14%)";
    private String onActiveShadow = "hsl(240, 3%, 19%)";
    private String actionBackground = "hsl(168, 76%, 42%)";
    private String actionHoverBackground = "hsl(168, 76%, 38%)";
    private String actionActiveBackground = "hsl(168, 76%, 36%)";
    private String actionActiveShadow = "hsl(168, 75%, 34%)";
    private String actionDisabledBackground = "hsl(168, 76%, 42%)";
    private String save = "hsl(120, 100%, 46%)";
    private String cancel = "hsl(15, 100%, 56%)";
    private String inputBorder = "hsl(257, 3%, 43%)";
    private String inputText = "hsl(0, 0%, 98%)";
    private String inputDisabledBackground = "hsl(255, 4%, 21%)";
    private String inputDisabledBorder = "hsl(250, 3%, 38%)";
    private String inputDisabledText = "hsl(0, 0%, 46%)";
    private String tooltipBackground = "hsl(252, 7%, 14%)";
    private String tooltipText = "hsl(0, 0%, 93%)";
    private String imageCaptionBackground = "hsl(0, 0%, 97%)";
    private String imageCaptionText = "hsl(0, 0%, 20%)";
    private String widgetBlurredBorder = "hsl(0, 0%, 87%)";
    private String widgetHoverBorder = "hsl(43, 100%, 68%)";
    private String linkDefault = "hsl(190, 100%, 75%)";

    private Theme() {}

    public Theme(Type type) {
        this.themeType = type;
    }

    public String getRadius() {
        return radius;
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

    public String getFocusBorder() {
        return focusBorder;
    }

    public String getColorText() {
        return colorText;
    }

    public String getShadowDrop() {
        return shadowDrop;
    }

    public String getShadowInner() {
        return shadowInner;
    }

    public String getDefaultHoverBackground() {
        return defaultHoverBackground;
    }

    public String getDefaultActiveBackground() {
        return defaultActiveBackground;
    }

    public String getDefaultActiveShadow() {
        return defaultActiveShadow;
    }

    public String getOnHoverBackground() {
        return onHoverBackground;
    }

    public String getOnActiveBackground() {
        return onActiveBackground;
    }

    public String getOnActiveShadow() {
        return onActiveShadow;
    }

    public String getActionBackground() {
        return actionBackground;
    }

    public String getActionHoverBackground() {
        return actionHoverBackground;
    }

    public String getActionActiveBackground() {
        return actionActiveBackground;
    }

    public String getActionActiveShadow() {
        return actionActiveShadow;
    }

    public String getActionDisabledBackground() {
        return actionDisabledBackground;
    }

    public String getSave() {
        return save;
    }

    public String getCancel() {
        return cancel;
    }

    public String getInputBorder() {
        return inputBorder;
    }

    public String getInputText() {
        return inputText;
    }

    public String getInputDisabledBackground() {
        return inputDisabledBackground;
    }

    public String getInputDisabledBorder() {
        return inputDisabledBorder;
    }

    public String getInputDisabledText() {
        return inputDisabledText;
    }

    public String getTooltipBackground() {
        return tooltipBackground;
    }

    public String getTooltipText() {
        return tooltipText;
    }

    public String getImageCaptionBackground() {
        return imageCaptionBackground;
    }

    public String getImageCaptionText() {
        return imageCaptionText;
    }

    public String getWidgetBlurredBorder() {
        return widgetBlurredBorder;
    }

    public String getWidgetHoverBorder() {
        return widgetHoverBorder;
    }

    public String getLinkDefault() {
        return linkDefault;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setForeground(String foreground) {
        this.foreground = foreground;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    public void setFocusBorder(String focusBorder) {
        this.focusBorder = focusBorder;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public void setShadowDrop(String shadowDrop) {
        this.shadowDrop = shadowDrop;
    }

    public void setShadowInner(String shadowInner) {
        this.shadowInner = shadowInner;
    }

    public void setDefaultHoverBackground(String defaultHoverBackground) {
        this.defaultHoverBackground = defaultHoverBackground;
    }

    public void setDefaultActiveBackground(String defaultActiveBackground) {
        this.defaultActiveBackground = defaultActiveBackground;
    }

    public void setDefaultActiveShadow(String defaultActiveShadow) {
        this.defaultActiveShadow = defaultActiveShadow;
    }

    public void setOnHoverBackground(String onHoverBackground) {
        this.onHoverBackground = onHoverBackground;
    }

    public void setOnActiveBackground(String onActiveBackground) {
        this.onActiveBackground = onActiveBackground;
    }

    public void setOnActiveShadow(String onActiveShadow) {
        this.onActiveShadow = onActiveShadow;
    }

    public void setActionBackground(String actionBackground) {
        this.actionBackground = actionBackground;
    }

    public void setActionHoverBackground(String actionHoverBackground) {
        this.actionHoverBackground = actionHoverBackground;
    }

    public void setActionActiveBackground(String actionActiveBackground) {
        this.actionActiveBackground = actionActiveBackground;
    }

    public void setActionActiveShadow(String actionActiveShadow) {
        this.actionActiveShadow = actionActiveShadow;
    }

    public void setActionDisabledBackground(String actionDisabledBackground) {
        this.actionDisabledBackground = actionDisabledBackground;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public void setInputBorder(String inputBorder) {
        this.inputBorder = inputBorder;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public void setInputDisabledBackground(String inputDisabledBackground) {
        this.inputDisabledBackground = inputDisabledBackground;
    }

    public void setInputDisabledBorder(String inputDisabledBorder) {
        this.inputDisabledBorder = inputDisabledBorder;
    }

    public void setInputDisabledText(String inputDisabledText) {
        this.inputDisabledText = inputDisabledText;
    }

    public void setTooltipBackground(String tooltipBackground) {
        this.tooltipBackground = tooltipBackground;
    }

    public void setTooltipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }

    public void setImageCaptionBackground(String imageCaptionBackground) {
        this.imageCaptionBackground = imageCaptionBackground;
    }

    public void setImageCaptionText(String imageCaptionText) {
        this.imageCaptionText = imageCaptionText;
    }

    public void setWidgetBlurredBorder(String widgetBlurredBorder) {
        this.widgetBlurredBorder = widgetBlurredBorder;
    }

    public void setWidgetHoverBorder(String widgetHoverBorder) {
        this.widgetHoverBorder = widgetHoverBorder;
    }

    public void setLinkDefault(String linkDefault) {
        this.linkDefault = linkDefault;
    }

    public Type getThemeType() {
        return themeType;
    }


}
