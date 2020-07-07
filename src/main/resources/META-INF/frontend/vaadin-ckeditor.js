import { LitElement, html } from "lit-element";
import { classMap } from 'lit-html/directives/class-map';
import * as EDITOR from './ckeditor';

class VaadinCKEditor extends LitElement {

    constructor() {
        super();
        this.classes = {
            'editable-container' : true,
            'document-editor__editable'  : true,
            'ck-editor__editable' : true
        };
        this.editorMap = {};
        this.isFirefox = typeof InstallTrigger !== 'undefined';
        this.isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    }

    loadLanguage(scriptUrl) {
        let script = document.createElement('script');
        script.src = scriptUrl;
        document.head.appendChild(script);
        return new Promise((success, failed) => {
            script.onload = function() {
                success();
            }
            script.onerror = function () {
                failed();
            }
        });
    }

    static get properties() {
        return { editorId: String,
                 editorType: String,
                 editorData: String,
                 editorWidth: String,
                 editorHeight: String,
                 themeType: String,
                 themeCss: String,
                 placeHolder: String,
                 uiLanguage: String,
                 isReadOnly: Boolean,
                 isFirefox: Boolean,
                 isChrome: Boolean,
                 editorMap: Object,
                 toolBar: Array};
    }

    createRenderRoot() {
        return this;
    }

    initDarkTheme() {
        let darkStyle = document.querySelector(':root').style;
        darkStyle.setProperty('--ck-color-base-foreground', 'hsl(270, 1%, 29%)');
        darkStyle.setProperty('--ck-color-base-background', 'hsl(0, 0%, 77%)');
        darkStyle.setProperty('--ck-color-focus-border', 'hsl(208, 90%, 62%)');
        darkStyle.setProperty('--ck-color-text', 'hsl(0, 0%, 98%)');
        darkStyle.setProperty('--ck-color-shadow-drop', 'hsla(0, 0%, 0%, 0.2)');
        darkStyle.setProperty('--ck-color-shadow-inner', 'hsla(0, 0%, 0%, 0.1)');
        darkStyle.setProperty('--ck-color-button-default-background', 'hsl(270, 1%, 29%)');
        darkStyle.setProperty('--ck-color-button-default-hover-background', 'hsl(270, 1%, 22%)');
        darkStyle.setProperty('--ck-color-button-default-active-background', 'hsl(270, 2%, 20%)');
        darkStyle.setProperty('--ck-color-button-default-active-shadow', 'hsl(270, 2%, 23%)');
        darkStyle.setProperty('--ck-color-button-default-disabled-background', 'hsl(270, 1%, 29%)');
        darkStyle.setProperty('--ck-color-button-on-background', 'hsl(255, 3%, 18%)');
        darkStyle.setProperty('--ck-color-button-on-hover-background', 'hsl(255, 4%, 16%)');
        darkStyle.setProperty('--ck-color-button-on-active-background', 'hsl(255, 4%, 14%)');
        darkStyle.setProperty('--ck-color-button-on-active-shadow', 'hsl(240, 3%, 19%)');
        darkStyle.setProperty('--ck-color-button-on-disabled-background', 'hsl(255, 3%, 18%)');
        darkStyle.setProperty('--ck-color-button-action-background', 'hsl(168, 76%, 42%)');
        darkStyle.setProperty('--ck-color-button-action-hover-background', 'hsl(168, 76%, 38%)');
        darkStyle.setProperty('--ck-color-button-action-active-background', 'hsl(168, 76%, 36%)');
        darkStyle.setProperty('--ck-color-button-action-active-shadow', 'hsl(168, 75%, 34%)');
        darkStyle.setProperty('--ck-color-button-action-disabled-background', 'hsl(168, 76%, 42%)');
        darkStyle.setProperty('--ck-color-button-action-text', 'hsl(0, 0%, 100%)');
        darkStyle.setProperty('--ck-color-button-save', 'hsl(120, 100%, 46%)');
        darkStyle.setProperty('--ck-color-button-cancel', 'hsl(15, 100%, 56%)');
        darkStyle.setProperty('--ck-color-dropdown-panel-background', 'hsl(270, 1%, 29%)');
        darkStyle.setProperty('--ck-color-dropdown-panel-border', 'hsl(255, 3%, 18%)');
        darkStyle.setProperty('--ck-color-split-button-hover-border', 'hsl(255, 3%, 18%)');
        darkStyle.setProperty('--ck-color-input-background', 'hsl(255, 3%, 18%)');
        darkStyle.setProperty('--ck-color-input-border', 'hsl(257, 3%, 43%)');
        darkStyle.setProperty('--ck-color-input-text', 'hsl(0, 0%, 98%)');
        darkStyle.setProperty('--ck-color-input-disabled-background', 'hsl(255, 4%, 21%)');
        darkStyle.setProperty('--ck-color-input-disabled-border', 'hsl(250, 3%, 38%)');
        darkStyle.setProperty('--ck-color-input-disabled-text', 'hsl(0, 0%, 46%)');
        darkStyle.setProperty('--ck-color-list-background', 'hsl(270, 1%, 29%)');
        darkStyle.setProperty('--ck-color-panel-background', 'hsl(270, 1%, 29%)');
        darkStyle.setProperty('--ck-color-panel-border', 'hsl(300, 1%, 22%)');
        darkStyle.setProperty('--ck-color-toolbar-background', 'hsl(270, 1%, 29%)');
        darkStyle.setProperty('--ck-color-toolbar-border', 'hsl(300, 1%, 22%)');
        darkStyle.setProperty('--ck-color-tooltip-background', 'hsl(252, 7%, 14%)');
        darkStyle.setProperty('--ck-color-tooltip-text', 'hsl(0, 0%, 93%)');
        darkStyle.setProperty('--ck-color-image-caption-background', 'hsl(0, 0%, 97%)');
        darkStyle.setProperty('--ck-color-image-caption-text', 'hsl(0, 0%, 20%)');
        darkStyle.setProperty('--ck-color-widget-blurred-border', 'hsl(0, 0%, 87%)');
        darkStyle.setProperty('--ck-color-widget-hover-border', 'hsl(43, 100%, 68%)');
        darkStyle.setProperty('--ck-color-widget-editable-focus-background', 'hsl(0, 0%, 100%)');
        darkStyle.setProperty('--ck-color-link-default', 'hsl(190, 100%, 75%)');
    }

    firstUpdated(changedProperties) {
        super.firstUpdated(changedProperties);

        if(this.themeType==='dark') {
            this.initDarkTheme();
        }

        let scriptUrl = 'https://www.wontlost.com/translations/'+this.uiLanguage+'.js';
        if('en'!==this.uiLanguage && document.querySelectorAll('[src="' + scriptUrl + '"]').length === 0) {
            this.loadLanguage(scriptUrl).then(() => {
                console.log('Language \''+this.uiLanguage+'\' loaded! Initilizing the ckeditor...');
                this.createEditor();
            }).catch(() => {
                console.error('Language loading failed!');
            });
        } else {
            this.createEditor();
        }

    }

    getEditorByType(editorType) {
        try {//for prodcution mode
            return 'classic'===editorType?CKEDITOR.ClassicEditor:
                   'inline'===editorType?CKEDITOR.InlineEditor:
                   'balloon'===editorType?CKEDITOR.BalloonEditor:
                   'decoupled'===editorType?CKEDITOR.DcoupledEditor:CKEDITOR.ClassicEditor;
        }catch (e) {//for development mode
            console.log("development mode: " + e );
            return 'classic'===editorType?EDITOR.ClassicEditor:
                   'inline'===editorType?EDITOR.InlineEditor:
                   'balloon'===editorType?EDITOR.BalloonEditor:
                   'decoupled'===editorType?EDITOR.DcoupledEditor:editor.ClassicEditor;
        }
    }

    createEditor() {
        this.getEditorByType(this.editorType).create(document.querySelector( "#"+this.editorId ) , {
            toolbar:this.toolBar,
            placeholder:this.placeHolder,
            language: this.uiLanguage
        }).then( editor => {
            editor.isReadOnly = this.isReadOnly;
            editor.setData(this.editorData);
            if(this.isChrome)
                this.style.width='-webkit-fill-available';
            else if(this.isFirefox)
                this.style.width='-moz-available';
            else
                this.style.width='100%';
            this.style.height='100%';
            editor.editing.view.change( writer => {
                if(this.editorHeight) {
                    writer.setStyle( 'height', this.editorHeight, editor.editing.view.document.getRoot());
                }
                if(this.editorWidth) {
                    writer.setStyle( 'width', this.editorWidth, editor.editing.view.document.getRoot());
                }
            } );
            editor.model.document.on( 'change:data', (event, batch) => {
                this.$server.setEditorData(editor.getData());
            } );
            this.editorMap[this.editorId] = editor;
            if(this.editorType==='decoupled') {
                document.querySelector( '.toolbar-container' ).appendChild( editor.ui.view.toolbar.element );
                document.querySelector( '.editable-container' ).appendChild( editor.ui.view.editable.element );
            }
        } ).catch( err => {
            console.error( err.stack );
        } );
    }

    updateData(editorId, value) {
        this.editorMap[editorId].setData(value);
    }

    render() {
        if(this.editorType==='decoupled') {
            return html`
                <div class="toolbar-container"></div>
                <div class="editable-container"></div>
                <style>
                    .editable-container,
                    .toolbar-container {
                        position: relative;
                        border: 1px solid #ddd;
                        background: #eee;
                    }
            
                    .toolbar-container {
                        padding: 1em;
                    }
            
                    .editable-container {
                        padding: 3em;
                        overflow-y: scroll;
                        max-height: 500px;
                    }
            
                    .editable-container .document-editor__editable.ck-editor__editable {
                        min-height: 21cm;
                        padding: 2em;
                        border: 1px #D3D3D3 solid;
                        border-radius: var(--ck-border-radius);
                        background: white;
                        box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
                    }
                </style>
                <div id="${this.editorId}" class=${classMap(this.classes)}/>
            `;
        } else {
            return html`
                <div id="${this.editorId}"/>
            `;
        }

    }

}

customElements.get('vaadin-ckeditor') || customElements.define('vaadin-ckeditor', VaadinCKEditor);
