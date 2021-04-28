import {html, LitElement} from 'lit-element';
import {classMap} from 'lit-html/directives/class-map';
import {BalloonEditor, ClassicEditor, DcoupledEditor, InlineEditor} from './ckeditor';
import Pagination from '@ckeditor/ckeditor5-pagination';

class VaadinCKEditor extends LitElement {

    constructor() {
        super();
        this.classes = {
            'editable-container' : true,
            'document-editor__editable'  : true,
            'ck-editor__editable' : true
        };
        this.editorMap = {};
        this.config = {};
        this.autosave = false;
        this.isFirefox = typeof InstallTrigger !== 'undefined';
        this.isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    }

    // loadLanguage(scriptUrl) {
    //     let script = document.createElement('script');
    //     script.src = scriptUrl;
    //     document.head.appendChild(script);
    //     return new Promise((success, failed) => {
    //         script.onload = function() {
    //             success();
    //         }
    //         script.onerror = function () {
    //             failed();
    //         }
    //     });
    // }

    static get properties() {
        return { editorId: String,
                 label: String,
                 editorType: String,
                 editorData: String,
                 editorWidth: String,
                 editorHeight: String,
                 themeType: String,
                 errorMessage: String,
                 isReadOnly: Boolean,
                 isFirefox: Boolean,
                 isChrome: Boolean,
                 autosave: Boolean,
                 editorMap: Object,
                 config: Object};
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
        this.createEditor();
    }

    getEditorByType(editorType) {
        if(typeof CKEDITOR !== 'undefined' && CKEDITOR !== null) {
            return 'classic'===editorType?CKEDITOR.ClassicEditor:
                   'inline'===editorType?CKEDITOR.InlineEditor:
                   'balloon'===editorType?CKEDITOR.BalloonEditor:
                   'decoupled'===editorType?CKEDITOR.DcoupledEditor:
                   ClassicEditor;
        } else {
            return 'classic'===editorType?ClassicEditor:
                   'inline'===editorType?InlineEditor:
                   'balloon'===editorType?BalloonEditor:
                   'decoupled'===editorType?DcoupledEditor:
                   ClassicEditor;
        }

    }

    getConfig() {//check if contains function
        return this.autosave===true? {
            ...this.config,
            ...{
                autosave: {
                    save( editor ) {
                        return window.saveData( editor.id, editor.getData() );
                    },
                    waitingTime: 2000
                }
            }
        } : this.config;
    }


    createEditor() {
        this.getEditorByType(this.editorType).create(document.querySelector( "#"+this.editorId ) , this.getConfig()).then( editor => {
            editor.id = this.editorId;
            editor.isReadOnly = this.isReadOnly;
            editor.setData(this.editorData?this.editorData:'');
            this.style.width = this.isChrome?'-webkit-fill-available':
                               this.isFirefox?'-moz-available':'100%';
            this.style.height='100%';
            if(this.required) {
                this.showIndicator(true);
            }
            window.server = this.$server;
            editor.editing.view.change( writer => {
                if(this.editorHeight) {
                    writer.setStyle( 'height', this.editorHeight, editor.editing.view.document.getRoot());
                }
                // if(this.editorWidth) {
                //     writer.setStyle( 'width', this.editorWidth, editor.editing.view.document.getRoot());
                // }
            } );
            editor.model.document.on( 'change:data', (event, batch) => {
                this.$server.setEditorData(editor.getData());
                this.showIndicator(''===editor.getData() && this.required);
                this.showError(this.invalid || (''===editor.getData() && this.required));
            } );
            editor.editing.view.document.on( 'change:isFocused', ( evt, data, isFocused ) => {
                this.focusedColor(isFocused);
            } );
            window.saveData = function( editorId, editorData ) {
                return new Promise( resolve => {
                    setTimeout( () => {
                        server.saveEditorData(editorId, editorData);
                        resolve();
                    }, 400 );
                } );
            }
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
        if(this.editorMap && this.editorMap[editorId]) {
            this.editorMap[editorId].setData(value);
        }
    }

    showIndicator(shown) {
        let labelId = 'label_'+this.editorId;
        let newStyle = this.contains('#'+labelId+'::after');
        if(!newStyle) {
            document.head.appendChild(shown?this.opacity(1):this.opacity(0));
        } else {
            newStyle.style.opacity = shown ? 1 : 0;
        }
    }

    showError(shown) {
        let errorId = 'error_'+this.editorId;
        let errorStyle = this.contains('#'+errorId);
        if(!errorStyle) {
            document.head.appendChild(shown?this.display('block'):this.display('none'));
        } else {
            errorStyle.style.display = shown ? 'block' : 'none';
        }
    }

    focusedColor(isFocused) {
        let id = 'label_'+this.editorId;
        let newColor = this.contains('#'+id);
        if(!newColor) {
            document.head.appendChild(isFocused? this.color('var(--lumo-primary-text-color)'):
                this.color('var(--lumo-secondary-text-color)'));
        } else {
            newColor.style.color=isFocused?'var(--lumo-primary-text-color)':'var(--lumo-secondary-text-color)';
        }
    }

    contains(style) {
        const styleSheets = Array.from(document.styleSheets).filter(
            (styleSheet) => !styleSheet.href || styleSheet.href.startsWith(window.location.origin)
        );
        for (let styleSheet of styleSheets) {
            if (styleSheet instanceof CSSStyleSheet && styleSheet.cssRules) {
                for(let cssRule of styleSheet.cssRules) {
                    if(style===cssRule.selectorText) {
                        return cssRule;
                    }
                }
            }
        }
        return false;
    }

    opacity(value) {
        let labelId = 'label_'+this.editorId;
        let newLabelAfter = document.createElement('style');
        newLabelAfter.innerHTML = `#`+labelId+`::after{ opacity:`+value+` }`;
        return newLabelAfter;
    }

    color(value) {
        let labelId = 'label_'+this.editorId;
        let newColor = document.createElement('style');
        newColor.innerHTML = `#`+labelId+`{ color :`+value+` }`;
        return newColor;
    }

    display(value) {
        let errorId = 'error_'+this.editorId;
        let errorStyle = document.createElement('style');
        errorStyle.innerHTML = `#`+errorId+`{ display :`+value+` }`;
        return errorStyle;
    }

    render() {
        return html`
            <label part="label" id="label_${this.editorId}">${this.label} </label>
            <ul part="label-ul"><li part="label-li">
                <div part="error-message" id="error_${this.editorId}">${this.errorMessage}</div>
            </li></ul>
            ${this.editorWidth !== 'auto'? html`
                <style>
                    .ck.ck-editor {
                        width: ${this.editorWidth};
                    }
                </style>`: html``} 
            ${this.editorType==='decoupled' ? html`
                <div class="toolbar-container"></div>
                <div class="editable-container"></div>
                <div id="${this.editorId}" class=${classMap(this.classes)}/>
            `:html`<div id="${this.editorId}"/>`}
        `;
    }

    validate() {
        return !(this.focusElement.invalid = this.invalid);
    };

}

customElements.get('vaadin-ckeditor') || customElements.define('vaadin-ckeditor', VaadinCKEditor);
