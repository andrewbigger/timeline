package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.Dialogs;
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
        Dialogs.openEpicDialog(
                state,
                new Epic(
                        state.getOpenDocument().getLastEpicIdentifier()
                ), 
                state.getOpenDocument().getShelf(), 
                true
        );
    }
}
