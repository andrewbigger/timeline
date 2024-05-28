package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Doc.ParagraphType;
import com.biggerconcept.timeline.State;
import java.io.IOException;
import java.util.HashMap;

/**
 * Inserts a notes into a report
 * 
 * @author Andrew Bigger
 */
public class NotesElement extends Element {
    public NotesElement() {
        super();
    }
    
    public NotesElement(State state) {
        super(state);
        this.type = ParagraphType.p;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        document.p(compile(getDocument().getNotes(), vars));
    }
    
    public boolean modifiable() {
        return false;
    }
}
