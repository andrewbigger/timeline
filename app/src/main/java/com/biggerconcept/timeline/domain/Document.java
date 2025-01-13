package com.biggerconcept.timeline.domain;

import com.biggerconcept.doctree.domain.Group;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.timeline.domain.Judgement.Assessment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * In memory representation of file.
 * 
 * @author Andrew Bigger
 */
public class Document {
    /**
     * Document file.
     */
    @JsonIgnore
    private File file;
    
    /**
     * Document preferences.
     */
    @JsonInclude(Include.NON_NULL)
    private Preferences preferences;
    
    /**
     * Epic shelf
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Epic> shelf;
    
    /**
     * Document epics.
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Epic> epics;
    
    /**
     * Timeline judgement
     */
    @JsonInclude(Include.NON_NULL)
    private Assessment judgement;
    
    /**
     * Timeline notes
     */
    @JsonInclude(Include.NON_NULL)
    private String notes;
    
    /**
     * Releases
     */
    @JsonInclude(Include.NON_NULL)
    private ArrayList<Release> releases;
    
    /**
     * Root document node
     */
    @JsonInclude(Include.NON_NULL)
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.CLASS,
            include = JsonTypeInfo.As.PROPERTY,
            property = "className"
    )
    private Group documents;
    
    /**
     * Last epic number
     */
    @JsonIgnore
    private int lastEpicIdentifier = 0;
    
    /**
     * Loads file from disk.
     * 
     * By default empty or null values are to be omitted from the parser.
     * 
     * This will then attempt to deserialize a data file  
     * from disk. This may fail throwing an IO Exception which will be thrown to
     * the calling method.
     * 
     * The deserialized Locale will then be returned to the caller.
     * 
     * @param f file to load
     * @return loaded document
     * @throws IOException when unable to read document
     */
    public static Document load(File f) throws IOException {
        ObjectMapper oMap = new ObjectMapper();
        
        oMap.setSerializationInclusion(Include.NON_NULL);
        oMap.setSerializationInclusion(Include.NON_EMPTY);
        
        Document doc = oMap.readValue(f, Document.class);
        doc.setFile(f);
        
        return doc;
    }
    
    /**
     * Saves document to disk.
     * 
     * @throws IOException when unable to save document
     */
    public void save() throws IOException {       
        ObjectMapper oMap = new ObjectMapper();
        
        oMap.setSerializationInclusion(Include.NON_NULL);
        oMap.setSerializationInclusion(Include.NON_EMPTY);
        
        oMap.writeValue(file, this);
    }
    
    /**
     * Getter for file.
     * 
     * @return pointer to on disk file
     */
    public File getFile() {
        return file;
    }

    
    /**
     * Getter for preferences.
     * 
     * @return document preferences.
     */
    public Preferences getPreferences() {
        if (preferences == null) {
            return Preferences.defaultPreferences();
        }

        return preferences;
    }
    
    /**
     * Getter for epic shelf.
     * 
     * @return shelved epics
     */
    public ArrayList<Epic> getShelf() {
        if (shelf == null) {
            shelf = new ArrayList<>();
        }
        
        return shelf;
    }
    
    /**
     * Getter for epics.
     * 
     * @return timeline epics
     */
    public ArrayList<Epic> getEpics() {
        if (epics == null) {
            epics = new ArrayList<>();
        }
        
        return epics;
    }
    
    /**
     * Getter for judgement.
     * 
     * @return assessment
     */
    public Assessment getJudgement() {
        if (judgement == null) {
            judgement = Judgement.DEFAULT_ASSESSMENT;
        }
        
        return judgement;
    }
    
    /**
     * Getter for notes.
     * 
     * @return notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * Getter for releases.
     * 
     * @return releases
     */
    public ArrayList<Release> getReleases() {
        if (releases == null) {
            releases = new ArrayList<Release>();
        }
        
        return releases;
    }
    
    /**
     * Getter for root document group.
     * 
     * @return root document group
     */
    public Group getDocuments() {
        if (documents == null) {
            documents = new Group("/");
        }
        
        return documents;
    }
    
    /**
     * Getter for last epic identifier.
     * 
     * @return identifier for last epic
     */
    @JsonIgnore
    public int getLastEpicIdentifier() {
        return lastEpicIdentifier;
    }

    /**
     * Setter for file.
     * 
     * @param value new file
     */
    public void setFile(File value) {
        file = value;
    }
    
    /**
     * Setter for preferences.
     * 
     * @param value new preferences
     */
    public void setPreferences(Preferences value) {
        preferences = value;
    }
    
    /**
     * Setter for shelf.
     * 
     * @param value new set of shelved epics
     */
    public void setShelf(ArrayList<Epic> value) {
        shelf = value;
    }
    
    /**
     * Setter for epics.
     * 
     * @param value new set of epics
     */
    public void setEpics(ArrayList<Epic> value) {
        epics = value;
    }
    
    /**
     * Removes a target epic from the shelf.
     * 
     * @param target epic to remove.
     */
    public void removeFromShelf(Epic target) {
        getShelf().remove(target);
    }
    
    /**
     * Moves an epic from shelf to commitment.
     * 
     * @param target epic to move
     */
    public void commitToEpic(Epic target) {
        getEpics().add(target);
        getShelf().remove(target);
    }
    
    /**
     * Moves an epic from commitment to shelf.
     * 
     * @param target epic to move
     */
    public void unCommitToEpic(Epic target) {
        getShelf().add(target);
        getEpics().remove(target);
    }
    
    /**
     * Setter for judgement.
     * 
     * @param value new judgement.
     */
    public void setJudgement(Assessment value) {
        judgement = value;
    }
    
    /**
     * Setter for notes.
     * 
     * @param value timeline notes.
     */
    public void setNotes(String value) {
        notes = value;
    }
    
    /**
     * Setter for releases
     * 
     * @param value releases
     */
    public void setReleases(ArrayList<Release> value) {
        releases = value;
    }
    
    /**
     * Setter for root documentation group.
     * 
     * @param value root documentation group
     */
    public void setDocumentation(Group value) {
        documents = value;
    }

    /**
     * Returns document title which is the file name if it's saved
     * empty string if it is not.
     * 
     * @return project title
     */
    public String title() {
        if (file == null) {
            return "";
        }

        return file.getName();
    }
    
    /**
     * Rebuilds identifiers for epics and their tasks.
     */
    public void rebuildIdentifiers() {
        identifyEpics();
        Release.numberReleases(getReleases());
    }
    
    /**
     * Find epic by ID
     * 
     * @param id id to search for
     * @return found epic
     */
    public Epic findEpicById(UUID id) {
        for (Epic e : getEpics()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        
        return null;
    }
    
    /**
     * Sets identifiers on all epics and tasks
     */
    private void identifyEpics() {
        int idx = 1;
        
        for (Epic e : getEpics()) {
            e.setIdentifier(idx);
            idx += 1;
            
            int tidx = 0;
            for (Task t : e.getTasks()) {
                tidx += 1;
                t.setIdentifier(tidx);
            }
        }
        
        for (Epic e : getShelf()) {
            e.setIdentifier(idx);
            idx += 1;
            
            int tidx = 0;
            for (Task t : e.getTasks()) {
                tidx += 1;
                t.setIdentifier(tidx);
            }
        }
        
        lastEpicIdentifier = idx;
    }
    
    /**
     * Calculates total number of points on the shelf.
     * 
     * @param prefs projectus preferences
     * @return total number of points on the shelf.
     */
    public int calculateShelfPoints(
            com.biggerconcept.projectus.domain.Preferences prefs
    ) {
        int count = 0;
        
        for (Epic e : getShelf()) {
            count += e.calculateTotalPoints(prefs);
        }
        
        return count;
    }
    
    /**
     * Returns list of available epics for releases.
     * 
     * @return available epics
     */
    public ArrayList<Epic> availableEpicsForRelease() {
        ArrayList<Epic> all = getEpics();
        ArrayList<Release> allReleases = getReleases();
        ArrayList<Epic> releaseEpics = new ArrayList<>();
        
        for (Release r : allReleases) {
            releaseEpics.addAll(r.epics(this));
        }
        
        ArrayList<Epic> available = new ArrayList<>();
        
        for (Epic e : all) {
            if (hasEpic(releaseEpics, e) == false) {
                available.add(e);
            }
        }
        
        return available;
    }

    /**
     * Adds an epic to the set.
     * 
     * @param epic epic to add
     */
    public void addEpic(Epic epic) {
        getEpics().add(epic);
    }
    
    /**
     * Returns true if given epic is in the set.
     * 
     * @param set set of epics to search through
     * @param epic epic to search for
     * 
     * @return whether epic is in the set
     */
    private boolean hasEpic(ArrayList<Epic> set, Epic epic) {
        for (Epic e : set) {
            if (epic.getId().equals(e.getId())) {
                return true;
            }
        }
        
        return false;
    }
    
}
