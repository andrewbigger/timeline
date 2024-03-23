package com.biggerconcept.timeline;

import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.domain.Epic;
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
 * Dialog open methods
 * 
 * @author Andrew Bigger
 */
public class Dialogs {
    public static void openEpicDialog(
            State state,
            Epic epic,
            ArrayList<Epic> targetSet,
            boolean isNew
    ) {
        try {
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
                    state.getOpenDocument(),
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
