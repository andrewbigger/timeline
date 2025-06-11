package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Inserts a release table into a report
 * 
 * @author Andrew Bigger
 */
public class EpicsTableElement extends Element {
    /**
     * Default constructor
     */
    public EpicsTableElement() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state.
     */
    public EpicsTableElement(State state) {
        super(state);
    }
    
    /**
     * Inserts epic table into report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to read file
     */
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        ArrayList<Epic> epics = getDocument().getEpics();
        
        document.table(
                headers(getState().bundle()), 
                body(getState().getOpenDocument().getPreferences(), epics)
        );
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
    public static ArrayList<String> headers(ResourceBundle bundle) {
        ArrayList<String> headers = new ArrayList<>();
        
        headers.add(
                bundle
                    .getString("reports.elements.epicsTable.number")
        );
        
        headers.add(
                bundle
                    .getString("reports.elements.epicsTable.name")
        );
        headers.add(
                bundle
                    .getString("reports.elements.epicsTable.estimate")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic.
     * 
     * @param prefs document preferences
     * @param epics epics to include in table body
     * 
     * @return epic body table as array list of array list.
    */
    public static ArrayList<ArrayList<String>> body(
            Preferences prefs,
            ArrayList<Epic> epics
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        for (Epic e : epics) {
            ArrayList<String> row = new ArrayList<>();
            
            row.add(String.valueOf(e.getIdentifier()));
            row.add(e.getName());
            row.add(String.valueOf(
                    e.getSize(
                            prefs.asProjectusPreferences()
                    )
            ));
            
            rows.add(row);
        }
        
        return rows;
    }

}
