package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.ui.dialogs.EpicDialog;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.stage.Stage;

/**
 * Toggles sprint counts in view
 * 
 * @author Andrew Bigger
 */
public class ToggleCounts implements Action {
    public void perform(State state, Stage window) {
        state.toggleCounts();
        state.mapDocumentToWindow();
    }
}
