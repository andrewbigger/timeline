package com.biggerconcept.timeline.reports.paragraphs;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.util.HashMap;

/**
 * Inserts a new line into a report
 * 
 * @author Andrew Bigger
 */
public class NewLineElement extends Element {
    /**
     * Default constructor
     */
    public NewLineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public NewLineElement(State state) {
        super(state);
        this.type = Doc.ParagraphType.nl;
    }
    
    /**
     * Inserts a new line into a report document.
     * 
     * @param document report document
     * @param vars content variables
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) {
        document.nl();
    }
    
    /**
     * Element modifiable. Indicates whether to allow the presentation of a
     * editor dialog in the report builder.
     * 
     * This element is not modifiable, so no editor dialog will be called for.
     * 
     * @return false
     */
    public boolean modifiable() {
        return false;
    }
}
