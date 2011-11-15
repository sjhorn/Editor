package com.hornmicro.actions

import org.eclipse.jface.action.Action
import org.eclipse.jface.text.ITextOperationTarget
import org.eclipse.swt.SWT

import com.hornmicro.ui.MainController

class PasteAction extends Action {
    MainController controller
    
    PasteAction(MainController controller) {
        super("Paste")
        setAccelerator(SWT.MOD1 + (int)'V' )
        setToolTipText("Paste")
        
        this.controller = controller
    }
    
    void run() {
        controller
            .activeSourceViewer
            .doOperation(ITextOperationTarget.PASTE)
    }
}
