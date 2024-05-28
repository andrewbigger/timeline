package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import java.util.HashMap;

/**
 * Inserts a table of contents into a report
 * 
 * @author Andrew Bigger
 */
public class TableOfContentsElement extends Element {
    public TableOfContentsElement() {
        super();
    }
    
    public TableOfContentsElement(State state) {
        super(state);
        this.type = Doc.ParagraphType.toc;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) {
        document.toc();
    }
    
    public boolean modifiable() {
        return false;
    }
}
