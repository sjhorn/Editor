package com.hornmicro

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.graphics.Region
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.Listener
import org.eclipse.swt.widgets.Shell;

class CalloutWidget extends Composite implements PaintListener, DisposeListener {
    static int triangleHeight = 5
    static int triangleWidth = 16
    static int triangleOffset = 15
    static int edgeRadius = 15 
    
    private Color background 
    private Color light
    
    CalloutWidget(Composite parent) {
        super(parent, SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND | SWT.INHERIT_DEFAULT)
        addPaintListener(this)
        addDisposeListener(this)
        
        updateBackground()
    }
    
    void setBackground(Color color) {
        background?.dispose()
        background = color
        
        updateBackground()
    }
    
    protected updateBackground() {
        if(!background) {
            background = new Color(this.display, 0x00, 0x00, 0x00)
        }
        RGB bgRGB = background.getRGB()
        def alpha = 0.50
        def desta = (1 - alpha)
        def src = 0xFF * alpha
        
        light?.dispose()
        light = new Color(this.display, new RGB(
            src + (bgRGB.red * desta) as int,
            src + (bgRGB.green * desta) as int,
            src + (bgRGB.blue * desta) as int,
        ))
    }

    void paintControl(PaintEvent pe) {
        Rectangle bounds = getClientArea()
        GC gc = pe.gc
        
        gc.setAdvanced(true)
        gc.setAntialias(SWT.ON)
        
        gc.setBackground(pe.display.getSystemColor(SWT.COLOR_BLACK))
        int offsetHeight = bounds.height - triangleHeight
        int triangleMid = triangleOffset+(triangleWidth/2)
        
        // Drop Shadow
        
        gc.alpha = 10
        gc.fillRoundRectangle(0, triangleHeight, bounds.width, offsetHeight, edgeRadius, edgeRadius)
        gc.fillPolygon([
            triangleOffset-2, triangleHeight,
            triangleMid,0,
            triangleOffset+triangleWidth+2, triangleHeight
        ] as int[])
        
        gc.alpha = 20
        gc.fillRoundRectangle(1, triangleHeight+1, bounds.width-2, offsetHeight-2, edgeRadius, edgeRadius)
        gc.fillPolygon([
            triangleOffset-1, triangleHeight+1,
            triangleMid,1,
            triangleOffset+triangleWidth+1, triangleHeight+1
        ] as int[])
        
        
        // Transparent fill Rounded Rect and Triangle
        gc.setBackground(background)
        gc.setForeground(background)
        gc.setClipping(null)
        //gc.setBackground(pe.display.getSystemColor(SWT.COLOR_RED))
        gc.alpha = 160
        gc.fillRoundRectangle(3, triangleHeight+3, bounds.width-6, offsetHeight-6, edgeRadius, edgeRadius)
        gc.fillPolygon([
            triangleOffset, triangleHeight+3,
            triangleMid,3,
            triangleOffset+triangleWidth, triangleHeight+3
        ] as int[])

        
        // Black line Rounded Rect and Triangle
        gc.alpha = 255
        gc.drawPolyline([
            triangleOffset, triangleHeight+2,  
            triangleMid,2,
            triangleOffset+triangleWidth, triangleHeight+2
        ] as int[])
         
        Region r = new Region(pe.display)
        r.add(2, triangleHeight+2, bounds.width-4, offsetHeight-4)
        r.subtract(triangleOffset+1, triangleHeight+2, triangleWidth-1, 1)
        gc.setClipping(r)
        gc.drawRoundRectangle(2, triangleHeight+2, bounds.width-5, offsetHeight-5, edgeRadius, edgeRadius)
       
            
        // Lighting at top
        gc.setClipping(null)
        gc.alpha = 210
        gc.setForeground(light)
        //gc.setForeground(pe.display.getSystemColor(SWT.COLOR_BLUE))
        //gc.setClipping(triangleOffset, 3, triangleWidth, triangleHeight)
        gc.drawPolyline([
            triangleOffset, triangleHeight+3,
            triangleMid,3,
            triangleOffset+triangleWidth, triangleHeight+3
        ] as int[])
        
        r = new Region(pe.display)
        r.add(2, triangleHeight+2, bounds.width-4, 7)
        r.subtract(triangleOffset+1, triangleHeight+3, triangleWidth-1, 1)
        gc.setClipping(r)
        gc.drawRoundRectangle(3, triangleHeight+3, bounds.width-7, offsetHeight-7, edgeRadius, edgeRadius)
        
    }
    
    

    void widgetDisposed(DisposeEvent de) {
        background.dispose()
        light.dispose()
    }
    
    Point computeSize(int wHint, int hHint, boolean changed) {
        return new Point(wHint, hHint)
	}

    
    static void main(String[] args) {
        final Display display = new Display();
        Shell shell = new Shell(display);

        final CalloutWidget cw = new CalloutWidget(shell)
        cw.setBounds(40, 40, 170, 240)
        display.addFilter(SWT.MouseMove, new Listener() {
            void handleEvent(Event e) {
                if(e.type == SWT.MouseMove && e.x && e.y) {
                    cw.setLocation(display.map(e.widget, shell, e.x, e.y))
                    cw.redraw()
                }
        	}
        })
        
        
        def st = new StyledText(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP)
        st.setBounds(0, 0, 500, 500)
        st.text = /
Note: The NO_BACKGROUND, NO_FOCUS, NO_MERGE_PAINTS, and NO_REDRAW_RESIZE styles are intended for use with Canvas. They can be used with Composite if you are drawing your own, but their behavior is undefined if they are used with subclasses of Composite other than Canvas.

Note: The CENTER style, although undefined for composites, has the same value as EMBEDDED which is used to embed widgets from other widget toolkits into SWT. On some operating systems (GTK, Motif), this may cause the children of this composite to be obscured.

This class may be subclassed by custom control implementors who are building controls that are constructed from aggregates of other controls.         
/
        
        shell.setLocation(300,300)
        shell.setSize(600, 600)

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}
