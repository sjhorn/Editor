package com.hornmicro.ui

import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.CursorLinePainter;
import org.eclipse.jface.text.Document
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.ChangeRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberChangeRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.text.source.VerticalRuler;

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell

import com.hornmicro.CocoaUIEnhancer;
import com.hornmicro.TextEditor;
import com.hornmicro.actions.AboutAction;
import com.hornmicro.actions.CloseAction;
import com.hornmicro.actions.CopyAction;
import com.hornmicro.actions.CutAction;
import com.hornmicro.actions.OpenAction;
import com.hornmicro.actions.PasteAction;
import com.hornmicro.actions.PreferenceAction;
import com.hornmicro.actions.RedoAction;
import com.hornmicro.actions.SaveAction;
import com.hornmicro.actions.SaveAsAction;
import com.hornmicro.actions.SelectAllAction;
import com.hornmicro.actions.UndoAction;

class MainView extends ApplicationWindow {
    SourceViewer sourceViewer
    Document model 
    SourceViewerConfiguration config
    TextViewerUndoManager undoManager
    
    Action openAction = new OpenAction()
    Action saveAction = new SaveAction()
    Action saveAsAction = new SaveAsAction()
    Action closeAction = new CloseAction()
    
    Action undoAction = new UndoAction()
    Action redoAction = new RedoAction()
    
    Action cutAction = new CutAction()
    Action copyAction = new CopyAction()
    Action pasteAction = new PasteAction()
    
    Action selectAllAction = new SelectAllAction()
    
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
        AnnotationModel annotationModel = new AnnotationModel()
        annotationModel.connect(model)
        
        LineNumberChangeRulerColumn lineNumberChangeRulerColumn = 
            new LineNumberChangeRulerColumn(TextEditor.APP.colorManager)
        lineNumberChangeRulerColumn.model = annotationModel
        lineNumberChangeRulerColumn.setBackground(Display.current.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND))
        lineNumberChangeRulerColumn.setForeground(Display.current.getSystemColor(SWT.COLOR_DARK_GRAY))
        lineNumberChangeRulerColumn.font = new Font(Display.current,"Mensch", 12, SWT.NORMAL)
        
        CompositeRuler compositeRuler = new CompositeRuler(5)
        compositeRuler.model = annotationModel
        
        compositeRuler.addDecorator(0, new AnnotationRulerColumn(10))
        compositeRuler.addDecorator(1, lineNumberChangeRulerColumn)
        compositeRuler.addDecorator(2, new AnnotationRulerColumn(10))
        
        sourceViewer = new SourceViewer(parent, compositeRuler, SWT.V_SCROLL | SWT.H_SCROLL);
        sourceViewer.setDocument(model)
        sourceViewer.configure(config)
        
        CursorLinePainter clp = new CursorLinePainter(sourceViewer)
        clp.setHighlightColor(TextEditor.APP.colorManager.getColor(new RGB(0xff, 0xfe, 0xcc)))
        sourceViewer.addPainter(clp)
        
        Font initialFont = sourceViewer.textWidget.font
        FontData[] fontData = initialFont.getFontData();
        for (int i = 0; i < fontData.length; i++) {
            fontData[i].setHeight(12)
            fontData[i].setName("Mensch")
        }
        Font newFont = new Font(Display.current, fontData);
        sourceViewer.textWidget.font = newFont
        
        undoManager = new TextViewerUndoManager(500)
        undoManager.connect(sourceViewer)
        
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
    
    
}
