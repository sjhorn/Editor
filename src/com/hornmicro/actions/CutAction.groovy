package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;

class CutAction extends Action {
    MainController controller
    
    CutAction(MainController controller) {
        super("Cut")
        setAccelerator(SWT.MOD1 + (int)'X' )
        setToolTipText("Cut")
        
        this.controller = controller
    }
    
    void run() {
        controller.sourceViewer.textWidget.cut()    
    }
}
