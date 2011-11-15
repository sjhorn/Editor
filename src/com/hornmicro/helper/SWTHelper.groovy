package com.hornmicro.helper

import org.eclipse.swt.widgets.Display



class SWTHelper {
    static messagePump() {
        def display= Display.default
        display?.syncExec {
            def shell = display.activeShell
            
            while (display && shell && !display.isDisposed() && !shell.isDisposed()) {
                if (!display.readAndDispatch()) {
                    display.sleep()
                }
                
            }
        }
        display?.dispose()
    }
}
