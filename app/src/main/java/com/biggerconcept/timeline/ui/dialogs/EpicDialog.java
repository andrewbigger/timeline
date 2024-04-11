package com.biggerconcept.timeline.ui.dialogs;

import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.domain.Epic;
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
    /**
     * Opens the epic dialog
     * 
     * @param state application state
     * @param epic epic to modify
     * @param targetSet target set to apply epic to
     * @param isNew whether epic is new or existing
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
        
            stage.setScene(new Scene(epicWindow));
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
