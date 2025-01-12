package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Doc.ParagraphType;
import com.biggerconcept.timeline.State;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Insert markdown into a report
 * 
 * @author Andrew Bigger
 */
public class MarkdownElement extends Element {
    /**
     * Default constructor
     */
    public MarkdownElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public MarkdownElement(State state) {
        super(state);
        this.type = ParagraphType.md;
    }
    
    /**
     * Inserts markdown paragraphs into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        document.md(compile(getArgs(), vars));
    }
    
    /**
     * Constructs and instantiates an editor dialog for markdown.
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
