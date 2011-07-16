package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class SaveAction extends Action {
    SaveAction() {
        super("&Save")
        setAccelerator(SWT.MOD1 + (int)'S' )
        setToolTipText("Save")
    }
    
    void run() {
        MessageDialog.openInformation(TextEditor.APP.view.shell,
            "Save", "TODO");
    }
}
