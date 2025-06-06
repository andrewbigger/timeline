package com.biggerconcept.timeline.actions.document;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.stage.Stage;

/**
 * Progresses view year to next year
 * 
 * @author Andrew Bigger
 */
public class ViewNextYear implements Action {
    public void perform(State state, Stage window) throws CloneNotSupportedException {
        state.nextYear(state.getOpenDocument().getPreferences());
        state.mapDocumentToWindow();
    }
}
