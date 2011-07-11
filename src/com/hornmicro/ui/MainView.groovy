package com.hornmicro.ui

import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.jface.text.Document
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Shell

import com.hornmicro.syntaxhighlight.TextEditorSourceViewerConfiguration;


class MainView extends ApplicationWindow {
    SourceViewer viewer
    Document model 
    
    public MainView() {
        super(null)
    }
    
    void run() {
        blockOnOpen = true
        open()
        Display.current?.dispose()
    }

    protected void configureShell(Shell shell) {
        super.configureShell(shell)
        shell.text = "Text Editor"
        shell.setSize(600, 400)
    }

    protected Control createContents(Composite parent) {
        viewer = new SourceViewer(parent, new VerticalRuler(10), SWT.V_SCROLL
            | SWT.H_SCROLL);
        
        
        viewer.configure(new TextEditorSourceViewerConfiguration());
        viewer.document = model
        
        Font initialFont = viewer.textWidget.font
        FontData[] fontData = initialFont.getFontData();
        for (int i = 0; i < fontData.length; i++) {
            fontData[i].setHeight(12)
            fontData[i].setName("Mensch")
        }
        Font newFont = new Font(Display.current, fontData);
        viewer.textWidget.font = newFont
        
        
        return viewer.textWidget
    }
    
}
