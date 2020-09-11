package com.wontlost.ckeditor;

import com.vaadin.flow.component.HasElement;
import elemental.json.Json;
import elemental.json.JsonObject;

public interface HasConfig extends HasElement {

    default void setConfig(Config config) {
        getElement().setPropertyJson("config", config!=null?config.getConfigJson():new Config().getConfigJson());
    }

    default Config getConfig() {
        String configJson = getElement().getProperty("config");
        JsonObject jsonObject = Json.parse(configJson);
        return new Config(jsonObject);
    }

}
