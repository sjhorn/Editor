package com.hornmicro

import org.eclipse.swt.SWT
import org.eclipse.swt.events.DisposeEvent
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.events.MouseMoveListener
import org.eclipse.swt.events.MouseTrackAdapter
import org.eclipse.swt.events.PaintEvent
import org.eclipse.swt.events.TraverseEvent
import org.eclipse.swt.events.TraverseListener
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.FontMetrics
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Menu
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text

class CalloutListWidget extends CalloutWidget implements MouseMoveListener, TraverseListener {
    List items = ["Empty"]
    private int selectedIndex = -1
    private int itemHeight
    
    private Color selectBackgroundTop = new Color(this.display, 0x22, 0x7d, 0xd0)
    private Color selectBackgroundBottom = new Color(this.display,  
        blend(selectBackgroundTop.getRGB(), new RGB(0x00, 0x00, 0x00), 0.8) 
    )
    
    
    CalloutListWidget(Composite parent) {
        super(parent)
        setCursor(display.getSystemCursor(SWT.CURSOR_ARROW))
        setVisible(false)
        
        addMouseMoveListener(this)
        addMouseTrackListener(new MouseTrackAdapter() {
            void mouseExit(MouseEvent me) {
                 selectedIndex = -1
                 CalloutListWidget.this.redraw()
            }
        })
        addFocusListener(new FocusAdapter() {
            void focusLost(FocusEvent fe) {
                CalloutListWidget.this.setVisible(false)
            }
        })
        addMouseListener(new MouseAdapter() {
            void mouseDown(MouseEvent arg0) {
                CalloutListWidget.this.selectItem()
            }
        })
        addTraverseListener(this)
    }
    
    void mouseMove(MouseEvent me) {
		if(me.y && itemHeight) {
            int ypos = (me.y - triangleHeight - 10)
            int index = (ypos / itemHeight) as int
            if(ypos >= 0 && index < items.size()) {
                if(index != selectedIndex) {
                    selectedIndex = index
                    redraw()
                }
            } else {
                selectedIndex = -1
                redraw()    
            }
        }
	}

    void keyTraversed(TraverseEvent e) {
        switch(e.detail) {
            case SWT.TRAVERSE_ARROW_PREVIOUS:
                if(selectedIndex > 0) {
                    selectedIndex-- 
                    redraw()
                }
                break
            case SWT.TRAVERSE_ARROW_NEXT:
                if(selectedIndex < (items.size() - 1)) {
                    selectedIndex++
                    redraw()
                }
                break;
            case SWT.TRAVERSE_ESCAPE:
                selectedIndex = -1
                setVisible(false)
                break
            case SWT.TRAVERSE_RETURN:
                selectItem()
                break
               
        }
    }
    
    void selectItem() {
        
        println "Item selected is ${selectedIndex} -> ${items[selectedIndex]}"
        setVisible(false)
    }
    
    
    RGB blend(RGB src, RGB dst, float alpha) {
        def d_alpha = (1 - alpha)
        return new RGB(
            (src.red * alpha + dst.red * d_alpha) as int,
            (src.green * alpha + dst.green * d_alpha) as int,
            (src.blue * alpha + dst.blue * d_alpha) as int
        )
    }
    
    int getItemHeight() {
        if(!itemHeight) {
            GC gc = new GC(this)
            FontMetrics fm = gc.getFontMetrics()
            itemHeight = fm.height + 1
            gc.dispose()
        }
        return itemHeight
    }
    
    void paintControl(PaintEvent pe) {
        super.paintControl(pe)
        Rectangle bounds = getClientArea()
        int offsetHeight = bounds.height - triangleHeight
        GC gc = pe.gc
        
        gc.alpha = 255
        
        FontMetrics fm = gc.getFontMetrics()
        itemHeight = fm.height + 1
        int y = triangleHeight + 10
        gc.setClipping(3, 0, bounds.width - 6, bounds.height)
        items.eachWithIndex { it, index ->
            
            if(index == selectedIndex) {
                drawSelection(gc, y, bounds.width-6, itemHeight)
            }
            
            gc.setForeground(pe.display.getSystemColor(SWT.COLOR_BLACK))
            gc.drawText(it, 10,y+2, true)
            
            gc.setForeground(pe.display.getSystemColor(SWT.COLOR_WHITE))
            gc.drawText(it, 10,y, true)
            
            y+= itemHeight
        }
	}
    
    protected drawSelection(GC gc, int y, int width, int height) {
        gc.setForeground(selectBackgroundTop)
        gc.setBackground(selectBackgroundBottom)
        gc.fillGradientRectangle(3, y, width, height, true)
        
        
        Color topline = new Color(display, blend(selectBackgroundTop.getRGB(), new RGB(0xFF, 0xFF, 0xFF), 0.9))
        gc.setForeground(topline)
        gc.drawLine(3, y, width+3, y)
        topline.dispose()
        
        Color bottomline = new Color(display, blend(selectBackgroundBottom.getRGB(), new RGB(0x00, 0x00, 0x00), 0.8))
        gc.setForeground(bottomline)
        gc.drawLine(3, y+height+1, width+3, y+height)
        bottomline.dispose()
    }

    Point computeSize(int wHint, int hHint) {
        return new Point(170, (items.size() * getItemHeight() ) + triangleHeight + 20)
    }
    
    void pointAt(int x, int y) {
        /*
        int triangleCenter = triangleOffset + triangleWidth/2
        if(x - triangleCenter < 0) {
            triangleOffset = x
            setLocation(0, y)
        } else {
            setLocation(x - triangleCenter, y)
        }
        */
        setLocation(x, y)
    }
    
    void widgetDisposed(DisposeEvent de) {
		super.widgetDisposed(de)
        selectBackgroundTop.dispose()
        selectBackgroundBottom.dispose()
	}
    
    
    
    
    
    static void main(String[] args) {
        final Display display = new Display();
        Shell shell = new Shell(display);

        final CalloutListWidget cw = new CalloutListWidget(shell)
        cw.setItems(["Item 1", "Item 2", "Item 3"])
        
        
        Text st = new Text(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP)
        st.setMenu(new Menu(shell, 0))
        st.setBounds(0, 0, 500, 500)
        st.text = /
Note: The NO_BACKGROUND, NO_FOCUS, NO_MERGE_PAINTS, and NO_REDRAW_RESIZE styles are intended for use with Canvas. They can be used with Composite if you are drawing your own, but their behavior is undefined if they are used with subclasses of Composite other than Canvas.

Note: The CENTER style, although undefined for composites, has the same value as EMBEDDED which is used to embed widgets from other widget toolkits into SWT. On some operating systems (GTK, Motif), this may cause the children of this composite to be obscured.

This class may be subclassed by custom control implementors who are building controls that are constructed from aggregates of other controls.
/
        
        st.addMouseListener(new MouseAdapter() {
            void mouseDown(MouseEvent me) {
                if(me.button == 3) {
                    //cw.setLocation(me.x - 20, me.y+10)
                    cw.pointAt(me.x, me.y)
                    cw.setSize(cw.computeSize(-1,-1))
                    cw.setVisible(true)
                    cw.setFocus()
                    cw.redraw()
                    
                }
        	}
        })

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
