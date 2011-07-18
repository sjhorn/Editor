package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;

class SelectAllAction extends Action {
    SelectAllAction() {
        super("Select &All")
        setAccelerator(SWT.MOD1 + (int)'A' )
        setToolTipText("Select All")
    }
    
    void run() {
        def shell = TextEditor.APP.view.sourceViewer.textWidget.selectAll()
    }
}
