package com.hornmicro;

import org.eclipse.swt.widgets.Display

import com.hornmicro.ui.MainController
import com.hornmicro.ui.PersistentDocument

public class TextEditor {
    MainController controller
    
    public TextEditor() {
        Display.appName = "Text Editor"
        
        controller = new MainController()
    }

    void run() {
        controller.run()
    }

    static void main(String[] args) {
        new TextEditor().run();
    }
}
