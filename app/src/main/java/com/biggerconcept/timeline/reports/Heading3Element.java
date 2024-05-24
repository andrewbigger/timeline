package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Inserts a heading 3 into a report
 * 
 * @author Andrew Bigger
 */
public class Heading3Element extends Element {
    public Heading3Element() {
        super();
        this.type = Doc.ParagraphType.h3;
    }
    
    public void insertInto(Doc document) {
        document.h3((String) getArgs());
    }
    
    public IElementEditorDialog editorDialog(ResourceBundle rb, IReport report)
        throws IOException {
        return ParagraphDialog.create(rb, report, this);
    }
}
