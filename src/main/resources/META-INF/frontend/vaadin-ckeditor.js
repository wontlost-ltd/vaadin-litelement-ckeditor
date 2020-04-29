import {LitElement, html, css} from "lit-element";
import { classMap } from 'lit-html/directives/class-map';
import { ClassicEditor, InlineEditor, BalloonEditor, DcoupledEditor } from "./ckeditor";

class VaadinCKEditor extends LitElement {

    constructor() {
        super();
        this.classes = {
            'editable-container' : true,
            'document-editor__editable'  : true,
            'ck-editor__editable' : true
        };
        this.isFirefox = typeof InstallTrigger !== 'undefined';
        this.isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    }

    static get styles() {
        return css`
            :host {
                /* Overrides the default font size in the theme. */
                --ck-font-size-base: 14px;

                /* Helper variables to avoid duplication in the colors. */
                --ck-custom-radius:2px;
                --ck-custom-background: hsl(270, 1%, 29%);
                --ck-custom-foreground: hsl(255, 3%, 18%);
                --ck-custom-border: hsl(300, 1%, 22%);
                --ck-custom-white: hsl(0, 0%, 100%);
                --ck-custom-focus-border: hsl(208, 90%, 62%);
                --ck-custom-color-text:hsl(0, 0%, 98%);
                --ck-custom-shadow-drop:hsla(0, 0%, 0%, 0.2);
                --ck-custom-shadow-inner:hsla(0, 0%, 0%, 0.1);
                --ck-custom-default-hover-background:hsl(270, 1%, 22%);
                --ck-custom-default-active-background:hsl(270, 2%, 20%);
                --ck-custom-default-active-shadow:hsl(270, 2%, 23%);
                --ck-custom-on-hover-background:hsl(255, 4%, 16%);
                --ck-custom-on-active-background: hsl(255, 4%, 14%);
                --ck-custom-on-active-shadow: hsl(240, 3%, 19%);
                --ck-custom-action-background: hsl(168, 76%, 42%);
                --ck-custom-action-hover-background: hsl(168, 76%, 38%);
                --ck-custom-action-active-background: hsl(168, 76%, 36%);
                --ck-custom-action-active-shadow: hsl(168, 75%, 34%);
                --ck-custom-action-disabled-background: hsl(168, 76%, 42%);
                --ck-custom-save: hsl(120, 100%, 46%);
                --ck-custom-cancel: hsl(15, 100%, 56%);
                --ck-custom-input-border: hsl(257, 3%, 43%);
                --ck-custom-input-text: hsl(0, 0%, 98%);
                --ck-custom-input-disabled-background: hsl(255, 4%, 21%);
                --ck-custom-input-disabled-border: hsl(250, 3%, 38%);
                --ck-custom-input-disabled-text: hsl(0, 0%, 46%);
                --ck-custom-tooltip-background: hsl(252, 7%, 14%);
                --ck-custom-tooltip-text: hsl(0, 0%, 93%);
                --ck-custom-image-caption-background: hsl(0, 0%, 97%);
                --ck-custom-image-caption-text: hsl(0, 0%, 20%);
                --ck-custom-widget-blurred-border: hsl(0, 0%, 87%);
                --ck-custom-widget-hover-border: hsl(43, 100%, 68%);
                --ck-custom-link-default: hsl(190, 100%, 75%);

                /* Overrides the border radius setting in the theme. */
                --ck-border-radius: var(--ck-custom-radius, 2px);

                /* -- Overrides generic colors. ------------------------------------------------------------- */
                --ck-color-base-foreground: var(--ck-custom-background, #fafafa);
                --ck-color-focus-border: var(--ck-custom-focus-border, #1f89e5);
                --ck-color-text: var(--ck-custom-color-text, #333);
                --ck-color-shadow-drop: var(--ck-custom-shadow-drop, rgba(0,0,0,0.15));
                --ck-color-shadow-inner: var(--ck-custom-shadow-inner, rgba(0,0,0,0.1));

                /* -- Overrides the default .ck-button class colors. ---------------------------------------- */
                --ck-color-button-default-background: var(--ck-custom-background, transparent);
                --ck-color-button-default-hover-background: var(--ck-custom-default-hover-background, #e6e6e6);
                --ck-color-button-default-active-background: var(--ck-custom-default-active-background, #d9d9d9);
                --ck-color-button-default-active-shadow: var(--ck-custom-default-active-shadow, #bfbfbf);
                --ck-color-button-default-disabled-background: var(--ck-custom-background, transparent);

                --ck-color-button-on-background: var(--ck-custom-foreground, #dedede);
                --ck-color-button-on-hover-background: var(--ck-custom-on-hover-background, #c4c4c4);
                --ck-color-button-on-active-background: var(--ck-custom-on-active-background, #bababa);
                --ck-color-button-on-active-shadow: var(--ck-custom-on-active-shadow, #a1a1a1);
                --ck-color-button-on-disabled-background: var(--ck-custom-foreground, #dedede);

                --ck-color-button-action-background: var(--ck-custom-action-background, #61b045);
                --ck-color-button-action-hover-background: var(--ck-custom-action-hover-background, #579e3d);
                --ck-color-button-action-active-background: var(--ck-custom-action-active-background, #53973b);
                --ck-color-button-action-active-shadow: var(--ck-custom-action-active-shadow, #498433);
                --ck-color-button-action-disabled-background: var(--ck-custom-action-disabled-background, #7ec365);
                --ck-color-button-action-text: var(--ck-custom-white);

                --ck-color-button-save: var(--ck-custom-save, #008a00);
                --ck-color-button-cancel: var(--ck-custom-cancel, #db3700);

                /* -- Overrides the default .ck-dropdown class colors. -------------------------------------- */
                --ck-color-dropdown-panel-background: var(--ck-custom-background, #fff);
                --ck-color-dropdown-panel-border: var(--ck-custom-foreground, #c4c4c4);

                /* -- Overrides the default .ck-splitbutton class colors. ----------------------------------- */
                --ck-color-split-button-hover-background: var(--ck-color-button-default-hover-background, #ebebeb);
                --ck-color-split-button-hover-border: var(--ck-custom-foreground, #b3b3b3);

                /* -- Overrides the default .ck-input class colors. ----------------------------------------- */
                --ck-color-input-background: var(--ck-custom-foreground, #fff);
                --ck-color-input-border: var(--ck-custom-input-border, #c7c7c7);
                --ck-color-input-text: var(--ck-custom-input-text, #333);
                --ck-color-input-disabled-background: var(--ck-custom-input-disabled-background, #f2f2f2);
                --ck-color-input-disabled-border: var(--ck-custom-input-disabled-border, #c7c7c7);
                --ck-color-input-disabled-text: var(--ck-custom-input-disabled-text, #5c5c5c);

                /* -- Overrides the default .ck-list class colors. ------------------------------------------ */
                --ck-color-list-background: var(--ck-custom-background, #fff);
                --ck-color-list-button-hover-background: var(--ck-color-base-foreground, #e6e6e6);
                --ck-color-list-button-on-background: var(--ck-color-base-active, #198cf0);
                --ck-color-list-button-on-background-focus: var(--ck-color-base-active-focus, #0e7fe1);
                --ck-color-list-button-on-text: var(--ck-color-base-background, #fff);

                /* -- Overrides the default .ck-balloon-panel class colors. --------------------------------- */
                --ck-color-panel-background: var(--ck-custom-background, #fff);
                --ck-color-panel-border: var(--ck-custom-border, #c4c4c4);

                /* -- Overrides the default .ck-toolbar class colors. --------------------------------------- */
                --ck-color-toolbar-background: var(--ck-custom-background, #fafafa);
                --ck-color-toolbar-border: var(--ck-custom-border, #c4c4c4);

                /* -- Overrides the default .ck-tooltip class colors. --------------------------------------- */
                --ck-color-tooltip-background: var(--ck-custom-tooltip-background, #333);
                --ck-color-tooltip-text: var(--ck-custom-tooltip-text, #fff);

                /* -- Overrides the default colors used by the ckeditor5-image package. --------------------- */
                --ck-color-image-caption-background: var(--ck-custom-image-caption-background, #333);
                --ck-color-image-caption-text: var(--ck-custom-image-caption-text, #fff);

                /* -- Overrides the default colors used by the ckeditor5-widget package. -------------------- */
                --ck-color-widget-blurred-border: var(--ck-custom-widget-blurred-border, #dedede);
                --ck-color-widget-hover-border: var(--ck-custom-widget-hover-border, #ffc83d);
                --ck-color-widget-editable-focus-background: var(--ck-custom-white);

                /* -- Overrides the default colors used by the ckeditor5-link package. ---------------------- */
                --ck-color-link-default: var(--ck-custom-link-default);
            }   
        `;
    }

    static get properties() {
        return { editorType: String,
                 editorData: String,
                 editorWidth: String,
                 editorHeight: String,
                 themeType: String,
                 isReadOnly: Boolean,
                 isFirefox:Boolean,
                 isChrome:Boolean,
                 toolBar: Array};
    }

    createRenderRoot() {
        return this;
    }

    firstUpdated(changedProperties) {
        super.firstUpdated(changedProperties);
        if(this.editorType==='classic') {
            ClassicEditor.create( document.querySelector( '#classic-editor' ) , {
                    toolbar:this.toolBar
                }).then( editor => {
                    editor.isReadOnly = this.isReadOnly;
                    editor.setData(this.editorData);
                    if(this.isChrome)
                        this.style.width='-webkit-fill-available';
                    else if(this.isFirefox)
                        this.style.width='-moz-available';
                    else
                        this.style.width='100%';
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
                    window.editor = editor;
                } ).catch( err => {
                    console.error( err.stack );
                } );
        }else if(this.editorType==='inline') {
            InlineEditor.create( document.querySelector( '#inline-editor' ) , {
                    toolbar:this.toolBar
                }).then( editor => {
                    editor.isReadOnly = this.isReadOnly;
                    editor.setData(this.editorData);
                    if(this.isChrome)
                        this.style.width='-webkit-fill-available';
                    else if(this.isFirefox)
                        this.style.width='-moz-available';
                    else
                        this.style.width='100%';
                    editor.editing.view.change( writer => {
                        if(this.editorHeight) {
                            writer.setStyle( 'height', this.editorHeight, editor.editing.view.document.getRoot());
                        }
                        if(this.editorWidth) {
                            writer.setStyle( 'width', this.editorWidth, editor.editing.view.document.getRoot());
                        }
                    } );
                    editor.model.document.on( 'change:data', () => {
                        this.$server.setEditorData(editor.getData());
                    } );
                    window.editor = editor;
                } ).catch( err => {
                    console.error( err.stack );
                } );
        }else if(this.editorType==='balloon') {
            BalloonEditor.create( document.querySelector( '#balloon-editor' ) , {
                    toolbar:this.toolBar
                }).then( editor => {
                    editor.isReadOnly = this.isReadOnly;
                    editor.setData(this.editorData);
                    if(this.isChrome)
                        this.style.width='-webkit-fill-available';
                    else if(this.isFirefox)
                        this.style.width='-moz-available';
                    else
                        this.style.width='100%';
                    editor.editing.view.change( writer => {
                        if(this.editorHeight) {
                            writer.setStyle( 'height', this.editorHeight, editor.editing.view.document.getRoot());
                        }
                        if(this.editorWidth) {
                            writer.setStyle( 'width', this.editorWidth, editor.editing.view.document.getRoot());
                        }
                    } );
                    editor.model.document.on( 'change:data', () => {
                        this.$server.setEditorData(editor.getData());
                    } );
                    window.editor = editor;
                } ).catch( err => {
                    console.error( err.stack );
                } );
        }else if(this.editorType==='decoupled') {
            DcoupledEditor.create( document.querySelector( '#decoupled-editor' ) , {
                    toolbar:this.toolBar
                }).then( editor => {
                    editor.isReadOnly = this.isReadOnly;
                    editor.setData(this.editorData);
                    if(this.isChrome)
                        this.style.width='-webkit-fill-available';
                    else if(this.isFirefox)
                        this.style.width='-moz-available';
                    else
                        this.style.width='100%';
                    editor.editing.view.change( writer => {
                        if(this.editorHeight) {
                            writer.setStyle( 'height', this.editorHeight, editor.editing.view.document.getRoot());
                        }
                        if(this.editorWidth) {
                            writer.setStyle( 'width', this.editorWidth, editor.editing.view.document.getRoot());
                        }
                    } );
                    editor.model.document.on( 'change:data', () => {
                        this.$server.setEditorData(editor.getData());
                    } );
                    window.editor = editor;
                    document.querySelector( '.toolbar-container' ).appendChild( editor.ui.view.toolbar.element );
                    document.querySelector( '.editable-container' ).appendChild( editor.ui.view.editable.element );
                } ).catch( err => {
                    console.error( err.stack );
                } );
        }
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
                <div id="${this.editorType}-editor" class=${classMap(this.classes)}/>
            `;
        } else {
            return html`
                <div id="${this.editorType}-editor"/>
            `;
        }

    }

}

customElements.get('vaadin-ckeditor') || customElements.define('vaadin-ckeditor', VaadinCKEditor);
