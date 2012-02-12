package com.hornmicro.ui.editor

import org.eclipse.e4.ui.css.swt.CSSSWTConstants
import org.eclipse.jface.text.CursorLinePainter
import org.eclipse.jface.text.TextViewerUndoManager
import org.eclipse.jface.text.source.AnnotationModel
import org.eclipse.jface.text.source.AnnotationRulerColumn
import org.eclipse.jface.text.source.CompositeRuler
import org.eclipse.jface.text.source.LineNumberRulerColumn
import org.eclipse.jface.text.source.SourceViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Display

class EditorView extends Composite {
    
    SourceViewer sourceViewer
    def model
    def config
    def colorManager
    def undoManager
    
    public EditorView(Composite parent, int style) {
        super(parent, style)
    }
    void createContents() {
        setLayout(new FillLayout())
        
        AnnotationModel annotationModel = new AnnotationModel()
        annotationModel.connect(model)
        
        LineNumberRulerColumn lineNumberRulerColumn =
            new LineNumberRulerColumn() {
                public Control createControl(CompositeRuler parentRuler, Composite parentControl) {
                    Control control = super.createControl(parentRuler, parentControl)
                    //def listeners = control.getListeners(SWT.Paint)
                    //control.removeListener(SWT.Paint, listeners[0])
                    control.background = null
                    return control
                }
                
            }
        lineNumberRulerColumn.model = annotationModel
        //lineNumberChangeRulerColumn.setBackground(Display.current.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND))
        //lineNumberChangeRulerColumn.setForeground(Display.current.getSystemColor(SWT.COLOR_DARK_GRAY))
        lineNumberRulerColumn.font = new Font(Display.current,"Mensch", 12, SWT.NORMAL)
        
        
        CompositeRuler compositeRuler = new CompositeRuler(5)
        compositeRuler.model = annotationModel
        
        def spacer1 = new AnnotationRulerColumn(10)
        def spacer2 = new AnnotationRulerColumn(10)
        
        
        //compositeRuler.addDecorator(0, spacer1)
        compositeRuler.addDecorator(1, lineNumberRulerColumn)
        //compositeRuler.addDecorator(2, spacer2)
        
        sourceViewer = new SourceViewer(this, compositeRuler, SWT.V_SCROLL | SWT.H_SCROLL)

        sourceViewer.setDocument(model)
        sourceViewer.configure(config)
        
        CursorLinePainter clp = new CursorLinePainter(sourceViewer)
        clp.setHighlightColor(colorManager.getColor(new RGB(0xff, 0xfe, 0xcc)))
        sourceViewer.addPainter(clp)
        
        /*
        Font initialFont = sourceViewer.textWidget.font
        FontData[] fontData = initialFont.getFontData();
        for (int i = 0; i < fontData.length; i++) {
            fontData[i].setHeight(12)
            fontData[i].setName("Mensch")
        }
        Font newFont = new Font(Display.current, fontData);
        sourceViewer.textWidget.font = newFont
        */
        sourceViewer.textWidget.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY, "Editor" )
        
        undoManager = new TextViewerUndoManager(500)
        undoManager.connect(sourceViewer)
    }
    
}
