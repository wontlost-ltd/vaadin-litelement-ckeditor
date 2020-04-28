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
                <link rel="stylesheet" href="${this.themeType==='dark'?'./theme/custom.css':''}">
                <div id="${this.editorType}-editor"/>
            `;
        }

    }

}

customElements.get('vaadin-ckeditor') || customElements.define('vaadin-ckeditor', VaadinCKEditor);
