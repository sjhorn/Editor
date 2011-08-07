package com.hornmicro.actions

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;

class PasteAction extends Action {
    MainController controller
    
    PasteAction(MainController controller) {
        super("Paste")
        setAccelerator(SWT.MOD1 + (int)'V' )
        setToolTipText("Paste")
        
        this.controller = controller
    }
    
    void run() {
        controller.sourceViewer.textWidget.paste()    
    }
}
