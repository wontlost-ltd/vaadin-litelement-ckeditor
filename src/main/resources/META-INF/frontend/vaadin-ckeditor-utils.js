window.vaadinCKEditor = window.vaadinCKEditor || {};
window.vaadinCKEditor.serverMap = window.vaadinCKEditor.serverMap || {};
window.vaadinCKEditor.sourceDataObserverMap= window.vaadinCKEditor.sourceDataObserverMap || {};
window.vaadinCKEditor.editorMap = window.vaadinCKEditor.editorMap || {};
window.vaadinCKEditor.empty = ['', '<p>&nbsp;</p>'];

window.vaadinCKEditor.contains = function(style) {
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

window.vaadinCKEditor.saveData = function( editorId, editorData ) {
    return new Promise( resolve => {
        setTimeout( () => {
            window.vaadinCKEditor.serverMap[editorId].saveEditorData(editorData);
            resolve();
        }, 400 );
    } );
}

window.vaadinCKEditor.showIndicator = function(editor, shown) {
    let labelId = 'label_'+editor.id;
    let newStyle = window.vaadinCKEditor.contains('#'+labelId+'::after');
    if(!newStyle) {
        document.head.appendChild(shown?window.vaadinCKEditor.opacity(editor,1):window.vaadinCKEditor.opacity(editor,0));
    } else {
        newStyle.style.opacity = shown ? 1 : 0;
    }
}

window.vaadinCKEditor.showError = function(editor, shown) {
    let errorId = 'error_'+editor.id;
    let errorStyle = window.vaadinCKEditor.contains('#'+errorId);
    if(!errorStyle) {
        document.head.appendChild(shown?window.vaadinCKEditor.display(editor,'block'):window.vaadinCKEditor.display(editor,'none'));
    } else {
        errorStyle.style.display = shown ? 'block' : 'none';
    }
}

window.vaadinCKEditor.focusedColor = function(editor, isFocused) {
    let id = 'label_'+editor.id;
    let newColor = window.vaadinCKEditor.contains('#'+id);
    if(!newColor) {
        document.head.appendChild(isFocused? window.vaadinCKEditor.color(editor, 'var(--lumo-primary-text-color)'):
            window.vaadinCKEditor.color(editor, 'var(--lumo-secondary-text-color)'));
    } else {
        newColor.style.color=isFocused?'var(--lumo-primary-text-color)':'var(--lumo-secondary-text-color)';
    }
}

window.vaadinCKEditor.opacity = function(editor, value) {
    let labelId = 'label_'+editor.id;
    let newLabelAfter = document.createElement('style');
    newLabelAfter.innerHTML = `#`+labelId+`::after{ opacity:`+value+` }`;
    return newLabelAfter;
}

window.vaadinCKEditor.color = function(editor, value) {
    let labelId = 'label_'+editor.id;
    let newColor = document.createElement('style');
    newColor.innerHTML = `#`+labelId+`{ color :`+value+` }`;
    return newColor;
}

window.vaadinCKEditor.display = function(editor, value) {
    let errorId = 'error_'+editor.id;
    let errorStyle = document.createElement('style');
    errorStyle.innerHTML = `#`+errorId+`{ display :`+value+` }`;
    return errorStyle;
}

window.vaadinCKEditor.setAndCheck = function(editor, value, required, invalid) {
    console.log("=============>"+value);
    // editor.setData(value);
    window.vaadinCKEditor.serverMap[editor.id].setEditorData(value);
    window.vaadinCKEditor.showIndicator(editor, ''===value && required);
    window.vaadinCKEditor.showError(editor,invalid || (''===value && required));
}

window.vaadinCKEditor.initEditorObserver = function(editor, required, invalid) {
    if(!window.vaadinCKEditor.sourceDataObserverMap[editor.id]) {
        window.vaadinCKEditor.sourceDataObserverMap[editor.id] = new MutationObserver(function(mutations) {
            mutations.forEach(function(mutation) {
                if (mutation.type === "attributes" && mutation.attributeName==="data-value") {
                    const data = mutation.target.getAttribute(mutation.attributeName);
                    window.vaadinCKEditor.setAndCheck(editor, data, required, invalid);
                }
            });
        });
    }
}

// window.vaadinCKEditor.importStyle = async function(editor, cssUrl) {
//         let editorStyle = editor.querySelector("style[override-cke='true']");
//         if(!editorStyle) {
//             console.log("read file from " + cssUrl);
//             let newStyle = document.createElement("style");
//             newStyle.setAttribute('override-cke', 'true');
//             const reader = new FileReader();
//             reader.addEventListener(
//                 "load",
//                 () => {
//                     // this will then display a text file
//                     newStyle.innerText = ''+reader.result;
// 					editor.appendChild(newStyle);
//                 },
//                 false,
//             );
//             if (cssUrl) {
//                 (await fetch(cssUrl)).blob().then(b=>reader.readAsText(b));
//             }
//         }
// }

window.vaadinCKEditor.sourceEditObserver = function(editor) {
    window.vaadinCKEditor.initEditorObserver(editor);
    let sourceEditButtonInToolbar = document.querySelector("#"+editor.id).parentElement.querySelector("button.ck-source-editing-button");
    if(sourceEditButtonInToolbar) {
        sourceEditButtonInToolbar.onclick = (evt) => {
            let sourceEdit = document.querySelector("#"+editor.id).parentElement.querySelector("div.ck-source-editing-area");
            if(sourceEdit) {
                window.vaadinCKEditor.initEditorObserver(editor);
                window.vaadinCKEditor.sourceDataObserverMap[editor.id].observe(sourceEdit, {
                    attributes: true //configure it to listen to attribute changes
                });
            }
        }
    }
}

