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

    static final Constants.Toolbar[] TOOLBAR = new Constants.Toolbar[] {
            Constants.Toolbar.heading,
            Constants.Toolbar.pipe,
            Constants.Toolbar.fontSize,
            Constants.Toolbar.fontFamily,
            Constants.Toolbar.fontColor,
            Constants.Toolbar.fontBackgroundColor,
            Constants.Toolbar.pipe,
            Constants.Toolbar.bold,
            Constants.Toolbar.italic,
            Constants.Toolbar.underline,
            Constants.Toolbar.strikethrough,
            Constants.Toolbar.subscript,
            Constants.Toolbar.superscript,
            Constants.Toolbar.highlight,
            Constants.Toolbar.removeFormat,
            Constants.Toolbar.pipe,
            Constants.Toolbar.horizontalLine,
            Constants.Toolbar.pageBreak,
            Constants.Toolbar.link,
            Constants.Toolbar.bulletedList,
            Constants.Toolbar.numberedList,
            Constants.Toolbar.alignment,
            Constants.Toolbar.todoList,
            Constants.Toolbar.indent,
            Constants.Toolbar.outdent,
            Constants.Toolbar.code,
            Constants.Toolbar.codeBlock,
            Constants.Toolbar.pipe,
            Constants.Toolbar.specialCharacters,
            Constants.Toolbar.exportPdf,
            Constants.Toolbar.exportWord,
            Constants.Toolbar.imageUpload,
            Constants.Toolbar.blockQuote,
            Constants.Toolbar.insertTable,
            Constants.Toolbar.mediaEmbed,
            Constants.Toolbar.undo,
            Constants.Toolbar.redo };

    Map<Constants.ConfigType, JsonValue> configs = new HashMap<>();

    public Config() {
        configs.put(Constants.ConfigType.alignment, Json.createObject());
        configs.put(Constants.ConfigType.balloonToolbar, Json.createArray());
        configs.put(Constants.ConfigType.blockToolbar, Json.createArray());
        configs.put(Constants.ConfigType.ckfinder, Json.createObject());
        configs.put(Constants.ConfigType.cloudServices, Json.createObject());
        configs.put(Constants.ConfigType.codeBlock, Json.createObject());
        configs.put(Constants.ConfigType.exportPdf, Json.createObject());
        configs.put(Constants.ConfigType.exportWord, Json.createObject());
        configs.put(Constants.ConfigType.extraPlugins, Json.createArray());
        configs.put(Constants.ConfigType.fontBackgroundColor, Json.createObject());
        configs.put(Constants.ConfigType.fontColor, Json.createObject());
        configs.put(Constants.ConfigType.fontFamily, Json.createObject());
        configs.put(Constants.ConfigType.fontSize, Json.createObject());
        configs.put(Constants.ConfigType.heading, Json.createObject());
        configs.put(Constants.ConfigType.highlight, Json.createObject());
        configs.put(Constants.ConfigType.image, Json.createObject());
        configs.put(Constants.ConfigType.indentBlock, Json.createObject());
        configs.put(Constants.ConfigType.initialData, Json.create(""));
        configs.put(Constants.ConfigType.language, Json.create("en"));
        configs.put(Constants.ConfigType.link, Json.createObject());
        configs.put(Constants.ConfigType.mediaEmbed, Json.createObject());
        configs.put(Constants.ConfigType.mention, Json.createObject());
        configs.put(Constants.ConfigType.placeholder, Json.create(""));
        configs.put(Constants.ConfigType.removePlugins, toJsonArray(Constants.Plugins.WProofreader.name()));
        configs.put(Constants.ConfigType.restrictedEditing, Json.createObject());
        configs.put(Constants.ConfigType.simpleUpload, Json.createObject());
        configs.put(Constants.ConfigType.table, Json.createObject());
        configs.put(Constants.ConfigType.title, Json.createObject());
        configs.put(Constants.ConfigType.toolbar, toJsonArray(TOOLBAR));
        configs.put(Constants.ConfigType.typing, Json.createObject());
        configs.put(Constants.ConfigType.wordCount, Json.createObject());
        configs.put(Constants.ConfigType.wproofreader, Json.createObject());
    }

    Config(JsonObject jsonObject) {
        configs.put(Constants.ConfigType.alignment, jsonObject.get(Constants.ConfigType.alignment.name()));
        configs.put(Constants.ConfigType.balloonToolbar, jsonObject.get(Constants.ConfigType.balloonToolbar.name()));
        configs.put(Constants.ConfigType.blockToolbar, jsonObject.get(Constants.ConfigType.blockToolbar.name()));
        configs.put(Constants.ConfigType.ckfinder, jsonObject.get(Constants.ConfigType.ckfinder.name()));
        configs.put(Constants.ConfigType.cloudServices, jsonObject.get(Constants.ConfigType.cloudServices.name()));
        configs.put(Constants.ConfigType.codeBlock, jsonObject.get(Constants.ConfigType.codeBlock.name()));
        configs.put(Constants.ConfigType.exportPdf, jsonObject.get(Constants.ConfigType.exportPdf.name()));
        configs.put(Constants.ConfigType.exportWord, jsonObject.get(Constants.ConfigType.exportWord.name()));
        configs.put(Constants.ConfigType.extraPlugins, jsonObject.get(Constants.ConfigType.extraPlugins.name()));
        configs.put(Constants.ConfigType.fontBackgroundColor, jsonObject.get(Constants.ConfigType.fontBackgroundColor.name()));
        configs.put(Constants.ConfigType.fontColor, jsonObject.get(Constants.ConfigType.fontColor.name()));
        configs.put(Constants.ConfigType.fontFamily, jsonObject.get(Constants.ConfigType.fontFamily.name()));
        configs.put(Constants.ConfigType.fontSize, jsonObject.get(Constants.ConfigType.fontSize.name()));
        configs.put(Constants.ConfigType.heading, jsonObject.get(Constants.ConfigType.heading.name()));
        configs.put(Constants.ConfigType.highlight, jsonObject.get(Constants.ConfigType.highlight.name()));
        configs.put(Constants.ConfigType.image, jsonObject.get(Constants.ConfigType.image.name()));
        configs.put(Constants.ConfigType.indentBlock, jsonObject.get(Constants.ConfigType.indentBlock.name()));
        configs.put(Constants.ConfigType.initialData, jsonObject.get(Constants.ConfigType.initialData.name()));
        configs.put(Constants.ConfigType.language, jsonObject.get(Constants.ConfigType.language.name()));
        configs.put(Constants.ConfigType.link, jsonObject.get(Constants.ConfigType.link.name()));
        configs.put(Constants.ConfigType.mediaEmbed, jsonObject.get(Constants.ConfigType.mediaEmbed.name()));
        configs.put(Constants.ConfigType.mention, jsonObject.get(Constants.ConfigType.mention.name()));
        configs.put(Constants.ConfigType.placeholder, jsonObject.get(Constants.ConfigType.placeholder.name()));
        configs.put(Constants.ConfigType.removePlugins, jsonObject.get(Constants.ConfigType.removePlugins.name())==null?
                                                        toJsonArray(Constants.Plugins.WProofreader.name()):
                                                        jsonObject.get(Constants.ConfigType.removePlugins.name()));
        configs.put(Constants.ConfigType.restrictedEditing, jsonObject.get(Constants.ConfigType.restrictedEditing.name()));
        configs.put(Constants.ConfigType.simpleUpload, jsonObject.get(Constants.ConfigType.simpleUpload.name()));
        configs.put(Constants.ConfigType.table, jsonObject.get(Constants.ConfigType.table.name()));
        configs.put(Constants.ConfigType.title, jsonObject.get(Constants.ConfigType.title.name()));
        configs.put(Constants.ConfigType.toolbar, jsonObject.get(Constants.ConfigType.toolbar.name()));
        configs.put(Constants.ConfigType.typing, jsonObject.get(Constants.ConfigType.typing.name()));
        configs.put(Constants.ConfigType.wordCount, jsonObject.get(Constants.ConfigType.wordCount.name()));
        configs.put(Constants.ConfigType.wproofreader, jsonObject.get(Constants.ConfigType.wproofreader.name()));
    }

    JsonObject getConfigJson() {
        JsonObject configResult = new JreJsonFactory().createObject();
        configs.forEach((configType, configJson) -> configResult.put(configType.name(), configJson));
        return configResult;
    }

    public Map<Constants.ConfigType, JsonValue> getConfigs() {
        return configs;
    }

    /**
     * @param toolbar Toolbar of Editor, refer to enum @Constants.Toolbar
     * @return JsonArray
     */
    JsonArray toJsonArray(Constants.Toolbar... toolbar) {
        List<String> values = new ArrayList<>();
        if(toolbar == null || toolbar.length==0) {
            toolbar = TOOLBAR;
        }
        Arrays.stream(toolbar).forEach(item -> values.add(item.getValue()));
        String toolbarJson = new Gson().toJson(values);
        return Json.instance().parse(toolbarJson);
    }

    JsonArray toJsonArray(List<String[]> options) {
        return Json.instance().parse(new Gson().toJson(options));
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
        configs.put(Constants.ConfigType.placeholder, Json.create(placeHolder==null?"Type the content here!":placeHolder));
    }

    /**
     * @param editorToolBar Toolbar of Editor, refer to enum @Constants.Toolbar
     */
    public void setEditorToolBar(Constants.Toolbar[] editorToolBar) {
        configs.put(Constants.ConfigType.toolbar, toJsonArray(editorToolBar));
    }

    /**
     * @param uiLanguage Language of user interface, refer to enum @Language
     */
    public void setUILanguage(Constants.Language uiLanguage) {
        configs.put(Constants.ConfigType.language, Json.create(uiLanguage==null?"en":uiLanguage.getLanguage()));
    }

    /**
     * Configuation of alignment
     * @param options The available options are: 'left', 'right', 'center' and 'justify'. Other values are ignored.
     */
    public void setAlignment(String[] options) {
        JsonObject alignment = Json.createObject();
        alignment.put("options", toJsonArray(options));
        configs.put(Constants.ConfigType.alignment, alignment);
    }

    /**
     * @param balloonToolBar BalloonToolbar of Editor, refer to enum @Constants.Toolbar.
     *                       Contextual toolbar configuration. Used by the BalloonToolbar feature.
     */
    public void setBalloonToolBar(Constants.Toolbar[] balloonToolBar) {
        configs.put(Constants.ConfigType.balloonToolbar, toJsonArray(balloonToolBar));
    }

    /**
     * @param blockToolBar BlockToolbar of Editor, refer to enum @Constants.Toolbar.
     *                     The block toolbar configuration. Used by the BlockToolbar feature.
     */
    public void setBlockToolBar(Constants.Toolbar[] blockToolBar) {
        configs.put(Constants.ConfigType.blockToolbar, toJsonArray(blockToolBar));
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
        configs.put(Constants.ConfigType.ckfinder, ckfinder);
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
        configs.put(Constants.ConfigType.cloudServices, cloudServices);
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
        configs.put(Constants.ConfigType.codeBlock, codeBlock);
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
        configs.put(Constants.ConfigType.exportPdf, exportPdf);
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
        configs.put(Constants.ConfigType.exportWord, exportWord);
    }

    /**
     * The configuration of the font background color feature.
     * @param columns Represents the number of columns in the font background color dropdown. Defaults to 5.
     * @param documentColors Determines the maximum number of available document colors.
     *                       Setting it to 0 will disable the document colors feature.
     *                       By default it equals to the columns value.
     * @param colors Available font background colors defined as an array of strings or objects.
     *               colors:
     *               [
     *                {
     * 			        color: 'hsl(0, 0%, 0%)',
     * 			        label: 'Black'
     *                },
     *                {
     * 			        color: 'hsl(0, 0%, 30%)',
     * 			        label: 'Dim grey'
     *                }
     * 	            ]
     */
    public void setFontBackgroundColor(int columns, int documentColors, Map<String, String> colors) {
        JsonObject fontBackgroundColor = Json.createObject();
        fontBackgroundColor.put("columns", Json.create(columns));
        fontBackgroundColor.put("documentColors", Json.create(documentColors));
        fontBackgroundColor.put("colors", Json.createArray());//TODO: imply colors
        configs.put(Constants.ConfigType.fontBackgroundColor, fontBackgroundColor);
    }

    /**
     * The configuration of the font color feature.
     * @param columns Represents the number of columns in the font background color dropdown. Defaults to 5.
     * @param documentColors Determines the maximum number of available document colors.
     *                       Setting it to 0 will disable the document colors feature.
     *                       By default it equals to the columns value.
     * @param colors Available font background colors defined as an array of strings or objects.
     *               colors:
     *               [
     *                {
     * 			        color: 'hsl(0, 0%, 0%)',
     * 			        label: 'Black'
     *                },
     *                {
     * 			        color: 'hsl(0, 0%, 30%)',
     * 			        label: 'Dim grey'
     *                }
     * 	            ]
     */
    public void setFontColor(int columns, int documentColors, Map<String, String> colors) {
        JsonObject fontBackgroundColor = Json.createObject();
        fontBackgroundColor.put("columns", Json.create(columns));
        fontBackgroundColor.put("documentColors", Json.create(documentColors));
        fontBackgroundColor.put("colors", Json.createArray());//TODO: imply colors
        configs.put(Constants.ConfigType.fontColor, fontBackgroundColor);
    }

    /**
     *
     * @param supportAllValues By default the plugin removes any font-family value that does not match
     *                         the plugin's configuration. It means that if you paste content with font families
     *                         that the editor does not understand, the font-family attribute will be removed and
     *                         the content will be displayed with the default font.
     *                         You can preserve pasted font family values by switching the supportAllValues option to true
     * @param options Available font family options defined as an array of strings. The default value is:
     *                'default',
     * 		          'Arial, Helvetica, sans-serif',
     * 		          'Courier New, Courier, monospace',
     * 		          'Georgia, serif',
     * 		          'Lucida Sans Unicode, Lucida Grande, sans-serif',
     * 		          'Tahoma, Geneva, sans-serif',
     * 		          'Times New Roman, Times, serif',
     * 		          'Trebuchet MS, Helvetica, sans-serif',
     * 		          'Verdana, Geneva, sans-serif'
     */
    public void setFontFamily(boolean supportAllValues, String[] options) {
        JsonObject fontFamily = Json.createObject();
        fontFamily.put("supportAllValues", Json.create(supportAllValues));
        fontFamily.put("options", toJsonArray(options));
        configs.put(Constants.ConfigType.fontFamily, fontFamily);
    }

    /**
     *
     * @param supportAllValues By default the plugin removes any font-size value that
     *                         does not match the plugin's configuration. It means that if you paste content with
     *                         font sizes that the editor does not understand, the font-size attribute will be removed
     *                         and the content will be displayed with the default size.
     *                         You can preserve pasted font size values by switching the supportAllValues option to true
     * @param options Available font size options. Expressed as predefined presets, numerical "pixel" values
     */
    public void setFontSize(boolean supportAllValues, String[] options) {
        JsonObject fontSize = Json.createObject();
        fontSize.put("supportAllValues", Json.create(supportAllValues));
        fontSize.put("options", toJsonArray(options));
        configs.put(Constants.ConfigType.fontSize, fontSize);
    }

    /**
     * Configuration of heading
     * @param options The available heading options.
     *                [
     *                  { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
     *                  { model: 'heading1', view: 'h2', title: 'Heading 1', class: 'ck-heading_heading1' },
     *                  { model: 'heading2', view: 'h3', title: 'Heading 2', class: 'ck-heading_heading2' },
     *                  { model: 'heading3', view: 'h4', title: 'Heading 3', class: 'ck-heading_heading3' }
     * 	              ]
     */
    public void setHeading(List<String[]> options) {
        JsonObject heading = Json.createObject();
        heading.put("options", toJsonArray(options));
        configs.put(Constants.ConfigType.heading, heading);
    }

    /**
     * Configuration of highlight
     * @param options The available highlight options.
     *                [
     *                  {
     * 		              model: 'yellowMarker',
     * 		              class: 'marker-yellow',
     * 		              title: 'Yellow marker',
     * 		              color: 'var(--ck-highlight-marker-yellow)',
     * 		              type: 'marker'
     *                  },
     *                  {
     * 		              model: 'greenMarker',
     * 		              class: 'marker-green',
     * 		              title: 'Green marker',
     * 		              color: 'var(--ck-highlight-marker-green)',
     * 		              type: 'marker'
     *                  }
     *                ]
     */
    public void setHighlight(List<String[]> options) {
        JsonObject highlight = Json.createObject();
        highlight.put("options", toJsonArray(options));
        configs.put(Constants.ConfigType.highlight, highlight);
    }

    /**
     * @param resizeOptions
     * @param resizeUnit
     * @param styles
     * @param toolbar
     * @param upload
     */
    public void setImage(List<String[]> resizeOptions, String resizeUnit, List<String[]> styles,
                         List<String> toolbar, List<String> upload) {
        JsonObject image = Json.createObject();
        image.put("resizeOptions", toJsonArray(resizeOptions));
        configs.put(Constants.ConfigType.image, image);
    }



}
