package com.hornmicro.ui.editor

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;

class EditorModel extends Document implements IDocumentListener {
    String fileName
    boolean dirty

    EditorModel() {
        addDocumentListener(this);
    }
    
    int getSize() {
        return get().size()    
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
