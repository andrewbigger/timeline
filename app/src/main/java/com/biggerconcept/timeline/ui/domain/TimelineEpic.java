package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Preferences;
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
     * @param s sprint to add
     */
    public void addSprint(Sprint s) {
        getSprints().add(s);
    }
    
    /**
     * Builds a set of occupied sprints based on epic data.
     * 
     * @param prefs document preferences
     * @param startSprint first sprint to start epic
     */
    public void calculateSprints(
            Preferences prefs,
            int startSprint
    ) {
        int points = getEpic().calculateTotalPoints(
                prefs.asProjectusPreferences()
        );
        
        int pointsPerSprint = prefs.calculateAveragePointsPerSprint();
        int sprints = (int) points / pointsPerSprint;
        int sprintCounter = startSprint;

        for (int i = 0; i < sprints + 1; i++) {
            addSprint(new Sprint(sprintCounter));
            sprintCounter++;
        }
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
     * Returns true if given sprint is the current sprint.
     * 
     * @param number sprint number
     * @param prefs document preferences
     * @return whether sprint is current sprint
     */
    public boolean isCurrentSprint(int number, Preferences prefs) {
        LocalDate now = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = now.get(woy);
        
        int sprintNumber = weekNumber / prefs.getSprintLength() + prefs.getStartSprintNumber();
        
        for (Sprint s : getSprints()) {
            if (number == sprintNumber) {
                return true;
            }
        }
        
        return false;
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
