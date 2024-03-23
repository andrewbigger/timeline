package com.biggerconcept.timeline.actions.document;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.stage.Stage;

/**
 * Creates a new document
 * 
 * @author Andrew Bigger
 */
public class CreateDocument implements Action {
    public void perform(State state, Stage window) {
        state.reset();
        state.mapDocumentToWindow();
    }
}
