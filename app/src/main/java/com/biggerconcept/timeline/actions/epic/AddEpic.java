package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.ui.dialogs.EpicDialog;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.stage.Stage;

/**
 * Handles addition of epic
 * 
 * @author Andrew Bigger
 */
public class AddEpic implements Action {
    public void perform(State state, Stage window) {
        EpicDialog.open(
                state,
                new Epic(
                        state.getOpenDocument().getLastEpicIdentifier()
                ), 
                state.getOpenDocument().getShelf(), 
                true
        );
    }
}
