package com.hornmicro.actions

import org.eclipse.jface.action.Action
import org.eclipse.jface.text.ITextOperationTarget
import org.eclipse.swt.SWT

import com.hornmicro.ui.MainController

class CutAction extends Action {
    MainController controller
    
    CutAction(MainController controller) {
        super("Cut")
        setAccelerator(SWT.MOD1 + (int)'X' )
        setToolTipText("Cut")
        
        this.controller = controller
    }
    
    void run() {
        controller
            .activeSourceViewer
            .doOperation(ITextOperationTarget.CUT)
    }
}
