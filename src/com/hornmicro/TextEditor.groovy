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
import org.eclipse.jface.text.rules.DefaultPartitioner

import com.hornmicro.syntaxhighlight.CodeScanner
import com.hornmicro.syntaxhighlight.ColorManager
import com.hornmicro.syntaxhighlight.PartitionScanner
import com.hornmicro.ui.MainView
import com.hornmicro.ui.PersistentDocument

public class TextEditor {
    static final String PARTITIONING = "text_partitioning";
    static TextEditor APP 
    def view
    def document
    def colorManager
    def codeScanner
    def scanner
    
    public TextEditor() {
        APP = this
        colorManager = new ColorManager()
        codeScanner = new CodeScanner()
        
        view = new MainView()
        setUpDocument()
    }

    void run() {
        view.run()
    }
    
    protected void setUpDocument() {
        
        // Create the document
        view.model = new PersistentDocument()
        
        // Create the partition scanner
        scanner = new PartitionScanner()
        
        // Create the partitioner
        IDocumentPartitioner partitioner = new DefaultPartitioner(
            scanner,
            PartitionScanner.TYPES
        )
        
        // Connect the partitioner and document
        view.model.setDocumentPartitioner(PARTITIONING, partitioner);
        partitioner.connect(view.model);
        
      }

    static void main(String[] args) {
        new TextEditor().run();
    }
}
