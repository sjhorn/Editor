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
        
        final def text = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL)
        text.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY, "Editor" )
        text.text = "0 1 3 ! ? & i j l Q q < > \n" * 200
        
        CTabItem tabItem = new CTabItem(tabFolder, SWT.CLOSE)
        tabItem.setText("Test")
        tabItem.setControl(text)

        final File css = new File("src/com/hornmicro/ui/css/groovyed.css")
        def fis1 = new FileInputStream(css)

        final def cssModule = new CSSModule()
        cssModule.parseStyleSheet(fis1)
        cssModule.applyStyles(shell)
        fis1.close()
        
        
        
        /*
        final Menu menu = new Menu(shell , SWT.POP_UP)
        MenuItem item = new MenuItem(menu, SWT.PUSH);
        item.setText("Popup");
        item = new MenuItem(menu, SWT.PUSH);
        item.setText("Popup1");
        item = new MenuItem(menu, SWT.PUSH);
        item.setText("Popup2");
        item = new MenuItem(menu, SWT.PUSH);
        item.setText("Popup3");
        item = new MenuItem(menu, SWT.PUSH);
        item.setText("Popup4");
        item = new MenuItem(menu, SWT.PUSH);
        item.setText("Popup5");
        */
        /*
        ContentProposalAdapter adapter = new ContentProposalAdapter(
            text,
            new StyledTextContentAdapter(),
            new SimpleContentProposalProvider([" A", "B", "C"]  as  String []),
            null,
            null)
        */
        
        
        
        //AutoCompleteField afield = new AutoCompleteField(text, new StyledTextContentAdapter(),["one", "two"] as String[])
        
        
        shell.addMouseListener([
            mouseDown: { MouseEvent me ->
                if(me.button == 3) {
                    //new DialogTray(shell).open()
                    //new MyPopup().open()
                    
                    //
                    def tip = new Shell (null, (SWT.RESIZE | SWT.TOOL) & ~(SWT.NO_TRIM | SWT.SHELL_TRIM))
                    def fProposalTable = new Table(tip, SWT.H_SCROLL | SWT.V_SCROLL)
                    
                    fProposalTable.setLocation(0, 0);
                    
                    GridLayout layout3 = new GridLayout();
                    layout3.marginWidth = 0;
                    layout3.marginHeight = 0;
                    tip.setLayout(layout3);
            
                    GridData data = new GridData(GridData.FILL_BOTH);
                    fProposalTable.setLayoutData(data);
            
                    tip.pack();
                   
                    tip.setBounds(300,300, 200,200)
                    
                    
                    tip.open()
                    /*
                    //tip.setAlpha(190)
                    tip.setBackgroundMode(SWT.INHERIT_DEFAULT )
                    //tip.setBackground (display.getSystemColor (SWT.COLOR_BLACK));
                    //tip.setForeground (display.getSystemColor (SWT.COLOR_WHITE));
                    FillLayout layout2 = new FillLayout();
                    layout2.marginWidth = 2;
                    tip.setLayout (layout2);
                    def label = new Label (tip, SWT.NONE);
                    //label.setForeground (display.getSystemColor (SWT.COLOR_WHITE));
                    //label.setBackground (display.getSystemColor (SWT.COLOR_INFO_BACKGROUND));
                    label.setText ("Some Text\n"*10);

                    Point size = tip.computeSize (SWT.DEFAULT, SWT.DEFAULT);
                    

                    Point pt = shell.toDisplay (me.x, me.y);
                    tip.setBounds (pt.x, pt.y - 200, 200, 200);
                    tip.setVisible (true);
                    */
                }
            }] 
        as MouseAdapter)
        //shell.pack();
        shell.setSize(600,600)
        shell.open();
        
        final Long lastModified = css.lastModified()
        Thread.start {
            while(1) {
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
