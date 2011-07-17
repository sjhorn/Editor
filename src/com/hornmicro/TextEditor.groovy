package com.hornmicro;

import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.jface.text.IDocumentPartitioner
import org.eclipse.jface.text.TextViewer
import org.eclipse.jface.text.Document
import org.eclipse.jface.text.rules.FastPartitioner

import com.hornmicro.syntaxhighlight.CodeScanner
import com.hornmicro.syntaxhighlight.ColorManager
import com.hornmicro.syntaxhighlight.PartitionScanner
import com.hornmicro.syntaxhighlight.TextEditorSourceViewerConfiguration;
import com.hornmicro.ui.MainView
import com.hornmicro.ui.PersistentDocument

public class TextEditor {
    static final String PARTITIONING = "text_partitioning"
    static TextEditor APP 
    MainView view
    PersistentDocument model
    ColorManager colorManager
    def codeScanner
    def scanner
    
    
    public TextEditor() {
        APP = this
        Display.appName = "Text Editor"
        colorManager = new ColorManager()
        codeScanner = new CodeScanner()
        view = new MainView()
        setUpModel()
    }

    void run() {
        view.run()
    }

    protected void setUpModel() {
        model = new PersistentDocument()
        view.model = model
        view.config = new TextEditorSourceViewerConfiguration()
        
        scanner = new PartitionScanner()
        IDocumentPartitioner partitioner = new FastPartitioner(
            scanner,
            PartitionScanner.TYPES
        )
        view.model.setDocumentPartitioner(PARTITIONING, partitioner);
        partitioner.connect(view.model);
    }

    static void main(String[] args) {
        new TextEditor().run();
    }
}
