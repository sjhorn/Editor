package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.hornmicro.TextEditor;

class PreferenceAction extends Action {
    PreferenceAction() {
        super("&Preferences")
        //setAccelerator(SWT.MOD1 + (int)'Y' )
        setToolTipText("Preferences")
    }
    
    void run() {
        MessageDialog.openInformation(TextEditor.APP.view.shell,
            "Preferences", "TODO");
    }
}
