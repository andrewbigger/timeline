package com.biggerconcept.timeline.ui.tables;

import com.biggerconcept.appengine.ui.tables.StandardTable;
import com.biggerconcept.appengine.ui.tables.StandardTableColumn;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Timeline table
 * 
 * @author Andrew Bigger
 */
public class TimelineTable {
    public static final boolean SORTABLE = false;
    
    public static final int SPRINT_COL_WIDTH = 100;
    
    private final ResourceBundle bundle;
    
    private final Preferences prefs;
    
    private final Year currentYear;
    
    public TimelineTable(
            ResourceBundle rb,
            Preferences prefs,
            Year currentYear
    ) {
        this.bundle = rb;
        this.prefs = prefs;
        this.currentYear = currentYear;
    }
    
    public void bind(TableView view) {
        StandardTable.bind(
                view,
                "Empty", // TODO: Translation
                columns()
        );
    }
    
    private ArrayList<TableColumn> columns() {
        ArrayList<TableColumn> cols = new ArrayList<>();
        
        for (int i = 1; i == 4; i++) {
            cols.add(new TableColumn());
        }

        return cols;
    }
    
    private TableColumn column(int sprint) {
        TableColumn col = new TableColumn(String.valueOf(sprint));
        
        col.setMaxWidth(SPRINT_COL_WIDTH);
        col.setMinWidth(SPRINT_COL_WIDTH);
        
        return col;
    }
}
