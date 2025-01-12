package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a heading into a report
 * 
 * @author Andrew Bigger
 */
public class ShelfTableElement extends Element {
    /**
     * Default constructor
     */
    public ShelfTableElement() {
        super();
    }
    
    /**
     * Application state constructor.
     * 
     * @param state application state
     */
    public ShelfTableElement(State state) {
        super(state);
    }
    
     /**
     * Inserts an shelf table into the report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write to document
     */
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        ArrayList<Epic> shelfEpics = getDocument().getShelf();
        
        document.table(headers(), body(shelfEpics));
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
     * Creates a header row array for the shelf epic table.
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
                        .getString("reports.elements.shelfTable.number")
        );
        
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.shelfTable.name")
        );
        
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.shelfTable.size")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for a shelf epic table.
     * 
     * @param epics epics to build table body with
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
            row.add(
                    String.valueOf(
                            e.getSize(
                                    getDocument()
                                            .getPreferences()
                                            .asProjectusPreferences()
                            )
                    )
            );
            
            rows.add(row);
        }
        
        return rows;
    }

}
