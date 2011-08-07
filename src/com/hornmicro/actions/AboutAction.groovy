package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;


class AboutAction extends Action {
    MainController controller
    
    AboutAction(MainController controller) {
        super("About")
        //setAccelerator(SWT.MOD1 + (int)'Y' )
        setToolTipText("About Text Editor")
        
        this.controller = controller
    }
    
    void run() {
        MessageDialog.openInformation(controller.shell,
            "About", "Text Editor - a cross platform editor");
    }
}
