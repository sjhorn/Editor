package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.hornmicro.TextEditor;


class AboutAction extends Action {
    AboutAction() {
        super("About")
        //setAccelerator(SWT.MOD1 + (int)'Y' )
        setToolTipText("About Text Editor")
    }
    
    void run() {
        MessageDialog.openInformation(TextEditor.APP.view.shell,
            "About", "Text Editor - a cross platform editor");
    }
}