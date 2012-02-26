package com.hornmicro

import org.eclipse.swt.SWT
import org.eclipse.swt.events.PaintEvent
import org.eclipse.swt.events.PaintListener
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

class ShowColors {

    static main(args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

        Canvas c = new Canvas(shell, SWT.NONE)
        
        c.addPaintListener(new PaintListener() {
            void paintControl(PaintEvent pe) {
                GC gc = pe.gc
                def bg = gc.getBackground()
                int y = 0
                [
                    "SWT.COLOR_BLACK": SWT.COLOR_BLACK,
                    "SWT.COLOR_BLUE": SWT.COLOR_BLUE,
                    "SWT.COLOR_CYAN": SWT.COLOR_CYAN,
                    "SWT.COLOR_DARK_BLUE": SWT.COLOR_DARK_BLUE,
                    "SWT.COLOR_DARK_CYAN": SWT.COLOR_DARK_CYAN,
                    "SWT.COLOR_DARK_GRAY": SWT.COLOR_DARK_GRAY,
                    "SWT.COLOR_DARK_GREEN": SWT.COLOR_DARK_GREEN,
                    "SWT.COLOR_DARK_MAGENTA": SWT.COLOR_DARK_MAGENTA,
                    "SWT.COLOR_DARK_RED": SWT.COLOR_DARK_RED,
                    "SWT.COLOR_DARK_YELLOW": SWT.COLOR_DARK_YELLOW,
                    "SWT.COLOR_GRAY": SWT.COLOR_GRAY,
                    "SWT.COLOR_GREEN": SWT.COLOR_GREEN,
                    "SWT.COLOR_INFO_BACKGROUND": SWT.COLOR_INFO_BACKGROUND,
                    "SWT.COLOR_INFO_FOREGROUND": SWT.COLOR_INFO_FOREGROUND,
                    "SWT.COLOR_LIST_BACKGROUND": SWT.COLOR_LIST_BACKGROUND,
                    "SWT.COLOR_LIST_FOREGROUND": SWT.COLOR_LIST_FOREGROUND,
                    "SWT.COLOR_LIST_SELECTION": SWT.COLOR_LIST_SELECTION,
                    "SWT.COLOR_LIST_SELECTION_TEXT": SWT.COLOR_LIST_SELECTION_TEXT,
                    "SWT.COLOR_MAGENTA": SWT.COLOR_MAGENTA,
                    "SWT.COLOR_RED": SWT.COLOR_RED,
                    "SWT.COLOR_TITLE_BACKGROUND": SWT.COLOR_TITLE_BACKGROUND,
                    "SWT.COLOR_TITLE_BACKGROUND_GRADIENT": SWT.COLOR_TITLE_BACKGROUND_GRADIENT,
                    "SWT.COLOR_TITLE_FOREGROUND": SWT.COLOR_TITLE_FOREGROUND,
                    "SWT.COLOR_TITLE_INACTIVE_BACKGROUND": SWT.COLOR_TITLE_INACTIVE_BACKGROUND,
                    "SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT": SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT,
                    "SWT.COLOR_TITLE_INACTIVE_FOREGROUND": SWT.COLOR_TITLE_INACTIVE_FOREGROUND,
                    "SWT.COLOR_WHITE": SWT.COLOR_WHITE,
                    "SWT.COLOR_WIDGET_BACKGROUND": SWT.COLOR_WIDGET_BACKGROUND,
                    "SWT.COLOR_WIDGET_BORDER": SWT.COLOR_WIDGET_BORDER,
                    "SWT.COLOR_WIDGET_DARK_SHADOW": SWT.COLOR_WIDGET_DARK_SHADOW,
                    "SWT.COLOR_WIDGET_FOREGROUND": SWT.COLOR_WIDGET_FOREGROUND,
                    "SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW": SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW,
                    "SWT.COLOR_WIDGET_LIGHT_SHADOW": SWT.COLOR_WIDGET_LIGHT_SHADOW,
                    "SWT.COLOR_WIDGET_NORMAL_SHADOW": SWT.COLOR_WIDGET_NORMAL_SHADOW,
                    "SWT.COLOR_YELLOW": SWT.COLOR_YELLOW                
                ].each { k,v ->
                    
                    gc.setBackground(pe.display.getSystemColor(v))
                    gc.fillRectangle(10, y, 20, 20)  
                    gc.setBackground(bg)
                    gc.drawText(k, 40, y)
                    y += 25
                }
        	}
        }) 

        shell.setBounds(100, 100, 200, 700)
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

}
