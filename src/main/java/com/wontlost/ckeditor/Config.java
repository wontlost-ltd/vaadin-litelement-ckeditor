package com.wontlost.ckeditor;

import com.google.gson.Gson;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import elemental.json.impl.JreJsonFactory;

import java.util.*;

/**
 * Configuration before Editor is initialized
 */
public class Config {

    static final Toolbar[] TOOLBAR = new Toolbar[]{Toolbar.heading, Toolbar.pipe, Toolbar.fontSize, Toolbar.fontFamily,
            Toolbar.fontColor, Toolbar.fontBackgroundColor, Toolbar.pipe, Toolbar.bold, Toolbar.italic,
            Toolbar.underline, Toolbar.strikethrough, Toolbar.subscript, Toolbar.superscript, Toolbar.highlight,
            Toolbar.removeFormat, Toolbar.pipe, Toolbar.horizontalLine, Toolbar.pageBreak, Toolbar.link,
            Toolbar.bulletedList, Toolbar.numberedList, Toolbar.alignment, Toolbar.todoList, Toolbar.indent, Toolbar.outdent,
            Toolbar.code, Toolbar.codeBlock, Toolbar.pipe, Toolbar.specialCharacters, Toolbar.exportPdf, Toolbar.exportWord,
            Toolbar.imageUpload, Toolbar.blockQuote, Toolbar.insertTable, Toolbar.mediaEmbed, Toolbar.undo, Toolbar.redo};

    Map<ConfigType, JsonValue> configs = new HashMap<>();

    public Config() {
        configs.put(ConfigType.alignment, Json.createObject());
        configs.put(ConfigType.balloonToolbar, Json.createArray());
        configs.put(ConfigType.blockToolbar, Json.createArray());
        configs.put(ConfigType.ckfinder, Json.createObject());
        configs.put(ConfigType.cloudServices, Json.createObject());
        configs.put(ConfigType.codeBlock, Json.createObject());
        configs.put(ConfigType.exportPdf, Json.createObject());
        configs.put(ConfigType.exportWord, Json.createObject());
        configs.put(ConfigType.extraPlugins, Json.createArray());
        configs.put(ConfigType.fontBackgroundColor, Json.createObject());
        configs.put(ConfigType.fontColor, Json.createObject());
        configs.put(ConfigType.fontFamily, Json.createObject());
        configs.put(ConfigType.fontSize, Json.createObject());
        configs.put(ConfigType.heading, Json.createObject());
        configs.put(ConfigType.highlight, Json.createObject());
        configs.put(ConfigType.image, Json.createObject());
        configs.put(ConfigType.indentBlock, Json.createObject());
        configs.put(ConfigType.initialData, Json.create(""));
        configs.put(ConfigType.language, Json.create("en"));
        configs.put(ConfigType.link, Json.createObject());
        configs.put(ConfigType.mediaEmbed, Json.createObject());
        configs.put(ConfigType.mention, Json.createObject());
        configs.put(ConfigType.placeholder, Json.create(""));
        configs.put(ConfigType.restrictedEditing, Json.createObject());
        configs.put(ConfigType.simpleUpload, Json.createObject());
        configs.put(ConfigType.table, Json.createObject());
        configs.put(ConfigType.title, Json.createObject());
        configs.put(ConfigType.toolbar, toJsonArray(TOOLBAR));
        configs.put(ConfigType.typing, Json.createObject());
        configs.put(ConfigType.wordCount, Json.createObject());
        configs.put(ConfigType.wproofreader, Json.createObject());
    }

    Config(JsonObject jsonObject) {
        configs.put(ConfigType.alignment, jsonObject.get(ConfigType.alignment.name()));
        configs.put(ConfigType.balloonToolbar, jsonObject.get(ConfigType.balloonToolbar.name()));
        configs.put(ConfigType.blockToolbar, jsonObject.get(ConfigType.blockToolbar.name()));
        configs.put(ConfigType.ckfinder, jsonObject.get(ConfigType.ckfinder.name()));
        configs.put(ConfigType.cloudServices, jsonObject.get(ConfigType.cloudServices.name()));
        configs.put(ConfigType.codeBlock, jsonObject.get(ConfigType.codeBlock.name()));
        configs.put(ConfigType.exportPdf, jsonObject.get(ConfigType.exportPdf.name()));
        configs.put(ConfigType.exportWord, jsonObject.get(ConfigType.exportWord.name()));
        configs.put(ConfigType.extraPlugins, jsonObject.get(ConfigType.extraPlugins.name()));
        configs.put(ConfigType.fontBackgroundColor, jsonObject.get(ConfigType.fontBackgroundColor.name()));
        configs.put(ConfigType.fontColor, jsonObject.get(ConfigType.fontColor.name()));
        configs.put(ConfigType.fontFamily, jsonObject.get(ConfigType.fontFamily.name()));
        configs.put(ConfigType.fontSize, jsonObject.get(ConfigType.fontSize.name()));
        configs.put(ConfigType.heading, jsonObject.get(ConfigType.heading.name()));
        configs.put(ConfigType.highlight, jsonObject.get(ConfigType.highlight.name()));
        configs.put(ConfigType.image, jsonObject.get(ConfigType.image.name()));
        configs.put(ConfigType.indentBlock, jsonObject.get(ConfigType.indentBlock.name()));
        configs.put(ConfigType.initialData, jsonObject.get(ConfigType.initialData.name()));
        configs.put(ConfigType.language, jsonObject.get(ConfigType.language.name()));
        configs.put(ConfigType.link, jsonObject.get(ConfigType.link.name()));
        configs.put(ConfigType.mediaEmbed, jsonObject.get(ConfigType.mediaEmbed.name()));
        configs.put(ConfigType.mention, jsonObject.get(ConfigType.mention.name()));
        configs.put(ConfigType.placeholder, jsonObject.get(ConfigType.placeholder.name()));
        configs.put(ConfigType.restrictedEditing, jsonObject.get(ConfigType.restrictedEditing.name()));
        configs.put(ConfigType.simpleUpload, jsonObject.get(ConfigType.simpleUpload.name()));
        configs.put(ConfigType.table, jsonObject.get(ConfigType.table.name()));
        configs.put(ConfigType.title, jsonObject.get(ConfigType.title.name()));
        configs.put(ConfigType.toolbar, jsonObject.get(ConfigType.toolbar.name()));
        configs.put(ConfigType.typing, jsonObject.get(ConfigType.typing.name()));
        configs.put(ConfigType.wordCount, jsonObject.get(ConfigType.wordCount.name()));
        configs.put(ConfigType.wproofreader, jsonObject.get(ConfigType.wproofreader.name()));
    }

    JsonObject getConfigJson() {
        JsonObject configResult = new JreJsonFactory().createObject();
        configs.forEach((configType, configJson) -> configResult.put(configType.name(), configJson));
        return configResult;
    }

    public Map<ConfigType, JsonValue> getConfigs() {
        return configs;
    }

    /**
     * @param toolbar Toolbar of Editor, refer to enum @Toolbar
     * @return JsonArray
     */
    JsonArray toJsonArray(Toolbar... toolbar) {
        List<String> values = new ArrayList<>();
        if(toolbar == null || toolbar.length==0) {
            toolbar = TOOLBAR;
        }
        Arrays.stream(toolbar).forEach(item -> values.add(item.getValue()));
        String toolbarJson = new Gson().toJson(values);
        return Json.instance().parse(toolbarJson);
    }

    /**
     * @param list String list
     * @return JsonArray
     */
    JsonArray toJsonArray(String... list) {
        List<String> values = Arrays.asList(list==null?new String[0]:list);
        String toolbarJson = new Gson().toJson(values);
        return Json.instance().parse(toolbarJson);
    }

    /**
     * @param placeHolder Place holder of Editor
     */
    public void setPlaceHolder(String placeHolder){
        configs.put(ConfigType.placeholder, Json.create(placeHolder==null?"Type the content here!":placeHolder));
    }

    /**
     * @param editorToolBar Toolbar of Editor, refer to enum @Toolbar
     */
    public void setEditorToolBar(Toolbar[] editorToolBar) {
        configs.put(ConfigType.toolbar, toJsonArray(editorToolBar));
    }

    /**
     * @param uiLanguage Language of user interface, refer to enum @Language
     */
    public void setUILanguage(Language uiLanguage) {
        configs.put(ConfigType.language, Json.create(uiLanguage==null?"en":uiLanguage.getLanguage()));
    }

    /**
     * Configuation of alignment
     * @param options The available options are: 'left', 'right', 'center' and 'justify'. Other values are ignored.
     */
    public void setAlignment(String[] options) {
        JsonObject alignment = Json.createObject();
        alignment.put("options", toJsonArray(options));
        configs.put(ConfigType.alignment, alignment);
    }

    /**
     * @param balloonToolBar BalloonToolbar of Editor, refer to enum @Toolbar.
     *                       Contextual toolbar configuration. Used by the BalloonToolbar feature.
     */
    public void setBalloonToolBar(Toolbar[] balloonToolBar) {
        configs.put(ConfigType.balloonToolbar, toJsonArray(balloonToolBar));
    }

    /**
     * @param blockToolBar BlockToolbar of Editor, refer to enum @Toolbar.
     *                     The block toolbar configuration. Used by the BlockToolbar feature.
     */
    public void setBlockToolBar(Toolbar[] blockToolBar) {
        configs.put(ConfigType.blockToolbar, toJsonArray(blockToolBar));
    }

    /**
     * CKFinder configurations
     * @param openerMethod The type of the CKFinder opener method.
     *                     Supported types are:
     *                     'modal' – Opens CKFinder in a modal,
     *                     'popup' – Opens CKFinder in a new "pop-up" window.
     *                     Defaults to 'modal'.
     * @param uploadUrl The path (URL) to the connector which handles the file upload in CKFinder file manager.
     *                  When specified, it enables the automatic upload of resources
     *                  such as images inserted into the content.
     *                  Used by the upload adapter.
     * @param options Configuration settings for CKFinder. Not fully integrated with ckfinder
     */
    public void setCKFinder(String openerMethod, String uploadUrl, Map<String, String> options) {
        JsonObject ckfinder = Json.createObject();
        ckfinder.put("openerMethod", Json.create(openerMethod));
        ckfinder.put("uploadUrl", Json.create(uploadUrl));
        JsonObject ckfinderOptions = Json.createObject();
        ckfinderOptions.put("connectorInfo", options.get("connectorInfo"));
        ckfinderOptions.put("connectorPath", options.get("connectorPath"));
        ckfinderOptions.put("height", options.get("height"));
        ckfinderOptions.put("width", options.get("width"));
        ckfinder.put("options", ckfinderOptions);
        configs.put(ConfigType.ckfinder, ckfinder);
    }

    /**
     * The configuration of CKEditor Cloud Services
     * @param bundleVersion An optional parameter used for integration with CKEditor Cloud Services
     *                      when uploading the editor build to cloud services. Whenever the editor build or
     *                      the configuration changes, this parameter should be set to a new, unique value
     *                      to differentiate the new bundle (build + configuration) from the old ones.
     * @param tokenUrl  A token URL which should be a URL to the security token endpoint in your application.
     *                  The role of this endpoint is to securely authorize the end users of your application
     *                  to use CKEditor Cloud Services only if they should have access e.g. to upload files
     *                  with Easy Image or to use the Collaboration service.
     * @param uploadUrl The endpoint URL for CKEditor Cloud Services uploads.
     *                  This option must be set for Easy Image to work correctly.
     *                  The upload URL is unique for each customer and can be found in the CKEditor Ecosystem customer dashboard
     *                  after subscribing to the Easy Image service. To learn how to start using Easy Image,
     *                  check the Easy Image - Quick start documentation.
     *                  Note: Make sure to also set the tokenUrl configuration option.
     * @param webSocketUrl The URL for web socket communication, used by the RealTimeCollaborativeEditing plugin.
     *                     Every customer (organization in the CKEditor Ecosystem dashboard) has their own,
     *                     unique URLs to communicate with CKEditor Cloud Services. The URL can be found in the
     *                     CKEditor Ecosystem customer dashboard.
     *
     */
    public void setCloudServices(String bundleVersion, String tokenUrl, String uploadUrl, String webSocketUrl) {
        JsonObject cloudServices = Json.createObject();
        cloudServices.put("bundleVersion", Json.create(bundleVersion));
        cloudServices.put("tokenUrl", Json.create(tokenUrl));
        cloudServices.put("uploadUrl", Json.create(uploadUrl));
        cloudServices.put("webSocketUrl", Json.create(webSocketUrl));
        configs.put(ConfigType.cloudServices, cloudServices);
    }

    /**
     *
     * @param indentSequence A sequence of characters inserted or removed from the code block lines 、
     *                       when its indentation is changed by the user, for instance, using Tab and Shift+Tab keys.
     *                       The default value is a single tab character (" ", \u0009 in Unicode).
     *                       This configuration is used by indentCodeBlock and outdentCodeBlock commands
     *                       (instances of IndentCodeBlockCommand).
     *                       Note: Setting this configuration to false will disable the code block indentation commands
     *                       and associated keystrokes.
     * @param languages  The list of code languages available in the user interface to choose for a particular code block.
     *                   [
     *                    { language: 'plaintext', label: 'Plain text', class: '' },
     *                    { language: 'php', label: 'PHP', class: 'php-code' }
     *                   ]
     *                   class is optional
     */
    public void setCodeBlock(String indentSequence, String[] languages) {
        JsonObject codeBlock = Json.createObject();
        codeBlock.put("indentSequence", Json.create(indentSequence));
        codeBlock.put("languages", toJsonArray(languages));
        configs.put(ConfigType.codeBlock, codeBlock);
    }

    /**
     * The configuration of the export to PDF feature.
     * @param fileName The name of the generated PDF file.
     * @param converterUrl A URL to the HTML to PDF converter.
     */
    public void setExportPdf(String fileName, String converterUrl) {
        JsonObject exportPdf = Json.createObject();
        exportPdf.put("fileName", Json.create(fileName));
        exportPdf.put("converterUrl", Json.create(converterUrl));
        configs.put(ConfigType.exportPdf, exportPdf);
    }

    /**
     * The configuration of the export to Word feature.
     * @param fileName The name of the generated Word file.
     * @param converterUrl A URL to the HTML to Word converter.
     */
    public void setExportWord(String fileName, String converterUrl) {
        JsonObject exportWord = Json.createObject();
        exportWord.put("fileName", Json.create(fileName));
        exportWord.put("converterUrl", Json.create(converterUrl));
        configs.put(ConfigType.exportWord, exportWord);
    }

}
