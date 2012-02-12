package com.hornmicro;

import org.eclipse.swt.widgets.Display

import com.hornmicro.ui.MainController

public class GroovyEd implements Runnable {
    MainController controller
    
    public GroovyEd() {
        Display.appName = "GroovyEd"
        
        controller = new MainController()
    }

    void run() {
        controller.run()
    }

    static void main(String[] args) {
        new GroovyEd().run();
    }
}
