package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a heading 4 into a report
 * 
 * @author Andrew Bigger
 */
public class Heading4Element extends Element {
    public Heading4Element() {
        super();
        this.type = Doc.ParagraphType.h4;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        document.h4(compile(getArgs(), vars));
    }
    
    public IElementEditorDialog editorDialog(ResourceBundle rb, IReport report)
        throws IOException {
        return ParagraphDialog.create(rb, report, this);
    }
}
