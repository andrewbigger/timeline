package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Doc.ParagraphType;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.HashMap;

/**
 * Inserts a notes into a report
 * 
 * @author Andrew Bigger
 */
public class NotesElement extends Element {
    /**
     * Default constructor
     */
    public NotesElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public NotesElement(State state) {
        super(state);
        this.type = ParagraphType.p;
    }
    
    /**
     * Inserts a new line into a report document.
     * 
     * @param document report document
     * @param vars content variables
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        document.md(compile(getDocument().getNotes(), vars));
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
