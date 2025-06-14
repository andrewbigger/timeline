package com.biggerconcept.timeline.ui.dialogs;

import com.biggerconcept.sdk.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.EpicDialogController;
import com.biggerconcept.timeline.State;
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
 * Epic editor dialog
 * 
 * @author Andrew Bigger
 */
public class EpicDialog {
    private Epic visibleEpic;
    
    /**
     * Opens the epic dialog
     * 
     * @param state application state
     * @param epic epic to modify
     * @param targetSet target set to apply epic to
     * @param isNew whether epic is new or existing
     * @throws java.lang.CloneNotSupportedException when unable to clone object
     */
    public static void open(
            State state,
            Epic epic,
            ArrayList<Epic> targetSet,
            boolean isNew
    ) throws CloneNotSupportedException {
        try {
            state.setOpenEpic(epic);
            
            URL location = state
                    .mainController()
                    .getClass()
                    .getResource("/fxml/EpicDialog.fxml");
            
            FXMLLoader loader = new FXMLLoader();
        
            loader.setLocation(location);
            loader.setResources(state.bundle());
            loader.setBuilderFactory(new JavaFXBuilderFactory());
        
            Parent epicWindow = (Parent) loader.load();
        
            EpicDialogController controller = (EpicDialogController) loader
                .getController();
        
            controller.setEpic(
                    state,
                    epic,
                    state.mainController(),
                    targetSet,
                    isNew
            );
        
            Stage stage = new Stage();
            
            Scene scene = new Scene(epicWindow);
            
            if (App.config().isTrue("darkMode")) {
                scene.getStylesheets().add("/css/application.dark.css");
            } else {
                scene.getStylesheets().add("/css/application.css");
            }
        
            stage.setScene(scene);
            stage.setTitle(epic.getName());
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
