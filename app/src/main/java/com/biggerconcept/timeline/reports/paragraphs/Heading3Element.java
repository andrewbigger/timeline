package com.biggerconcept.timeline.reports.paragraphs;

import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.elements.Content;
import com.biggerconcept.sdk.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.sdk.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a heading 3 into a report
 * 
 * @author Andrew Bigger
 */
public class Heading3Element extends Element {
    /**
     * Default constructor
     */
    public Heading3Element() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public Heading3Element(State state) {
        super(state);
        this.type = Doc.ParagraphType.h3;
    }
    
    /**
     * Inserts a h3 into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        document.h3(compile(getArgs(), vars));
    }
    
    /**
     * Constructs and instantiates an editor dialog for h3 paragraph.
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
