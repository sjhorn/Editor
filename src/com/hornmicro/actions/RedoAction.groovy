package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class RedoAction extends Action {
    
    RedoAction() {
        super("&Redo")
        setAccelerator(SWT.MOD1 + (int)'Y' )
        setToolTipText("Redo")
    }

    void run() {
        TextEditor.APP.view.undoManager.redo()    
    }
}
