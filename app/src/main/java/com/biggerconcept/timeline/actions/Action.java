package com.biggerconcept.timeline.actions;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.State;
import javafx.stage.Stage;

/**
 * Application Action
 * 
 * @author Andrew Bigger
 */
public interface Action {
    public void perform(State state, Stage window) 
            throws Exception, NoChoiceMadeException;
}
