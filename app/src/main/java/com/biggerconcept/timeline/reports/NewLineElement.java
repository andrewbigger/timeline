package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import java.util.HashMap;

/**
 * Inserts a new line into a report
 * 
 * @author Andrew Bigger
 */
public class NewLineElement extends Element {
    public NewLineElement() {
        super();
    }
    
    public NewLineElement(State state) {
        super(state);
        this.type = Doc.ParagraphType.nl;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) {
        document.nl();
    }
    
    public boolean modifiable() {
        return false;
    }
}
