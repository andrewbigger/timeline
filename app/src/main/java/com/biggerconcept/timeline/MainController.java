package com.biggerconcept.timeline;

import com.biggerconcept.timeline.domain.Judgement.Assessment;
import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.platform.OperatingSystem;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.appengine.ui.helpers.Date;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.actions.document.CreateDocument;
import com.biggerconcept.timeline.actions.epic.EditEpic;
import com.biggerconcept.timeline.actions.epic.EpicUnCommit;
import com.biggerconcept.timeline.actions.application.ExitApplication;
import com.biggerconcept.timeline.actions.epic.ImportEpic;
import com.biggerconcept.timeline.actions.document.OpenDocument;
import com.biggerconcept.timeline.actions.document.SaveDocument;
import com.biggerconcept.timeline.actions.application.OpenAboutDialog;
import com.biggerconcept.timeline.actions.application.OpenHelpPage;
import com.biggerconcept.timeline.actions.application.OpenPreferences;
import com.biggerconcept.timeline.actions.document.ViewNextYear;
import com.biggerconcept.timeline.actions.document.ViewPreviousYear;
import com.biggerconcept.timeline.actions.epic.AddEpic;
import com.biggerconcept.timeline.actions.epic.EditShelfEpic;
import com.biggerconcept.timeline.actions.epic.EpicCommit;
import com.biggerconcept.timeline.actions.epic.MoveEpicDown;
import com.biggerconcept.timeline.actions.epic.MoveEpicUp;
import com.biggerconcept.timeline.actions.epic.MoveShelfEpicDown;
import com.biggerconcept.timeline.actions.epic.MoveShelfEpicUp;
import com.biggerconcept.timeline.actions.epic.RemoveShelfEpic;
import com.biggerconcept.timeline.ui.domain.Timeline;
import com.biggerconcept.timeline.ui.tables.EpicsTimelineTable;
import com.biggerconcept.timeline.ui.tables.ShelfEpicsTable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Controller for the main view.
 * 
 * @author Andrew Bigger
 */
public class MainController implements Initializable {  
    /**
     * Application state
     */
    private State state;
    
    /**
     * Application menu.
     */
    @FXML
    public MenuBar mainMenu;
    
    /**
     * Application exit menu item.
     */
    @FXML
    public MenuItem quitMenuItem;
    
    /**
     * View year label
     */
    @FXML
    public Label currentYearLabel;
    
    /**
     * Velocity points label
     */
    @FXML
    public Label velocityPointsLabel;
    
    /**
     * Used sprints label
     */
    @FXML
    public Label usedSprintsLabel;
    
    /**
     * Total sprints label.
     */
    @FXML
    public Label totalSprintsLabel;
    
    /**
     * Committed points total
     */
    @FXML
    public Label commitmentPointsLabel;
    
    /**
     * Commitment indicator progress bar
     */
    @FXML
    public ProgressBar commitmentProgress;
    
    /**
     * Available points total
     */
    @FXML
    public Label availableCommitmentLabel;
    
    /**
     * Adds epic to shelf.
     */
    @FXML
    public Button addEpicToShelfButton;
    
    /**
     * Button to remove epic from shelf
     */
    @FXML
    public Button removeEpicFromShelfButton;
    
    /**
     * Button to edit epic on shelf.
     */
    @FXML
    public Button editEpicOnShelfButton;
    
    /**
     * Button to move epic from shelf.
     */
    @FXML
    public Button moveEpicFromShelfButton;
    
    /**
     * Button to move epic to shelf
     */
    @FXML
    public Button moveEpicToShelfButton;
    
    /**
     * Shelf epics table view
     */
    @FXML
    public TableView shelfTableView;
    
    /**
     * Timeline table view
     */
    @FXML
    public TableView timelineTableView;
    
    /**
     * Selected epics table view
     */
    @FXML
    public TableView epicTableView;
    
    /**
     * Chart web view
     */
    @FXML
    public WebView chartWebView;
    
    /**
     * Judgement combo box
     */
    @FXML
    public ComboBox judgementComboBox;
    
    /**
     * Notes text area.
     */
    @FXML
    public TextArea notesTextArea;
    
    /**
     * Open file toolbar button.
     * 
     * When clicked, this should prompt the user to choose a file.
     */
    @FXML
    public Button openFileButton;
    
    /**
     * Save file toolbar button.
     * 
     * When clicked, this serializes the current state of the app to file.
     */
    @FXML
    public Button saveFileButton;
    
    /**
     * Toolbar button for creating a new file.
     * 
     * When clicked this will clear the current object and replace it with
     * a new one.
     */
    @FXML
    public Button newFileButton;
    
    /**
     * Initializes the main window.
     * 
     * @param url main window FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        state = new State(this, rb);

        if (OperatingSystem.isMac()) {
            mainMenu.useSystemMenuBarProperty().set(true);
            quitMenuItem.visibleProperty().set(false);
        }

        applyTooltips();
        mapDocumentToWindow();
    }
    
    /**
     * Assembles window title
     * 
     * @return title for window
     */
    private String buildWindowTitle() {
        String title = state.getOpenDocument().title();
        
        if (title == null || title.trim() == "") {
            title = state.bundle().getString("application.name");
        }
        
        return title + " - " + state.bundle().getString("application.name");
    }
    
    /**
     * Sets window title on main stage
     */
    private void setWindowTitle() {
        Stage window = window();
        
        if (window != null) {
            window.setTitle(buildWindowTitle());
        }
    }

    /**
     * Creates and adds tool tips to UI controls.
     */
    private void applyTooltips() {
        openFileButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.open.tooltip"))
        );
        saveFileButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.save.tooltip"))
        );
        newFileButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.new.tooltip"))
        );
    }
    
    /**
     * Returns the main window stage.
     * 
     * The main window stage is the window that the new file button control is
     * in. 
     * 
     * @return 
     */
    private Stage window() {
        Scene scene = (Scene) newFileButton.getScene();
        
        if (scene != null) {
            Stage stage = (Stage) scene.getWindow();
            return stage;
        }
        
        return null;
    }
    
    /**
     * Maps given document to window.
     */
    public void mapDocumentToWindow() {
        state.getOpenDocument().rebuildIdentifiers();
        
        setWindowTitle();
        applyPreferencesToWindow();
        mapYearToWindow();
        mapOutlookToWindow();
        mapShelfToWindow();
        mapEpicsToWindow();
        mapAssessmentsToWindow();
        mapNotesToWindow();
    }
    
    /**
     * Maps year to window
     */
    private void mapYearToWindow() {
        currentYearLabel.setText(state.getViewYear().getName());
    }
    
    /**
     * Maps velocity, available sprints and commitment to window.
     */
    private void mapOutlookToWindow() {
        velocityPointsLabel.setText(
                String.valueOf(
                    state
                        .getOpenDocument()
                        .getPreferences()
                        .calculateAveragePointsPerSprint()
                )
        );
        
        int availableSprints = state.getViewYear().calculateSprints(
                                state
                                    .getOpenDocument()
                                    .getPreferences()
                                    .getSprintLength()
                        );
        
        usedSprintsLabel.setText(
                String.valueOf(
                        availableSprints - 
                                state
                                    .getOpenDocument()
                                    .calculateCommittedSprints()
                )
        );
        
        totalSprintsLabel.setText(
                String.valueOf(
                     availableSprints   
                )
        );
        
        commitmentPointsLabel.setText(
                String.valueOf(
                        state.getOpenDocument().calculateCommittedPoints()
                )
        );
        
        int availablePoints = state.getOpenDocument()
                                .getPreferences()
                                .calculateAvailablePointsIn(availableSprints);
        
        commitmentProgress.setProgress(
                state.getOpenDocument().calculateCommitmentProgress(
                        availablePoints
                )
        );

        availableCommitmentLabel.setText(
                String.valueOf(
                        availablePoints
                )
        );
    }
    
    /**
     * Maps shelf epics to window
     */
    private void mapShelfToWindow() {
        ShelfEpicsTable epicsTable = new ShelfEpicsTable(
                state.bundle(),
                state.getOpenDocument().getPreferences().asProjectusPreferences(),
                state.getOpenDocument().getShelf()
        );
        
        epicsTable.bind(shelfTableView);
    }
    
    /**
     * Maps selected epics to window
     */
    private void mapEpicsToWindow() {
        int availableSprints = state.getViewYear().calculateSprints(
                                state.getOpenDocument()
                                    .getPreferences()
                                    .getSprintLength()
                        );
        
        Timeline tl = new Timeline(
                state.getViewYear(),
                state.getOpenDocument().getEpics(),
                Date.fromEpoch(state.getOpenDocument().getPreferences().getStart()),
                state.getOpenDocument().getPreferences(),
                availableSprints
        );
        
        tl.calculate(state.getOpenDocument().getPreferences(), availableSprints);
        
        EpicsTimelineTable epicsTable = new EpicsTimelineTable(
                state.bundle(),
                state.getOpenDocument().getPreferences().asProjectusPreferences(),
                tl.getEpics(),
                state.getViewYear().getFirstDay(),
                availableSprints,
                state.getViewYear()
        );
        
        epicsTable.bind(epicTableView);
    }
    
    /**
     * Maps assessment options and selection to window
     */
    private void mapAssessmentsToWindow() {
        judgementComboBox.getItems().clear();
        
        for (Assessment a : Assessment.values()) {
            judgementComboBox.getItems().add(a);
        }
        
        judgementComboBox
                .getSelectionModel()
                .select(state.getOpenDocument().getJudgement());
    }
    
    /**
     * Maps notes to window
     */
    private void mapNotesToWindow() {
        notesTextArea.setText(state.getOpenDocument().getNotes());
    }
    
    /**
     * Applies document settings to window.
     */
    private void applyPreferencesToWindow() {
        // TODO: apply preferences
    }
    
    /**
     * Maps window content to new document object for serialization.
     * 
     * @return 
     */
    public void mapWindowToDocument() {
        mapWindowToJudgement();
        mapWindowToNotes();
    }
    
    /**
     * Maps judgement selection to document
     */
    private void mapWindowToJudgement() {
        state.getOpenDocument().setJudgement(
                (Assessment) judgementComboBox
                        .getSelectionModel()
                        .getSelectedItem()
        );
    }
    
    /**
     * Maps window notes to document.
     */
    private void mapWindowToNotes() {
        state.getOpenDocument().setNotes(
                notesTextArea.getText()
        );
    }
    
    
    /**
     * Handles adding an epic to the shelf.
     */
    @FXML
    private void handleAddEpicToShelf() {
        try {
            perform(AddEpic.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Handles removing an epic from the shelf
     */
    @FXML
    private void handleRemoveEpicFromShelf() {
        try {
            perform(RemoveShelfEpic.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Handles moving a shelf epic up
     */
    @FXML
    private void handleMoveShelfEpicUp() {
        try {
           perform(MoveShelfEpicUp.class);
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
     * Handles moving a shelf epic down
     */
    @FXML
    private void handleMoveShelfEpicDown() {
        try {
            perform(MoveShelfEpicDown.class);
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
     * Handles editing a shelf epic
     */
    @FXML
    private void handleEditShelfEpic() {
        try {
            perform(EditShelfEpic.class);
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
     * Handles commitment to an epic
     */
    @FXML
    private void handleCommitToEpic() {
        try {
            perform(EpicCommit.class);
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
     * Handles moving a shelf epic up
     */
    @FXML
    private void handleMoveEpicUp() {
        try {
            perform(MoveEpicUp.class);
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
     * Handles moving a shelf epic down
     */
    @FXML
    private void handleMoveEpicDown() {
        try {
            perform(MoveEpicDown.class);
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
     * Handles editing a shelf epic
     */
    @FXML
    private void handleEditEpic() {
        try {
            perform(EditEpic.class);
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
     * Handles removal of commitment to an epic
     */
    @FXML
    private void handleUnCommit() {
        try {
            perform(EpicUnCommit.class);
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
     * Handles show previous year
     */
    @FXML
    private void handleShowPrevYear() {
        try {
            perform(ViewPreviousYear.class);
        } catch (Exception e) {
             ErrorAlert.show(
                     state.bundle(),
                     state.bundle().getString("errors.viewYear"),
                     e
             );
        }
    }
    
    /**
     * Handles show next year
     */
    @FXML
    private void handleShowNextYear() {
        try {
            perform(ViewNextYear.class);
        } catch (Exception e) {
             ErrorAlert.show(
                     state.bundle(),
                     state.bundle().getString("errors.viewYear"),
                     e
             );
        }
    }
    
    /**
     * Handles import epic
     */
    @FXML
    private void handleImportEpic() {
        try {
            perform(ImportEpic.class);
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
     * Creates a new document.
     * 
     * This will replace the currentDocument with a new one, having the effect
     * of creating a new locale file.
     */
    @FXML
    private void handleCreateNewDocument() {
        try {
            perform(CreateDocument.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.new"),
                    e
            );
        }
    }
    
    /**
     * Opens a document.
     * 
     * Shows open file dialog to allow user to choose a file.
     * 
     * If there is a choice then the document is deserialized and loaded
     * into memory.
     * 
     * If there is an error deserializing the document a error alert will be
     * shown.
     * 
     * When no choice is made, nothing will be done.
     */
    @FXML
    private void handleOpenDocument() {
        try {
            perform(OpenDocument.class);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.open"),
                    e
            );
        }
    }
    
    /**
     * Saves open document.
     * 
     * When the file is not set, a save file dialog is shown. If a location is
     * not picked, then nothing will happen.
     * 
     * The document will then be saved to disk.
     */
    @FXML
    private void handleSaveDocument() {
        try {
            perform(SaveDocument.class);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.saveFile"),
                    e
            );
        }
    }
    
    /**
     * Shows preference dialog.The contents of the dialog are loaded from the 
     * pane FXML.
     * 
     * A call to setup dialog ensures that the dialog is configured 
     * appropriately before it is shown.
     * 
     */
    @FXML
    private void handleOpenPreferencesDialog() {
        try {
            perform(OpenPreferences.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Opens about application dialog box.
     */
    @FXML
    private void handleOpenAboutDialog() {
        try {
            perform(OpenAboutDialog.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Opens help website in default browser.
     */
    @FXML
    private void handleViewHelp() {
        try {
            perform(OpenHelpPage.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Exits application.
     * 
     * @param event
     */
    @FXML
    private void handleApplicationExit() {
        try {
            perform(ExitApplication.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    private void perform(Class action) 
            throws Exception {
        Action act = (Action) action.newInstance();
        act.perform(state, window());
        mapDocumentToWindow();
    }

}
