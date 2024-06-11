package com.biggerconcept.timeline.actions.document;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.ui.dialogs.OpenFileDialog;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.domain.Document;
import java.io.File;
import java.io.IOException;
import javafx.stage.Stage;

/**
 * Open document action
 * 
 * @author Andrew Bigger
 */
public class OpenDocument implements Action {
    public void perform(State state, Stage window)
        throws NoChoiceMadeException, IOException, CloneNotSupportedException {
        File documentFile = OpenFileDialog.show(
                    state.bundle().getString("dialogs.open.title"),
                    window,
                    App.EXTENSION_FILTER
            );

        Document doc = Document.load(documentFile);
        
        state.setOpenDocument(doc);
        state.getViewYear().setStartSprint(doc.getPreferences().getStartSprintNumber());
        state.mapDocumentToWindow();
    }
}
