package com.biggerconcept.timeline;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.sdk.ui.dialogs.ErrorAlert;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Release;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the release dialog.
 * 
 * @author Andrew Bigger
 */
public class ReleaseDialogController implements Initializable {
    /**
     * Application state
     */
    private State state;
    
    /**
     * Application resource bundle
     */
    private ResourceBundle bundle;
    
    /**
     * Open release
     */
    private Release openRelease;
    
    /**
     * Target set of releases
     */
    private ArrayList<Release> targetSet;
    
    /**
     * Whether the release is a new release
     */
    private boolean isNew;
    
    /**
     * Release dialog border pane
     */
    @FXML
    public BorderPane releaseEditorBorderPane;
    
    /**
     * Release name field
     */
    @FXML
    public TextField releaseNameTextField;
    
    /**
     * Release description area
     */
    @FXML
    public TextArea releaseDescriptionTextArea;
    
    /**
     * Epics list view
     */
    @FXML
    public ListView epicsListView;
    
    /**
     * Epic choice list
     */
    @FXML
    public ComboBox epicChooserComboBox;
    
    /**
     * Add epic button
     */
    @FXML
    public Button addEpicButton;
    
    /**
     * Remove epic button
     */
    @FXML
    public Button removeEpicButton;
    
    /**
     * Cancel preferences button.
     */
    @FXML
    public Button cancelButton;

    /**
     * Save changes button.
     */
    @FXML
    public Button saveButton;
    
    /**
     * Initializer for the dialog window
     * 
     * @param url url for dialog window
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        MenuController.loadMenu(this, rb, releaseEditorBorderPane);
        applyTooltips();
    }
    
    /**
     * Creates and adds tool tips to UI controls.
     */
    private void applyTooltips() {
        // TODO: Tooltips
    }

    /**
     * Sets release pointer for the dialog window.
     * 
     * @param state application state
     * @param release open release
     * @param parent parent window
     * @param targetSet target set on save
     * @param isNew whether the epic should be added on save
     * @throws java.lang.CloneNotSupportedException when unable to clone object
     */
    public void setRelease(
            State state,
            Release release,
            MainController parent,
            ArrayList<Release> targetSet,
            boolean isNew
    ) throws CloneNotSupportedException {
        this.state = state;
        this.targetSet = targetSet;
        this.isNew = isNew;
        this.openRelease = release;
        
        mapDocumentToWindow();
    }
    
    /**
     * Returns the preference window stage.
     * 
     * The pref window stage is the window that the save button control is
     * in. 
     * 
     * @return 
     */
    private Stage window() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps given epic to window.
     * 
     * @param doc 
     */
    private void mapDocumentToWindow() throws CloneNotSupportedException {
        mapDetailsToWindow();
        mapEpicOptionsToWindow();
        mapEpicsToWindow();
        
        state.mainController().mapDocumentToWindow();
    }
    
    /**
     * Maps release details to the window
     */
    private void mapDetailsToWindow() {
        releaseNameTextField.setText(openRelease.getName());
        releaseDescriptionTextArea.setText(openRelease.getDescription());
    }
    
    /**
     * Maps available epics to add to release
     */
    private void mapEpicOptionsToWindow() {
        epicChooserComboBox.getItems().clear();
        
        for (Epic e : state.getOpenDocument().availableEpicsForRelease()) {
            epicChooserComboBox.getItems().add(e);
        }
    }
    
    /**
     * Maps epics items to window
     */
    private void mapEpicsToWindow() {
        epicsListView.getItems().clear();

        for (Epic e : openRelease.epics(state.getOpenDocument())) {
            epicsListView.getItems().add(e);
        }
    }
    
    /**
     * Maps epic for serialization.
     * 
     * @return 
     */
    private void mapWindowToDocument() {
        state.getOpenDocument().rebuildIdentifiers();
        
        openRelease.setName(releaseNameTextField.getText());
        openRelease.setDescription(releaseDescriptionTextArea.getText());
    }
    
    /**
     * Handles adding an epic to the release
     */
    @FXML
    private void handleAddEpicToRelease() {
        try {
            Epic selectedEpic = (Epic) epicChooserComboBox
                    .getSelectionModel()
                    .getSelectedItem();
            
            if (selectedEpic == null) {
                throw new NoChoiceMadeException();
            }
            
            openRelease.addEpic(selectedEpic);
            
            mapEpicOptionsToWindow();
            mapEpicsToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Handles removing an epic from the release
     */
    @FXML
    private void handleRemoveEpicFromRelease() {
        try {
            Epic selectedEpic = (Epic) epicsListView
                    .getSelectionModel()
                    .getSelectedItem();
            
            if (selectedEpic == null) {
                throw new NoChoiceMadeException();
            }
            
            openRelease.removeEpic(selectedEpic);
            
            mapEpicOptionsToWindow();
            mapEpicsToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Cancels the modification of edit.
     */
    @FXML
    private void handleCancelRelease() {
        try {
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Saves epic changes to epic.
     */
    @FXML
    private void handleSaveRelease() {
        try {
            if (isNew == true) {
                targetSet.add(openRelease);
            }
            
            mapWindowToDocument();
            state.mainController().mapDocumentToWindow();
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
}
