package com.hornmicro.actions

import org.eclipse.jface.action.Action
import org.eclipse.swt.SWT

import com.hornmicro.ui.MainController

class RedoAction extends Action {
    MainController controller
    
    RedoAction(MainController controller) {
        super("&Redo")
        setAccelerator(SWT.MOD1 + (int)'Y' )
        setToolTipText("Redo")
        
        this.controller = controller
    }

    void run() {
        controller.undoManager.redo()    
    }
}
