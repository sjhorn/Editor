package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;

class CloseAction extends Action {
    MainController controller
    
    CloseAction(MainController controller) {
        super("Close")
        setAccelerator(SWT.MOD1 + (int)'W' )
        setToolTipText("Close")
        
        this.controller = controller
    }
    
    void run() {
        def model = controller.model
        def shell = controller.shell
        if(checkOverwrite(model, shell)) {
            model.clear()
        }
    }

    boolean checkOverwrite(model, shell) {
        def proceed = true;
        if (model.isDirty()) {
            proceed = MessageDialog.openConfirm(shell, "Are you sure?",
                "You have unsaved changes, are you sure you want to lose them?")
        }
        return proceed;
    }
}
