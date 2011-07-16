package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class CloseAction extends Action {
    CloseAction() {
        super("Close")
        setAccelerator(SWT.MOD1 + (int)'W' )
        setToolTipText("Close")
    }
    
    void run() {
        def model = TextEditor.APP.model
        def shell = TextEditor.APP.view.shell
        if(checkOverwrite(model, shell)) {
            model.clear()
        }
    }

    boolean checkOverwrite(model, shell) {
        def proceed = true;
        if (model.isDirty()) {
            proceed = MessageDialog.openConfirm(shell, "Are you sure?",
            "You have unsaved changes, are you sure you want to lose them?");
        }
        return proceed;
    }
}
