package com.hornmicro

import org.eclipse.core.runtime.ContributorFactorySimple
import org.eclipse.core.runtime.IExtensionRegistry
import org.eclipse.core.runtime.RegistryFactory
import org.eclipse.core.runtime.spi.IRegistryProvider
import org.eclipse.e4.ui.css.core.engine.CSSEngine
import org.eclipse.e4.ui.css.core.engine.CSSErrorHandler
import org.eclipse.e4.ui.css.core.util.impl.resources.FileResourcesLocatorImpl
import org.eclipse.e4.ui.css.swt.CSSSWTConstants
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl
import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.CTabFolder
import org.eclipse.swt.custom.CTabItem
import org.eclipse.swt.custom.StyledText
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

class Scratch {
    
    static main(args) {
        IExtensionRegistry registry = RegistryFactory.createRegistry(null, null, null)
        
        def classLoader = Scratch.class.classLoader
        classLoader.getResources("plugin.xml").each {
            def plugin = it.openStream()
            def text = plugin.text
            plugin.close()
            
            def id = new XmlSlurper().parseText(text)?.'extension-point'?.'@id'
            if(id) {
                def c = ContributorFactorySimple.createContributor(id as String)
                registry.addContribution(it.openStream(), c, false, null, null, null)
            }
        }
        
        def defRegProv = [ getRegistry : { registry } ] as IRegistryProvider
        RegistryFactory.setDefaultRegistryProvider(defRegProv)
        
        Display display = new Display();
        Shell shell = new Shell(display);
        GridLayout layout = new GridLayout()
        shell.setLayout(layout);
        shell.setData(CSSSWTConstants.CSS_ID_KEY, "MainWindow" )
        shell.setData(CSSSWTConstants.MARGIN_WRAPPER_KEY, "true")
        shell.setBackgroundMode(SWT.INHERIT_DEFAULT )
        
        def tabFolder = new CTabFolder(shell, SWT.CLOSE )
        GridDataFactory.fillDefaults().grab( true, true ).applyTo(tabFolder)
//        tabFolder.setRenderer(new CTabRendering(tabFolder))
        tabFolder.setBackgroundMode(SWT.NO_BACKGROUND)
        tabFolder.setData(CSSSWTConstants.CSS_ID_KEY, "Tabs" )
        tabFolder.setLayout(new FillLayout())
        
        def text = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL)
        text.setData(CSSSWTConstants.CSS_CLASS_NAME_KEY, "Editor" )
        text.text = "0 1 3 ! ? & i j l Q q < > \n" * 200
        
        CTabItem tabItem = new CTabItem(tabFolder, SWT.CLOSE)
        tabItem.setText("Test")
        tabItem.setControl(text)

        
        CSSEngine engine = new CSSSWTEngineImpl(display);
        engine.parseStyleSheet(new StringReader('''
            #MainWindow {
                background-image: url(/Users/shorn/Desktop/seamless_bgs/bg2.jpg);
            }

            #Tabs {
                margin: 20;
            }
            .Editor { 
                font: 12 "Mensch";
                background-image: url(/Users/shorn/Desktop/seamless_bgs/paper2.png);
            }
            
            
        '''))
        engine.getResourcesLocatorManager().registerResourceLocator(new FileResourcesLocatorImpl())
        engine.setErrorHandler(new CSSErrorHandler() {
          public void error(Exception e) {
            e.printStackTrace();
          }
        });
    
    
        // applying styles to the child nodes means that the engine
        // should recurse downwards, in this example, the engine
        // should style the children of the Shell
        engine.applyStyles(shell, /* applyStylesToChildNodes */ true);
        
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
