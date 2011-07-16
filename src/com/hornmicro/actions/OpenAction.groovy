package com.hornmicro.actions

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

import com.hornmicro.TextEditor;

class OpenAction extends Action {
    
    OpenAction() {
        super("&Open")
        setAccelerator(SWT.MOD1 + (int)'O' )
        setToolTipText("Open")
    }
    
    void run() {
        def model = TextEditor.APP.model
        def shell = TextEditor.APP.view.shell

        FileDialog dlg = new FileDialog(shell, SWT.OPEN)
        dlg.filterNames = ["Groovy Files (*.groovy)", "All Files (*.*)"]
        dlg.filterExtensions = [ "*.groovy", "*.*"]
        String fileName = dlg.open()
        
        if (fileName != null && checkOverwrite(model, shell) ) {
            try {
                model.clear()
                model.fileName = fileName
                model.open()
            } catch (IOException e) {
                MessageDialog.openError(shell, "Error", e.message);
            }
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
