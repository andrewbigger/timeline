package com.biggerconcept.timeline.actions.release;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.domain.Release;
import com.biggerconcept.timeline.ui.dialogs.ReleaseDialog;
import javafx.stage.Stage;

/**
 * Handles addition of a release
 * 
 * @author Andrew Bigger
 */
public class AddRelease implements Action {
    public void perform(State state, Stage window) throws CloneNotSupportedException {
        ReleaseDialog.open(
                state, 
                new Release(), 
                state.getOpenDocument().getReleases(), 
                true
        );
        
        state.mapDocumentToWindow();
    }
}
