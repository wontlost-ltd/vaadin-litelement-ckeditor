import {html, LitElement} from 'lit';
import {classMap} from 'lit/directives/class-map.js';
import * as EDITORS from './ckeditor';

export class VaadinCKEditor extends LitElement {

    constructor() {
        super();
        this.classes = {
            'editor-content'  : true
        };
        this.editorMap = {};
        this.config = {};
        this.version = 'v3.2.0';
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
                 miniMapEnabled:Boolean,
                 isReadOnly: Boolean,
                 isFirefox: Boolean,
                 isChrome: Boolean,
                 autosave: Boolean,
                 ghsEnabled: Boolean,
                 hideToolbar: Boolean,
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
            return 'classic'===editorType?CKEDITOR.VaadinClassicEditor:
                   'inline'===editorType?CKEDITOR.VaadinInlineEditor:
                   'balloon'===editorType?CKEDITOR.VaadinBalloonEditor:
                   'decoupled'===editorType?CKEDITOR.VaadinDcoupledEditor:
                   ClassicEditor;
        } else {
            return 'classic'===editorType?EDITORS.CKEDITOR.VaadinClassicEditor:
                   'inline'===editorType?EDITORS.CKEDITOR.VaadinInlineEditor:
                   'balloon'===editorType?EDITORS.CKEDITOR.VaadinBalloonEditor:
                   'decoupled'===editorType?EDITORS.CKEDITOR.VaadinDcoupledEditor:
                   EDITORS.CKEDITOR.VaadinClassicEditor;
        }

    }

    getConfig() {//check if contains function
        let configuration = this.autosave===true? {
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
        configuration = this.ghsEnabled===true? {
            ...configuration,
            ...{
                htmlSupport: {
                    allow: [
                        {
                            name: /.*/,
                            attributes: true,
                            classes: true,
                            styles: true
                        }
                    ]
                }
            }
        } : configuration;
        let minimap = {
                minimap: {
                    container: document.querySelector( '.minimap-container' )
            }
        };
        if(this.editorType==='decoupled') {
            return {
                ...minimap,
                ...configuration
            };
        } else {
            return configuration;
        }
    }


    createEditor() {
        this.getEditorByType(this.editorType).create(document.querySelector( "#"+this.editorId ) , this.getConfig()).then( editor => {
            editor.id = this.editorId;
            const toolbar = editor.ui.view.toolbar;
            if(this.isReadOnly) {
                editor.enableReadOnlyMode( this.editorId );
            } else {
                editor.disableReadOnlyMode(this.editorId);
            }
            if(toolbar) {
                if(this.hideToolbar) {
                    toolbar.element.style.display = 'none';
                } else {
                    toolbar.element.style.display = 'flex';
                }
            }
            console.log({...{"ckeditor-vaadin":this.version},
                         ...this.getConfig()});
            if(this.editorType === 'classic' && typeof editor.ui.element.children[1] !== 'undefined') {
                editor.ui.element.children[1].style.position='sticky';
                editor.ui.element.children[1].style.top=0;
                editor.ui.element.children[1].style.boxShadow='0 1.5px 1px -1px darkgrey';
                editor.ui.element.children[1].style.zIndex=2;
            }
            editor.setData(this.editorData?this.editorData:'');
            this.style.width = this.style.width ? this.style.width :
                               this.isChrome ? '-webkit-fill-available':
                               this.isFirefox ? '-moz-available': '100%';
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
            editor.editing.view.document.on( 'change:isFocused', ( evt, data, isFocused ) => {
                if(this.editorType === 'classic') {
                    editor.ui.view.stickyPanel.element.children[0].style.display="none";
                    editor.ui.view.stickyPanel.element.children[1].classList.remove("ck-sticky-panel__content_sticky");
                    editor.ui.view.stickyPanel.element.children[1].style = "";
                }
            } );
            editor.model.document.on( 'change:data', (event, batch) => {
                this.$server.setEditorData(editor.getData());
                this.showIndicator(''===editor.getData() && this.required);
                this.showError(this.invalid || (''===editor.getData() && this.required));
                // if (typeof editor.ui.view.stickyPanel !== 'undefined'
                //     && typeof editor.ui.view.stickyPanel.isSticky !== 'undefined') {
                //     editor.ui.view.stickyPanel.isSticky = true;
                // }
                // console.log('*================' + window.document.documentElement.scrollTop);
            } );
            editor.editing.view.document.on( 'change:isFocused', ( evt, data, isFocused ) => {
                this.focusedColor(isFocused);
            } );

            editor.on( 'change:isReadOnly', ( evt, propertyName, isReadOnly ) => {
                if ( isReadOnly ) {
                    editor.enableReadOnlyMode( this.editorId );
                } else {
                    editor.disableReadOnlyMode(this.editorId);
                }
                if(toolbar) {
                    if ( this.hideToolbar ) {
                        toolbar.element.style.display = 'none';
                    } else {
                        toolbar.element.style.display = 'flex';
                    }
                }
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
                document.querySelector( '#toolbar-container' ).appendChild( editor.ui.view.toolbar.element );
                // document.querySelector( "#"+this.editorId ).appendChild( editor.ui.view.editable.element );
                editor.ui.update();
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

    fire(editorId, event, property, value) {
        if(this.editorMap && this.editorMap[editorId]) {
            this.editorMap[editorId].fire(event, property, value);
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

//     ${this.editorWidth !== 'auto'? html`
//                 <style>
//                     .ck.ck-editor {
//                         width: ${this.editorWidth};
//      }
//      </style>`: html``}
    render() {
        return html`
            <label part="label" id="label_${this.editorId}">${this.label} </label>
            <ul part="label-ul"><li part="label-li">
                <div part="error-message" id="error_${this.editorId}">${this.errorMessage}</div>
            </li></ul>
            
            ${this.editorType==='decoupled' ? html`
                <div id="document-container">
                    <div id="toolbar-container">
                        <!-- This is where the document editor toolbar will be inserted. -->
                    </div>
                    <div class="minimap-wrapper">
                        <div class="editor-container">
                            <div id="${this.editorId}" class=${classMap(this.classes)}>
                                <!-- This is where the edited content will render (the page). -->
                            </div>
                        </div>
                        <div class="minimap-container" style="display: ${ this.miniMapEnabled ? 'block' : 'none' }">
                            <!-- This is where the minimap will be inserted. -->
                        </div>
                    </div>
                </div>
            `:html`<div id="${this.editorId}"/>`}
        `;
    }

    validate() {
        return !(this.focusElement.invalid = this.invalid);
    };

}

customElements.get('vaadin-ckeditor') || customElements.define('vaadin-ckeditor', VaadinCKEditor);
