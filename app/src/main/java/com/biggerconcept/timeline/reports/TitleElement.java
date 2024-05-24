package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Inserts a heading into a report
 * 
 * @author Andrew Bigger
 */
public class TitleElement extends Element {
    public TitleElement() {
        super();
        this.type = Doc.ParagraphType.title;
    }
    
    public void insertInto(Doc document) {
        document.title((String) getArgs());
    }
    
    public IElementEditorDialog editorDialog(ResourceBundle rb, IReport report)
        throws IOException {
        return ParagraphDialog.create(rb, report, this);
    }
}
