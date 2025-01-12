package com.biggerconcept.timeline.ui.tables;

import com.biggerconcept.appengine.ui.tables.StandardTable;
import com.biggerconcept.appengine.ui.tables.StandardTableColumn;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Release;
import java.util.Arrays;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * View model for releases table.
 * 
 * @author Andrew Bigger
 */
public class ReleasesTable {
    /**
     * Sortable constant.
     * 
     * This should be false as reordering tasks is not supported in table
     * view.
     */
    public static final boolean SORTABLE = false;
    
    /**
     * Width of number column.
     */
    public static final int NUMBER_COL_MIN_WIDTH = 80;

    /**
     * Width of name column in currentEpics table.
     */
    public static final int NAME_COL_MIN_WIDTH = 200;

    /**
     * Application state
     */
    private final State state;
    
    /**
     * Constructor for currentEpics table view model.
     * 
     * @param state application state
     */
    public ReleasesTable(
            State state
     ) {
        this.state = state;
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
        StandardTable.bind(
                view,
                state.bundle().getString("releases.table.empty"),
                FXCollections.observableArrayList(state.getOpenDocument().getReleases()),
                Arrays.asList(
                        numberCol(),
                        nameCol()
                ),
                false
        );
    }
    
    /**
     * Release number column builder.
     * 
     * Constructs a column for the display of release number.
     * 
     * @return number column
     */
    private TableColumn numberCol() {
        TableColumn<Release, String> numberCol = new TableColumn<>(
                state.bundle().getString("releases.table.number")
        );
        
        StandardTableColumn.apply(numberCol, NUMBER_COL_MIN_WIDTH);
        
        numberCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    String.valueOf(data.getValue().getNumber())
            );
        });
        
        return numberCol;
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
        TableColumn<Release, String> nameCol = new TableColumn<>(
                state.bundle().getString("releases.table.name")
        );
        
        StandardTableColumn.apply(nameCol, NAME_COL_MIN_WIDTH);
        
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        
        return nameCol;
    }

}
