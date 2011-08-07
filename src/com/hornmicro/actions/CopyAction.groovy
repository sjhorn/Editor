package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;

class CopyAction extends Action {
    MainController controller
    
    CopyAction(MainController controller) {
        super("&Copy")
        setAccelerator(SWT.MOD1 + (int)'C' )
        setToolTipText("Copy")
        
        this.controller = controller
    }
    
    void run() {
        controller.sourceViewer.textWidget.copy()    
    }
}
