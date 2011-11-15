package com.hornmicro.actions;

import groovy.mock.interceptor.MockFor
import groovy.util.GroovyTestCase

import java.lang.Thread.UncaughtExceptionHandler

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swtbot.swt.finder.SWTBot
import org.eclipse.swtbot.swt.finder.waits.Conditions

import com.hornmicro.helper.SWTHelper
import com.hornmicro.ui.MainController

class AboutActionTests extends GroovyTestCase {

    Display display
    Shell shell
    
    protected void setUp() throws Exception {
        super.setUp();
        display = Display.default
        shell = new Shell(display, SWT.SHELL_TRIM)
        shell.layout = new FillLayout()
        shell.open()
    }

    protected void tearDown() throws Exception {
        super.tearDown()
        if(display && !display.isDisposed() && shell && !shell.isDisposed()) {
            display.syncExec {
                    shell?.dispose()
                    shell = null
            }
        }
    }

    void testAboutAction() {
        def mock = new MockFor(MainController)
        mock.demand.getShell(1..1) {
            return shell
        }
        def controller = mock.proxyDelegateInstance()
        
        // Run Swtbot on a different thread
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            void uncaughtException(Thread t, Throwable e) {
                // Todo. 
            }
        })
        Thread test = Thread.start {
            try {
                SWTBot bot = new SWTBot()
                bot.waitUntil(Conditions.shellIsActive("About"))
                assert bot.label("Text Editor - a cross platform editor")
                bot.button("OK").click()
            } finally {
                display.syncExec{ display.dispose() }
            }
        }
        
        new AboutAction(controller).run()
        
        SWTHelper.messagePump(test)
    }
}
