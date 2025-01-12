package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Doc.ParagraphType;
import com.biggerconcept.timeline.State;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a code block into a report
 * 
 * @author Andrew Bigger
 */
public class CodeElement extends Element {
    /**
     * Default constructor
     */
    public CodeElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public CodeElement(State state) {
        super(state);
        this.type = ParagraphType.code;
    }
    
    /**
     * Inserts a paragraph into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        
        ArrayList<String> lines = new ArrayList();
        lines.addAll(Arrays.asList(compile(getArgs(), vars).split("\n")));
         
        document.code(lines);
    }
    
    /**
     * Constructs and instantiates an editor dialog for a paragraph.
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
