package com.hornmicro

import org.eclipse.e4.ui.css.swt.CSSSWTConstants
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

import com.hornmicro.ui.css.CSSModule

class Scratch {
    
    static main(args) {
        Display display = new Display()
        
        def cssModule = new CSSModule()
        
        Shell shell = new Shell(display);
        GridLayout layout = new GridLayout()
        shell.setLayout(layout);
        shell.setData(CSSSWTConstants.CSS_ID_KEY, "MainWindow" )
        shell.setData(CSSSWTConstants.MARGIN_WRAPPER_KEY, "true")
        shell.setBackgroundMode(SWT.INHERIT_DEFAULT )
        
        def tabFolder = new CTabFolder(shell, SWT.CLOSE )
        GridDataFactory.fillDefaults().grab( true, true ).applyTo(tabFolder)
        //tabFolder.setRenderer(new CTabRendering(tabFolder))
        tabFolder.setBackgroundMode(SWT.NO_BACKGROUND)
        tabFolder.setData(CSSSWTConstants.CSS_ID_KEY, "Tabs" )
        tabFolder.setLayout(new FillLayout())
        
        def text = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL)
        text.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY, "Editor" )
        text.text = "0 1 3 ! ? & i j l Q q < > \n" * 200
        
        CTabItem tabItem = new CTabItem(tabFolder, SWT.CLOSE)
        tabItem.setText("Test")
        tabItem.setControl(text)

        
        cssModule.parseStyleSheet(CSSModule.class.getResourceAsStream('groovyed.css'))
        cssModule.applyStyles(shell)
        
        shell.pack();
        shell.setSize(600,600)
        shell.open();
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
    
}
