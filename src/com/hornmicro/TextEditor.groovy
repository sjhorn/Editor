package com.hornmicro;

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.jface.text.TextViewer
import org.eclipse.jface.text.Document

import com.hornmicro.ui.MainView

public class TextEditor {
    static TextEditor APP 
    def view
    
    public TextEditor() {
        APP = this
        
        view = new MainView()
        view.model = new Document()

    }

    void run() {
        view.run()
    }

    static void main(String[] args) {
        new TextEditor().run();
    }
}
