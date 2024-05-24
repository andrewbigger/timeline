package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Doc.ParagraphType;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Inserts a strong paragraph into a report
 * 
 * @author Andrew Bigger
 */
public class StrongParagraphElement extends Element {
    public StrongParagraphElement() {
        super();
        this.type = Doc.ParagraphType.strong;
    }
    
    public void insertInto(Doc document) {
        document.strong((String) getArgs());
    }
    
    public IElementEditorDialog editorDialog(ResourceBundle rb, IReport report)
        throws IOException {
        return ParagraphDialog.create(rb, report, this);
    }
}
