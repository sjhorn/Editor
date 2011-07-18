package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class CutAction extends Action {
    CutAction() {
        super("Cut")
        setAccelerator(SWT.MOD1 + (int)'X' )
        setToolTipText("Cut")
    }
    
    void run() {
        TextEditor.APP.view.sourceViewer.textWidget.cut()    
    }
}
