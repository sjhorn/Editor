package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;

class PreferenceAction extends Action {
    MainController controller
    
    PreferenceAction(MainController controller) {
        super("&Preferences")
        //setAccelerator(SWT.MOD1 + (int)'Y' )
        setToolTipText("Preferences")
        
        this.controller = controller
    }
    
    void run() {
        MessageDialog.openInformation(controller.shell,
            "Preferences", "TODO");
    }
}
