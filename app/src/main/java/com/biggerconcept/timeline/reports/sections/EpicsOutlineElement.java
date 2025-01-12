package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import com.biggerconcept.timeline.reports.Element;
import com.biggerconcept.timeline.ui.domain.Timeline;
import com.biggerconcept.timeline.ui.domain.TimelineEpic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a release outline into a report
 * 
 * @author Andrew Bigger
 */
public class EpicsOutlineElement extends Element {
    /**
     * Default constructor
     */
    public EpicsOutlineElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public EpicsOutlineElement(State state) {
        super(state);
    }
    
    /**
     * Inserts a release table into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            Document openDocument = getState().getOpenDocument();
            Preferences prefs = openDocument.getPreferences();
            Timeline tl = Timeline.fromState(getState());
            Year viewYear = getState().getViewYear();
            
            ArrayList<TimelineEpic> epics = 
                    tl.getEpicsInYear(viewYear);
            
            for (TimelineEpic te : epics) {
                document.h3(te.getName());
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.epicsOutline.description"
                                )
                );
                
                document.md(te.getEpic().getSummary());
                document.nl();
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.epicsOutline.scope"
                                )
                );
                document.ol(te.getEpic().getScope().getIncluded());
                document.nl();
                
                document.strong(
                        getState()
                                .bundle()
                                .getString(
                                        "reports.elements.epicsOutline.tasks"
                                )
                );
                
                document.table(headers(), body(te.getEpic().getTasks()));

                document.br();
            }

        } catch (CloneNotSupportedException ex) {
            // skip release documentation when unable to construct timeline
        }
        
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
     * Creates a header row array for the epic tasks table.
     * 
     * @param bundle application resource bundle
     * 
     * @return headers as an array list
     */
    private ArrayList<String> headers() {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.tasksTable.number")
        );
        
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.tasksTable.name")
        );
        
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.tasksTable.size")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic task table.
     * 
     * @param tasks tasks to include in table body
     * 
     * @return epic task table as array list of array list.
    */
    private ArrayList<ArrayList<String>> body(ArrayList<Task> tasks) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        for (Task t : tasks) {
            ArrayList<String> row = new ArrayList<>();
            
            row.add(String.valueOf(t.getIdentifier()));
            row.add(t.getName());
            row.add(t.getSize().name());
            
            rows.add(row);
        }
        
        return rows;
    }

}
