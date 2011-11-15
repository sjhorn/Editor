package com.hornmicro.actions

import org.eclipse.jface.action.Action
import org.eclipse.swt.SWT

import com.hornmicro.ui.MainController


class UndoAction extends Action {
    MainController controller
    
    UndoAction(MainController controller) {
        super("&Undo")   
        setAccelerator(SWT.MOD1 + (int)'Z' )
        setToolTipText("Undo")
        
        this.controller = controller
    }

    void run() {
        controller.undoManager.undo()
    }
}
