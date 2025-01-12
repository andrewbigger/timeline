package com.biggerconcept.timeline.reports.sections;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Release;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a release table into a report
 * 
 * @author Andrew Bigger
 */
public class ReleaseTableElement extends Element {
    /**
     * Default constructor
     */
    public ReleaseTableElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public ReleaseTableElement(State state) {
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
        ArrayList<Release> releases = getDocument().getReleases();
        
        document.table(headers(), body(releases));
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
     * Creates a header row array for the release table.
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
                        .getString("reports.elements.releaseTable.number")
        );
        
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.releaseTable.name")
        );
        headers.add(
                getState()
                        .bundle()
                        .getString("reports.elements.releaseTable.description")
        );
        
        return headers;
    }
    
    /**
     * Creates a table body for releases.
     * 
     * @param releases releases to include in table body
     * 
     * @return release table body table as array list of array list.
    */
    public ArrayList<ArrayList<String>> body(
            ArrayList<Release> releases
    ) {
        ArrayList<ArrayList<String>> rows = new ArrayList();
        
        for (Release r : releases) {
            ArrayList<String> row = new ArrayList<>();
            
            row.add(String.valueOf(r.getNumber()));
            row.add(r.getName());
            row.add(r.getDescription());
            
            rows.add(row);
        }
        
        return rows;
    }

}
