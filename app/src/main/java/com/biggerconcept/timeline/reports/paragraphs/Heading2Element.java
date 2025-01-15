package com.biggerconcept.timeline.reports.paragraphs;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a heading 2 into a report
 * 
 * @author Andrew Bigger
 */
public class Heading2Element extends Element {
    /**
     * Default constructor
     */
    public Heading2Element() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public Heading2Element(State state) {
        super(state);
        this.type = Doc.ParagraphType.h2;
    }
    
    /**
     * Inserts a h2 into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        document.h2(compile(getArgs(), vars));
    }
    
    /**
     * Constructs and instantiates an editor dialog for h2 paragraph.
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
