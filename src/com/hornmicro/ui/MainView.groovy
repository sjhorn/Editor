package com.hornmicro.ui

import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.Document

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Shell

class MainView extends ApplicationWindow {
    TextViewer viewer
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
        viewer = new TextViewer(parent, SWT.NONE)
        viewer.document = model
        viewer.textWidget.font = new Font()
        return viewer.textWidget
    }
    
}
