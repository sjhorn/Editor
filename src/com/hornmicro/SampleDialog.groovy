package com.hornmicro

import net.miginfocom.swt.MigLayout;

import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class SampleDialog extends ApplicationWindow {
    public SampleDialog() {
        super(null)
    }
    public void run() {
        setBlockOnOpen(true);
        open();
        def display = Display.getCurrent()?.dispose()
    }
    protected Control createContents(Composite parent) {
        Shell shell = parent.getShell();
        final MigLayout layout = new MigLayout("fillx", "[right] rel [grow, fill]");
        shell.setLayout(layout);
 
        final Label nameLabel = new Label(shell, SWT.NONE);
        nameLabel.setText("Name:");
 
        final Text nameText = new Text(shell, SWT.SINGLE | SWT.BORDER);
        nameText.setText("Somebody");
        nameText.setLayoutData("wrap, width 300px");
 
        final Label emailLabel = new Label(shell, SWT.NONE);
        emailLabel.setText("Email:");
 
        final Text emailText = new Text(shell, SWT.SINGLE | SWT.BORDER);
        emailText.setText("foo@bar.com");
        emailText.setLayoutData("wrap");
 
        final Button displayButton = new Button(shell, SWT.PUSH);
        displayButton.setText("Display");
        displayButton.setLayoutData("span 2, tag ok, gapy unrelated, wrap");
 
        final Link urlLink = new Link(shell, SWT.NONE);
        urlLink.setText("<a>More informations...</a>");
        urlLink.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                Program.launch("http://jpz-log.info/");
            }
        });
        urlLink.setLayoutData("span 2, align left, gapy unrelated, wrap");
 
        shell.setDefaultButton(displayButton);
 
        displayButton.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                String normalForm = new StringBuilder()
                        .append(nameText.getText())
                        .append(" <")
                        .append(emailText.getText())
                        .append(">")
                        .toString();
 
                MessageBox box = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                box.setMessage(normalForm);
                box.open();
            }
        });
    
        return parent;
    }
    static main(args) {
        new SampleDialog().run()
    }
}
