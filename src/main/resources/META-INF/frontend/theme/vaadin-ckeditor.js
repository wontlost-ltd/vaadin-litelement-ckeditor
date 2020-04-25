// import the non-themed component
import '../vaadin-ckeditor.js';

import { html } from '@polymer/polymer/lib/utils/html-tag.js';

// set your custom CSS rules for button.
// Use an unique id for the dom-module.
const $_documentContainer = html`
  <dom-module id="vaadin-ckeditor"
              theme-for="vaadin-ckeditor">
    <template>
      <style>
        :host {
            --ck-highlight-marker-blue: #72cdfd;
            --ck-highlight-marker-green: #63f963;
            --ck-highlight-marker-pink: #fc7999;
            --ck-highlight-marker-yellow: #fdfd77;
            --ck-highlight-pen-green: #118800;
            --ck-highlight-pen-red: #e91313;
            --ck-image-style-spacing: 1.5em;
            --ck-todo-list-checkmark-size: 16px;
            --ck-border-radius: 4px;
            --ck-font-size-base: 14px;
            --ck-custom-background: hsl(270, 1%, 29%);
            --ck-custom-foreground: hsl(255, 3%, 18%);
            --ck-custom-border: hsl(300, 1%, 22%);
            --ck-custom-white: hsl(0, 0%, 100%);
            --ck-color-base-foreground: var(--ck-custom-background);
            --ck-color-focus-border: hsl(208, 90%, 62%);
            --ck-color-text: hsl(0, 0%, 98%);
            --ck-color-shadow-drop: hsla(0, 0%, 0%, 0.2);
            --ck-color-shadow-inner: hsla(0, 0%, 0%, 0.1);
            --ck-color-button-default-background: var(--ck-custom-background);
            --ck-color-button-default-hover-background: hsl(270, 1%, 22%);
            --ck-color-button-default-active-background: hsl(270, 2%, 20%);
            --ck-color-button-default-active-shadow: hsl(270, 2%, 23%);
            --ck-color-button-default-disabled-background: var(--ck-custom-background);
            --ck-color-button-on-background: var(--ck-custom-foreground);
            --ck-color-button-on-hover-background: hsl(255, 4%, 16%);
            --ck-color-button-on-active-background: hsl(255, 4%, 14%);
            --ck-color-button-on-active-shadow: hsl(240, 3%, 19%);
            --ck-color-button-on-disabled-background: var(--ck-custom-foreground);
            --ck-color-button-action-background: hsl(168, 76%, 42%);
            --ck-color-button-action-hover-background: hsl(168, 76%, 38%);
            --ck-color-button-action-active-background: hsl(168, 76%, 36%);
            --ck-color-button-action-active-shadow: hsl(168, 75%, 34%);
            --ck-color-button-action-disabled-background: hsl(168, 76%, 42%);
            --ck-color-button-action-text: var(--ck-custom-white);
            --ck-color-button-save: hsl(120, 100%, 46%);
            --ck-color-button-cancel: hsl(15, 100%, 56%);
            --ck-color-dropdown-panel-background: var(--ck-custom-background);
            --ck-color-dropdown-panel-border: var(--ck-custom-foreground);
            --ck-color-split-button-hover-background: var(--ck-color-button-default-hover-background);
            --ck-color-split-button-hover-border: var(--ck-custom-foreground);
            --ck-color-input-background: var(--ck-custom-foreground);
            --ck-color-input-border: hsl(257, 3%, 43%);
            --ck-color-input-text: hsl(0, 0%, 98%);
            --ck-color-input-disabled-background: hsl(255, 4%, 21%);
            --ck-color-input-disabled-border: hsl(250, 3%, 38%);
            --ck-color-input-disabled-text: hsl(0, 0%, 46%);
            --ck-color-list-background: var(--ck-custom-background);
            --ck-color-list-button-hover-background: var(--ck-color-base-foreground);
            --ck-color-list-button-on-background: var(--ck-custom-background);
            --ck-color-list-button-on-background-focus: var(--ck-custom-white);
            --ck-color-list-button-on-text: var(--ck-custom-background);
            --ck-color-panel-background: var(--ck-custom-background);
            --ck-color-panel-border: var(--ck-custom-border);
            --ck-color-toolbar-background: var(--ck-custom-background);
            --ck-color-toolbar-border: var(--ck-custom-border);
            --ck-color-tooltip-background: hsl(252, 7%, 14%);
            --ck-color-tooltip-text: hsl(0, 0%, 93%);
            --ck-color-image-caption-background: hsl(0, 0%, 97%);
            --ck-color-image-caption-text: hsl(0, 0%, 20%);
            --ck-color-widget-blurred-border: hsl(0, 0%, 87%);
            --ck-color-widget-hover-border: hsl(43, 100%, 68%);
            --ck-color-widget-editable-focus-background: var(--ck-custom-white);
            --ck-color-link-default: hsl(190, 100%, 75%);
        }
      </style>
    </template>
  </dom-module>`;

document.head.appendChild($_documentContainer.content);