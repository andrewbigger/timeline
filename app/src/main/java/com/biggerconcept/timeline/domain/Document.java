package com.biggerconcept.timeline.domain;

import com.biggerconcept.projectus.domain.Epic;
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

}
