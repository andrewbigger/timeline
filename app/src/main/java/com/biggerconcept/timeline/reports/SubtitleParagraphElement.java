package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.reports.ui.dialogs.ParagraphDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Inserts a subtitle paragraph into a report
 * 
 * @author Andrew Bigger
 */
public class SubtitleParagraphElement extends Element {
    public SubtitleParagraphElement() {
        super();
        this.type = Doc.ParagraphType.subtitle;
    }
    
    public void insertInto(Doc document) {
        document.subtitle((String) getArgs());
    }
    
    public IElementEditorDialog editorDialog(ResourceBundle rb, IReport report)
        throws IOException {
        return ParagraphDialog.create(rb, report, this);
    }
}
