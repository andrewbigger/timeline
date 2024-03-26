package com.biggerconcept.timeline.ui.tables;

import com.biggerconcept.appengine.ui.tables.StandardTable;
import com.biggerconcept.appengine.ui.tables.StandardTableColumn;
import com.biggerconcept.timeline.domain.Preferences;
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
     * Resizable constant.
     * 
     * This should be false as we're not supporting resizing of columns in 
     * this view.
     */
    public static final boolean RESIZABLE = false;

    /**
     * Width of name column in currentEpics table.
     */
    public static final int NAME_COL_MIN_WIDTH = 200;
    
    /**
     * Width of estimate column.
     */
    public static final int ESTIMATE_COL_MIN_WIDTH = 80;
    
    /**
     * Width of sprint cell.
     */
    public static final int SPRINT_CELL_WIDTH = 30;
    
    /**
     * Style for sprint column.
     */
    public static final String SPRINT_COL_STYLE = "-fx-alignment: CENTER;";
   
    /**
     * Style for current sprint column.
     */
    public static final String CURRENT_SPRINT_COL_STYLE = 
            "-fx-background-color: #cee2f2; -fx-alignment: CENTER;";
    
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
     * Number of sprints in a quarter
     */
    private final int sprintsPerQuarter;
    
    /**
     * Number of sprints in a month
     */
    private final int sprintsPerMonth;
    
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
        this.bundle = rb;
        this.documentPreferences = preferences;
        this.currentEpics = epics;
        this.start = start;
        this.viewYear = viewYear;
        
        this.numberOfSprints = sprints;
        this.sprintsPerQuarter = numberOfSprints / 4;
        this.sprintsPerMonth = sprintsPerQuarter / 3;
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

    /**
     * Builds date columns to be added to the timeline table.
     * 
     * @return date columns
     */
    private List<TableColumn> dateCols() {
        ArrayList<TableColumn> cols = new ArrayList<>();
        
        int monthCounter = 0;
        int sprintCounter = documentPreferences.getStartSprintNumber();
        
        for (String quarter : quarters()) {
            TableColumn<String, String> q = new TableColumn<>(
                    quarter
            );
            
            q.setSortable(SORTABLE);
            q.setResizable(RESIZABLE);
            
            // months
            for (int i = 0; i < 3; i++) {
                q.getColumns().add(monthCol(monthCounter, sprintCounter));
                monthCounter += 1;
                sprintCounter += sprintsPerMonth;
            }

            cols.add(q);
        }
        
        return cols;
    }
    
    /**
     * Builds a month column with columns for each sprint in the 
     * month.
     * 
     * @param month to build column for
     * @param startSprintNumber starting sprint number for the month
     * 
     * @return month columns
     */
    private TableColumn monthCol(int month, int startSprintNumber) {
        TableColumn<String, String> m = new TableColumn(
                months().get(month)
        );
        
        m.setSortable(SORTABLE);
        m.setResizable(RESIZABLE);
        
        int sprintCounter = startSprintNumber;
        for (int i = 0; i < sprintsPerMonth; i++) {
            m.getColumns().add(sprintCol(sprintCounter));
            sprintCounter += 1;
        }
        
        return m;
    }
    
    /**
     * Builds a column for a specific sprint
     * 
     * @param number sprint number
     * 
     * @return column for the sprint
     */
    private TableColumn sprintCol(int number) {
        TableColumn<TimelineEpic, String> s = new TableColumn(
                String.valueOf(number)
        );
        
        s.setSortable(SORTABLE);
        s.setResizable(RESIZABLE);
        s.setMinWidth(SPRINT_CELL_WIDTH);
        s.setMaxWidth(SPRINT_CELL_WIDTH);
        s.setStyle(SPRINT_COL_STYLE);
        
        s.setCellValueFactory(data -> {
            String value = "";
            
            int sprintNumber = (number + 1) - documentPreferences.getStartSprintNumber();
            
            if (data.getValue().hasSprint(viewYear, sprintNumber)) {
                value = "■";
            }
            
            if (data.getValue().isCurrentSprint(
                    viewYear,
                    sprintNumber,
                    documentPreferences
            )) {
                data.getTableColumn().setStyle(CURRENT_SPRINT_COL_STYLE);
            }
            
            return new SimpleStringProperty(
                    value
            );
        });
        
        return s;
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
                    String.valueOf(
                            data
                                    .getValue()
                                    .getEpic()
                                    .getSize(documentPreferences.asProjectusPreferences())
                    )
            );
        });
        
        return estimateCol;
    }
    
    /**
     * List of quarters
     * 
     * @return quarter labels
     */
    private List<String> quarters() {
        return Arrays.asList(
            bundle.getString("dates.quarter.q1"),
            bundle.getString("dates.quarter.q2"),
            bundle.getString("dates.quarter.q3"),
            bundle.getString("dates.quarter.q4")
        );
    }
    
    /**
     * List of months
     * 
     * @return month labels
     */
    private List<String> months() {
        return Arrays.asList(
            bundle.getString("dates.months.jan"),
            bundle.getString("dates.months.feb"),
            bundle.getString("dates.months.mar"),
            bundle.getString("dates.months.apr"),
            bundle.getString("dates.months.may"),
            bundle.getString("dates.months.jun"),
            bundle.getString("dates.months.jul"),
            bundle.getString("dates.months.aug"),
            bundle.getString("dates.months.sep"),
            bundle.getString("dates.months.oct"),
            bundle.getString("dates.months.nov"),
            bundle.getString("dates.months.dec")
        );
    }

}
