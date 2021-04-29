package com.wontlost.ckeditor;

import com.google.gson.Gson;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import elemental.json.impl.JreJsonFactory;

import java.util.*;

import static com.wontlost.ckeditor.Constants.*;

/**
 * Configuration before Editor is initialized
 */
public class Config {

    static final Toolbar[] TOOLBAR = new Toolbar[] {
            Toolbar.heading,
            Toolbar.pipe,
            Toolbar.fontSize,
            Toolbar.fontFamily,
            Toolbar.fontColor,
            Toolbar.fontBackgroundColor,
            Toolbar.pipe,
            Toolbar.bold,
            Toolbar.italic,
            Toolbar.underline,
            Toolbar.selectAll,
            Toolbar.strikethrough,
            Toolbar.subscript,
            Toolbar.superscript,
            Toolbar.highlight,
            Toolbar.removeFormat,
            Toolbar.pipe,
            Toolbar.horizontalLine,
            Toolbar.pageBreak,
            Toolbar.link,
            Toolbar.bulletedList,
            Toolbar.numberedList,
            Toolbar.alignment,
            Toolbar.todoList,
            Toolbar.indent,
            Toolbar.outdent,
            Toolbar.code,
            Toolbar.codeBlock,
            Toolbar.pipe,
            Toolbar.specialCharacters,
            Toolbar.exportPdf,
            Toolbar.exportWord,
            Toolbar.imageUpload,
            Toolbar.blockQuote,
            Toolbar.insertTable,
            Toolbar.mediaEmbed,
            Toolbar.htmlEmbed,
            Toolbar.pipe,
            Toolbar.undo,
            Toolbar.redo,
            Toolbar.previousPage,
            Toolbar.nextPage,
            Toolbar.pageNavigation};

    List<String> removedPlugins = new ArrayList<>();

    Map<ConfigType, JsonValue> configs = new HashMap<>();

    private void initPlugins() {
        removedPlugins.add(Plugins.WProofreader.name());
        removedPlugins.add(Plugins.StandardEditingMode.name());
        removedPlugins.add(Plugins.RestrictedEditingMode.name());
        removedPlugins.add(Plugins.Markdown.name());
        configs.put(ConfigType.removePlugins, toJsonArray(removedPlugins));
        configs.put(ConfigType.toolbar, toJsonArray(TOOLBAR));
    }

    public Config() {
        initPlugins();
        configs.put(ConfigType.alignment, Json.createObject());
        configs.put(ConfigType.autosave, Json.createObject());
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
        configs.put(ConfigType.typing, Json.createObject());
        configs.put(ConfigType.wordCount, Json.createObject());
        configs.put(ConfigType.wproofreader, Json.createObject());
    }

    Config(JsonObject jsonObject) {
        initPlugins();
        configs.put(ConfigType.alignment, jsonObject.get(ConfigType.alignment.name()));
        configs.put(ConfigType.autosave, jsonObject.get(ConfigType.autosave.name()));
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
        configs.put(ConfigType.removePlugins, jsonObject.get(ConfigType.removePlugins.name())==null?
                                                        toJsonArray(removedPlugins):
                                                        jsonObject.get(ConfigType.removePlugins.name()));
        configs.put(ConfigType.restrictedEditing, jsonObject.get(ConfigType.restrictedEditing.name()));
        configs.put(ConfigType.simpleUpload, jsonObject.get(ConfigType.simpleUpload.name()));
        configs.put(ConfigType.table, jsonObject.get(ConfigType.table.name()));
        configs.put(ConfigType.title, jsonObject.get(ConfigType.title.name()));
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
     * @param toolbar Toolbar of Editor, refer to enum @Constants.Toolbar
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

    JsonArray toJsonArray(List<String> options) {
        return Json.instance().parse(new Gson().toJson(options));
    }

    JsonArray toJsonArray(String[][] options) {
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
        configs.put(ConfigType.placeholder, Json.create(Optional.ofNullable(placeHolder).orElse("Type the content here!")));
    }

    /**
     * @param editorToolBar Toolbar of Editor, refer to enum @Constants.Toolbar
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
     * @param balloonToolBar BalloonToolbar of Editor, refer to enum @Constants.Toolbar.
     *                       Contextual toolbar configuration. Used by the BalloonToolbar feature.
     */
    public void setBalloonToolBar(Toolbar[] balloonToolBar) {
        configs.put(ConfigType.balloonToolbar, toJsonArray(balloonToolBar));
    }

    /**
     * @param blockToolBar BlockToolbar of Editor, refer to enum @Constants.Toolbar.
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
        configs.put(ConfigType.fontBackgroundColor, fontBackgroundColor);
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
        configs.put(ConfigType.fontColor, fontBackgroundColor);
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
        configs.put(ConfigType.fontFamily, fontFamily);
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
        configs.put(ConfigType.fontSize, fontSize);
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
    public void setHeading(String[][] options) {
        JsonObject heading = Json.createObject();
        heading.put("options", toJsonArray(options));
        configs.put(ConfigType.heading, heading);
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
    public void setHighlight(String[][] options) {
        JsonObject highlight = Json.createObject();
        highlight.put("options", toJsonArray(options));
        configs.put(ConfigType.highlight, highlight);
    }

    /**
     * @param resizeOptions The image resize options.
     *                      resizeOptions: [ {
     * 				                name: 'imageResize:original',
     * 				                value: null
     *                          },
     *                          {
     * 				                name: 'imageResize:50',
     * 				                value: '50'
     *                          } ]
     * @param resizeUnit The available options are 'px' or '%'.
     * @param styles Available image styles.
     *               The default value is:
     *               const imageConfig = {
     * 	                styles: [ 'full', 'side' ]
     *               };
     * @param toolbar Items to be placed in the image toolbar.
     *                three toolbar items will be available in ComponentFactory: 'imageStyle:full', 'imageStyle:side', and 'imageTextAlternative'
     *                so you can configure the toolbar like this:
     *                const imageConfig = {
     * 	                  toolbar: [ 'imageStyle:full', 'imageStyle:side', '|', 'imageTextAlternative' ]
     *                };
     * @param upload The image upload configuration.
     *               The list of accepted image types.
     *               The accepted types of images can be customized to allow only certain types of images:
     *               // Allow only JPEG and PNG images:
     *              const imageUploadConfig = {
     * 	                types: [ 'png', 'jpeg' ]
     *              };
     */
    public void setImage(String[][] resizeOptions, String resizeUnit, String[] styles,
                         String[] toolbar, String[] upload) {
        JsonObject image = Json.createObject();
        image.put("resizeOptions", toJsonArray(resizeOptions));
        image.put("resizeUnit", Json.create(resizeUnit));
        image.put("styles", toJsonArray(styles));
        image.put("toolbar", toJsonArray(toolbar));
        image.put("types", toJsonArray(upload));
        configs.put(ConfigType.image, image);
    }

    /**
     * The configuration of the block indentation feature.
     * @param offset The size of indentation units for each indentation step. Default 40
     * @param unit The unit used for indentation offset.
     */
    public void setIndentBlock(int offset, String unit) {
        JsonObject indentBlock = Json.createObject();
        indentBlock.put("offset", Json.create(offset));
        indentBlock.put("unit", Json.create(unit));
        configs.put(ConfigType.indentBlock, indentBlock);
    }

    /**
     * By default, the editor is initialized with the content of the element on which this editor is initialized.
     * @param initialData The initial editor data to be used instead of the provided element's HTML content.
     */
    public void setInitialData(String initialData) {
        configs.put(ConfigType.initialData, Json.create(initialData));
    }

    /**
     * The configuration of the link feature.
     * @param defaultProtocal    When set, the editor will add the given protocol to the link when the user creates a link without one. For example, when the user is creating a link and types
     *                           ckeditor.com in the link form input, during link submission the editor will automatically add the http:// protocol, so the link will look as follows: http://ckeditor.com.
     *                           The feature also provides email address auto-detection. When you submit hello@example.com, the plugin will automatically change it to mailto:hello@example.com.
     * @param addTargetToExternalLinks When set to true, the target="blank" and rel="noopener noreferrer"
     *                                 attributes are automatically added to all external links in the editor. "External links" are all links in the editor content starting with http, https, or //.
     */
    public void setLink(String defaultProtocal, Boolean addTargetToExternalLinks) {
        JsonObject link = Json.createObject();
        link.put("defaultProtocal", Json.create(defaultProtocal));
        link.put("addTargetToExternalLinks", Json.create(addTargetToExternalLinks));
        configs.put(ConfigType.link, link);
    }

    /**
     * The configuration of the media embed features.
     * @param previewsInData Controls the data format produced by the feature.
     * @param providers The default media providers supported by the editor.
     * @param extraProviders The additional media providers supported by the editor. This configuration helps extend the default providers.
     * @param removeProviders The list of media providers that should not be used despite being available in config.mediaEmbed.providers and config.mediaEmbed.extraProviders
     * @param toolbar Items to be placed in the media embed toolbar. This option requires adding MediaEmbedToolbar to the plugin list.
     */
    public void setMediaEmbed(Boolean previewsInData, List<String> providers, List<String> extraProviders, List<String> removeProviders, List<String> toolbar) {
        JsonObject mediaEmbed = Json.createObject();
        mediaEmbed.put("previewsInData", Json.create(previewsInData));
        mediaEmbed.put("providers", toJsonArray(providers));
        mediaEmbed.put("extraProviders", toJsonArray(extraProviders));
        mediaEmbed.put("removeProviders", toJsonArray(removeProviders));
        mediaEmbed.put("toolbar", toJsonArray(toolbar));
        configs.put(ConfigType.mediaEmbed, mediaEmbed);
    }

    /**
     * The configuration of the mention feature.
     * @param feeds The list of mention feeds supported by the editor.
     */
    public void setMention(List<String> feeds) {
        JsonObject mention = Json.createObject();
        mention.put("feeds", toJsonArray(feeds));
        configs.put(ConfigType.mention, mention);
    }

    /**
     * The list of plugins which should not be loaded despite being available in an editor build.
     * @param plugins names of plugin
     */
    public void setRemovePlugins(List<Plugins> plugins) {
        JsonObject removePlugins = Json.createObject();
        List<String> toBeRemoved = new ArrayList<>();
        plugins.forEach(plugin -> {
            toBeRemoved.add(plugin.name());
        });
        removePlugins.put("removePlugins", toJsonArray(toBeRemoved));
        configs.put(ConfigType.removePlugins, removePlugins);
    }

    /**
     * The configuration of the restricted editing mode feature.
     * @param allowedAttributes The text attribute names allowed when pasting content ot non-restricted areas.
     * @param allowedCommands The command names allowed in non-restricted areas of the content.
     *                        Defines which feature commands should be enabled in the restricted editing mode.
     *                        The commands used for typing and deleting text ('input', 'delete' and 'forwardDelete')
     *                        are allowed by the feature inside non-restricted regions and do not need to be defined.
     *                        Note: The restricted editing mode always allows to use the restricted mode navigation
     *                        commands as well as 'undo' and 'redo' commands.
     */
    public void setRestrictedEditing(List<String> allowedAttributes, List<String> allowedCommands) {
        JsonObject restrictedEditing = Json.createObject();
        restrictedEditing.put("allowedAttributes", toJsonArray(allowedAttributes));
        restrictedEditing.put("allowedCommands", toJsonArray(allowedCommands));
        configs.put(ConfigType.restrictedEditing, restrictedEditing);
    }

    /**
     *
     * @param uploadUrl The path (URL) to the server (application) which handles the file upload. When specified,
     *                  enables the automatic upload of resources (images) inserted into the editor content.
     * @param withCredentials This flag enables the withCredentials property of the request sent to the server
     *                        during the upload. It affects cross-site requests only and, for instance,
     *                        allows credentials such as cookies to be sent along with the request.
     * @param headers An object that defines additional headers sent with the request to the server during the upload.
     *                This is the right place to implement security mechanisms like authentication and CSRF protection.
     */
    public void setSimpleUpload(String uploadUrl, Boolean withCredentials, List<String> headers) {
        JsonObject simpleUpload = Json.createObject();
        simpleUpload.put("uploadUrl", Json.create(uploadUrl));
        simpleUpload.put("withCredentials", Json.create(withCredentials));
        simpleUpload.put("headers", toJsonArray(headers));
        configs.put(ConfigType.simpleUpload, simpleUpload);
    }

    /**
     * The configuration of the table feature. Used by the table feature in the
     * @param contentToolbar Items to be placed in the table content toolbar. The TableToolbar plugin is required to make this toolbar work.
     * @param tableToolbar Items to be placed in the table toolbar. The TableToolbar plugin is required to make this toolbar work.
     * @param tableCellProperties The configuration of the table cell properties user interface (balloon).
     * @param tableProperties The configuration of the table properties user interface (balloon)
     */
    public void setTable(List<String> contentToolbar, List<String> tableToolbar, JsonObject tableCellProperties, JsonObject tableProperties) {
        JsonObject table = Json.createObject();
        table.put("contentToolbar", toJsonArray(contentToolbar));
        table.put("tableToolbar", toJsonArray(tableToolbar));
        table.put("tableCellProperties", tableCellProperties);
        table.put("tableProperties", tableCellProperties);
        configs.put(ConfigType.table, table);
    }

    /**
     * The configuration of the title feature.
     * @param placeholder Defines a custom value of the placeholder for the title field.
     */
    public void setTitle(String placeholder) {
        JsonObject title = Json.createObject();
        title.put("placeholder", Json.create(placeholder));
        configs.put(ConfigType.title, title);
    }

    /**
     * The configuration of the title feature.
     * @param undo Default to 20
     * @param transformations Transformations
     */
    public void setTyping(int undo, JsonObject transformations) {
        JsonObject typing = Json.createObject();
        typing.put("undo", Json.create(undo));
        typing.put("transformations", transformations);
        configs.put(ConfigType.typing, typing);
    }

    /**
     * If you are going to use WproofReader, you have to add this config or WproofReaderServer.
     * @param serviceId After signing up for a trial or paid version, you will receive your service ID which is used to activate the service.
     * @param srcUrl Default: https://svc.webspellchecker.net/spellcheck31/wscbundle/wscbundle.js
     */
    public void setWproofReaderCloud(String serviceId, String srcUrl) {
        JsonObject wproofReaderCloud = Json.createObject();
        String defaultSrcUrl = "https://svc.webspellchecker.net/spellcheck31/wscbundle/wscbundle.js";
        wproofReaderCloud.put("serviceId", Json.create(serviceId));
        wproofReaderCloud.put("srcUrl", Json.create(Optional.ofNullable(srcUrl).orElse(defaultSrcUrl)));
        configs.put(ConfigType.wproofreader, wproofReaderCloud);
        setPluginStatus(Plugins.WProofreader, true); //Wproofreader is not enabled initially.
    }

    /**
     * If you are going to use WproofReader, you have to add this config or WproofReaderCloud.
     * @param serviceProtocol Default to 'https'.
     * @param serviceHost   Default to localhost.
     * @param servicePort Default to 8080.
     * @param servicePath Default to '/'.
     * @param srcUrl String like 'https://host_name/virtual_directory/wscbundle/wscbundle.js'
     */
    public void setWproofReaderServer(String serviceProtocol, String serviceHost, Integer servicePort, String servicePath, String srcUrl) {
        JsonObject wproofReaderServer = Json.createObject();
        wproofReaderServer.put("serviceProtocol", Json.create(Optional.ofNullable(serviceProtocol).orElse("https")));
        wproofReaderServer.put("serviceHost", Json.create(Optional.ofNullable(serviceHost).orElse("localhost")));
        wproofReaderServer.put("servicePort", Json.create(Optional.ofNullable(servicePort).orElse(8080)));
        wproofReaderServer.put("servicePath", Json.create(Optional.ofNullable(servicePath).orElse("/")));
        wproofReaderServer.put("srcUrl", Json.create(Optional.ofNullable(srcUrl).orElse("/wscbundle/wscbundle.js")));
        configs.put(ConfigType.wproofreader, wproofReaderServer);
        setPluginStatus(Plugins.WProofreader, true); //Wproofreader is not enabled initially.
    }
    
     /**
     * If you are going to use Pagination, you have to add this config.
     * Page Size:
     * @param pageWidth Default to '21cm'.
     * @param pageHeight Default to '29.7cm'.
     * Page Margins:
     * @param top Default to '20mm'.
     * @param bottom Default to '20mm'.
     * @param right Default to '12mm'.
     * @param left Default to '12mm'.
     */
    public void setPagination(String pageWidth, String pageHeight, String top, String bottom, String right, String left) {
        JsonObject pagination = Json.createObject();
        pagination.put("pageWidth", Json.create(Optional.ofNullable(pageWidth).orElse("21cm")));
        pagination.put("pageHeight", Json.create(Optional.ofNullable(pageHeight).orElse("29.7cm")));
        
        JsonObject pageMargins = Json.createObject();
        pageMargins.put("top", Json.create(Optional.ofNullable(top).orElse("20mm")));
        pageMargins.put("bottom", Json.create(Optional.ofNullable(bottom).orElse("20mm")));
        pageMargins.put("right", Json.create(Optional.ofNullable(right).orElse("12mm")));
        pageMargins.put("left", Json.create(Optional.ofNullable(left).orElse("12mm")));
        
        pagination.put("pageMargins", pageMargins);
        configs.put(ConfigType.pagination , pagination);
        setPluginStatus(Plugins.Pagination, true); //Pagination is not enabled initially.
    }

    /**
     * All plugins are enabled by default
     * @param plugin Plugin
     * @param active Plugin status
     */
    public void setPluginStatus(Plugins plugin, boolean active) {
//        JsonArray pluginArray = (JsonArray) configs.get(ConfigType.plugins);
        JsonArray removePluginArray = (JsonArray) configs.get(ConfigType.removePlugins);
//        JsonArray extraPluginArray = (JsonArray) configs.get(ConfigType.extraPlugins);
        updateJsonArray(active, removePluginArray, plugin.name());
    }

    /**
     * Use standard editing mode by invoking this method
     */
    public void enableStandardMode() {
        setPluginStatus(Plugins.StandardEditingMode, false);
        setPluginStatus(Plugins.RestrictedEditingMode, true);
        updateToolbar();
    }

    /**
     * Use restricted editing mode by invoking this method
     */
    public void enableRestrictedMode() {
        setPluginStatus(Plugins.StandardEditingMode, true);
        setPluginStatus(Plugins.RestrictedEditingMode, false);
        updateToolbar();
    }

    private void updateJsonArray(boolean active, JsonArray jsonArray, String name) {
        int index = -1;
        for(int i=0; i<jsonArray.length(); i++) {
            if(name.equals(jsonArray.get(i).asString())){
                index = i;
            }
        }
        if(index>=0) {
            if(active)
                jsonArray.remove(index);
            else
                jsonArray.set(index, name);
        } else {
            if(!active)
                jsonArray.set(jsonArray.length(), name);
        }
    }

    /**
     * Update toolbar accordingly, because there is a conflict between standard edit mode and restrict edit mode.
     * They have different actions in toolbar.
     */
    private void updateToolbar() {
        JsonArray removePluginArray = (JsonArray) configs.get(ConfigType.removePlugins);
        if(removePluginArray!=null) {
            for(int i=0; i<removePluginArray.length(); i++) {
                if(Plugins.RestrictedEditingMode.name().equals(removePluginArray.get(i).asString())) {
                    changeToolbarItem(Toolbar.restrictedEditing, true);
                    changeToolbarItem(Toolbar.restrictedEditingException, false);
                } else if(Plugins.StandardEditingMode.name().equals(removePluginArray.get(i).asString())){
                    changeToolbarItem(Toolbar.restrictedEditing, false);
                    changeToolbarItem(Toolbar.restrictedEditingException, true);
                }
            }
        }
    }

    private void changeToolbarItem(Toolbar toolbar, boolean remove) {
        JsonArray toolbarArray = (JsonArray) configs.get(ConfigType.toolbar);
        updateJsonArray(remove, toolbarArray, toolbar.getValue());
    }

    /**
     * The configuration of the word count feature.
     * @param container Allows for providing the HTML element that the word count container will be appended to automatically.
     * @param displayCharacters This option allows for hiding the character counter. The element obtained through
     *                          wordCountContainer will only preserve the words part. Character counter is displayed
     *                          by default when this configuration option is not defined.
     * @param displayWords This option allows for hiding the word counter. The element obtained through wordCountContainer
     *                     will only preserve the characters part. Word counter is displayed by default when this configuration
     *                     option is not defined.
     * @param onUpdate  This configuration takes a function that is executed whenever the word count plugin updates its
     *                  values. This function is called with one argument, which is an object with the words and characters
     *                  keys containing the number of detected words and characters in the document.
     */
    public void setWordCount(String container, Boolean displayCharacters, Boolean displayWords, JsonObject onUpdate) {
        JsonObject wordCount = Json.createObject();
        wordCount.put("container", Json.create(container));
        wordCount.put("displayCharacters", Json.create(displayCharacters));
        wordCount.put("displayWords", Json.create(displayWords));
        wordCount.put("onUpdate", onUpdate);
        configs.put(ConfigType.wordCount, wordCount);
    }

}
