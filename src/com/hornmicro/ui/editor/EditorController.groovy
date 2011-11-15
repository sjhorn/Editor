package com.hornmicro.ui.editor

import org.eclipse.jface.text.IDocumentPartitioner
import org.eclipse.jface.text.TextViewerUndoManager
import org.eclipse.jface.text.rules.FastPartitioner
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.jface.text.source.SourceViewerConfiguration
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite


class EditorController implements Runnable {
    SourceViewer sourceViewer
    EditorModel model
    SourceViewerConfiguration config
    ColorManager colorManager
    EditorView view
    
    Composite parent
    
    public EditorController(ColorManager colorManager) {
        this.colorManager = colorManager
        setUpModel()
    }
    
    void run() {
        if(parent && parent.isDisposed() == false) {
            view = new EditorView(parent, SWT.NONE)
            view.config = config
            view.model = model
            view.colorManager = colorManager
            view.createContents()
            this.sourceViewer = view.sourceViewer
        }
    }
    
    TextViewerUndoManager getUndoManager() {
        return view?.undoManager
    }
    
    protected void setUpModel() {
        this.model = new EditorModel()
        this.config = new EditorConfiguration(colorManager)
        
        EditorPartitionScanner scanner = new EditorPartitionScanner()
        IDocumentPartitioner partitioner = new FastPartitioner(
            scanner,
            EditorPartitionScanner.TYPES
        )
        model.setDocumentPartitioner(EditorConfiguration.PARTITIONING, partitioner);
        partitioner.connect(model);
    }
}
