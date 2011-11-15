package com.hornmicro.ui.editor

import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.presentation.IPresentationReconciler
import org.eclipse.jface.text.presentation.PresentationReconciler
import org.eclipse.jface.text.rules.DefaultDamagerRepairer
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.jface.text.source.SourceViewerConfiguration

class EditorConfiguration extends SourceViewerConfiguration {
    static final String PARTITIONING = "text_partitioning"
    
    CodeScanner codeScanner 
    CommentScanner commentScanner
    
    public EditorConfiguration(ColorManager colorManager) {
        codeScanner = new CodeScanner(colorManager)
        commentScanner = new CommentScanner(colorManager)
    } 
    
    /**
     * Gets the presentation reconciler. This will color the code.
     */
    public IPresentationReconciler getPresentationReconciler( ISourceViewer sourceViewer) {

        // Create the presentation reconciler
        PresentationReconciler reconciler = new PresentationReconciler();
        reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

        // Create the damager/repairer for comment partitions
        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(commentScanner)
        reconciler.setDamager(dr, EditorPartitionScanner.COMMENT);
        reconciler.setRepairer(dr, EditorPartitionScanner.COMMENT);

        // Create the damager/repairer for default
        dr = new DefaultDamagerRepairer(codeScanner)
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
    }

    /**
     * Gets the configured document partitioning
     * 
     * @return String
     */
    public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
        return PARTITIONING
    }

    /**
     * Gets the configured partition types
     * 
     * @return String[]
     */
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return  [
            IDocument.DEFAULT_CONTENT_TYPE,
            EditorPartitionScanner.COMMENT 
        ] as String[]
    }
}
