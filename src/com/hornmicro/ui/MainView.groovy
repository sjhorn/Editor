package com.hornmicro.ui

import org.eclipse.jface.text.CursorLinePainter
import org.eclipse.jface.text.TextViewerUndoManager
import org.eclipse.jface.text.source.AnnotationModel
import org.eclipse.jface.text.source.AnnotationRulerColumn
import org.eclipse.jface.text.source.CompositeRuler
import org.eclipse.jface.text.source.LineNumberChangeRulerColumn
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.FontData
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display

class MainView extends Composite {
    
    SourceViewer sourceViewer
    def model
    def config
    def colorManager
    def undoManager
    
    public MainView(Composite parent, int style) {
        super(parent, style)
    }
    void createContents() {
        setLayout(new FillLayout())
        AnnotationModel annotationModel = new AnnotationModel()
        annotationModel.connect(model)
        
        LineNumberChangeRulerColumn lineNumberChangeRulerColumn =
            new LineNumberChangeRulerColumn(colorManager)
        lineNumberChangeRulerColumn.model = annotationModel
        lineNumberChangeRulerColumn.setBackground(Display.current.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND))
        lineNumberChangeRulerColumn.setForeground(Display.current.getSystemColor(SWT.COLOR_DARK_GRAY))
        lineNumberChangeRulerColumn.font = new Font(Display.current,"Mensch", 12, SWT.NORMAL)
        
        CompositeRuler compositeRuler = new CompositeRuler(5)
        compositeRuler.model = annotationModel
        
        compositeRuler.addDecorator(0, new AnnotationRulerColumn(10))
        compositeRuler.addDecorator(1, lineNumberChangeRulerColumn)
        compositeRuler.addDecorator(2, new AnnotationRulerColumn(10))
        
        sourceViewer = new SourceViewer(this, compositeRuler, SWT.V_SCROLL | SWT.H_SCROLL)
        sourceViewer.setDocument(model)
        sourceViewer.configure(config)
        
        CursorLinePainter clp = new CursorLinePainter(sourceViewer)
        clp.setHighlightColor(colorManager.getColor(new RGB(0xff, 0xfe, 0xcc)))
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
    }
    
}
