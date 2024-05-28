package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Release;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a release table into a report
 * 
 * @author Andrew Bigger
 */
public class EpicsTableElement extends Element {
    public EpicsTableElement() {
        super();
    }
    
    public EpicsTableElement(State state) {
        super(state);
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        ArrayList<Epic> epics = getDocument().getEpics();
        
        document.table(headers(), body(epics));
    }
    
    public boolean modifiable() {
        return false;
    }
    
    /**
     * Creates a header row array for the epic table.
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
                        .getString("reports.elements.epicsTable.number")
        );
        
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.epicsTable.name")
        );
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.epicsTable.estimate")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic.
     * 
     * @param epics epics to include in table body
     * 
     * @return epic body table as array list of array list.
    */
    public ArrayList<ArrayList<String>> body(
            ArrayList<Epic> epics
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        for (Epic e : epics) {
            ArrayList<String> row = new ArrayList<>();
            
            row.add(String.valueOf(e.getIdentifier()));
            row.add(e.getName());
            row.add(String.valueOf(
                    e.getSize(
                            getState()
                                    .getOpenDocument()
                                    .getPreferences()
                                    .asProjectusPreferences()
                    )
            ));
            
            rows.add(row);
        }
        
        return rows;
    }

}
