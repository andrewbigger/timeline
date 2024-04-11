package com.biggerconcept.timeline.ui.domain;

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
}
