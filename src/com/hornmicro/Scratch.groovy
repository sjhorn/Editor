package com.hornmicro

import org.eclipse.e4.ui.css.swt.CSSSWTConstants
import org.eclipse.jface.dialogs.PopupDialog
import org.eclipse.jface.fieldassist.IControlContentAdapter
import org.eclipse.jface.fieldassist.IControlContentAdapter2
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.events.MouseAdapter
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.Text

import com.hornmicro.ui.css.CSSModule

class Scratch {
    
    static main(args) {
        Display display = new Display()
        
        
        final Shell shell = new Shell(display);
        GridLayout layout = new GridLayout()
        shell.setLayout(layout);
        shell.setData(CSSSWTConstants.CSS_ID_KEY, "MainWindow" )
        shell.setData(CSSSWTConstants.MARGIN_WRAPPER_KEY, "true")
        shell.setBackgroundMode(SWT.INHERIT_DEFAULT )
      
        def tabFolder = new CTabFolder(shell, SWT.CLOSE )
        GridDataFactory.fillDefaults().grab( true, true ).applyTo(tabFolder)
        //tabFolder.setRenderer(new CTabRendering(tabFolder))
        tabFolder.setBackgroundMode(SWT.NO_BACKGROUND)
        tabFolder.setData(CSSSWTConstants.CSS_ID_KEY, "Tabs" )
        tabFolder.setLayout(new FillLayout())
        
        final CalloutListWidget cw = new CalloutListWidget(tabFolder)
        GridDataFactory.fillDefaults().exclude(true).applyTo(cw)
        
        final def text = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL)
        text.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY, "Editor" )
        text.text = "0 1 3 ! ? & i j l Q q < > \n" * 200
        
        text.addMouseListener(new MouseAdapter() {
            void mouseDown(MouseEvent me) {
                if(me.button == 3) {
                    cw.setItems( [
                         "charset",
                         "class",
                         "contenteditable",
                         "contextmenu",
                         "dir",
                         "draggable"
                    ])
                    cw.setLocation(me.x - 20, me.y+10)
                    cw.setSize(cw.computeSize(-1, -1))
                    cw.setVisible(true)
                    cw.setFocus()
                    cw.redraw()
                    
                }
            }
        })
        
        
        CTabItem tabItem = new CTabItem(tabFolder, SWT.CLOSE)
        tabItem.setText("Test")
        tabItem.setControl(text)

        final File css = new File("src/com/hornmicro/ui/css/groovyed.css")
        def fis1 = new FileInputStream(css)

        final def cssModule = new CSSModule()
        cssModule.parseStyleSheet(fis1)
        cssModule.applyStyles(shell)
        fis1.close()
        
        
        
        
        
        //shell.pack();
        shell.setSize(600,600)
        shell.open();
        
        final Boolean shutdown = false
        final Long lastModified = css.lastModified()
        Thread cssThread = Thread.start {
            while(!shutdown) {
                long modified = css.lastModified()
                if(modified != lastModified) {
                    Display.default.asyncExec {
                        def fis = new FileInputStream(css)
                        cssModule.reset()
                        cssModule.parseStyleSheet(fis)
                        
                        cssModule.applyStyles(shell)
                        fis.close()  
                        shell.layout()
                        
                    }
                    lastModified = modified
                }
                Thread.sleep(500)
            }
        }
        
        
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        shutdown = true
        cssThread.join()
        display.dispose();
    }
    
}

public class MyPopup extends PopupDialog {
    public MyPopup(shell) {
        super(shell, SWT.RESIZE,
            false, false, false, false, null, "Content Assist me")
    }
    
    protected Control createDialogArea(final Composite parent) {
        def text = new Text(parent, SWT.NONE)
        text.text = "Whatever\n"*10
        return text
    }
    
    /*
     * (non-Javadoc)
     * @see org.eclipse.jface.dialogs.PopupDialog#getBackground()
     */
    protected Color getBackground() {
        return Display.default.getSystemColor(SWT.COLOR_WHITE)
    }
    
}


public class StyledTextContentAdapter implements IControlContentAdapter,IControlContentAdapter2 {
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter#getControlContents(org.eclipse
     * .swt.widgets.Control)
     */
    public String getControlContents(Control control) {
        return ((StyledText)control).getText();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter#getCursorPosition(org.eclipse
     * .swt.widgets.Control)
     */
    public int getCursorPosition(Control control) {
        return ((StyledText)control).getCaretOffset();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter#getInsertionBounds(org.eclipse
     * .swt.widgets.Control)
     */
    public Rectangle getInsertionBounds(Control control) {
        StyledText text= (StyledText)control;
        Point caretOrigin= text.getLocationAtOffset(text.getCaretOffset());
        return new Rectangle(caretOrigin.x + text.getClientArea().x, caretOrigin.y + text.getClientArea().y + 3, 1, text.getLineHeight());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter#insertControlContents(org.eclipse
     * .swt.widgets.Control, java.lang.String, int)
     */
    public void insertControlContents(Control control, String contents, int cursorPosition) {
        StyledText text= ((StyledText)control);
        text.insert(contents);
        cursorPosition= Math.min(cursorPosition, contents.length());
        text.setCaretOffset(text.getCaretOffset() + cursorPosition);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter#setControlContents(org.eclipse
     * .swt.widgets.Control, java.lang.String, int)
     */
    public void setControlContents(Control control, String term, int cursorPosition) {
        
        String text = ((StyledText)control).getText();
        
        final String after = text.substring(((StyledText)control).getCaretOffset(), text.length());
        text = text.substring(0, ((StyledText)control).getCaretOffset());
        
        // We just add the maximum ammount matched from the term
        for (int i = term.length(); i > 0; i--) {
            final String subterm = term.substring(0,i);
            if (text.endsWith(subterm)) {
                term = term.substring(i,term.length());
                break;
            }
        }
        
        final StringBuffer buf = new StringBuffer();
        buf.append(text);
        buf.append(term);
        final int len = buf.length();
        buf.append(after);
        ((StyledText)control).setText(buf.toString());
        ((StyledText)control).setCaretOffset(len);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter#setCursorPosition(org.eclipse
     * .swt.widgets.Control, int)
     */
    public void setCursorPosition(Control control, int index) {
        ((StyledText)control).setCaretOffset(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter2#getSelection(org.eclipse.swt
     * .widgets.Control)
     */
    public Point getSelection(Control control) {
        return ((StyledText)control).getSelection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IControlContentAdapter2#setSelection(org.eclipse.swt
     * .widgets.Control, org.eclipse.swt.graphics.Point)
     */
    public void setSelection(Control control, Point range) {
        ((StyledText)control).setSelection(range);
    }
}
