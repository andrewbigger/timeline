package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import com.biggerconcept.timeline.reports.Element;
import com.biggerconcept.timeline.ui.domain.Timeline;
import com.biggerconcept.timeline.ui.domain.TimelineEpic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts an epics timeline table section into a report
 * 
 * @author Andrew Bigger
 */
public class EpicsTimelineTableElement extends Element {
    /**
     * Default constructor
     */
    public EpicsTimelineTableElement() {
        super();
    }
    
    /**
     * Application state constructor.
     * 
     * @param state application state
     */
    public EpicsTimelineTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts an epic timeline table into the report document.
     * 
     * The timeline is broken down into quarters so it can easily fit
     * on an A4 page.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write to document
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        ResourceBundle bundle = getState().bundle();
        document.strong(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q1.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q1.dates"
                )
        );
        
        document.nl();
        document.table(headers(1), body(1));
        document.nl();
        
        document.strong(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q2.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q2.dates"
                )
        );
        
        document.nl();
        document.table(headers(2), body(2));
        document.nl();
        
        document.strong(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q3.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q3.dates"
                )
        );
        
        document.nl();
        document.table(headers(3), body(3));
        document.nl();
        
        document.strong(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q4.title"
                )
        );
        
        document.p(
                bundle.getString(
                    "reports.elements.epicsTimelineTable.q4.dates"
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
     * Creates a header row array for the epic timeline table.
     * 
     * @param half half of year
     * 
     * @return headers as an array list
     */
    private ArrayList<String> headers(int quarter) {
        ArrayList<String> headers = new ArrayList<>();
        
        ResourceBundle bundle = getState().bundle();
        
        headers.add(
                bundle.getString("reports.elements.epicsTimelineTable.name")
        );
        
        headers.add(
                bundle.getString("reports.elements.epicsTimelineTable.estimate")
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
     * Creates a table body for an epic timeline table.
     * 
     * @param quarter year quarter
     * 
     * @return epic body table as array list of array list.
    */
    public ArrayList<ArrayList<String>> body(int quarter) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        Preferences prefs = getState().getOpenDocument().getPreferences();
        Year viewYear = getState().getViewYear();
        
        int firstSprint = viewYear.firstSprintInQuarter(quarter, prefs);
        int lastSprint = viewYear.lastSprintInQuarter(quarter, prefs);
        
        try {
            ArrayList<TimelineEpic> epics = 
                    Timeline.fromState(getState()).getEpics();
            
            for (TimelineEpic te : epics) {
                if (te.hasSprintInQuarter(viewYear, quarter, prefs) == true) {
                    ArrayList<String> row = new ArrayList<>();
                    
                    row.add(te.getName());
                    row.add(String.valueOf(te.getLength()));
                    
                    for (int i = firstSprint; i < lastSprint + 1; i++) {
                        if (te.hasSprint(i)) {
                            row.add("■");
                        } else {
                            row.add(" ");
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
