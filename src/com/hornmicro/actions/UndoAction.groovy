package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;


class UndoAction extends Action {
    
    UndoAction() {
        super("&Undo")   
        setAccelerator(SWT.MOD1 + (int)'Z' )
        setToolTipText("Undo")
    }

    void run() {
        TextEditor.APP.view.undoManager.undo()
    }
}
