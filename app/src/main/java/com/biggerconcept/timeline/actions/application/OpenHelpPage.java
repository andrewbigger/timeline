package com.biggerconcept.timeline.actions.application;

import com.biggerconcept.appengine.platform.OperatingSystem;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.stage.Stage;

/**
 * View help action
 * 
 * @author Andrew Bigger
 */
public class OpenHelpPage implements Action {
    /**
     * Opens help page
     * 
     * @param state application state
     * 
     * @throws URISyntaxException when URL is not valid
     * @throws IOException when unable to ask operating system to open URL
     */
    public void perform(State state, Stage window) 
            throws URISyntaxException, IOException {
        OperatingSystem.goToUrl(App.HELP_URL);
    }
}
