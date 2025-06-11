package com.biggerconcept.timeline.reports.paragraphs;

import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.elements.Content;
import com.biggerconcept.sdk.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.sdk.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.serializers.documents.Doc.ParagraphType;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
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
     * Inserts a code block into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        
        ArrayList<String> lines = new ArrayList();
        lines.addAll(Arrays.asList(compile(getArgs(), vars).split("\n")));
        
        document.code(lines);
    }
    
    /**
     * Constructs and instantiates an editor dialog for a code block.
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
