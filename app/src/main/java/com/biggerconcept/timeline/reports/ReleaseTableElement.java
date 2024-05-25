package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.domain.Release;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a release table into a report
 * 
 * @author Andrew Bigger
 */
public class ReleaseTableElement extends Element {
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        ArrayList<Release> releases = getDocument().getReleases();
        
        document.table(headers(), body(releases));
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
        
        // TODO: Translations
        headers.add("#");
        headers.add("Name");
        headers.add("Description");
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic.
     * 
     * @param releases releases to include in table body
     * 
     * @return epic body table as array list of array list.
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
