package com.biggerconcept.timeline;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.appengine.ui.dialogs.YesNoPrompt;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.projectus.ui.dialogs.EpicChooserDialog;
import com.biggerconcept.projectus.ui.dialogs.ScopeDialog;
import com.biggerconcept.timeline.ui.dialogs.TaskDialog;
import com.biggerconcept.timeline.ui.tables.TasksTable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the epic dialog.
 * 
 * @author Andrew Bigger
 */
public class EpicDialogController implements Initializable {
    /**
     * Application state
     */
    private State state;
    
    /**
     * Application resource bundle
     */
    private ResourceBundle bundle;
    
    /**
     * Target set of epics
     */
    private ArrayList<Epic> targetSet;
    
    /**
     * Whether the epic is a new epic
     */
    private boolean isNew;
    
    /**
     * Epic dialog border pane
     */
    @FXML
    public BorderPane epicEditorBorderPane;
    
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
    public TableView tasksTableView;
    
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
     * Add task button
     */
    @FXML
    public Button addTaskButton;
    
    /**
     * Remove task button
     */
    @FXML
    public Button removeTaskButton;
    
    /**
     * Move task up button
     */
    @FXML
    public Button moveTaskUpButton;
    
    /**
     * Move task down button
     */
    @FXML
    public Button moveTaskDownButton;
    
    /**
     * Edit task button
     */
    @FXML
    public Button editTaskButton;
    
    /**
     * Move task button
     */
    @FXML
    public Button moveTaskButton;
    
    /**
     * Outlook label
     */
    @FXML
    public Label outlookLabel;
    
    /**
     * Add scope button
     */
    @FXML
    public Button addScopeButton;
    
    /**
     * Remove scope button
     */
    @FXML
    public Button removeScopeButton;
    
    /**
     * Scope list view
     */
    @FXML
    public ListView scopeListView;
    
    /**
     * Start sprint selector combo box
     */
    @FXML
    public ComboBox startSprintComboBox;
    
    /**
     * End sprint selector combo box
     */
    @FXML
    public ComboBox endSprintComboBox;
    
    /**
     * Initializer for the dialog window
     * 
     * @param url url for dialog window
     * @param rb application resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        MenuController.loadMenu(this, rb, epicEditorBorderPane);
        applyTooltips();
    }
    
    /**
     * Creates and adds tool tips to UI controls.
     */
    private void applyTooltips() {
        addTaskButton.setTooltip(
                new Tooltip(bundle.getString("epic.tasks.add.tooltip"))
        );
        removeTaskButton.setTooltip(
                new Tooltip(bundle.getString("epic.tasks.remove.tooltip"))
        );
        moveTaskUpButton.setTooltip(
                new Tooltip(bundle.getString("epic.tasks.moveUp.tooltip"))
        );
        moveTaskDownButton.setTooltip(
                new Tooltip(bundle.getString("epic.tasks.moveDown.tooltip"))
        );
        editTaskButton.setTooltip(
                new Tooltip(bundle.getString("epic.tasks.edit.tooltip"))
        );
        moveTaskButton.setTooltip(
                new Tooltip(bundle.getString("epic.tasks.move.tooltip"))
        );
        addScopeButton.setTooltip(
                new Tooltip(bundle.getString("epic.scope.add.tooltip"))
        );
        removeScopeButton.setTooltip(
                new Tooltip(bundle.getString("epic.scope.remove.tooltip"))
        );
    }

    /**
     * Sets document pointer for the dialog window.
     * 
     * @param state application state
     * @param epic open epic
     * @param parent parent window
     * @param targetSet target set on save
     * @param isNew whether the epic should be added on save
     * @throws java.lang.CloneNotSupportedException when unable to clone sprint
     */
    public void setEpic(
            State state,
            Epic epic,
            MainController parent,
            ArrayList<Epic> targetSet,
            boolean isNew
    ) throws CloneNotSupportedException {
        this.state = state;
        this.state.setOpenEpic(epic);
        this.targetSet = targetSet;
        this.isNew = isNew;
        
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
        Stage stage = (Stage) saveEpicButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps given epic to window.
     * 
     * @param doc 
     */
    private void mapDocumentToWindow() throws CloneNotSupportedException {
        mapDetailsToWindow();
        mapSprintsToWindow();
        mapScopeToWindow();
        mapTasksToWindow();
        mapOutlookToWindow();
        
        state.mainController().mapDocumentToWindow();
    }
    
    /**
     * Maps epic details to the window
     */
    private void mapDetailsToWindow() {
        epicName.setText(state.getOpenEpic().getName());
        epicSummary.setText(state.getOpenEpic().getSummary());
    }
    
    /**
     * Maps sprint options to window
     */
    private void mapSprintsToWindow() {
        startSprintComboBox.getItems().clear();
        endSprintComboBox.getItems().clear();
        
        int startSprint = state.getViewYear().getStartSprint();
        int endSprint = state.getViewYear().lastSprint(
                state.getOpenDocument().getPreferences()
        );
        
        startSprintComboBox.getItems().add("");
        endSprintComboBox.getItems().add("");
        
        for (int i = startSprint; i < endSprint; i++) {
            startSprintComboBox.getItems().add(i);
            endSprintComboBox.getItems().add(i);
        }
        
        if (state.getOpenEpic().hasAssignedSprints()) {
            startSprintComboBox.getSelectionModel().select(
                    String.valueOf(state.getOpenEpic().getStartSprint())
            );
            
            endSprintComboBox.getSelectionModel().select(
                    String.valueOf(state.getOpenEpic().getEndSprint())
            );
        } else {
            startSprintComboBox.getSelectionModel().select(0);
            endSprintComboBox.getSelectionModel().select(0);
        }
    }
    
    /**
     * Maps scope items to window
     */
    private void mapScopeToWindow() {
        scopeListView.getItems().clear();
        for (String scope : state.getOpenEpic().getScope().getIncluded()) {
            scopeListView.getItems().add(scope);
        }
    }
    
    /**
     * Maps tasks to tasks table view
     */
    private void mapTasksToWindow() {
        TasksTable tasksTable = new TasksTable(
            state.bundle(),
            state.getOpenEpic().getTasks(),
            state.getOpenDocument().getPreferences().asProjectusPreferences(),
            state.getOpenEpic().getIdentifier()
        );
        tasksTable.bind(tasksTableView);
    }
    
    private void mapOutlookToWindow() {
         outlookLabel.setText(
                String.valueOf(
                        calculateWeeks()
                )
        );
    }
    
    /**
     * Maps epic for serialization.
     */
    private void mapWindowToDocument() {
        state.getOpenDocument().rebuildIdentifiers();
        
        state.getOpenEpic().setName(epicName.getText());
        state.getOpenEpic().setSummary(epicSummary.getText());
        
        mapWindowToSprints();
    }
    
    /**
     * Maps sprint selections to the document.
     */
    private void mapWindowToSprints() {
        Integer startSprint = getStartSprint();
        Integer endSprint = getEndSprint();
        
        if (startSprint > 0 && endSprint > 0) {
            state.getOpenEpic().setStartSprint(startSprint);
            state.getOpenEpic().setEndSprint(endSprint);
        }
    }
    
    /**
     * Handles adding a task to the epic
     */
    @FXML
    private void handleAddTask() {
        try {
            ArrayList<Task> empty = new ArrayList<>();
            Task newTask = new Task();
            newTask.setIdentifier(state.getOpenEpic().getStories().size() + 1);
            empty.add(newTask);
            
            TaskDialog addTask = new TaskDialog(
                    state.bundle(),
                    state.getOpenEpic(),
                    empty,
                    false
            );
            
            addTask.show(window());
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.epic.tasks.add"),
                    e
            );
        }
    }
    
    /**
     * Handles removing a task from the epic
     */
    @FXML
    private void handleRemoveTask() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    Alert.AlertType.CONFIRMATION,
                    state.bundle().getString("epic.tasks.dialogs.remove.title"),
                    state.bundle().getString("epic.tasks.dialogs.remove.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Task t: items) {
                    state.getOpenEpic().removeTask(t);
                }
            }
            
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.epic.tasks.remove"),
                    e
            );
        }
    }
    
    /**
     * Moves task to another epic
     */
    @FXML
    private void handleMoveTask() {
        try {
             ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            EpicChooserDialog pickEpic = new EpicChooserDialog(
                    bundle,
                    state.getOpenDocument().getEpics()
            );
            
            Epic chosenEpic = pickEpic.show(window());
            
            for (Task t : items) {
                state.getOpenEpic().removeTask(t);
                chosenEpic.addTask(t);
            }
            
            mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.moveTask"),
                    e
            );
        }
    }
    
    /**
     * Moves task up in epic
     */
    @FXML
    private void handleMoveTaskUp() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = tasksTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex - 1;
            
            if (targetIndex < 0) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getTasks(),
                    selectedIndex,
                    targetIndex
            );
            
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.moveTask"),
                    e
            );
        }
    }
    
    /**
     * Handler for moving a task down one position in the list.
     * 
     * When invoked, this will move the task down one position.
     * 
     * If nothing is selected, then no change is made.
     */
    @FXML
    private void handleMoveTaskDown() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            int selectedIndex = tasksTableView
                    .getItems()
                    .indexOf(items.get(0));
            
            int targetIndex = selectedIndex + 1;
            
            if (targetIndex > tasksTableView.getItems().size() - 1) {
                throw new NoChoiceMadeException();
            }
            
            Collections.swap(
                    state.getOpenEpic().getTasks(),
                    selectedIndex,
                    targetIndex
            );

            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.moveTask"), 
                    e
            );
        }
    }
    
    /**
     * Handles editing the selected task
     */
    @FXML
    private void handleEditTask() {
        try {
            ObservableList<Task> items = tasksTableView
                    .getSelectionModel()
                    .getSelectedItems();
            
            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }
            
            TaskDialog manageTask = new TaskDialog(
                    state.bundle(),
                    state.getOpenEpic(),
                    items,
                    items.size() > 1
            );
            
            manageTask.show(window());
            
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.epic.tasks.edit"),
                    e
            );
        }
    }
    
    /**
     * Adds scope to epic
     */
    @FXML
    private void handleAddScope() {
        try {
            ScopeDialog addScope = new ScopeDialog(
                    bundle,
                    state.getOpenEpic().getScope().getIncluded(),
                    ""
            );
            
            addScope.show(window());
            mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.scope.add"),
                    e
            );
        }
    }
    
    /**
     * Removes scope from epic.
     */
    @FXML
    private void handleRemoveScope() {
        try {
            String item = (String) scopeListView
                    .getSelectionModel()
                    .getSelectedItem();
            
            if (item == null) {
                throw new NoChoiceMadeException();
            }
            
            ButtonType answer = YesNoPrompt.show(
                    Alert.AlertType.CONFIRMATION,
                    bundle.getString(
                            "epic.dialogs.scope.included.remove.title"
                    ),
                    bundle.getString(
                            "epic.dialogs.scope.included.remove.description"
                    )
            );
            
            if (answer == ButtonType.YES) {
                state.getOpenEpic().getScope().getIncluded().remove(item);
            }
            
            mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.scope.remove"),
                    e
            );
        }
    }
    
    /**
     * Ensures that start sprint is before end sprint
     * 
     * When start sprint is after end sprint, the end sprint will be
     * adjusted to be equivalent to the start sprint.
     */
    @FXML
    private void handleChangeStartSprint() {
        Integer startSprint = getStartSprint();
        Integer endSprint = getEndSprint();
        
        if (startSprint > endSprint) {
            if (startSprint == 0) {
                endSprintComboBox.getSelectionModel().select(0);
            } else {
                endSprintComboBox.getSelectionModel().select(startSprint);
            }
        }
        
        mapWindowToSprints();
        mapOutlookToWindow();
    }
    
    /**
     * Ensures that the end sprint is after the start sprint
     * 
     * When the end sprint is less than the start sprint, the end sprint
     * will be adjusted to be equivalent to the start sprint.
     */
    @FXML
    private void handleChangeEndSprint() {
        Integer startSprint = getStartSprint();
        Integer endSprint = getEndSprint();
        
        if (endSprint < startSprint) {
            if (startSprint == 0) {
                endSprintComboBox.getSelectionModel().select(0);
            } else {
                endSprintComboBox.getSelectionModel().select(startSprint);
            }
        }
        
        mapWindowToSprints();
        mapOutlookToWindow();
    }
    
    /**
     * Clears selected sprints
     */
    @FXML
    private void handleClearSelectedSprints() {
        state.getOpenEpic().setStartSprint(0);
        state.getOpenEpic().setEndSprint(0);
        mapSprintsToWindow();
        mapOutlookToWindow();
    }
    
    /**
     * Cancels the modification of edit.
     */
    @FXML
    private void handleCancelEpic() {
        try {
            state.releaseOpenEpic();
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.epic.cancel"),
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
            if (isNew == true) {
                targetSet.add(state.getOpenEpic());
            }
            
            mapWindowToDocument();
            state.mainController().mapDocumentToWindow();
            state.releaseOpenEpic();
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.epic.save"),
                    e
            );
        }
    }

    /**
     * Calculates number of weeks.
     * 
     * When the sprint has assigned sprints, the number of sprints that
     * were taken is calculated and returned.
     * 
     * Otherwise the duration is calculated based on reference sprint data.
     * 
     * @return number of weeks
     */
    private int calculateWeeks() {
        // Calculate number of weeks required for epic
        int totalPoints = state.getOpenEpic().calculateTotalPoints(
                state.getOpenDocument().getPreferences().asProjectusPreferences()
        );
        
        int pointsPerWeek = state.getOpenDocument()
                .getPreferences()
                .calculateAveragePointsPerWeek();
        
        int weeks = 0;
        
        if (totalPoints > 0 && pointsPerWeek > 0) {
            weeks = totalPoints / pointsPerWeek;
        } else if (totalPoints > 0) {
            weeks = 1;
        }
        
        // When sprints are assigned, calculate the number of weeks
        // if it the calculation is greater than one sprint, 
        // use the assigned sprint length
        
        if (state.getOpenEpic().hasAssignedSprints()) {
            int sprints = state.getOpenEpic().getEndSprint() - 
                    state.getOpenEpic().getStartSprint();
            
            if (sprints == 0) {
                return weeks;
            }
            
            return sprints * state
                    .getOpenDocument()
                    .getPreferences()
                    .getSprintLength();
        }
        
        // Return calculation by default
        return weeks;
    }
    
    /**
     * Returns the selected start sprint.
     * 
     * If there is nothing selected, then zero is returned by default
     * 
     * @return start sprint selection
     */
    private Integer getStartSprint() {
        Object selection = startSprintComboBox
                            .getSelectionModel()
                            .getSelectedItem();
        
        if (selection == null) {
            return 0;
        }
        
        if (selection == startSprintComboBox.getItems().get(0)) {
            return 0;
        }
        
        try {
            return (Integer) selection;
        } catch (ClassCastException e) {
            return 0;
        }
    }
    
    /**
     * Returns the selected end sprint.
     * 
     * If there is nothing selected, then zero is returned by default
     * 
     * @return end sprint selection
     */
    private Integer getEndSprint() {
        Object selection = endSprintComboBox
                            .getSelectionModel()
                            .getSelectedItem();
        
        if (selection == null) {
            return 0;
        }
        
        if (selection == endSprintComboBox.getItems().get(0).toString()) {
            return 0;
        }
        
        try {
            return (Integer) selection;
        } catch (ClassCastException e) {
            return 0;
        }
    }
}
