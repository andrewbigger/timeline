package com.biggerconcept.timeline.ui.tables;

import com.biggerconcept.sdk.ui.tables.StandardTable;
import com.biggerconcept.sdk.ui.tables.StandardTableColumn;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.timeline.EpicDialogController;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.epic.EditEpic;
import com.biggerconcept.timeline.ui.dialogs.TaskDialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * View model for currentTasks table.
 * 
 * @author Andrew Bigger
 */
public class TasksTable {
    /**
     * Sortable constant.
     * 
     * This should be false as reordering tasks are presented in
     * user specified order.
     */
   public static final boolean SORTABLE = false;
   
   /**
    * Default width of ID column.
    */
   public static final int ID_COL_MIN_WIDTH = 100;
   
   /**
    * Default width of name column.
    */
   public static final int NAME_COL_MIN_WIDTH = 350;
   
   /**
    * Default width of size column.
    */
   public static final int SIZE_COL_MIN_WIDTH = 80;
   
   /**
    * Default width of estimate column.
    */
   public static final int ESTIMATE_COL_MIN_WIDTH = 80;
   
   /**
    * Application state
    */
   private final State state;
   
   /**
    * Epic dialog controller
    */
   private final EpicDialogController controller;
   
   /**
    * Application resource bundle.
    */
   private final ResourceBundle bundle;
   
   /**
    * Parent epic number.
    */
   private final int parentEpicNumber;
   
   /**
    * List of epic currentTasks.
    */
   private final ArrayList<Task> currentTasks;
   
   /**
    * Document documentPreferences.
    */
   private final Preferences documentPreferences;
   
   /**
    * Constructor for currentTasks table view model.
    * 
    * @param state application state
    * @param edc epic dialog controller
    * @param rb application resource bundle
    * @param tasks list of currentTasks
    * @param preferences document documentPreferences
    * @param epicNumber epic number
    */
   public TasksTable(
           State state,
           EpicDialogController edc,
           ResourceBundle rb,
           ArrayList<Task> tasks,
           Preferences preferences,
           int epicNumber
   ) {
       this.state = state;
       this.controller = edc;
       bundle = rb;
       currentTasks = tasks;
       documentPreferences = preferences;
       parentEpicNumber = epicNumber;
   }
   
   /**
    * Binds data to tasks table view.
    * 
    * This sets the items of the tasks view to the list of epic tasks.
    * 
    * This also sets the empty state string.
    * 
    * The column builder functions are invoked here to build columns for each
    * visible attribute.
    * 
    * @param view currentTasks table view
    */
   public void bind(TableView view) {
       view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       StandardTable.bind(
               view,
               bundle.getString("epic.tasks.table.empty"),
               FXCollections.observableArrayList(currentTasks),
               Arrays.asList(
                    nameCol(), 
                    sizeCol(), 
                    estimateCol()
               ),
               false
       );
       
       view.setOnMousePressed(event -> {
           if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                try {
                    List<Task> selected = (List<Task>) controller
                            .tasksTableView
                            .getSelectionModel()
                            .getSelectedItems();
                    
                    if (selected == null) {
                        return;
                    }
                    
                    TaskDialog manageTask = new TaskDialog(
                        state.bundle(),
                        state.getOpenEpic(),
                        selected,
                        selected.size() > 1
                    );
            
                    manageTask.show(controller.window());
            
                    controller.mapTasksToWindow();
                    controller.mapOutlookToWindow();
                } catch (Exception ex) {
                    // ignore
                    System.out.println(ex.getStackTrace());
                }
            }
       });
   }
   
   /**
    * ID attribute column builder.
    * 
    * ID refers to a number assigned by order of the task in the epic.
    * 
    * Constructs a column for the display of task IDs.
    * 
    * The cell factory builds a task number for each task.
    * 
    * @return ID column
    */
   private TableColumn idCol() {
       TableColumn<Task, String> idCol = new TableColumn<>(
               bundle.getString("epic.tasks.table.id")
       );
        
       StandardTableColumn.apply(idCol, ID_COL_MIN_WIDTH);
        
       idCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    parentEpicNumber 
                            + "." 
                            + String.valueOf(
                                    data.getValue().getIdentifier()
                            )
            );
       });
        
       return idCol;
   }
   
   /**
    * Name attribute column builder.
    * 
    * Constructs a column for the task name.
    * 
    * The cell factory retrieves the name property from each instance in the
    * table.
    * 
    * @return name column
    */
   private TableColumn nameCol() {
        TableColumn<Task, String> nameCol = new TableColumn<>(
                bundle.getString("epic.tasks.table.name")
        );
        
        StandardTableColumn.apply(nameCol, NAME_COL_MIN_WIDTH);
        
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }
   
   /**
    * Size attribute column builder.
    * 
    * Constructs a column for the task size.
    * 
    * The cell factory retrieves the size property from each instance in the
    * table.
    * 
    * @return size column
    */
   private TableColumn sizeCol() {
        TableColumn<Task, String> sizeCol = new TableColumn<>(
                bundle.getString("epic.tasks.table.size")
        );
        
        StandardTableColumn.apply(sizeCol, SIZE_COL_MIN_WIDTH);

        sizeCol.setCellValueFactory(new PropertyValueFactory("size"));
        
        return sizeCol;
    }
   
   /**
    * Estimate attribute column builder.
    * 
    * Constructs a column for the task estimate.
    * 
    * The cell factory retrieves the estimate from the preferences.
    * 
    * @return estimate column
    */
   private TableColumn estimateCol() {
       TableColumn<Task, String> estimateCol = new TableColumn<>(
               bundle.getString("epic.tasks.table.estimate")
       );
        
       StandardTableColumn.apply(estimateCol, ESTIMATE_COL_MIN_WIDTH);
        
       estimateCol.setCellValueFactory(data -> {
            
            return new SimpleStringProperty(
                    String.valueOf(documentPreferences.estimateFor(data.getValue().getSize())
                    )
            );
       });
        
       return estimateCol;
   }

}
