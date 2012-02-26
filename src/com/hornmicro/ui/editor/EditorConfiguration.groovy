package com.hornmicro.ui.editor

import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.ITextViewer
import org.eclipse.jface.text.contentassist.ContentAssistant
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.jface.text.contentassist.IContentAssistProcessor
import org.eclipse.jface.text.contentassist.IContentAssistant
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.presentation.IPresentationReconciler
import org.eclipse.jface.text.presentation.PresentationReconciler
import org.eclipse.jface.text.rules.DefaultDamagerRepairer
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.jface.text.source.SourceViewerConfiguration
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display

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
    
    IContentAssistant getContentAssistant(ISourceViewer arg0) {
		ContentAssistant ca = new ContentAssistant()
        println "getting assistant"
        
        ca.setContentAssistProcessor([
            computeCompletionProposals: { ITextViewer viewer, int offset ->
                println "getting proposals"
                return [ new TestProposal("test") ] as ICompletionProposal[]
            },
            lastWord: { IDocument doc, int offset -> 
                return ""
            },
            lastIndent: { IDocument doc, int offset ->
                return ""
            },
            getCompletionProposalAutoActivationCharacters: {
                return "#" as char[]
            },
            getContextInformationAutoActivationCharacters: {
                return '$' as char[]
            },
            getErrorMessage: {
                return "woot!"
            }
        ] as IContentAssistProcessor, IDocument.DEFAULT_CONTENT_TYPE)
        
        ca.enableAutoActivation(true)
        return ca
	}
}

class TestProposal implements ICompletionProposal {
    String prop
    public TestProposal(prop) {
        this.prop = prop
    }
    @Override
    public void apply(IDocument arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getAdditionalProposalInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextInformation getContextInformation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayString() {
        return prop;
    }

    @Override
    public Image getImage() {
        // TODO Auto-generated method stub
        return new Image(Display.default, "profile.png");
    }

    @Override
    public Point getSelection(IDocument arg0) {
        // TODO Auto-generated method stub
        return null;
    }
    
}