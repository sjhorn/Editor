package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;
import com.hornmicro.ui.PersistentDocument;

class SelectAllAction extends Action {
    MainController controller
    
    SelectAllAction(MainController controller) {
        super("Select &All")
        setAccelerator(SWT.MOD1 + (int)'A' )
        setToolTipText("Select All")
        
        this.controller = controller
    }
    
    void run() {
        SourceViewer viewer = controller.sourceViewer
        PersistentDocument doc = controller.model
        viewer.setSelectedRange(0, doc.size)
    }
}
