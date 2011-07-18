package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class PasteAction extends Action {
    PasteAction() {
        super("Paste")
        setAccelerator(SWT.MOD1 + (int)'V' )
        setToolTipText("Paste")
    }
    
    void run() {
        TextEditor.APP.view.sourceViewer.textWidget.paste()    
    }
}
