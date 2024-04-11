package com.biggerconcept.timeline.actions.application;

import com.biggerconcept.timeline.MainController;
import com.biggerconcept.timeline.PreferencesController;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Opens preferences dialog
 * 
 * @author Andrew Bigger
 */
public class OpenPreferences implements Action {
    /**
     * Opens preferences dialog
     * 
     * @param state application state
     * 
     * @throws IOException when unable to load preference FXML
     */
    public void perform(State state, Stage window) 
            throws IOException {
        URL location = getClass().getResource("/fxml/Preferences.fxml");
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(location);
        loader.setResources(state.bundle());
        loader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent preferencePane = (Parent) loader.load();

        PreferencesController prefsController = (PreferencesController) loader
            .getController();

        prefsController.setDocument(state.getOpenDocument());

        Stage stage = new Stage();

        stage.setScene(new Scene(preferencePane));
        stage.setTitle(
                state.bundle().getString("dialogs.preferences.title")
        );
        stage.initStyle(StageStyle.DECORATED);
        stage.resizableProperty().setValue(false);

        stage.showAndWait();
    }
    
}
