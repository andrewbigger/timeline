package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a heading 1 into a report
 * 
 * @author Andrew Bigger
 */
public class Heading1Element extends Element {
    public Heading1Element() {
        super();
        this.type = Doc.ParagraphType.h1;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        document.h1(compile(getArgs(), vars));
    }
    
    public IElementEditorDialog editorDialog(
            ResourceBundle rb,
            IReport report,
            Content content
    ) throws IOException {
        return ParagraphDialog.create(rb, report, this, content);
    }
}
