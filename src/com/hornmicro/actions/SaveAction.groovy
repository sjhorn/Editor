package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;

class SaveAction extends Action {
    MainController controller
    
    SaveAction(MainController controller) {
        super("&Save")
        setAccelerator(SWT.MOD1 + (int)'S' )
        setToolTipText("Save")
        
        this.controller = controller
    }
    
    void run() {
        if(controller.model.fileName) {
            controller.model.save()
        } else {
            new SaveAsAction(controller).run()
        }
    }
}
