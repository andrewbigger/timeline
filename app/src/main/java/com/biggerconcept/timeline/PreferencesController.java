package com.biggerconcept.timeline;

import com.biggerconcept.sdk.IPreferencesController;
import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.ui.dialogs.ReportBuilderDialog;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.sdk.ui.dialogs.ErrorAlert;
import com.biggerconcept.sdk.ui.helpers.Date;
import com.biggerconcept.projectus.domain.Sprint;
import com.biggerconcept.timeline.reports.Element;
import com.biggerconcept.timeline.reports.Report;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the preferences window.
 * 
 * @author Andrew Bigger
 */
public class PreferencesController 
        implements Initializable, IPreferencesController {
    /**
     * Application state
     */
    private State state;
    
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
     * Sprint size combo box field
     */
    @FXML
    public ComboBox sprintSizeComboBox;
    
    /**
     * Start sprint number field
     */
    @FXML
    public TextField startSprintNumberTextField;
    
    /**
     * Add report button
     */
    @FXML
    public Button addReportButton;
    
    /**
     * Remove report button
     */
    @FXML
    public Button removeReportButton;
    
    /**
     * Edit report button
     */
    @FXML
    public Button editReportButton;
    
    /**
     * Reports list view
     */
    @FXML
    public ListView reportsListView;
    
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
     * Preference tabs
     */
    @FXML
    public TabPane preferenceTabs;
    
    /**
     * Reports tab
     */
    @FXML
    public Tab reportsTab;
    
    /**
     * Company tab
     */
    @FXML
    public Tab companyTab;
    
    /**
     * Default company name text field
     */
    @FXML
    public TextField defaultCompanyName;
    
    /**
     * Default company domain text field
     */
    @FXML
    public TextField defaultCompanyDomain;

    
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
        
        sprintSizeComboBox.getItems().add(1);
        sprintSizeComboBox.getItems().add(2);
        sprintSizeComboBox.getItems().add(4);
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
     * @param state application state
     */
    public void setState(State state) {
        this.state = state;
        this.currentDocument = state.getOpenDocument();
        this.currentPreferences = currentDocument.getPreferences();
        mapPreferencesToWindow();
    }
    
    /**
     * Switch to selected tab
     * 
     * @param target target tab
     */
    public void focusTab(Tab target) {
        preferenceTabs.getSelectionModel().select(target);
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
     */
    public void mapPreferencesToWindow() {
        mapTaskPreferencesToWindow();
        mapEstimatePreferencesToWindow();
        mapSprintPreferencesToWindow();
        mapReportsToWindow();
        mapCompanyToWindow();
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
        sprintSizeComboBox.getSelectionModel().select(
                String.valueOf(currentPreferences.getSprintLength())
        );
    }
    
    /**
     * Maps reports to window
     */
    private void mapReportsToWindow() {
        reportsListView.getItems().clear();
        
        for (IReport r : currentPreferences.getReports()) {
            reportsListView.getItems().add(r);
        }
    }
    
    private void mapCompanyToWindow() {
        defaultCompanyName.setText(currentPreferences.getDefaultCompany());
        defaultCompanyDomain.setText(currentPreferences.getDefaultDomain());
    }
    
    /**
     * Maps window content to new document object for serialization.
     */
    @Override
    public void mapWindowToPreferences() {
        mapWindowToTaskPreferences();
        mapWindowToEstimatePreferences();
        mapWindowToSprintPreferences();
        mapWindowToCompany();
        
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
                Integer.valueOf(
                        sprintSizeComboBox
                                .getSelectionModel()
                                .getSelectedItem()
                                .toString()
                )
        );
    }
    
    /**
     * Maps company settings to preferences.
     */
    private void mapWindowToCompany() {
        currentPreferences.setDefaultCompany(defaultCompanyName.getText());
        currentPreferences.setDefaultDomain(defaultCompanyDomain.getText());
    }
    
    /**
     * Returns application resources
     * @return application resources
     */
    public ResourceBundle bundle() {
        return bundle;
    }
    
    /**
     * Opens report builder dialog to add a new report
     */
    @FXML
    private void handleAddReport() {
        try {
            ReportBuilderDialog.open(
                    this,
                    Element.availableContent(state),
                    new Report("New Report"), 
                    currentPreferences.getReports(),
                    true,
                    com.biggerconcept.sdk.Engine.class.getResource("/fxml/ReportBuilder.fxml")
            );
            
            mapPreferencesToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Opens report builder dialog to add a new report
     */
    @FXML
    private void handleRemoveReport() {
        try {
            Report selected = (Report) reportsListView
                    .getSelectionModel()
                    .getSelectedItem();
            
            if (selected == null) {
                throw new NoChoiceMadeException();
            }
            
            // TODO confirm
            
            currentPreferences.getReports().remove(selected);
            
            mapPreferencesToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Opens report builder dialog to edit an existing report
     */
    @FXML
    private void handleEditReport() {
        try {
             Report selected = (Report) reportsListView
                    .getSelectionModel()
                    .getSelectedItem();
            
            if (selected == null) {
                throw new NoChoiceMadeException();
            }
            
            ReportBuilderDialog.open(
                    this,
                    Element.availableContent(state),
                    selected,
                    currentPreferences.getReports(),
                    false,
                    this.getClass().getResource("/fxml/ReportBuilder.fxml")
            );
            
            mapPreferencesToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.generic"),
                    e
            );
        }
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
            state.mainController().mapDocumentToWindow();
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
