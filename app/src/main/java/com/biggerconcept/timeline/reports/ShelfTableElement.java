package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.projectus.domain.Epic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inserts a heading into a report
 * 
 * @author Andrew Bigger
 */
public class ShelfTableElement extends Element {
    public ShelfTableElement() {
        super();
        this.type = Doc.ParagraphType.shelf_epics_table;
    }
    
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        ArrayList<Epic> shelfEpics = getDocument().getShelf();
        
        document.table(headers(), body(shelfEpics));
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
        headers.add("Size");
        
        return headers;
    }
    
    /**
     * Creates a table body for an epic.
     * 
     * @param epics epics to build table body for
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
