package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import java.util.HashMap;

/**
 * Inserts a new line into a report
 * 
 * @author Andrew Bigger
 */
public class PageBreakElement extends Element {
    public PageBreakElement() {
        super();
    }
    
    public PageBreakElement(State state) {
        super(state);
        this.type = Doc.ParagraphType.br;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) {
        document.br();
    }
    
    public boolean modifiable() {
        return false;
    }
}
