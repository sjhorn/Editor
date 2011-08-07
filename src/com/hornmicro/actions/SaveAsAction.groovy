package com.hornmicro.actions

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;

import com.hornmicro.TextEditor;
import com.hornmicro.ui.MainController;

class SaveAsAction extends Action {
    MainController controller
    
    SaveAsAction(MainController controller) {
        super("Save As")
        setToolTipText("Save As")
        
        this.controller = controller
    }
    
    void run() {
        def model = controller.model
        def shell = controller.shell
        String fileName
        FileDialog dlg = new FileDialog(shell, SWT.SAVE)
        dlg.filterNames = ["Groovy Files (*.groovy)", "All Files (*.*)"]
        dlg.filterExtensions = [ "*.groovy", "*.*"]
        
        boolean done = false
        while (!done) {
            fileName = dlg.open()
            if (fileName == null) {
                done = true;
            } else {
                File file = new File(fileName)
                if (file.exists()) {
                    MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO)
                    mb.setMessage(fileName + " already exists. Do you want to replace it?")
                    done = mb.open() == SWT.YES
                } else {
                    done = true
                }
            }
        }
        if (fileName != null) {
            try {
                model.setFileName(fileName)
                model.save()
            } catch (IOException e) {
                MessageDialog.openError(shell, "Error", "Can't save file ${fileName}; ${e.message}")
            }
        }
    }
}
