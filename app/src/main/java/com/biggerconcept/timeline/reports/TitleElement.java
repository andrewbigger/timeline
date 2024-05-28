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
 * Inserts a heading into a report
 * 
 * @author Andrew Bigger
 */
public class TitleElement extends Element {
    public TitleElement() {
        super();
    }
    
    public TitleElement(State state) {
        super(state);
        this.type = Doc.ParagraphType.title;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        document.title(compile(getArgs(), vars));
    }
    
    public IElementEditorDialog editorDialog(
            ResourceBundle rb, 
            IReport report,
            Content content
    ) throws IOException {
        return ParagraphDialog.create(rb, report, this, content);
    }
}
