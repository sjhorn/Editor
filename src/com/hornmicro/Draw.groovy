package com.hornmicro

import org.eclipse.swt.SWT
import org.eclipse.swt.events.PaintEvent
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Region
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

class Draw {

    static main(args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout())

        final Canvas canvas = new Canvas(shell, SWT.NONE)
        
        canvas.addPaintListener(new PaintListener() {
            void paintControl(PaintEvent pe) {
        		GC gc = pe.gc
                gc.setAdvanced(true)
                gc.setAntialias(SWT.ON)
                gc.setBackground(pe.display.getSystemColor(SWT.COLOR_WHITE))
                gc.fillRectangle(canvas.clientArea)
                gc.setForeground(pe.display.getSystemColor(SWT.COLOR_DARK_BLUE))
                gc.drawText(/Draws the given string, using the receiver's current 
font and foreground color. Tab expansion and carriage return processing 
are performed. If isTransparent is true, then the background of the rectangular 
area where the text is being drawn will not be modified, otherwise it will be filled 
with the receiver's background color./,
                        1,1, true)
                
                
                gc.setBackground(pe.display.getSystemColor(SWT.COLOR_BLACK))
                
                // Drop Shadow
                gc.alpha = 10
                gc.fillRoundRectangle(28, 28, 205, 205, 20, 20)
                gc.alpha = 40
                gc.fillRoundRectangle(29, 29, 203, 203, 20, 20)
                
                // Black line Rounded Rect and Triangle
                gc.alpha = 255
                gc.drawPolyline([60,30,  75,15     ,90,30] as int[])
                Region r = new Region(pe.display)
                r.add(30, 30, 201, 201)
                r.subtract(60, 30, 30, 1)
                gc.setClipping(r)
                gc.drawRoundRectangle(30, 30, 200, 200, 20, 20)
                
                
                // Transparent fill Rounded Rect and Triangle
                gc.setClipping(null)
                gc.alpha = 210
                gc.fillRoundRectangle(30, 30, 201, 201, 20, 20)
                gc.fillPolygon([60,30,  75,15     ,90,30] as int[])
                
                // Lighting at top
                gc.alpha = 210
                gc.setForeground(pe.display.getSystemColor(SWT.COLOR_DARK_GRAY))
                gc.drawPolyline([61,31,  75,16     ,89,31] as int[])
                r = new Region(pe.display)
                r.add(31, 31, 200, 7)
                r.subtract(61, 31, 28, 1)
                gc.setClipping(r)
                gc.drawRoundRectangle(30, 31, 200, 198, 20, 20)
   
                gc.setClipping(null)
                gc.alpha = 255
                
                Color topline = new Color(pe.display, 0x39, 0x37, 0x3a)
        		Color topline2 = new Color(pe.display, 0x3a, 0x88, 0xd2)
                Color fg = new Color(pe.display, 0x22,0x7b, 0xcd)
                Color bg = new Color(pe.display, 0x13, 0x53, 0xad)
                Color bottomline = new Color(pe.display, 0x17, 0x17, 0x15)
                gc.setForeground(fg)
                gc.setBackground(bg)
                gc.fillGradientRectangle(31, 56, 199, 17, true)
                gc.setForeground(topline)
                gc.drawLine(30, 55, 230, 55)
                gc.setForeground(topline2)
                gc.drawLine(30, 56, 230, 56)
                
                gc.setForeground(bottomline)
                gc.drawLine(30, 73, 230, 73)
                
                topline.dispose()
                fg.dispose()
                bg.dispose()
                bottomline.dispose()
                
                // Some Text
                gc.setForeground(pe.display.getSystemColor(SWT.COLOR_BLACK))
                def text = """charset
class
contenteditable
contextmenu
dir
                """
                gc.drawText(text, 41,42, true)
                
                gc.setForeground(pe.display.getSystemColor(SWT.COLOR_WHITE))
                gc.drawText(text, 40,40, true)
                
                }
                
        	}
        )

        shell.pack();
        shell.setSize(600,600)
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

}
