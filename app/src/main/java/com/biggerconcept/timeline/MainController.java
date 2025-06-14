package com.biggerconcept.timeline;

import com.biggerconcept.timeline.domain.Judgement.Assessment;
import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.ui.menus.ReportMenuBuilder;
import com.biggerconcept.sdk.ui.dialogs.ErrorAlert;
import com.biggerconcept.sdk.doctree.ui.ResourcesTreeView;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.actions.application.OpenPreferences;
import com.biggerconcept.timeline.actions.resources.AddDocument;
import com.biggerconcept.timeline.actions.resources.AddGroup;
import com.biggerconcept.timeline.actions.resources.EditNode;
import com.biggerconcept.timeline.actions.resources.MoveNode;
import com.biggerconcept.timeline.actions.resources.RemoveNode;
import com.biggerconcept.timeline.actions.document.CreateDocument;
import com.biggerconcept.timeline.actions.document.OpenDocument;
import com.biggerconcept.timeline.actions.document.SaveDocument;
import com.biggerconcept.timeline.actions.epic.EditEpic;
import com.biggerconcept.timeline.actions.epic.EpicUnCommit;
import com.biggerconcept.timeline.actions.epic.ImportEpic;
import com.biggerconcept.timeline.actions.document.ViewNextYear;
import com.biggerconcept.timeline.actions.document.ViewPreviousYear;
import com.biggerconcept.timeline.actions.epic.AddEpic;
import com.biggerconcept.timeline.actions.epic.EditShelfEpic;
import com.biggerconcept.timeline.actions.epic.EpicCommit;
import com.biggerconcept.timeline.actions.epic.ExportEpic;
import com.biggerconcept.timeline.actions.epic.MoveEpicDown;
import com.biggerconcept.timeline.actions.epic.MoveEpicUp;
import com.biggerconcept.timeline.actions.epic.MoveShelfEpicDown;
import com.biggerconcept.timeline.actions.epic.MoveShelfEpicUp;
import com.biggerconcept.timeline.actions.epic.RemoveShelfEpic;
import com.biggerconcept.timeline.actions.epic.ToggleCounts;
import com.biggerconcept.timeline.actions.release.AddRelease;
import com.biggerconcept.timeline.actions.release.RemoveRelease;
import com.biggerconcept.timeline.actions.release.EditRelease;
import com.biggerconcept.timeline.actions.release.MoveReleaseDown;
import com.biggerconcept.timeline.actions.release.MoveReleaseUp;
import com.biggerconcept.timeline.actions.resources.AddContact;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.ui.domain.Timeline;
import com.biggerconcept.timeline.ui.tables.EpicsTimelineTable;
import com.biggerconcept.timeline.ui.tables.ReleasesTable;
import com.biggerconcept.timeline.ui.tables.ReleasesTimelineTable;
import com.biggerconcept.timeline.ui.tables.ShelfEpicsTable;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
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
    
    @FXML
    public BorderPane toolbarBorderPane;
    
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
     * Go to previous year button
     */
    @FXML
    public Button prevYearButton;
    
    /**
     * Go to next year button
     */
    @FXML
    public Button nextYearButton;
    
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
     * Button to move shelf epic up
     */
    @FXML
    public Button moveShelfEpicUpButton;
    
    /**
     * Button to move shelf epic down
     */
    @FXML
    public Button moveShelfEpicDownButton;
    
    /**
     * Button to import epic from projectus
     */
    @FXML
    public Button importEpicButton;
    
    /**
     * Button to commit to epic
     */
    @FXML
    public Button commitToEpicButton;
    
    /**
     * Button to move epic up
     */
    @FXML
    public Button moveEpicUpButton;
    
    /**
     * Button to move epic down
     */
    @FXML
    public Button moveEpicDownButton;
    
    /**
     * Button to edit epic
     */
    @FXML
    public Button editEpicButton;
    
    /**
     * Button to export epic to projectus
     */
    @FXML
    public Button exportToProjectusButton;
    
    /**
     * Button to remove commitment to epic
     */
    @FXML
    public Button unCommitToEpicButton;
    
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
     * Toggle count view button
     */
    @FXML
    public Button toggleCountsButton;
    
    /**
     * Add release button
     */
    @FXML
    public Button addReleaseButton;
    
    /**
     * Remove release button
     */
    @FXML
    public Button removeReleaseButton;
    
    /**
     * Move release up button
     */
    @FXML
    public Button moveReleaseUpButton;
    
    /**
     * Move release down button
     */
    @FXML
    public Button moveReleaseDownButton;
    
    /**
     * Edit release button
     */
    @FXML
    public Button editReleaseButton;
    
    /**
     * Shelf epics table view
     */
    @FXML
    public TableView shelfTableView;
    
    /**
     * Releases table view
     */
    @FXML
    public TableView releasesTableView;
    
    /**
     * Releases timeline table view
     */
    @FXML
    public TableView releasesTimelineTableView;
    
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
     * Reports menu button
     */
    @FXML
    public MenuButton reportsMenuButton;
    
    /**
     * Add document group button
     */
    @FXML
    public Button addGroupButton;
    
    /**
     * Add document button
     */
    @FXML
    public Button addDocumentButton;
    
    /**
     * Remove resource button
     */
    @FXML
    public Button removeNodeButton;
    
    /**
     * Edit resource button
     */
    @FXML
    public Button editNodeButton;
    
    /**
     * Move resource button
     */
    @FXML
    public Button moveNodeButton;
    
    /**
     * Resources tree view
     */
    @FXML
    public TreeView resourcesTreeView;
    
    /**
     * Add contact button
     */
    @FXML
    public Button addContactButton;
    
    /**
     * Initializes the main window.
     * 
     * @param url main window FXML
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        state = new State(this, rb);

        MenuController.loadMenu(this, state, window(), toolbarBorderPane);
        
        applyTooltips();
        
        try {
            mapDocumentToWindow();
        } catch (CloneNotSupportedException ex) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    ex
            );
            
            System.exit(1);
        }
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
        prevYearButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.prevYear.tooltip"))
        );
        nextYearButton.setTooltip(
                new Tooltip(state.bundle().getString("toolbar.nextYear.tooltip"))
        );
        addEpicToShelfButton.setTooltip(
                new Tooltip(state.bundle().getString("shelf.addEpic.tooltip"))
        );
        removeEpicFromShelfButton.setTooltip(
                new Tooltip(state.bundle().getString("shelf.removeEpic.tooltip"))
        );
        moveShelfEpicUpButton.setTooltip(
                new Tooltip(state.bundle().getString("shelf.moveUp.tooltip"))
        );
        moveShelfEpicDownButton.setTooltip(
                new Tooltip(state.bundle().getString("shelf.moveDown.tooltip"))
        );
        editEpicOnShelfButton.setTooltip(
                new Tooltip(state.bundle().getString("shelf.editEpic.tooltip"))
        );
        importEpicButton.setTooltip(
                new Tooltip(state.bundle().getString("shelf.import.tooltip"))
        );
        commitToEpicButton.setTooltip(
                new Tooltip(state.bundle().getString("shelf.commit.tooltip"))
        );
        moveEpicUpButton.setTooltip(
                new Tooltip(state.bundle().getString("timeline.moveUp.tooltip"))
        );
        moveEpicDownButton.setTooltip(
                new Tooltip(state.bundle().getString("timeline.moveDown.tooltip"))
        );
        editEpicButton.setTooltip(
                new Tooltip(state.bundle().getString("timeline.edit.tooltip"))
        );
        exportToProjectusButton.setTooltip(
                new Tooltip(state.bundle().getString("timeline.projectus.tooltip"))
        );
        unCommitToEpicButton.setTooltip(
                new Tooltip(state.bundle().getString("timeline.uncommit.tooltip"))
        );
        judgementComboBox.setTooltip(
                new Tooltip(state.bundle().getString("judgement.tooltip"))
        );
        toggleCountsButton.setTooltip(
                new Tooltip(state.bundle().getString("timeline.numberView.tooltip"))
        );
        addReleaseButton.setTooltip(
                new Tooltip(state.bundle().getString("releases.add.tooltip"))
        );
        removeReleaseButton.setTooltip(
                new Tooltip(state.bundle().getString("releases.remove.tooltip"))
        );
        editReleaseButton.setTooltip(
                new Tooltip(state.bundle().getString("releases.edit.tooltip"))
        );
        moveReleaseUpButton.setTooltip(
                new Tooltip(state.bundle().getString("releases.moveUp.tooltip"))
        );
        moveReleaseDownButton.setTooltip(
                new Tooltip(state.bundle().getString("releases.moveDown.tooltip"))
        );
        addGroupButton.setTooltip(
                new Tooltip(state.bundle().getString("resources.addGroup.tooltip"))
        );
        addDocumentButton.setTooltip(
                new Tooltip(state.bundle().getString("resources.addDocument.tooltip"))
        );
        removeNodeButton.setTooltip(
                new Tooltip(state.bundle().getString("resources.removeNode.tooltip"))
        );
        editNodeButton.setTooltip(
                new Tooltip(state.bundle().getString("resources.editNode.tooltip"))
        );
        moveNodeButton.setTooltip(
                new Tooltip(state.bundle().getString("resources.moveNode.tooltip"))
        );
        addContactButton.setTooltip(
                new Tooltip(state.bundle().getString("resources.addContact.tooltip"))
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
    public Stage window() {
        Scene scene = (Scene) newFileButton.getScene();
        
        if (scene != null) {
            Stage stage = (Stage) scene.getWindow();
            return stage;
        }
        
        return null;
    }
    
    /**
     * Maps given document to window.
     * @throws java.lang.CloneNotSupportedException when unable to clone sprint
     */
    public void mapDocumentToWindow() throws CloneNotSupportedException {
        state.getOpenDocument().rebuildIdentifiers();
        
        Timeline timeline = new Timeline(
                state.getOpenDocument().getEpics(),
                state.getOpenDocument().getReleases(),
                state.getOpenDocument().getPreferences()
        );
        
        setWindowTitle();
        applyPreferencesToWindow();
        mapReportsToWindow();
        mapYearToWindow();
        mapShelfToWindow();
        mapEpicsToWindow(timeline);
        mapReleasesToWindow(timeline);
        mapOutlookToWindow(timeline);
        mapAssessmentsToWindow();
        mapNotesToWindow();
        mapResourcesToWindow();
    }
    
    /**
     * Map reports to report menu
     */
    private void mapReportsToWindow() {
        ArrayList<IReport> reports = state
                .getOpenDocument()
                .getPreferences()
                .getReports();
        
        ReportMenuBuilder.build(
                state.bundle(),
                reportsMenuButton, 
                reports,
                state.getReportContent()
        );
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
    private void mapOutlookToWindow(Timeline timeline) {
        velocityPointsLabel.setText(
                String.valueOf(
                    state
                        .getOpenDocument()
                        .getPreferences()
                        .calculateAveragePointsPerSprint()
                )
        );
        
        int availableSprints = state.getViewYear().countAvailableSprints(
                state.getOpenDocument().getPreferences()
        );
        
        usedSprintsLabel.setText(
                String.valueOf(
                        timeline.countUsedSprintsInYear(state.getViewYear()))
        );
        
        totalSprintsLabel.setText(
                String.valueOf(
                     availableSprints
                )
        );
        
        int committedPoints = timeline.countUsedPointsInYear(state.getViewYear());
        
        int availablePoints = state.getViewYear().countAvailablePoints(
                state.getOpenDocument().getPreferences()
        );
        
        double progress = (double) committedPoints / availablePoints;
        
        commitmentPointsLabel.setText(
                String.valueOf(committedPoints));
        
        commitmentProgress.setProgress(progress);

        availableCommitmentLabel.setText(
                String.valueOf(availablePoints));
    }
    
    /**
     * Maps shelf epics to window
     */
    private void mapShelfToWindow() {
        ShelfEpicsTable epicsTable = new ShelfEpicsTable(
                state,
                state.bundle(),
                state.getOpenDocument().getPreferences().asProjectusPreferences(),
                state.getOpenDocument().getShelf()
        );
        
        epicsTable.bind(shelfTableView);
    }
    
    /**
     * Maps selected epics to window
     */
    private void mapEpicsToWindow(Timeline timeline) {
        int availableSprints = state.getViewYear().calculateSprints(
                                state.getOpenDocument()
                                    .getPreferences()
                                    .getSprintLength()
                        );
        
        int selected = state
                .mainController()
                .epicTableView
                .getSelectionModel()
                .getSelectedIndex();
        
        EpicsTimelineTable epicsTable = new EpicsTimelineTable(
                state,
                timeline,
                state.getViewYear(),
                availableSprints
        );
        
        epicsTable.bind(epicTableView);
        
        epicTableView.getSelectionModel().select(selected);
    }
    
    private void mapReleasesToWindow(Timeline timeline) {
        int availableSprints = state.getViewYear().calculateSprints(
                                state.getOpenDocument()
                                    .getPreferences()
                                    .getSprintLength()
                        );
        
        ReleasesTable releasesTable = new ReleasesTable(state);
        
        releasesTable.bind(releasesTableView);
        
        ReleasesTimelineTable releasesTimelineTable = new ReleasesTimelineTable(
                state,
                timeline,
                state.getViewYear(),
                availableSprints
        );
        
        releasesTimelineTable.bind(releasesTimelineTableView);
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
                .select((Assessment) state.getOpenDocument().getJudgement());
    }
    
    /**
     * Maps notes to window
     */
    private void mapNotesToWindow() {
        notesTextArea.setText(state.getOpenDocument().getNotes());
    }
    
    /**
     * Maps documents to window.
     */
    private void mapResourcesToWindow() {
        ResourcesTreeView.bind(
                resourcesTreeView, 
                state.getOpenDocument().getResources(),
                window(),
                state.bundle()
        );
    }
    
    /**
     * Applies document settings to window.
     */
    private void applyPreferencesToWindow() {
        // TODO: apply preferences
    }
    
    /**
     * Maps window content to new document object for serialization.
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
     * Handles the creation of a release
     */
    @FXML
    private void handleAddRelease() {
        try {
            perform(AddRelease.class);
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
     * Handles the removal of a release
     */
    @FXML
    private void handleRemoveRelease() {
        try {
            perform(RemoveRelease.class);
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
     * Handles movement of a release up
     */
    @FXML
    private void handleMoveReleaseUp() {
        try {
            perform(MoveReleaseUp.class);
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
     * Handles movement of a release down
     */
    @FXML
    private void handleMoveReleaseDown() {
        try {
            perform(MoveReleaseDown.class);
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
     * Handles the editing of a release
     */
    @FXML
    private void handleEditRelease() {
        try {
            perform(EditRelease.class);
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
     * Handles moving a timeline epic up
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
     * Handles moving a timeline epic down
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
     * Handles editing a timeline epic
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
     * Toggles count view
     */
    @FXML
    private void handleToggleCounts() {
        try {
            perform(ToggleCounts.class);
        } catch (Exception e) {
            ErrorAlert.show(
                state.bundle(),
                state.bundle().getString("errors.generic"),
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
     * Handles export epic
     */
    @FXML
    private void handleExportEpic() {
        try {
            perform(ExportEpic.class);
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
     * Directly opens a given document.
     * 
     * @param documentFile document to open
     * 
     * @throws IOException when unable to open document.
     */
    public void openDocument(File documentFile) 
            throws IOException, CloneNotSupportedException {
        Document doc = Document.load(documentFile);
        
        state.setOpenDocument(doc);
        state.getViewYear()
                .setStartSprint(doc.getPreferences().getStartSprintNumber());
        state.mapDocumentToWindow();
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
     * Shows preference dialog on the reports tab.
     * 
     * The contents of the dialog are loaded from the pane FXML.
     * 
     * A call to setup dialog ensures that the dialog is configured 
     * appropriately before it is shown.
     * 
     */
    @FXML
    private void handleOpenReportsConfig() {
        try {
            state.setSelectedPreferenceTabName("Reports");
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
     * Adds a document group to the documentation tree.
     */
    @FXML
    private void handleAddGroup() {
        try {
            perform(AddGroup.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Adds a document to the documentation tree.
     */
    @FXML
    private void handleAddDocument() {
        try {
            perform(AddDocument.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Adds a document to the documentation tree.
     */
    @FXML
    private void handleAddContact() {
        try {
            perform(AddContact.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Removes a node from the documentation tree.
     */
    @FXML
    private void handleRemoveNode() {
        try {
            perform(RemoveNode.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Edits the selected node in the documentation tree
     */
    @FXML
    private void handleEditNode() {
        try {
            perform(EditNode.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Moves the selected node in the resources tree
     */
    @FXML
    private void handleMoveNode() {
        try {
            perform(MoveNode.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    public void perform(Class action) 
            throws Exception {
        Action act = (Action) action.newInstance();
        act.perform(state, window());
        mapDocumentToWindow();
    }
}
