package com.biggerconcept.timeline.actions.application;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.stage.Stage;

/**
 * Exit application action
 * 
 * @author Andrew Bigger
 */
public class ExitApplication implements Action {
    /**
     * Exits application with status 0
     * 
     * @param state application state
     */
    public void perform(State state, Stage window) {
        System.exit(0);
    }
}
