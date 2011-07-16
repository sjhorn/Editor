package com.hornmicro.ui

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;

class PersistentDocument extends Document implements IDocumentListener {
    private String fileName;
    private boolean dirty;

    PersistentDocument() {
        addDocumentListener(this);
    }

    void save() {
        if (fileName == null) {
            throw new IllegalStateException("Can't save file with null name");
        }
        new File(fileName).withWriter {
            it << get()
            dirty = false
        }
    }

    void open() {
        if (fileName == null) {
            throw new IllegalStateException("Can't open file with null name");
        }
        set(new File(fileName).text)
        dirty = false;
    }

    void clear() {
        set("");
        fileName = "";
        dirty = false;
    }

    void documentAboutToBeChanged(DocumentEvent event) { }

    void documentChanged(DocumentEvent event) {
        dirty = true;
    }
}
