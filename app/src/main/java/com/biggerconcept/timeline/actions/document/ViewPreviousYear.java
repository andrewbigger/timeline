package com.biggerconcept.timeline.actions.document;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.stage.Stage;

/**
 * Progresses view year to previous year
 * 
 * @author Andrew Bigger
 */
public class ViewPreviousYear implements Action {
    public void perform(State state, Stage window) {
        state.prevYear(state.getOpenDocument().getPreferences());
        state.mapDocumentToWindow();
    }
}
