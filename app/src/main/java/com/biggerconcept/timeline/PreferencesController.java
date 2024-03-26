package com.biggerconcept.timeline;

import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.appengine.ui.helpers.Date;
import com.biggerconcept.projectus.domain.Sprint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the preferences window.
 * 
 * @author Andrew Bigger
 */
public class PreferencesController implements Initializable {
    /**
     * Resource bundle for preferences window.
     */
    private ResourceBundle bundle;
    
    /**
     * Document domain model.
     */
    private Document currentDocument;
    
    /**
     * Document preferences
     */
    private Preferences currentPreferences;
    
    /**
     * Preferences border pane
     */
    @FXML
    public BorderPane preferencesBorderPane;
    
    /**
     * Extra small size text field.
     */
    @FXML
    public TextField extraSmallSizeTextField;
    
    /**
     * Small size text field.
     */
    @FXML
    public TextField smallSizeTextField;
    
    /**
     * Medium size text field.
     */
    @FXML
    public TextField mediumSizeTextField;
    
    /**
     * Large size text field.
     */
    @FXML
    public TextField largeSizeTextField;
    
    /**
     * Extra large size text field.
     */
    @FXML
    public TextField extraLargeSizeTextField;
    
    /**
     * Start date picker field.
     */
    @FXML
    public DatePicker startDatePicker;
    
    /**
     * Reference sprint 1 name text field
     */
    @FXML
    public TextField refSprintOneNameTextField;
    
    /**
     * Reference sprint 1 points text field
     */
    @FXML
    public TextField refSprintOneCompletedPointsTextField;
    
    /**
     * Reference sprint 2 name text field
     */
    @FXML
    public TextField refSprintTwoNameTextField;
    
    /**
     * Reference sprint 2 points text field
     */
    @FXML
    public TextField refSprintTwoCompletedPointsTextField;
    
    /**
     * Reference sprint 3 name text field
     */
    @FXML
    public TextField refSprintThreeNameTextField;
    
    /**
     * Reference sprint 3 points text field
     */
    @FXML
    public TextField refSprintThreeCompletedPointsTextField;
    
    /**
     * Reference sprint 4 name text field
     */
    @FXML
    public TextField refSprintFourNameTextField;
    
    /**
     * Reference sprint 4 points text field
     */
    @FXML
    public TextField refSprintFourCompletedPointsTextField;
    
    /**
     * Sprint length field
     */
    @FXML
    public TextField sprintSizeTextField;
    
    /**
     * Start sprint number field
     */
    @FXML
    public TextField startSprintNumberTextField;
    
    /**
     * Cancel preferences button.
     */
    @FXML
    public Button cancelPreferencesButton;

    /**
     * Save changes button.
     */
    @FXML
    public Button savePreferencesButton;
    
    /**
     * Initializer for the preference window
     * 
     * @param url preference fxml
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        MenuController.loadMenu(this, rb, preferencesBorderPane);
        applyTooltips();
    }
    
    /**
     * Applies tool tips to the controls of the preferences window.
     */
    private void applyTooltips() {
        cancelPreferencesButton.setTooltip(
            new Tooltip(bundle.getString(
                    "dialogs.preferences.actions.cancel.tooltip"
                )
            )
        );
        savePreferencesButton.setTooltip(
            new Tooltip(bundle.getString(
                    "dialogs.preferences.actions.save.tooltip"
                )
            )
        );
    }
    
    /**
     * Sets document pointer for the preference window.
     * 
     * @param doc current document
     */
    public void setDocument(Document doc) {
        this.currentDocument = doc;
        this.currentPreferences = currentDocument.getPreferences();
        mapPreferencesToWindow();
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
        Stage stage = (Stage) savePreferencesButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps given document to window.
     * 
     * @param doc 
     */
    private void mapPreferencesToWindow() {
        mapTaskPreferencesToWindow();
        mapEstimatePreferencesToWindow();
        mapSprintPreferencesToWindow();
    }
    
    /**
     * Maps task preferences to window
     */
    private void mapTaskPreferencesToWindow() {
        extraSmallSizeTextField.setText(
                String.valueOf(currentPreferences.getExtraSmallTaskSize())
        );
        smallSizeTextField.setText(
                String.valueOf(currentPreferences.getSmallTaskSize())
        );
        mediumSizeTextField.setText(
                String.valueOf(currentPreferences.getMediumTaskSize())
        );
        largeSizeTextField.setText(
                String.valueOf(currentPreferences.getLargeTaskSize())
        );
        extraLargeSizeTextField.setText(
                String.valueOf(currentPreferences.getExtraLargeTaskSize())
        );
    }
    
    /**
     * Maps estimate preferences to window
     */
    private void mapEstimatePreferencesToWindow() {
        startDatePicker.setValue(Date.fromEpoch(currentPreferences.getStart()));
        startSprintNumberTextField.setText(
                String.valueOf(
                        currentPreferences.getStartSprintNumber()
                )
        );
        
        refSprintOneNameTextField.setText(
                currentPreferences.getRefSprintOne().getName()
        );
        refSprintOneCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                                .getRefSprintOne()
                                .getCompletedPoints()
                )
        );
        refSprintTwoNameTextField.setText(
                currentPreferences.getRefSprintTwo().getName()
        );
        refSprintTwoCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                                .getRefSprintTwo()
                                .getCompletedPoints()
                )
        );
        refSprintThreeNameTextField.setText(
                currentPreferences.getRefSprintThree().getName()
        );
        refSprintThreeCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                                .getRefSprintThree()
                                .getCompletedPoints()
                )
        );
        refSprintFourNameTextField.setText(
                currentPreferences.getRefSprintFour().getName()
        );
        refSprintFourCompletedPointsTextField.setText(
                String.valueOf(
                        currentPreferences
                            .getRefSprintFour()
                            .getCompletedPoints()
                )
        );
    }
    
    /**
     * Maps sprint preferences to window
     */
    private void mapSprintPreferencesToWindow() {
        sprintSizeTextField.setText(
                String.valueOf(
                    currentPreferences.getSprintLength()
                )
        );
    }
    
    /**
     * Maps window content to new document object for serialization.
     * 
     * @return 
     */
    private void mapWindowToPreferences() {
        mapWindowToTaskPreferences();
        mapWindowToEstimatePreferences();
        mapWindowToSprintPreferences();
        
        currentDocument.setPreferences(currentPreferences);
    }
    
    /**
     * Maps window to task preferences.
     */
    private void mapWindowToTaskPreferences() {
        currentPreferences.setExtraSmallSize(extraSmallSizeTextField.getText());
        currentPreferences.setSmallSize(smallSizeTextField.getText());
        currentPreferences.setMediumSize(mediumSizeTextField.getText());
        currentPreferences.setLargeSize(largeSizeTextField.getText());
        currentPreferences.setExtraLargeSize(extraLargeSizeTextField.getText());
    }
    
    /**
     * Maps window to estimate preferences
     */
    private void mapWindowToEstimatePreferences() {
        currentPreferences.setStart(
                Date.toEpoch(startDatePicker.getValue())
        );
        
        currentPreferences.setStartSprintNumber(
                Integer.valueOf(
                        startSprintNumberTextField.getText()
                )
        );
        
        currentPreferences.setRefSprintOne(
                new Sprint(
                        refSprintOneNameTextField.getText(),
                        Integer.valueOf(
                                refSprintOneCompletedPointsTextField.getText()
                        )
                )
        );
        
        currentPreferences.setRefSprintTwo(
                new Sprint(
                        refSprintTwoNameTextField.getText(),
                        Integer.valueOf(
                                refSprintTwoCompletedPointsTextField.getText()
                        )
                )
        );
        
        currentPreferences.setRefSprintThree(
                new Sprint(
                        refSprintThreeNameTextField.getText(),
                        Integer.valueOf(
                                refSprintThreeCompletedPointsTextField.getText()
                        )
                )
        );
        
        currentPreferences.setRefSprintFour(
                new Sprint(
                        refSprintFourNameTextField.getText(),
                        Integer.valueOf(
                                refSprintFourCompletedPointsTextField.getText()
                        )
                )
        );
    }
    
    /**
     * Maps window to sprint preferences
     */
    private void mapWindowToSprintPreferences() {
        currentPreferences.setSprintLength(
                Integer.parseInt(
                        sprintSizeTextField.getText()
                )
        );
    }
    
    /**
     * Cancels the modification of preferences.
     */
    @FXML
    private void handleCancelPreferences() {
        try {
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.preferences.cancel"),
                    e
            );
        }
    }
    
    /**
     * Saves preference changes to document preferences.
     */
    @FXML
    private void handleSavePreferences() {
        try {
            mapWindowToPreferences();
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.preferences.save"),
                    e
            );
        }
    }

}
