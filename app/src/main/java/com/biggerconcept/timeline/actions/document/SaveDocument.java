package com.biggerconcept.timeline.actions.document;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.ui.dialogs.SaveFileDialog;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.File;
import java.io.IOException;
import javafx.stage.Stage;

/**
 * Save document action
 * 
 * @author Andrew Bigger
 */
public class SaveDocument implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, IOException {
        if (state.getOpenDocument().getFile() == null) {
                File f = SaveFileDialog.show(
                        state.bundle().getString("dialogs.save.title"),
                        window,
                        App.EXTENSION_FILTER
                );

                if (f == null) {
                    return;
                }

                state.getOpenDocument().setFile(f);
            }
            
            state.mapWindowToDocument();
            state.getOpenDocument().save();
    }
}
