package com.hornmicro.helper


import groovy.util.XmlSlurper;

import com.dd.plist.NSArray;
import com.dd.plist.NSData;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;

class ConvertPList {

    static main(args) {
        
        def input = new File("/Applications/TextMate.app/Contents/SharedSupport/Bundles/Java.tmbundle/Syntaxes/Java.plist")
        
        NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(input);
        
        PropertyListParser.saveAsXML(rootDict, new File("convert.plist"))
        
        def doc = new XmlSlurper().parse("convert.plist")
        
    }
}
