package com.biggerconcept.timeline.ui.tables;

import com.biggerconcept.appengine.ui.tables.StandardTable;
import com.biggerconcept.appengine.ui.tables.StandardTableColumn;
import com.biggerconcept.projectus.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.biggerconcept.timeline.ui.domain.TimelineEpic;
import javafx.scene.control.TableCell;

/**
 * View model for epic timeline table.
 * 
 * @author Andrew Bigger
 */
public class EpicsTimelineTable {
    /**
     * Sortable constant.
     * 
     * This should be false as reordering tasks is not supported in table
     * view.
     */
    public static final boolean SORTABLE = false;

    /**
     * Width of name column in currentEpics table.
     */
    public static final int NAME_COL_MIN_WIDTH = 200;
    
    /**
     * Width of estimate column.
     */
    public static final int ESTIMATE_COL_MIN_WIDTH = 80;
    
    /**
     * Application resource bundle.
     */
    private final ResourceBundle bundle;
    
    /**
     * Document documentPreferences.
     */
    private final Preferences documentPreferences;
    
    /**
     * List of currentEpics for the table.
     */
    private final ArrayList<TimelineEpic> currentEpics;
    
    /**
     * Start date
     */
    private final LocalDate start;
    
    /**
     * Total number of sprints
     */
    private final int numberOfSprints;
    
    /**
     * View year
     */
    private final Year viewYear;
    
    /**
     * Constructor for currentEpics table view model.
     * 
     * @param rb application resource bundle
     * @param preferences document documentPreferences
     * @param epics epic list
     * @param start start date
     * @param sprints total number of sprints
     * @param viewYear view year for table
     */
    public EpicsTimelineTable(
            ResourceBundle rb, 
            Preferences preferences, 
            ArrayList<TimelineEpic> epics,
            LocalDate start,
            int sprints,
            Year viewYear
     ) {
        bundle = rb;
        documentPreferences = preferences;
        currentEpics = epics;
        this.start = start;
        numberOfSprints = sprints;
        this.viewYear = viewYear;
        
    }
    
    /**
     * Binds data to epic table view.
     * 
     * This sets the items of the given view to an observable list of epics.
     * 
     * This also sets the empty table state string.
     * 
     * The column builder functions are invoked here to build columns for
     * each visible attribute.
     * 
     * @param view table view for currentEpics
     */
    public void bind(TableView view) {
        List<TableColumn> cols = new ArrayList<>();
        
        cols.add(nameCol());
        cols.add(estimateCol());
            
        for (TableColumn col : dateCols()) {
            cols.add(col);
        }
        
        StandardTable.bind(
                view,
                bundle.getString("project.table.empty"),
                FXCollections.observableArrayList(currentEpics),
                cols
        );
    }
    
    private List<TableColumn> dateCols() {
        TableColumn<String, String> q1 = new TableColumn<>(
                "Q1"
        );
        
        q1.setSortable(false);
        q1.setResizable(false);
        
        TableColumn<String, String> q2 = new TableColumn<>(
                "Q2"
        );
        
        q2.setSortable(false);
        q2.setResizable(false);
        
        TableColumn<String, String> q3 = new TableColumn<>(
                "Q3"
        );
        
        q3.setSortable(false);
        q3.setResizable(false);
        
        TableColumn<String, String> q4 = new TableColumn<>(
                "Q4"
        );
        
        q4.setSortable(false);
        q4.setResizable(false);
        
        int sprintsPerQuarter = numberOfSprints / 4;
        for (int i = 1; i < sprintsPerQuarter + 1; i++) {
            q1.getColumns().add(sprintCol(1, i, sprintsPerQuarter));
            q2.getColumns().add(sprintCol(2, i, sprintsPerQuarter));
            q3.getColumns().add(sprintCol(3, i, sprintsPerQuarter));
            q4.getColumns().add(sprintCol(4, i, sprintsPerQuarter));
        }

        return Arrays.asList(
                q1,
                q2,
                q3,
                q4
        );
    }
    
    private TableColumn sprintCol(int quarter, int number, int sprintsPerQuarter) {
        int sprintNumber = number;
        
        if (quarter > 1) {
            sprintNumber = number + ((quarter - 1) * sprintsPerQuarter);
        }
        
        TableColumn<TimelineEpic, String> col = new TableColumn(
                String.valueOf(sprintNumber)
        );
        
        col.setSortable(false);
        col.setResizable(false);
        col.setMinWidth(30);
        col.setMaxWidth(30);
        col.setStyle("-fx-alignment: CENTER;");
        
        col.setCellValueFactory(data -> {
            String value = "";
            
            int columnNumber = number;
            
            if (quarter > 1) {
                columnNumber = number + ((quarter - 1) * sprintsPerQuarter);
            }
            
            if (data.getValue().hasSprint(viewYear, columnNumber)) {
                value = "■";
            }
            
            return new SimpleStringProperty(
                    value
            );
        });
        
        return col;
    }
    
    /**
     * Name attribute column builder.
     * 
     * Constructs a column for the display of epic names.
     * 
     * The value retrieves the name property of each epic instance in the 
     * table.
     * 
     * @return name column
     */
    private TableColumn nameCol() {
        TableColumn<TimelineEpic, String> nameCol = new TableColumn<>(
                bundle.getString("project.table.name")
        );
        
        StandardTableColumn.apply(nameCol, NAME_COL_MIN_WIDTH);
        
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }
    
    /**
     * Estimate column builder.
     * 
     * Constructs a column for the display of epic estimate.
     * 
     * @return estimate column
     */
    private TableColumn estimateCol() {
        TableColumn<TimelineEpic, String> estimateCol = new TableColumn<>(
                bundle.getString("project.table.estimate")
        );
        
        StandardTableColumn.apply(estimateCol, ESTIMATE_COL_MIN_WIDTH);
        
        estimateCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(data.getValue().getEpic().getSize(documentPreferences))
            );
        });
        
        return estimateCol;
    }

}
