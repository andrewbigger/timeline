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
 * Timeline epic
 * 
 * @author Andrew Bigger
 */
public class TimelineEpic {
    private Epic epic;
    private ArrayList<Sprint> sprints;
    
    public TimelineEpic(Epic e) {
        epic = e;
        sprints = new ArrayList<>();
    }
    
    public Epic getEpic() {
        return epic;
    }
    
    public ArrayList<Sprint> getSprints() {
        if (sprints == null) {
            sprints = new ArrayList<>();
        }
        
        return sprints;
    }
    
    public String getName() {
        return epic.getName();
    }
    
    public int getSize(Preferences prefs) {
        return epic.getSize(prefs.asProjectusPreferences());
    }
    
    public int getLength() {
        return sprints.size();
    }
    
    public void setEpic(Epic value) {
        epic = value;
    }
    
    public void setSprints(ArrayList<Sprint> value) {
        sprints = value;
    }
    
    public void addSprint(Sprint s) {
        getSprints().add(s);
    }
    
    public void calculateSprints(int length, Year year, int sprint, int maxSprints) {
        Year currentYear = year;
        int currentSprint = sprint;
        
        if (length == 0) {
            Sprint s = new Sprint(currentYear, currentSprint);
            addSprint(s);
        } else {
            for (int i = 0; i < length + 1; i++) {
                int sprintNumber = currentSprint + i;

                if (sprintNumber > maxSprints) {
                    currentYear = currentYear.next();
                    currentSprint = 1;
                }

                Sprint s = new Sprint(currentYear, sprintNumber);
                addSprint(s);
            }
        }
    }
    
    public Sprint nextSprint(int maxSprints) {
        Sprint last = getLastSprint();
        
        if (last.getNumber() == maxSprints) {
            return new Sprint(last.getYear().next(), 1);
        }
        
        return new Sprint(last.getYear(), last.getNumber() + 1);
    }
    
    public boolean hasSprint(Year year, int number) {
        for (Sprint s : getSprints()) {
            if (!s.getYear().getName().equals(year.getName())) {
                return false;
            }

            if (s.getNumber() == number) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean isCurrentSprint(Year year, int number, Preferences prefs) {
        LocalDate now = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = now.get(woy);
        
        int sprintNumber = weekNumber / prefs.getSprintLength();
        
        for (Sprint s : getSprints()) {
            if (!s.getYear().getName().equals(year.getName())) {
                return false;
            }
            
            if (number == sprintNumber) {
                return true;
            }
        }
        
        return false;
    }
    
    public Sprint getLastSprint() {
        if (sprints.size() == 0) {
            return null;
        }
        
        return sprints.get(sprints.size() - 1);
    }
}
