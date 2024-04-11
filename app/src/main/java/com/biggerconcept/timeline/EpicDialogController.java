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
     * 
     * @return 
     */
    private void mapWindowToDocument() {
        state.getOpenDocument().rebuildIdentifiers();
        
        state.getOpenEpic().setName(epicName.getText());
        state.getOpenEpic().setSummary(epicSummary.getText());
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

    private int calculateWeeks() {
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
        
        return weeks;
    }
}
