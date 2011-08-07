package com.hornmicro.ui

import org.eclipse.jface.action.Action
import org.eclipse.jface.action.MenuManager
import org.eclipse.jface.action.Separator
import org.eclipse.jface.text.CursorLinePainter
import org.eclipse.jface.text.IDocumentPartitioner
import org.eclipse.jface.text.TextViewerUndoManager
import org.eclipse.jface.text.rules.FastPartitioner
import org.eclipse.jface.text.source.AnnotationModel
import org.eclipse.jface.text.source.AnnotationRulerColumn
import org.eclipse.jface.text.source.CompositeRuler
import org.eclipse.jface.text.source.LineNumberChangeRulerColumn
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.jface.text.source.SourceViewerConfiguration
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.FontData
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.layout.FillLayout;
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
import com.hornmicro.syntaxhighlight.ColorManager
import com.hornmicro.syntaxhighlight.PartitionScanner
import com.hornmicro.syntaxhighlight.TextEditorSourceViewerConfiguration

class MainController extends ApplicationWindow {
    SourceViewer sourceViewer
    PersistentDocument model 
    SourceViewerConfiguration config
    TextViewerUndoManager undoManager
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
        
        setUpModel()
        
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
    }

    protected Control createContents(Composite parent) {
        parent.setLayout(new FillLayout())
        MainView view = new MainView(parent, SWT.NONE)
        view.config = config
        view.model = model
        view.colorManager = colorManager
        view.createContents()
        this.undoManager = view.undoManager
        this.sourceViewer = view.sourceViewer
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
    
    protected void setUpModel() {
        
        this.model = new PersistentDocument()
        this.config = new TextEditorSourceViewerConfiguration(this)
        
        PartitionScanner scanner = new PartitionScanner()
        IDocumentPartitioner partitioner = new FastPartitioner(
            scanner,
            PartitionScanner.TYPES
        )
        model.setDocumentPartitioner(TextEditorSourceViewerConfiguration.PARTITIONING, partitioner);
        partitioner.connect(model);
    }
}
