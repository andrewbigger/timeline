package com.biggerconcept.timeline.ui.tables;

import com.biggerconcept.appengine.ui.tables.StandardTable;
import com.biggerconcept.appengine.ui.tables.StandardTableColumn;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import com.biggerconcept.timeline.ui.domain.Timeline;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.biggerconcept.timeline.ui.domain.TimelineRelease;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * View model for epic timeline table.
 * 
 * @author Andrew Bigger
 */
public class ReleasesTimelineTable {
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
     * Width of name column in currentReleases table.
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
     * Application resource bundle
     */
    private final ResourceBundle bundle;
    
    /**
     * Application state.
     */
    private final State state;
    
    /**
     * Timeline
     */
    private final Timeline timeline;
    
    /**
     * Document documentPreferences.
     */
    private final Preferences documentPreferences;
    
    /**
     * Total number of sprints
     */
    private final int numberOfSprints;
    
    /**
     * View year
     */
    private final Year viewYear;
    
    /**
     * Constructor for releases table view model.
     * 
     * @param state application state
     * @param timeline view timeline
     * @param viewYear view year for table
     * @param sprints total number of sprints
     */
    public ReleasesTimelineTable(
            State state,
            Timeline timeline,
            Year viewYear,
            int sprints
     ) {
        this.state = state;
        this.timeline = timeline;
        this.viewYear = viewYear;
        this.numberOfSprints = sprints;
        
        this.bundle = state.bundle();
        this.documentPreferences = state.getOpenDocument().getPreferences();
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
     * @param view table view for currentReleases
     */
    public void bind(TableView view) {
        List<TableColumn> cols = new ArrayList<>();
        
        cols.add(nameCol());
            
        for (TableColumn col : sprintCols()) {
            cols.add(col);
        }
        
        StandardTable.bind(view,
                bundle.getString("release.table.empty"),
                FXCollections.observableArrayList(
                        timeline.getReleasesInYear(viewYear)
                ),
                cols
        );
    }

    /**
     * Builds sprint columns for releases table
     * 
     * @return date columns
     */
    private List<TableColumn> sprintCols() {
        ArrayList<TableColumn> cols = new ArrayList<>();
        
        int sprintCounter = viewYear.getStartSprint();
        for (int i = 0; i < numberOfSprints; i++) {
            cols.add(sprintCol(sprintCounter));
            sprintCounter += 1;
        }
        
        return cols;
    }
    
    /**
     * Builds a column for a specific sprint
     * 
     * @param number sprint number
     * 
     * @return column for the sprint
     */
    private TableColumn sprintCol(int number) {
        TableColumn<TimelineRelease, String> s = new TableColumn(
                String.valueOf(number)
        );
        
        s.setSortable(SORTABLE);
        s.setResizable(RESIZABLE);
        s.setMinWidth(SPRINT_CELL_WIDTH);
        s.setMaxWidth(SPRINT_CELL_WIDTH);
        s.setStyle(SPRINT_COL_STYLE);
        
        s.setCellValueFactory(data -> {
            String value = "";
            
            int sprintNumber = number + 1;
            
            if (data.getValue().getSprintNumber() == sprintNumber) {
                value = "★";
            }
            
            return new SimpleStringProperty(
                    value
            );
        });
        
        return s;
    }
    
    /**
     * Name attribute placeholder.
     * 
     * Constructs a blank column so sprints line up with
     * timeline table.
     * 
     * @return name column
     */
    private TableColumn nameCol() {
        TableColumn<TimelineRelease, String> nameCol = new TableColumn<>(
                bundle.getString("release.table.name")
        );
        
        StandardTableColumn.apply(
                nameCol, 
                NAME_COL_MIN_WIDTH + 
                        ESTIMATE_COL_MIN_WIDTH
        );
        
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }

}
