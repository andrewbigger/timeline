package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.util.HashMap;

/**
 * Inserts a table of contents into a report
 * 
 * @author Andrew Bigger
 */
public class TableOfContentsElement extends Element {
    /**
     * Default constructor
     */
    public TableOfContentsElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public TableOfContentsElement(State state) {
        super(state);
        this.type = Doc.ParagraphType.toc;
    }
    
    /**
     * Inserts a table of contents into a report document.
     * 
     * @param document report document
     * @param vars content variables
     */
    public void insertInto(Doc document, HashMap<String, String> vars) {
        document.toc();
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
