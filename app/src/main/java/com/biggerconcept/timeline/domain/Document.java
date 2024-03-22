package com.biggerconcept.timeline.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.timeline.domain.Judgement.Assessment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
     * @param f
     * @return 
     * @throws IOException
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
     * @throws IOException 
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
     * @return 
     */
    public File getFile() {
        return file;
    }
    
    /**
     * Getter for file name.
     * 
     * @return project title
     */
    public String getTitle() {
        if (file == null) {
            return "";
        }

        return file.getName();
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
        shelf.remove(target);
    }
    
    /**
     * Moves an epic from shelf to commitment.
     * 
     * @param target epic to move
     */
    public void commitToEpic(Epic target) {
        epics.add(target);
        shelf.remove(target);
    }
    
    /**
     * Moves an epic from commitment to shelf.
     * 
     * @param target epic to move
     */
    public void unCommitToEpic(Epic target) {
        shelf.add(target);
        epics.remove(target);
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
     * Rebuilds identifiers for epics and their tasks.
     */
    public void rebuildIdentifiers() {
        identifyEpics();
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
     * Calculates total number of points that are committed.
     * 
     * @return total number of points in committed epics.
     */
    public int calculateCommittedPoints() {
        int count = 0;
        
        for (Epic e : getEpics()) {
            count += e.calculateTotalPoints(
                    getPreferences().asProjectusPreferences()
            );
        }
        
        return count;
    }
    
    /**
     * Calculates committed sprints.
     * 
     * @return committed number of sprints
     */
    public int calculateCommittedSprints() {
        int pointsPerSprint = getPreferences().calculateAveragePointsPerSprint();
        int points = calculateCommittedPoints();
        
        
        if (points == 0) {
            return 0;
        }
        
        if (pointsPerSprint == 0) {
            return 1;
        }

        return points / pointsPerSprint;
    }
    
    /**
     * Expresses committed points as a proportion of given number
     * of available points.
     * 
     * @param prefs projectus preferences
     * @param availablePoints total number of available points
     * @return progress of committed points
     */
    public double calculateCommitmentProgress(
            int availablePoints
    ) {
        return (double) calculateCommittedPoints() / availablePoints;
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
    
}
