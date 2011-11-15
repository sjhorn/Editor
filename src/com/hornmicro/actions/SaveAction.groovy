package com.hornmicro.actions

import org.eclipse.jface.action.Action
import org.eclipse.swt.SWT

import com.hornmicro.ui.MainController

class SaveAction extends Action {
    MainController controller
    
    SaveAction(MainController controller) {
        super("&Save")
        setAccelerator(SWT.MOD1 + (int)'S' )
        setToolTipText("Save")
        
        this.controller = controller
    }
    
    void run() {
        if(controller.activeModel.fileName) {
            controller.activeModel.save()
        } else {
            new SaveAsAction(controller).run()
        }
    }
}
