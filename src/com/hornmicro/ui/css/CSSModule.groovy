package com.hornmicro.ui.css

import org.eclipse.core.runtime.ContributorFactorySimple
import org.eclipse.core.runtime.IExtensionRegistry
import org.eclipse.core.runtime.RegistryFactory
import org.eclipse.core.runtime.spi.IRegistryProvider
import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler
import org.eclipse.e4.ui.css.core.engine.CSSErrorHandler
import org.eclipse.e4.ui.css.core.impl.engine.CSSEngineImpl
import org.eclipse.e4.ui.css.core.util.impl.resources.FileResourcesLocatorImpl
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl
import org.eclipse.swt.widgets.Control
import org.eclipse.swt.widgets.Display

class CSSModule {
    CSSEngineImpl engine
    
    CSSModule() {
        initEngine()
    }
    
    void initEngine() {
        IExtensionRegistry registry = RegistryFactory.createRegistry(null, null, null)
        
        def classLoader = this.getClass().classLoader
        classLoader.getResources("plugin.xml").each {
            def plugin = it.openStream()
            def text = plugin.text
            plugin.close()
            
            def id = new XmlSlurper().parseText(text)?.'extension-point'?.'@id'
            if(id) {
                def c = ContributorFactorySimple.createContributor(id as String)
                registry.addContribution(it.openStream(), c, false, null, null, null)
            }
            plugin.close()
        }
        
        def defRegProv = [ getRegistry : { registry } ] as IRegistryProvider
        RegistryFactory.setDefaultRegistryProvider(defRegProv)
        
        engine = new CSSSWTEngineImpl(Display.default)
        engine.getResourcesLocatorManager().registerResourceLocator(new FileResourcesLocatorImpl())
        engine.setErrorHandler(new CSSErrorHandler() {
          public void error(Exception e) {
            e.printStackTrace();
          }
        })
    }
    
    void applyStyles(Control control) {
        engine.applyStyles(control, true)
    }
    
    void parseStyleSheet(InputStream sheet) {
        engine.parseStyleSheet(sheet)
        sheet?.close()
    }
    
    void registerPropertyHandler(String property, ICSSPropertyHandler handler) {
        engine.re
    }
}
