package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Release;
import com.biggerconcept.timeline.domain.Year;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Timeline of epics
 * 
 * @author Andrew Bigger
 */
public class Timeline {
    private ArrayList<TimelineEpic> timelineEpics;
    private ArrayList<TimelineRelease> timelineReleases;
    private ArrayList<Sprint> sprints;
    private Preferences prefs;
    private LocalDate start;
    
    public Timeline(
            ArrayList<Epic> epics,
            ArrayList<Release> releases,
            Preferences prefs
    ) {
        this.timelineEpics = new ArrayList<>();
        this.timelineReleases = new ArrayList<>();
        this.sprints = new ArrayList<>();
        this.prefs = prefs;
        
        for (Epic e : epics) {
            addEpic(e);
        }
        
        for (Release r : releases) {
            TimelineRelease release = new TimelineRelease(r);
            timelineReleases.add(release);
        }
        
        build();
    }
    
    public ArrayList<TimelineEpic> getEpics() {
        if (timelineEpics == null) {
            timelineEpics = new ArrayList<>();
        }
        
        return timelineEpics;
    }
    
    public ArrayList<TimelineRelease> getReleases() {
        return timelineReleases;
    }
    
    public LocalDate getStart() {
        if (start == null) {
            start = LocalDate.now();
        }
        
        return start;
    }
    
    public ArrayList<Sprint> getSprints() {
        if (sprints == null) {
            sprints = new ArrayList<>();
        }
        
        return sprints;
    }
    
    public ArrayList<TimelineEpic> getEpicsInYear(Year year) {
        ArrayList<TimelineEpic> all = getEpics();
        ArrayList<TimelineEpic> inYear = new ArrayList<>();
        
        for (TimelineEpic te : all) {
            if (hasAnySprintInYear(year, te.getSprints()) == true) {
                inYear.add(te);
            }
        }
        
        return inYear;
    }
    
    public ArrayList<TimelineRelease> getReleasesInYear(Year year) {
        ArrayList<TimelineRelease> all = getReleases();
        ArrayList<TimelineRelease> inYear = new ArrayList<>();
        
        for (TimelineRelease tr : all) {
            if (hasSprintInYear(year, tr.getSprintNumber())) {
                inYear.add(tr);
            }
        }
        
        return inYear;
    }
    
    public ArrayList<Sprint> getSprintsInYear(Year year) {
        ArrayList<Sprint> all = getSprints();
        ArrayList<Sprint> sprints = new ArrayList<>();
        
        for (Sprint s : all) {
            boolean incl = true;
            
            if (s.getNumber() < year.getStartSprint()) {
                incl = false;
            }
            
            if (s.getNumber() > year.lastSprint(prefs) - 1) {
                incl = false;
            }
            
            if (incl == true) {
                sprints.add(s);
            }
        }
        
        return sprints;
    }
    
    public int countUsedSprintsInYear(Year year) {
        ArrayList<Sprint> yearSprints = getSprintsInYear(year);

        return yearSprints.size();
    }
    
    public Sprint findSprint(int number) {
        for (Sprint s : getSprints()) {
            if (s.getNumber() == number) {
                return s;
            }
        }
        
        return null;
    }
    
    public int countUsedPointsInYear(Year year) {
        int count = 0;
        ArrayList<Sprint> all = getSprintsInYear(year);
        
        for (Sprint s : all) {
            count += s.getPoints();
        }
        
        return count;
    }
    
    public Sprint findSprint(Sprint s) {
        return findSprint(s.getNumber());
    }
    
    public boolean hasSprint(int number) {
        Sprint s = findSprint(number);
        
        return s != null;
    }
    
    public boolean hasSprint(Sprint s) {
        return hasSprint(s.getNumber());
    }
    
    public boolean hasSprintInYear(Year year, int number) {
        ArrayList<Integer> sprints = year.sprintNumbers(prefs);
        
        for(int num : sprints) {
            if (num == number) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean hasSprintInYear(Year year, Sprint s) {
        return hasSprintInYear(year, s.getNumber());
    }
    
    public boolean hasAnySprintNumberInYear(
            Year year,
            ArrayList<Integer> numbers
    ) {
        for (Integer num : numbers) {            
            if (hasSprintInYear(year, num) == true) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean hasAnySprintInYear(
            Year year,
            ArrayList<Sprint> sprints
    ) {
        for (Sprint s : sprints) {
            if (hasSprintInYear(year, s) == true) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean hasRelease(TimelineEpic e) {
        TimelineRelease found = findRelease(e);
        
        return found != null;
    }
    
    public TimelineRelease findRelease(TimelineEpic e) {
        for (TimelineRelease r : getReleases()) {
            if (r.lastEpicId().equals(e.getEpic().getId())) {
                return r;
            }
        }
        
        return null;
    }

    public void addEpic(Epic e) {
        getEpics().add(new TimelineEpic(e));
    }
    
    public void addRelease(Release r) {
        TimelineRelease release = new TimelineRelease(r);
        timelineReleases.add(release);
    }
    
    public void addSprint(Sprint s) {
        Sprint target = findSprint(s);
        
        if (target != null) {
            target.merge(s);
            return;
        }

        sprints.add(s);
    }
    
    public void addSprints(ArrayList<Sprint> sprints) {
        for (Sprint s : sprints) {
            addSprint(s);
        }
    }
    
    public void addReleases(TimelineEpic epic, Sprint lastSprint) {
        if (hasRelease(epic)) {
            TimelineRelease r = findRelease(epic);
            r.setSprintNumber(lastSprint);
        }
    }
    
    public Sprint getLastSprint() {
        if (getSprints().size() == 0) {
            return null;
        }
        
        return getSprints().get(getSprints().size() - 1);
    }
    
    private void build() {
        int startSprint = prefs.getStartSprintNumber();
        
        Sprint s = new Sprint(startSprint);
        
        for (TimelineEpic te : getEpics()) {
            te.calculateSprints(
                    prefs, 
                    s.getNumber(),
                    s.getPoints()
            );
            
            addSprints(te.getSprints());
            addReleases(te, te.getLastSprint());
            s = te.getLastSprint();
        }
    }
}
