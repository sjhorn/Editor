package com.hornmicro.ui

import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.Document
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.source.VerticalRuler;

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell

import com.hornmicro.CocoaUIEnhancer;
import com.hornmicro.actions.AboutAction;
import com.hornmicro.actions.CloseAction;
import com.hornmicro.actions.OpenAction;
import com.hornmicro.actions.PreferenceAction;
import com.hornmicro.actions.RedoAction;
import com.hornmicro.actions.SaveAction;
import com.hornmicro.actions.UndoAction;

class MainView extends ApplicationWindow {
    SourceViewer sourceViewer
    Document model 
    SourceViewerConfiguration config
    TextViewerUndoManager undoManager
    
    Action undoAction = new UndoAction()
    Action redoAction = new RedoAction()
    Action openAction = new OpenAction()
    Action saveAction = new SaveAction()
    Action closeAction = new CloseAction()
    
    Action preferenceAction = new PreferenceAction()
    Action aboutAction = new AboutAction()
    
    
    public MainView() {
        super(null)
        addMenuBar()
        addToolBar(SWT.FLAT)
    }
    
    void run() {
        blockOnOpen = true
        open()
        Display.current?.dispose()
    }

    protected void configureShell(Shell shell) {
        super.configureShell(shell)
        shell.text = "Text Editor"
        shell.setSize(800, 400)
    }

    protected Control createContents(Composite parent) {
        sourceViewer = new SourceViewer(parent, new VerticalRuler(25), SWT.V_SCROLL | SWT.H_SCROLL);
        sourceViewer.setDocument(model)
        sourceViewer.configure(config)
        
        undoManager = new TextViewerUndoManager(500)
        undoManager.connect(sourceViewer)
        
        Font initialFont = sourceViewer.textWidget.font
        FontData[] fontData = initialFont.getFontData();
        for (int i = 0; i < fontData.length; i++) {
            fontData[i].setHeight(12)
            fontData[i].setName("Mensch")
        }
        Font newFont = new Font(Display.current, fontData);
        sourceViewer.textWidget.font = newFont
        
        return sourceViewer.textWidget
    }
    
    protected MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager();
        MenuManager fileMenu = new MenuManager("File")
        MenuManager editMenu = new MenuManager("Edit")
        MenuManager helpMenu = new MenuManager("Help")
    
        menuManager.add(fileMenu)
        fileMenu.add(openAction)
        fileMenu.add(saveAction)
        fileMenu.add(closeAction)
        
        menuManager.add(editMenu)
        editMenu.add(undoAction)
        editMenu.add(redoAction)
        
        
        menuManager.add(helpMenu)
        
        if ( CocoaUIEnhancer.isMac() ) {
            new CocoaUIEnhancer( "Text Editor" ).hookApplicationMenu( 
                Display.current, 
                [ handleEvent : { event -> } ] as Listener, 
                aboutAction, 
                preferenceAction 
            )
        } else {
        
           // Add the Preferences, About and Quit menus.
           editMenu.add(preferenceAction)
           helpMenu.add(aboutAction)
        }
        
        
        
        return menuManager
    }
    
    
}
