package com.biggerconcept.timeline.actions.application;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Opens about dialog
 * 
 * @author Andrew Bigger
 */
public class OpenAboutDialog implements Action {
    public void perform(State state, Stage window) 
            throws IOException {
        Stage aboutStage = new Stage();
        
        Parent aboutPane = FXMLLoader.load(
            getClass().getResource("/fxml/About.fxml"), state.bundle()
        );

        aboutStage.setAlwaysOnTop(true);
        aboutStage.setScene(new Scene(aboutPane));
        aboutStage.initStyle(StageStyle.UTILITY);
        aboutStage.resizableProperty().setValue(false);

        aboutStage.setTitle(
                state.bundle().getString("application.about.windowTitle")
        );

        aboutStage.showAndWait();
    }
}
