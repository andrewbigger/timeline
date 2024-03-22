package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Andrew Bigger
 */
public class Timeline {
    private ArrayList<TimelineEpic> timelineEpics;
    private LocalDate start;
    
    public Timeline(
            Year viewYear,
            ArrayList<Epic> epics,
            LocalDate start,
            Preferences prefs,
            int maxSprints
    ) {
        timelineEpics = new ArrayList<>();
        
        for (Epic e : epics) {
            add(e);
        }
    }
    
    public ArrayList<TimelineEpic> getEpics() {
        if (timelineEpics == null) {
            timelineEpics = new ArrayList<>();
        }
        
        return timelineEpics;
    }
    
    public LocalDate getStart() {
        if (start == null) {
            start = LocalDate.now();
        }
        
        return start;
    }
    
    public void add(Epic e) {
        timelineEpics.add(new TimelineEpic(e));
    }
    
    public void calculate(Preferences prefs, int maxSprints) {
        Sprint s = new Sprint(new Year(getStart()), 1);
        
        for (TimelineEpic te : getEpics()) {
            Epic e = te.getEpic();
            
            int points = e.calculateTotalPoints(prefs.asProjectusPreferences());
            int pointsPerSprint = prefs.calculateAveragePointsPerSprint();
            int sprints = points / pointsPerSprint;
            
            te.calculateSprints(sprints, s.getYear(), s.getNumber(), maxSprints);
            
            s = te.nextSprint(maxSprints);
        }
    }
}
