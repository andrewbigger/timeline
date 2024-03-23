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
        throws NoChoiceMadeException, IOException {
        File documentFile = OpenFileDialog.show(
                    state.bundle().getString("dialogs.open.title"),
                    window,
                    App.EXTENSION_FILTER
            );

            state.setOpenDocument(Document.load(documentFile));
            state.mapDocumentToWindow();
    }
}
