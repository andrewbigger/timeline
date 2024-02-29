package com.biggerconcept.timeline;

import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.ui.tables.TasksTable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the eoic dialog.
 * 
 * @author Andrew Bigger
 */
public class EpicDialogController implements Initializable {
    /**
     * Resource bundle for preferences window.
     */
    private ResourceBundle bundle;
    
    /**
     * Document domain model.
     */
    private Document currentDocument;
    
    /**
     * Epic to show
     */
    private Epic currentEpic;
    
    /**
     * Initializer for the dialog window
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
    }

    
    /**
     * Sets document pointer for the dialog window.
     * 
     * @param doc 
     */
    public void setEpic(Document doc, Epic epic) {
        this.currentDocument = doc;
        this.currentEpic = epic;
        mapEpicToWindow();
    }
    
    /**
     * Epic name field
     */
    @FXML
    public TextField epicName;
    
    /**
     * Epic summary field
     */
    @FXML
    public TextArea epicSummary;
    
    /**
     * Tasks table
     */
    @FXML
    public TableView tasksTable;
    
    /**
     * Cancel preferences button.
     */
    @FXML
    public Button cancelEpicButton;

    /**
     * Save changes button.
     */
    @FXML
    public Button saveEpicButton;
    
    /**
     * Outlook label
     */
    @FXML
    public Label outlookLabel;
    
    /**
     * Returns the preference window stage.
     * 
     * The pref window stage is the window that the save button control is
     * in. 
     * 
     * @return 
     */
    private Stage window() {
        Stage stage = (Stage) saveEpicButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps given epic to window.
     * 
     * @param doc 
     */
    private void mapEpicToWindow() {
        epicName.setText(currentEpic.getName());
        epicSummary.setText(currentEpic.getSummary());
        
        TasksTable tasksTable = new TasksTable(
            bundle,
            currentEpic.getTasks(),
            currentDocument.getPreferences().asProjectusPreferences(),
            currentEpic.getIdentifier()
        );
        
        outlookLabel.setText("0");
    }
    
    /**
     * Maps epic for serialization.
     * 
     * @return 
     */
    private void mapWindowToEpic() {
        currentEpic.setName(epicName.getText());
        currentEpic.setSummary(epicSummary.getText());
    }
    
    /**
     * Cancels the modification of edit.
     */
    @FXML
    private void handleCancelEpic() {
        try {
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.epic.cancel"),
                    e
            );
        }
    }
    
    /**
     * Saves epic changes to epic.
     */
    @FXML
    private void handleSaveEpic() {
        try {
            mapWindowToEpic();
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.epic.save"),
                    e
            );
        }
    }

}
