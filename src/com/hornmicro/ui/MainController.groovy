package com.hornmicro.ui

import org.eclipse.jface.action.Action
import org.eclipse.jface.action.MenuManager
import org.eclipse.jface.action.Separator
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.events.FocusEvent
import org.eclipse.swt.events.FocusListener
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Listener
import org.eclipse.swt.widgets.Shell

import com.hornmicro.actions.AboutAction
import com.hornmicro.actions.CloseAction
import com.hornmicro.actions.CopyAction
import com.hornmicro.actions.CutAction
import com.hornmicro.actions.OpenAction
import com.hornmicro.actions.PasteAction
import com.hornmicro.actions.PreferenceAction
import com.hornmicro.actions.RedoAction
import com.hornmicro.actions.SaveAction
import com.hornmicro.actions.SaveAsAction
import com.hornmicro.actions.SelectAllAction
import com.hornmicro.actions.UndoAction
import com.hornmicro.helper.CocoaUIEnhancer
import com.hornmicro.ui.editor.ColorManager
import com.hornmicro.ui.editor.EditorController
import com.hornmicro.ui.editor.EditorModel

class MainController extends ApplicationWindow implements Runnable {
    MainView view
    ColorManager colorManager
    
    Action openAction
    Action saveAction 
    Action saveAsAction 
    Action closeAction
    
    Action undoAction 
    Action redoAction 
    
    Action cutAction
    Action copyAction 
    Action pasteAction
    
    Action selectAllAction 
    
    Action preferenceAction
    Action aboutAction
    
    
    public MainController() {
        super(null)
        colorManager = new ColorManager()
        
        openAction = new OpenAction(this)            
        saveAction = new SaveAction(this)            
        saveAsAction = new SaveAsAction(this)        
        closeAction = new CloseAction(this)          
                                                 
        undoAction = new UndoAction(this)            
        redoAction = new RedoAction(this)            
                                                 
        cutAction = new CutAction(this)              
        copyAction = new CopyAction(this)            
        pasteAction = new PasteAction(this)          
                                                 
        selectAllAction = new SelectAllAction(this)  
                                                 
        preferenceAction = new PreferenceAction(this)
        aboutAction = new AboutAction(this)   
        
        addMenuBar()
        //addToolBar(SWT.FLAT)
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
        shell.setBackgroundMode(SWT.INHERIT_DEFAULT)
        //shell.setBackgroundImage(new Image(Display.default, "/Users/shorn/Desktop/seamless_bgs/bg2.jpg"))
    }

    protected Control createContents(Composite parent) {
        parent.setLayout(new FillLayout())
        view = new MainView(parent, SWT.NONE)
        view.colorManager = colorManager 
        view.createContents()
        
        EditorController editor = new EditorController(colorManager)
        editor.parent = view.tabFolder
        editor.run()
        
        CTabItem tabItem = new CTabItem(view.tabFolder, SWT.CLOSE)
        tabItem.setText("Test")
        tabItem.setControl(editor.view)
        view.tabFolder.addFocusListener(new FocusListener() {
            void focusGained(FocusEvent arg0) {
                editor.view.sourceViewer.textWidget.setFocus()
            }
            void focusLost(FocusEvent arg0) {
            }
        })
        return view
    }
    
    protected MenuManager createMenuManager() {
        MenuManager menuManager = new MenuManager();
        MenuManager fileMenu = new MenuManager("File")
        MenuManager editMenu = new MenuManager("Edit")
        MenuManager helpMenu = new MenuManager("Help")
    
        menuManager.add(fileMenu)
        fileMenu.add(openAction)
        fileMenu.add(saveAction)
        fileMenu.add(saveAsAction)
        fileMenu.add(closeAction)
        
        menuManager.add(editMenu)
        editMenu.add(undoAction)
        editMenu.add(redoAction)
        editMenu.add(new Separator())
        editMenu.add(copyAction)
        editMenu.add(cutAction)
        editMenu.add(pasteAction)
        editMenu.add(new Separator())
        editMenu.add(selectAllAction)
        
        
        menuManager.add(helpMenu)
        if ( CocoaUIEnhancer.isMac() ) {
            new CocoaUIEnhancer( "Text Editor" ).hookApplicationMenu( 
                Display.default, 
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
    
    SourceViewer getActiveSourceViewer() {
        return null
        //return view.sourceViewer
    }
    EditorModel getActiveModel() {
        return null
        //return view.model
    }
}
