package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Release;
import com.biggerconcept.timeline.domain.Year;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Timeline release, wraps a release for presentation in timeline.
 * 
 * @author Andrew Bigger
 */
public class TimelineRelease {
    /**
     * Returns all releases for the year.
     * 
     * @param prefs document preferences
     * @param year year in view
     * @param releases all releases
     * @return releases for year
     */
    public static ArrayList<TimelineRelease> inYear(
            Preferences prefs,
            Year year,
            ArrayList<TimelineRelease> releases
    ) {
        ArrayList<TimelineRelease> yearReleases = new ArrayList<>();
        
        for (TimelineRelease tr : releases) {
            if (tr.isSprintInYear(prefs, year)) {
                yearReleases.add(tr);
            }
        }
        
        return yearReleases;
    }
    
    /**
     * Release definition.
     */
    private Release release;
    
    /**
     * Sprint where release is available
     */
    private int sprintNumber;
    
    public TimelineRelease(Release r) {
        release = r;
    }
    
    /**
     * Getter for release domain model
     * 
     * @return release
     */
    public Release getRelease() {
        return release;
    }
    
    /**
     * Getter for sprint.
     * 
     * @return release sprint
     */
    public int getSprintNumber() {
        return sprintNumber;
    }
    
    /**
     * Getter for name
     * 
     * @return release name
     */
    public String getName() {
        return release.getName();
    }
    
    /**
     * Returns last epic ID
     * 
     * @return epic ID
     */
    public UUID lastEpicId() {
        return release.lastEpicId();
    }
    
    /**
     * Setter for the sprint number
     * 
     * @param value new sprint number
     */
    public void setSprintNumber(int value) {
        sprintNumber = value;
    }
    
    /**
     * Sets sprint for release
     * 
     * @param value new sprint
     */
    public void setSprintNumber(Sprint value) {
        sprintNumber = value.getNumber();
    }
    
    /**
     * Returns true if sprint is scheduled in the given year.
     * 
     * @param prefs document preferences
     * @param year year to limit search to
     * 
     * @return whether release sprint is in given year.
     */
    public boolean isSprintInYear(Preferences prefs, Year year) {
        if (getSprintNumber() == 0) {
            return false;
        }
        
        return getSprintNumber() > year.getStartSprint() &&
                getSprintNumber() < year.lastSprint(prefs);
    }
    
    /**
     * Returns true if the release sprint is in the given quarter.
     * 
     * @param year view year
     * @param quarter quarter to check
     * @param prefs document preferences
     * 
     * @return whether epic has sprint in given quarter
     */
    public boolean isSprintInQuarter(
            Year year, 
            int quarter, 
            Preferences prefs
    ) {
        int startSprint = year.firstSprintInQuarter(quarter, prefs);
        int endSprint = year.lastSprintInQuarter(quarter, prefs);
        
        for (int i = startSprint; i < endSprint + 1; i++) {
            if (getSprintNumber() == i) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Combines included epic scope into single list.
     * 
     * @param doc document with epics
     * 
     * @return release scope
     */
    public ArrayList<String> releaseScope(Document doc) {
        ArrayList<String> scope = new ArrayList<>();
        
        for (Epic e : getRelease().epics(doc)) {
            for (String s : e.getScope().getIncluded()) {
                scope.add(s);
            }
        }
        
        return scope;
    }
    
    /**
     * Compiles a list of epic names for included epics within
     * the release
     * 
     * @param doc document with epic definitions
     * 
     * @return epic names
     */
    public ArrayList<Epic> includedEpics(Document doc) {
        ArrayList<Epic> epics = new ArrayList<>();
        
        for (Epic e : getRelease().epics(doc)) {
            epics.add(e);
        }
        
        return epics;
    }
    
}
