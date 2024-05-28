package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a heading 1 into a report
 * 
 * @author Andrew Bigger
 */
public class Heading1Element extends Element {
    /**
     * Default constructor
     */
    public Heading1Element() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public Heading1Element(State state) {
        super(state);
        this.type = Doc.ParagraphType.h1;
    }
    
    /**
     * Inserts a h1 into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        document.h1(compile(getArgs(), vars));
    }
    
    /**
     * Constructs and instantiates an editor dialog for h1 paragraph.
     * 
     * @param rb application resource bundle
     * @param report current report
     * @param content content variables
     * 
     * @return editor dialog
     * 
     * @throws IOException when unable to read file from disk
     */
    public IElementEditorDialog editorDialog(
            ResourceBundle rb,
            IReport report,
            Content content
    ) throws IOException {
        return ParagraphDialog.create(rb, report, this, content);
    }
}
