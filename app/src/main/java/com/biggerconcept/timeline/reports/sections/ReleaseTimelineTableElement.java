package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import com.biggerconcept.timeline.reports.Element;
import com.biggerconcept.timeline.ui.domain.Timeline;
import com.biggerconcept.timeline.ui.domain.TimelineRelease;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a heading into a report
 * 
 * @author Andrew Bigger
 */
public class ReleaseTimelineTableElement extends Element {
    /**
     * Default constructor
     */
    public ReleaseTimelineTableElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public ReleaseTimelineTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a release timeline table into a report document.
     * 
     * @param document report document
     * @param vars content variables
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        ResourceBundle bundle = getState().bundle();
        document.strong(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q1.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q1.dates"
                )
        );
        
        document.nl();
        document.table(headers(1), body(1));
        document.nl();
        
        document.strong(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q2.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q2.dates"
                )
        );
        
        document.nl();
        document.table(headers(2), body(2));
        document.nl();
        
        document.strong(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q3.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q3.dates"
                )
        );
        
        document.nl();
        document.table(headers(3), body(3));
        document.nl();
        
        document.strong(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q4.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.releaseTimelineTable.q4.dates"
                )
        );
        
        document.nl();
        document.table(headers(4), body(4));
    }
    
    /**
     * Element modifiable. Indicates whether to allow the presentation of a
     * editor dialog in the report builder.
     * 
     * This element is not modifiable, so no editor dialog will be called for.
     * 
     * @return false
     */
    public boolean modifiable() {
        return false;
    }
    
    /**
     * Creates a header row array for the release timeline table.
     * 
     * @param quarter quarter of year
     * 
     * @return headers as an array list
     */
    private ArrayList<String> headers(int quarter) {
        ArrayList<String> headers = new ArrayList<>();
        
        ResourceBundle bundle = getState().bundle();
        
        headers.add(
                bundle.getString("reports.elements.releaseTimelineTable.name")
        );

        Preferences prefs = getState().getOpenDocument().getPreferences();
        Year viewYear = getState().getViewYear();
        
        int firstSprint = viewYear.firstSprintInQuarter(quarter, prefs);
        int lastSprint = viewYear.lastSprintInQuarter(quarter, prefs);
        
        for (int i = firstSprint; i < lastSprint + 1; i++) {
            headers.add(String.valueOf(i));
        }
        
        return headers;
    }
    
    /**
     * Creates a table body for a release timeline table.
     * 
     * @param quarter quarter of year
     * 
     * @return release timeline table body as array list of array list.
    */
    public ArrayList<ArrayList<String>> body(int quarter) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        Preferences prefs = getState().getOpenDocument().getPreferences();
        Year viewYear = getState().getViewYear();
        
        int firstSprint = viewYear.firstSprintInQuarter(quarter, prefs);
        int lastSprint = viewYear.lastSprintInQuarter(quarter, prefs);
        
        try {
            ArrayList<TimelineRelease> releases = 
                    Timeline.fromState(getState()).getReleases();
            
            for (TimelineRelease tr : releases) {
                if (tr.isSprintInQuarter(viewYear, quarter, prefs) == true) {
                    ArrayList<String> row = new ArrayList<>();
                    
                    row.add(tr.getName());
                    
                    for (int i = firstSprint; i < lastSprint + 1; i++) {
                        if (tr.getSprintNumber() == i) {
                            row.add("★");
                        } else {
                            row.add("");
                        }
                    }

                    rows.add(row);
                }
            }
            
        } catch (Exception e) {
            rows = new ArrayList<>();
        }
        
        return rows;
    }

}
