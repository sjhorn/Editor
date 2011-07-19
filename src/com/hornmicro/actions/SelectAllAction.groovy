package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.PersistentDocument;

class SelectAllAction extends Action {
    SelectAllAction() {
        super("Select &All")
        setAccelerator(SWT.MOD1 + (int)'A' )
        setToolTipText("Select All")
    }
    
    void run() {
        SourceViewer viewer = TextEditor.APP.view.sourceViewer
        PersistentDocument doc = TextEditor.APP.model
        viewer.setSelectedRange(0, doc.size)
    }
}
