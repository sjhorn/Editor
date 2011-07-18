package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class CopyAction extends Action {
    CopyAction() {
        super("&Copy")
        setAccelerator(SWT.MOD1 + (int)'C' )
        setToolTipText("Copy")
    }
    
    void run() {
        TextEditor.APP.view.sourceViewer.textWidget.copy()    
    }
}
