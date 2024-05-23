package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Timeline epic, wraps a Projectus epic for presentation in a timeline.
 * 
 * @author Andrew Bigger
 */
public class TimelineEpic {
    /**
     * Returns timeline epics that fit in given year.
     * 
     * @param year year
     * @param epics epics
     * @return timeline epics that fit in year
     */
    public static ArrayList<TimelineEpic> inYear(
            Year year,
            ArrayList<TimelineEpic> epics
    ) {
        ArrayList<TimelineEpic> yearEpics = new ArrayList<>();
        
        for (TimelineEpic te : epics) {
            if (te.hasSprintInYear(year)) {
                yearEpics.add(te);
            }
        }
        
        return yearEpics;
    }
    
    /**
     * Converts a set of epics to timeline epics.
     * 
     * @param epics epics
     * @return timeline epics
     */
    public static ArrayList<TimelineEpic> toTimelineEpics(
            ArrayList<Epic> epics
    ) {
        ArrayList<TimelineEpic> timelineEpics = new ArrayList<>();
        
        for (Epic e : epics) {
            timelineEpics.add(new TimelineEpic(e));
        }
        
        return timelineEpics;
    }
    
    /**
     * Epic definition
     */
    private Epic epic;
    
    /**
     * Sprints that the epic occupies
     */
    private ArrayList<Sprint> sprints;
    
    public TimelineEpic(Epic e) {
        epic = e;
        sprints = new ArrayList<>();
    }
    
    /**
     * Returns epic
     * 
     * @return epic
     */
    public Epic getEpic() {
        return epic;
    }
    
    /**
     * Returns occupied sprints.
     * 
     * @return occupied sprints
     */
    public ArrayList<Sprint> getSprints() {
        if (sprints == null) {
            sprints = new ArrayList<>();
        }
        
        return sprints;
    }
    
    /**
     * Getter for a specific sprint
     * 
     * @param number sprint number
     * @return sprint if found
     */
     public Sprint getSprint(int number) {
         for (Sprint s : getSprints()) {
             if (s.getNumber() == number) {
                 return s;
             }
         }
         
         return null;
     }
    
    /**
     * Retrieves the epic name from underlying epic
     * 
     * @return epic name
     */
    public String getName() {
        return epic.getName();
    }
    
    /**
     * Calculates the size of the epic using preference data.
     * 
     * @param prefs document preferences
     * 
     * @return size in points
     */
    public int getSize(Preferences prefs) {
        return epic.getSize(prefs.asProjectusPreferences());
    }
    
    /**
     * Returns number of sprints to achieve epic.
     * 
     * @return length of epic in sprints
     */
    public int getLength() {
        return sprints.size();
    }
    
    /**
     * Setter for underlying epic
     * 
     * @param value new epic
     */
    public void setEpic(Epic value) {
        epic = value;
    }
    
    /**
     * Setter for occupied sprints.
     * 
     * @param value new set of sprints
     */
    public void setSprints(ArrayList<Sprint> value) {
        sprints = value;
    }
    
    /**
     * Adds a sprint to the occupied sprints array.
     * 
     * @param sprint sprint to add
     */
    public void addSprint(Sprint sprint) {
        getSprints().add(sprint);
    }
    
    /**
     * Adds multiple sprints to the sprints array.
     * 
     * @param sprints sprints to add
     */
    public void addSprints(ArrayList<Sprint> sprints) {
        getSprints().addAll(sprints);
    }
    
    /**
     * Builds a set of occupied sprints based on epic data.
     * 
     * First we check whether sprints have been assigned to the epic. When
     * they are, those sprints are added and the function exits early.
     * 
     * In the event that sprints have not been assigned, then the timeline
     * will calculate the sprints using the reference sprint data.
     * 
     * @param prefs document preferences
     * @param startSprintNumber first sprint to start epic
     * @param startSprintCommitment amount of points already committed in start sprint
     */
    public void calculateSprints(
            Preferences prefs,
            int startSprintNumber,
            int startSprintCommitment
    ) {
        ArrayList<Sprint> sprints = new ArrayList<Sprint>();
        
        if (getEpic().hasAssignedSprints()) {
            int epicSize = getEpic().getSize(prefs.asProjectusPreferences());
            int numberOfSprints = getEpic().getAssignedSprints().size();
            float size = epicSize / numberOfSprints;
            
            for (int i : getEpic().getAssignedSprints()) {
                sprints.add(new Sprint(i, (int) size));
            }
            
            // Ensure remainder is accounted for in first sprint.
            int remainder = epicSize % numberOfSprints;
            
            if (remainder == 1) {
                sprints.get(1).addPoints(1);
            }
            
            addSprints(sprints);
            return;
        }
        
        // epic points
        int points = getEpic().calculateTotalPoints(
                prefs.asProjectusPreferences()
        );
        // maximum number of points in each sprint
        int pointsPerSprint = prefs.calculateAveragePointsPerSprint();
        
        int pointsAfterFirst = points;
        Sprint startSprint = new Sprint(startSprintNumber, startSprintCommitment);
        int availableFirstSprintPoints = startSprint
                .availablePoints(pointsPerSprint);
        
        if (points < availableFirstSprintPoints) {
            // add the whole epic to the start sprint if it fits
            startSprint.addPoints(points);
            addSprint(startSprint);
            return;
        } else if (availableFirstSprintPoints > 0) {
            // otherwise we need to add the available points
            startSprint.setPoints(availableFirstSprintPoints);
            pointsAfterFirst = points - availableFirstSprintPoints;
            addSprint(startSprint);
        } else {
            // last sprint is full, we'll start this on the
            // next one
        }
 
        int sprintCount = (int) pointsAfterFirst / pointsPerSprint;
        int remainder = (int) pointsAfterFirst % pointsPerSprint;
        int sprintCounter = startSprintNumber + 1;
        
        // add sprints that didn't fit into the first sprint
        for (int i = 0; i < sprintCount; i++) {
            sprints.add(new Sprint(sprintCounter, pointsPerSprint));
            sprintCounter++;
        }
        
        // finally add a sprint for the remainder if there is any
        if (remainder > 0) {
            sprints.add(new Sprint(sprintCounter, remainder));
        }
        
        // add all generated sprints to epic
        addSprints(sprints);
    }
    
    /**
     * Builds next sprint.
     * 
     * @return next sprint
     */
    public Sprint nextSprint() {
        Sprint last = getLastSprint();
        
        return new Sprint(last.getNumber() + 1);
    }
    
    /**
     * Returns true if described sprint is occupied by the underlying
     * epic.
     * 
     * @param number sprint number
     * 
     * @return whether epic occupies sprint
     */
    public boolean hasSprint(int number) {
        for (Sprint s : getSprints()) {
            if (s.getNumber() == number) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns true if there are any sprints in the given year
     * 
     * @param year year
     * 
     * @return whether there are any sprints in the year.
     */
    public boolean hasSprintInYear(Year year) {
        boolean result = true;
        
        for (Sprint s : getSprints()) {
            if (s.getNumber() < year.getStartSprint()) {
                result = false;
            } else {
                result = true;
            }
        }
        
        return result;
    }
    
    /**
     * Returns true if given sprint is the current sprint.
     * 
     * @param number sprint number
     * @param prefs document preferences
     * 
     * @return whether sprint is current sprint
     */
    public boolean isCurrentSprint(int number, Preferences prefs) {
        LocalDate now = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = now.get(woy);
        
        int sprintNumber = prefs.getStartSprintNumber() + 
                (weekNumber / prefs.getSprintLength());
        
        for (Sprint s : getSprints()) {
            if (number == sprintNumber - 1) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns true if the sprint is in the past
     * 
     * @param number sprint number
     * @param prefs document preferences
     * 
     * @return whether sprint is in the past
     */
    public boolean isPastSprint(int number, Preferences prefs) {
        LocalDate now = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = now.get(woy);
        
        int sprintNumber = weekNumber / prefs.getSprintLength() + prefs.getStartSprintNumber();
        
        for (Sprint s : getSprints()) {
            if (number < sprintNumber) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns whether the sprint is in the future
     * 
     * @param number sprint number
     * @param prefs document preferences
     * 
     * @return whether the sprint is in the future
     */
    public boolean isFutureSprint(int number, Preferences prefs) {
        return !isPastSprint(number, prefs);
    }
    
    /**
     * Returns the last sprint of the epic.
     * 
     * @return last sprint
     */
    public Sprint getLastSprint() {
        if (sprints.size() == 0) {
            return null;
        }
        
        return sprints.get(sprints.size() - 1);
    }
    
    /**
     * Returns the first sprint of the epic.
     * 
     * @return last sprint
     */
    public Sprint getFirstSprint() {
        if (sprints.size() == 0) {
            return null;
        }
        
        return sprints.get(0);
    }
}
