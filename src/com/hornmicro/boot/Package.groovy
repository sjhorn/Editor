package com.hornmicro.boot

import java.util.jar.Attributes
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

import com.hornmicro.TextEditor




class Package {
    static main(args) {
        new File("build/GroovyEd.jar").withOutputStream { os ->

            Manifest manifest = new Manifest()
            def mainAttributes = manifest.mainAttributes
            mainAttributes.putValue(Attributes.Name.MANIFEST_VERSION as String, "1.0")
            [
                'Manifest-Version' : "1.0",
                (SWTBootstrap.MAIN_CLASS) : TextEditor.class.name, 
                (SWTBootstrap.SWT_VERSION) : "3.7.1", 
                'Main-Class' : SWTBootstrap.class.name
            ].each { k,v -> 
                mainAttributes.putValue(k, v)
            }
            
            def libs = ["./", "."]
            new File("libs").eachFileRecurse { file ->
                if(!file.isDirectory() && !file.name.startsWith(".") && !file.path.startsWith("libs/swt")) {
                    libs << file.name
                }
            }
            mainAttributes.putValue(SWTBootstrap.CLASS_PATH, libs.join(" "))
            
            JarOutputStream jar = new JarOutputStream(os, manifest)
            new File("libs").eachFileRecurse { file ->
                if(!file.isDirectory() && !file.name.startsWith(".")) {
                    JarEntry jarEntry = new JarEntry(file.name)
                    jarEntry.setTime(file.lastModified())
                    jar.putNextEntry(jarEntry)
                    jar.write(file.readBytes())
                }
            }
            
            // Add our class files
            new File("bin").eachFileRecurse { file ->
                if(!file.isDirectory()) {
                    JarEntry jarEntry = new JarEntry(file.path.replaceAll(/^bin\//,""))
                    jarEntry.setTime(file.lastModified())
                    jar.putNextEntry(jarEntry)
                    jar.write(file.readBytes())
                }
            }
            jar.close()
        }
    }
}