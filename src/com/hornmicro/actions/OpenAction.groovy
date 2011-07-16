package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class OpenAction extends Action {
    
    OpenAction() {
        super("&Open")
        setAccelerator(SWT.MOD1 + (int)'O' )
        setToolTipText("Open")
    }
    
    void run() {
        MessageDialog.openInformation(TextEditor.APP.view.shell,
            "Open", "TODO");
    }
}
