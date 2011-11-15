package com.hornmicro.ui

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Composite

import com.hornmicro.ui.editor.ColorManager

class MainView extends Composite {
    CTabFolder tabFolder
    ColorManager colorManager
    Image img
    
    public MainView(Composite parent, int style) {
        super(parent, style)
    }
    
    void createContents() {
        def fillLayout = new FillLayout()
        fillLayout.marginHeight =15
        fillLayout.marginWidth = 4
        setLayout(fillLayout)
        
        tabFolder = new CTabFolder(this, SWT.FLAT)
        tabFolder.setBackgroundMode(SWT.NO_BACKGROUND)
        tabFolder.setLayout(new FillLayout())
    }
}
