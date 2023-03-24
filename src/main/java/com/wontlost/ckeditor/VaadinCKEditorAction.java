package com.wontlost.ckeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VaadinCKEditorAction {

    public static final String AUTOSAVE="autosave";

    private static final Map<String, IsAction> actionRegister = new HashMap<>();

    private static final Logger vaddinCKEditorActionLog = Logger.getLogger(VaadinCKEditorAction.class.getName());

    private VaadinCKEditorAction() {

    }
    /**
     * Should be used with method setAutosave. This is a default action.
     */
    private static final AutosaveAction autosaveAction = new AutosaveAction() {
        public void accept(String editorData) {
            vaddinCKEditorActionLog.log(Level.WARNING, "Saving data [{0}]," +
                    " This is a sample of autosave action. You need to fulfill your own action by " +
                    "extending AutosaveAction. And then register it by " +
                    "registerAction(VaadinCKEditorAction.AUTOSAVE, AutosaveAtion).", editorData);
        }
    };

    public static void registerAction(String name, IsAction action){
        actionRegister.put(name, action);
    }

    static Map<String, IsAction> getActionRegister() {
        actionRegister.putIfAbsent(AUTOSAVE, autosaveAction);
        return actionRegister;
    }

}
