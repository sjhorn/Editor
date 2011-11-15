package com.hornmicro.actions

import org.eclipse.jface.action.Action
import org.eclipse.jface.text.ITextOperationTarget
import org.eclipse.swt.SWT

import com.hornmicro.ui.MainController

class SelectAllAction extends Action {
    MainController controller
    
    SelectAllAction(MainController controller) {
        super("Select &All")
        setAccelerator(SWT.MOD1 + (int)'A' )
        setToolTipText("Select All")
        
        this.controller = controller
    }
    
    void run() {
        controller
            .activeSourceViewer
            .doOperation(ITextOperationTarget.SELECT_ALL)
    }
}
