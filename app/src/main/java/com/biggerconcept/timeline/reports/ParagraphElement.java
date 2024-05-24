package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Doc.ParagraphType;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Inserts a paragraph into a report
 * 
 * @author Andrew Bigger
 */
public class ParagraphElement extends Element {
    public ParagraphElement() {
        super();
        this.type = ParagraphType.p;
    }
    
    public void insertInto(Doc document) {
        document.p((String) getArgs());
    }
    
    public IElementEditorDialog editorDialog(ResourceBundle rb, IReport report)
        throws IOException {
        return ParagraphDialog.create(rb, report, this);
    }
}
