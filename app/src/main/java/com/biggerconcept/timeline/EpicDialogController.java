package com.biggerconcept.timeline;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.appengine.ui.dialogs.YesNoPrompt;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Task;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controller for the epic dialog.
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
     * Target set of epics
     */
    private ArrayList<Epic> targetSet;
    
    /**
     * Main window controller
     */
    private MainController parent;
    
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
     * Outlook label
     */
    @FXML
    public Label outlookLabel;
    
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
    }

    /**
     * Sets document pointer for the dialog window.
     * 
     * @param doc open document
     * @param epic open epic
     * @param parent parent window
     * @param targetSet target set on save
     * @param isNew whether the epic should be added on save
     */
    public void setEpic(
            Document doc,
            Epic epic,
            MainController parent,
            ArrayList<Epic> targetSet,
            boolean isNew
    ) {
        this.currentDocument = doc;
        this.currentEpic = epic;
        this.parent = parent;
        this.targetSet = targetSet;
        this.isNew = isNew;
        mapEpicToWindow();
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
    private void mapEpicToWindow() {
        mapDetailsToWindow();
        mapTasksToWindow();
        mapOutlookToWindow();
        
        parent.mapDocumentToWindow();
    }
    
    private void mapDetailsToWindow() {
        epicName.setText(currentEpic.getName());
        epicSummary.setText(currentEpic.getSummary());
    }
    
    private void mapTasksToWindow() {
        TasksTable tasksTable = new TasksTable(
            bundle,
            currentEpic.getTasks(),
            currentDocument.getPreferences().asProjectusPreferences(),
            currentEpic.getIdentifier()
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
    private void mapWindowToEpic() {
        currentDocument.rebuildIdentifiers();
        
        currentEpic.setName(epicName.getText());
        currentEpic.setSummary(epicSummary.getText());
    }
    
    /**
     * Handles adding a task to the epic
     */
    @FXML
    private void handleAddTask() {
        try {
            ArrayList<Task> empty = new ArrayList<>();
            Task newTask = new Task();
            newTask.setIdentifier(currentEpic.getStories().size() + 1);
            empty.add(newTask);
            
            TaskDialog addTask = new TaskDialog(
                    bundle,
                    currentEpic,
                    empty,
                    false
            );
            
            addTask.show(window());
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.epic.tasks.add"),
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
                    bundle.getString("epic.tasks.dialogs.remove.title"),
                    bundle.getString("epic.tasks.dialogs.remove.description")
            );
            
            if (answer == ButtonType.YES) {
                for (Task t: items) {
                    currentEpic.removeTask(t);
                }
            }
            
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.epic.tasks.remove"),
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
                    currentEpic.getTasks(),
                    selectedIndex,
                    targetIndex
            );
            
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.moveTask"), e);
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
                    currentEpic.getTasks(),
                    selectedIndex,
                    targetIndex
            );

            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.moveTask"), e);
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
                    bundle,
                    currentEpic,
                    items,
                    items.size() > 1
            );
            
            manageTask.show(window());
            
            mapTasksToWindow();
            mapOutlookToWindow();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.epic.tasks.edit"),
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
            if (isNew == true) {
                targetSet.add(currentEpic);
            }
            
            mapWindowToEpic();
            parent.mapDocumentToWindow();
            window().close();
        } catch (Exception e) {
            ErrorAlert.show(
                    bundle,
                    bundle.getString("errors.epic.save"),
                    e
            );
        }
    }
    
    private int calculateWeeks() {
        int totalPoints = currentEpic.calculateTotalPoints(
                currentDocument.getPreferences().asProjectusPreferences()
        );
        
        int pointsPerWeek = currentDocument
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
