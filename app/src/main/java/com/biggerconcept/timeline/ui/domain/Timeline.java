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
    private Year viewYear;
    private ArrayList<TimelineEpic> timelineEpics;
    private LocalDate start;
    
    public Timeline(
            Year viewYear,
            ArrayList<Epic> epics,
            LocalDate start,
            Preferences prefs,
            int maxSprints
    ) {
        this.viewYear = viewYear;
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
    
    public void calculate(Preferences prefs) {
        int startSprint = prefs.getStartSprintNumber();
        
        Sprint s = new Sprint(startSprint);
        
        for (TimelineEpic te : getEpics()) {
            int sprints = te.getLength();
            
            te.calculateSprints(
                    prefs, 
                    s.getNumber()
            );
            
            s = te.getLastSprint();
        }
    }
}
