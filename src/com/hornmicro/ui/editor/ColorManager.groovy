package com.hornmicro.ui.editor

import org.eclipse.jface.text.source.ISharedTextColors
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.widgets.Display

class ColorManager implements ISharedTextColors {
    static final RGB BACKGROUND = new RGB(255, 255, 255)
    static final RGB COMMENT = new RGB(0, 128, 0)
    static final RGB DEFAULT = new RGB(0, 0, 0)
    static final RGB KEYWORD = new RGB(0, 128, 128)
    static final RGB NUMBER = new RGB(255, 0, 255)
    static final RGB STRING = new RGB(255, 0, 0)

    def colors = [:]

    Color getColor(RGB rgb) {
        def color = colors[rgb]
        if (color == null) {
            color = new Color(Display.current, rgb)
            colors[rgb] = color
        }
        return color;
    }

    public void dispose() {
        colors.each { k, v ->
            v.dispose()
        }
    }
}
