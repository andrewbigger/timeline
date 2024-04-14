package com.biggerconcept.timeline.ui.dialogs;

import com.biggerconcept.appengine.ui.dialogs.StandardDialog;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Size.TaskSize;
import com.biggerconcept.projectus.domain.Task;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Task management dialog.
 * 
 * @author Andrew Bigger
 */
public class TaskDialog {
    /**
     * Resource bundle for dialog.
     */
    private final ResourceBundle bundle;
    
    /**
     * Pointer to the parentEpic that the task belongs to.
     */
    private final Epic parentEpic;
    
    /**
     * Tasks currently being managed by the dialog.
     */
    private List<Task> currentTasks;
    
    /**
     * Visible task (when not in bulk)
     */
    private Task visibleTask;
    
    /**
     * Task name text field.
     */
    private final TextField nameField;
    
    /**
     * Task size combo box.
     */
    private final ComboBox sizeField;
    
    /**
     * Whether the edit is bulk.
     */
    private final boolean bulk;
    
    /**
     * Constructor for task dialog
     * 
     * @param rb application resource bundle
     * @param epic parent parentEpic of chosen task
     * @param tasks chosen tasks
     * @param bulk whether dialog is editing in bulk
     */
    public TaskDialog(
            ResourceBundle rb, 
            Epic epic, 
            List<Task> tasks, 
            boolean bulk
    ) {
        bundle = rb;
        parentEpic = epic;
        currentTasks = tasks;
        this.bulk = bulk;
        
        if (bulk == false) {
            visibleTask = currentTasks.get(0);
        }
        
        nameField = new TextField();
 
        sizeField = new ComboBox();
        sizeField.getItems().addAll(TaskSize.values());
             
        mapTaskToDialog();
    }
    
    /**
     * Shows task manager dialog on the given stage.
     * 
     * This calls the attribute constructor private functions to build a form
     * for editing tasks.
     * 
     * When the apply button is clicked, the callback will create the task
     * in the epic (if it has not already been created) and then apply the 
     * values from the dialog to the task.
     * 
     * @param stage parent window for dialog
     */
    public void show(Stage stage) {
        List<Node> attributes = Arrays.asList(
                taskNav(),
                nameAttribute(),
                sizeAttribute()
        );
        
        ButtonType apply = StandardDialog.applyAction(
                bundle.getString(
                       "epic.tasks.dialogs.manage.actions.save" 
                )
        );
        
        List<ButtonType> actions = Arrays.asList(
                StandardDialog.cancelAction(
                        bundle.getString(
                                "epic.tasks.dialogs.manage.actions.cancel"
                        )
                ),
                apply
        );
        
        Dialog<String> dialog = StandardDialog.dialog(
            bundle.getString("epic.tasks.dialogs.manage.title"),
            attributes,
            actions,
            apply
        );
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == apply) {
                applyToTasks();
                return null;
            } 
            
            return null;
        });
        
        dialog.showAndWait(); 
    }
    
    /**
     * Ensures that the task exists in the epic.
     * 
     * If the task exists in the epic, then no task will be added.
     * 
     * However, if the task does not exist, then the current task will be
     * added to that epic.
     */
    private void addTasksToEpic() {
        for (Task currentTask : currentTasks) {
            if (parentEpic.hasTask(currentTask)) {
                return;
            }

            parentEpic.addTask(currentTask);
        }
    }
    
    /**
     * Applies form to current task.
     * 
     * First we ensure that the task exists in the epic.
     * 
     * Then the contents of the fields are retrieved from the dialog and applied
     * to the current task object.
     */
    private void applyToTasks() {
        addTasksToEpic();
        
        for (Task currentTask : currentTasks) {
            applyToTask(currentTask);
        }   
    }
    
    /**
     * Applies form to given task.
     * 
     * This will retrieve values from the form and set them on
     * the task.
     * 
     * The value will not be applied if it is empty.
     * 
     * @param currentTask task to apply changes to
     */
    private void applyToTask(Task currentTask) {
        String name = nameField.getText();
        TaskSize size = (TaskSize) sizeField
                .getSelectionModel().getSelectedItem();

        if (name.trim().isEmpty() == false) {
            currentTask.setName(name);
        }
        
        if (size != null) {
            currentTask.setSize(size);
        }
    }
    
    /**
     * Name attribute builder.
     * 
     * Builds a VBox with the name label and text field for managing a task
     * name.
     * 
     * @return name attribute
     */
    private VBox nameAttribute() {        
        VBox name = StandardDialog.attribute(
                new Label(bundle.getString("epic.tasks.dialogs.manage.name")),
                nameField
        );
        
        if (bulk == true) {
            name.setDisable(true);
        }
        
        return name;
    }
    
    /**
     * Size attribute builder.
     * 
     * Builds a VBox with the size label and a combo box for selecting task
     * size.
     * 
     * @return size attribute
     */
    private VBox sizeAttribute() {
        return StandardDialog.attribute(
                new Label(bundle.getString("epic.tasks.dialogs.manage.size")),
                sizeField
        );
    }
    
    private BorderPane taskNav() {
        BorderPane wrapper = new BorderPane();
        
        wrapper.setLeft(prevTaskButton());
        wrapper.setRight(nextTaskButton());
        
        return wrapper;
    }
    
    /**
     * Builds previous action button.
     * 
     * The previous action will change the task to the previous task in the
     * dialog.
     * 
     * Button will be disabled if the parent epic does not include the
     * task or if in bulk mode.
     * 
     * @return 
     */
    private Button prevTaskButton() {
        Button prevTaskBtn = new Button();
        prevTaskBtn.setText(bundle.getString("dialogs.nav.previous"));
        
        if (bulk == false) {
            prevTaskBtn.setOnAction((ActionEvent event) -> {
                applyToTask(visibleTask);
                visibleTask = parentEpic.getPrevTask(visibleTask);
                mapTaskToDialog();
            });

            if (parentEpic.hasTask(visibleTask) == false) {
                prevTaskBtn.setDisable(true);
            }
        } else {
            prevTaskBtn.setDisable(true);
        }
        
        return prevTaskBtn;
    }
    
    /**
     * Builds next action button.
     * 
     * The next action will change the task to the next task in the dialog.
     * 
     * Button will be disabled if the parent epic does not include the
     * task or if in bulk mode.
     * 
     * @return 
     */
    private Button nextTaskButton() {
        Button nextTaskBtn = new Button();
        nextTaskBtn.setText(bundle.getString("dialogs.nav.next"));
        
        if (bulk == false) {
            nextTaskBtn.setOnAction((ActionEvent event) -> {
                applyToTask(visibleTask);
                visibleTask = parentEpic.getNextTask(visibleTask);
                mapTaskToDialog();
            });
            
            if (parentEpic.hasTask(visibleTask) == false) {
                nextTaskBtn.setDisable(true);
            } 
        } else {
            nextTaskBtn.setDisable(true);
        }
     
        return nextTaskBtn;
    }
        
    /**
     * Sets the value of controls to the value set in currentTask.
     */
    private void mapTaskToDialog() {
        if (bulk == false) {
            Task currentTask = visibleTask;
            
            nameField.setText(currentTask.getName());
            sizeField.getSelectionModel().select(currentTask.getSize());
        }
    }
}
