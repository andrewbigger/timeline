package com.biggerconcept.timeline.ui.dialogs;

import com.biggerconcept.sdk.ui.dialogs.ErrorAlert;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.ReleaseDialogController;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Release;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Release editor dialog
 * 
 * @author Andrew Bigger
 */
public class ReleaseDialog {
    /**
     * Opens the release dialog
     * 
     * @param state application state
     * @param release release to edit
     * @param targetSet target set to add release to
     * @param isNew whether release is new or existing
     * @throws java.lang.CloneNotSupportedException when unable to clone sprint
     */
    public static void open(
            State state,
            Release release,
            ArrayList<Release> targetSet,
            boolean isNew
    ) throws CloneNotSupportedException {
        try {           
            URL location = state
                    .mainController()
                    .getClass()
                    .getResource("/fxml/ReleaseDialog.fxml");
            
            FXMLLoader loader = new FXMLLoader();
        
            loader.setLocation(location);
            loader.setResources(state.bundle());
            loader.setBuilderFactory(new JavaFXBuilderFactory());
        
            Parent releaseWindow = (Parent) loader.load();
        
            ReleaseDialogController controller = (ReleaseDialogController) loader
                .getController();
        
            controller.setRelease(
                    state,
                    release,
                    state.mainController(),
                    targetSet,
                    isNew
            );
        
            Stage stage = new Stage();
            Scene scene = new Scene(releaseWindow);
            
            if (App.config().isTrue("darkMode")) {
                scene.getStylesheets().add("/css/application.dark.css");
            } else {
                scene.getStylesheets().add("/css/application.css");
            }
        
            stage.setScene(scene);
            stage.setTitle(release.getName());
            stage.initStyle(StageStyle.DECORATED);
            stage.resizableProperty().setValue(false);
            
            stage.show();
        } catch (IOException e) {
            ErrorAlert.show(
                    state.bundle(), 
                    state.bundle().getString("errors.generic"), 
                    e
            );
        }
    }
}
