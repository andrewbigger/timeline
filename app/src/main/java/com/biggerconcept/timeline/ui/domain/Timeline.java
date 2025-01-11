package com.biggerconcept.timeline.ui.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Release;
import com.biggerconcept.timeline.domain.Year;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * In memory representation of a timeline for presentation.
 * 
 * @author Andrew Bigger
 */
public class Timeline {
    /**
     * Timeline epics
     */
    private ArrayList<TimelineEpic> timelineEpics;
    
    /**
     * Timeline releases
     */
    private ArrayList<TimelineRelease> timelineReleases;
    
    /**
     * All sprints
     */
    private ArrayList<Sprint> sprints;
    
    /**
     * Document preferences
     */
    private Preferences prefs;
    
    /**
     * Timeline start date
     */
    private LocalDate start;
    
    /**
     * Constructs a timeline from application state.When the state is not set, an empty timeline will be returned to avoid
 a crash when writing the report.
     * 
     *
     * @param state application state
     * 
     * @return epic timeline
     * 
     * @throws CloneNotSupportedException when unable to clone objects
     */
    public static Timeline fromState(State state)
        throws CloneNotSupportedException {
        if (state == null) {
            return new Timeline(
                    new ArrayList<Epic>(),
                    new ArrayList<Release>(),
                    Preferences.defaultPreferences()
            );
        }
        
        return new Timeline(
                state.getOpenDocument().getEpics(),
                state.getOpenDocument().getReleases(),
                state.getOpenDocument().getPreferences()
        );
    }
    
    /**
     * Full constructor for timeline
     * 
     * @param epics epics to present on timeline
     * @param releases releases to present on timeline
     * @param prefs document preferences
     * @throws CloneNotSupportedException when unable to clone sprints
     */
    public Timeline(
            ArrayList<Epic> epics,
            ArrayList<Release> releases,
            Preferences prefs
    ) throws CloneNotSupportedException {
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
    
    /**
     * Getter for epics
     * 
     * @return timeline epics
     */
    public ArrayList<TimelineEpic> getEpics() {
        if (timelineEpics == null) {
            timelineEpics = new ArrayList<>();
        }
        
        return timelineEpics;
    }
    
    /**
     * Getter for releases
     * 
     * @return timeline releases
     */
    public ArrayList<TimelineRelease> getReleases() {
        return timelineReleases;
    }
    
    /**
     * Getter for start date
     * 
     * @return timeline start date
     */
    public LocalDate getStart() {
        if (start == null) {
            start = LocalDate.now();
        }
        
        return start;
    }
    
    /**
     * Getter for sprints.
     * 
     * There might be multiple instances
     * referring to the same sprint as they are
     * required for ensuring that sprints belonging
     * to different epics can position 
     * themselves correctly.
     * 
     * For a list of unique sprints, please use
     * getUniqueSprints.
     * 
     * @return all sprints
     */
    public ArrayList<Sprint> getSprints() {
        if (sprints == null) {
            sprints = new ArrayList<>();
        }
        
        return sprints;
    }
    
    /**
     * Returns a hash map of sprints being presented
     * on the timeline.
     * 
     * There is a single entry on the map for each
     * sprint to be presented in the timeline. The index
     * of the hash map is the sprint number, and the value
     * is a pointer to the sprint.
     * 
     * Sprints are cloned for size calculation, when it
     * is not possible to clone a sprint, a 
     * CloneNotSupportedException will be raised.
     * 
     * @return unique sprints
     * 
     * @throws CloneNotSupportedException when unable to clone sprint
     */
    public HashMap<Integer, Sprint> getUniqueSprints() 
            throws CloneNotSupportedException {
        HashMap<Integer, Sprint> sprints = new HashMap<>();
        
        for (Sprint s : getSprints()) {
            int number = s.getNumber();
            
            if (sprints.containsKey(number)) {
                sprints.get(number).merge(s);
            } else {
                sprints.put(number, (Sprint) s.clone());
            }
        }
        
        return sprints;
    }
    
    /**
     * Returns timeline epics that belong in the
     * given year.
     * 
     * This is for building a collection for presentation
     * in the timeline table view.
     * 
     * @param year year to limit epics to
     * 
     * @return timeline epics to be scheduled in the given year
     */
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
    
    /**
     * Returns timeline releases that belong in the 
     * given year.
     * 
     * This is for building a collection of releases for
     * presentation in the timeline table view.
     * 
     * @param year presentation year
     * 
     * @return timeline releases for the year
     */
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
    
    /**
     * Returns all sprints scheduled in the given year.
     * 
     * Please note that there is one sprint for each
     * timeline epic in the set. This is so that we can
     * schedule multiple epics in a single sprint when
     * that's appropriate. For a list of unique sprints
     * please use getUniqueSprintsInYear.
     * 
     * @param year year to limit sprints to
     * 
     * @return sprints
     */
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
    
    /**
     * This returns a hash map of sprints to be scheduled
     * in the given year.
     * 
     * The hash map has the sprint number as a key and a
     * pointer to the sprint as the value. Sprints from
     * all epics are merged into a single set, for scheduling
     * please use getSprintsInYear.
     * 
     * @param year year to limit sprints to
     * 
     * @return sprints for the year.
     */
    public HashMap<Integer, Sprint> getUniqueSprintsInYear(Year year) {
        HashMap<Integer, Sprint> sprints = new HashMap<>();
        
        for (Sprint s : getSprintsInYear(year)) {
            int number = s.getNumber();
            
            if (sprints.containsKey(s)) {
                sprints.get(number).merge(s);
            } else {
                sprints.put(number, s);
            }
        }
        
        return sprints;
    }
    
    /**
     * Counts used sprints in given year.
     * 
     * @param year view year
     * 
     * @return total number of sprints used in the year.
     */
    public int countUsedSprintsInYear(Year year) {
        HashMap<Integer, Sprint> yearSprints = getUniqueSprintsInYear(year);
        return yearSprints.size();
    }
    
    /**
     * Returns scheduled quarter for sprint.
     * 
     * This iterates over each quarter and evaluates whether the sprint number 
     * is less than than the last sprint for the quarter. When this is true
     * that means that the sprint is scheduled in that quarter, so that quarter
     * can be returned.
     * 
     * When the sprint is scheduled outside of the view year, -1 is returned to
     * the caller to indicate that the sprint quarter cannot be determined.
     * 
     * @param prefs document preferences
     * @param year view year
     * @param sprint sprint to evaluate
     * 
     * @return quarter for sprint.
     */
    public int quarterForSprint(Preferences prefs, Year year, Sprint sprint) {
        for (int q = 1; q < 5; q++) {
            int lastSprintForQuarter = year.lastSprintInQuarter(q, prefs);
            
            if (sprint.getNumber() < lastSprintForQuarter + 1) {
                return q;
            }
        }
        
        return -1;
    }
    
    /**
     * Finds timeline epic by epic ID.
     * 
     * Iterates over the timeline epics and compares the given ID with the 
     * epic ID. When the epic is found it is returned to the caller.
     * 
     * In the event that the epic is not found, null will be returned.
     * 
     * @param id epic id
     * 
     * @return found epic
     */
    public TimelineEpic findEpic(UUID id) {
        for (TimelineEpic te : getEpics()) {
            if (te.getEpic().getId().toString().equals(id.toString())) {
                return te;
            }
        }
        
        return null;
    }
    
    /**
     * Finds timeline epic in year by ID
     * 
     * Iterates over the epics scheduled in the given year. If the ID of the
     * epic matches the given epic ID, then that epic will be returned to the
     * caller.
     * 
     * In the event that the epic is not found, null will be returned.
     * 
     * @param year year to scope search to
     * @param id epic ID
     * 
     * @return found epic
     */
    public TimelineEpic findEpicInYear(Year year, UUID id) {
        for (TimelineEpic te : getEpicsInYear(year)) {
            if (te.getEpic().getId() == id) {
                return te;
            }
        }
        
        return null;
    }
    
    /**
     * Check for epic.
     * 
     * Looks for epic that matches the given ID. If it is found, true will be 
     * returned, otherwise false will be returned.
     * 
     * @param id epic ID
     * 
     * @return whether epic is in the timeline
     */
    public boolean hasEpic(UUID id) {
        return findEpic(id) != null;
    }
    
    /**
     * Check for epic scoped by year.
     * 
     * Looks for the epic that matches the given id scoped by view year. If
     * it is found, true will be returned. Otherwise false will be returned.
     * 
     * @param year year to scope search to
     * @param id epic ID to search for
     * 
     * @return whether epic is scheduled within the year
     */
    public boolean hasEpicInYear(Year year, UUID id) {
        return findEpicInYear(year, id) != null;
    }
    
    /**
     * Find sprint by sprint number.
     * 
     * When the sprint is found it is returned to the
     * caller. When it is not, null will be returned.
     * 
     * @param number query value
     * 
     * @return matching sprint.
     */
    public Sprint findSprint(int number) {
        for (Sprint s : getSprints()) {
            if (s.getNumber() == number) {
                return s;
            }
        }
        
        return null;
    }
    
    /**
     * Find sprint in set.
     * 
     * This retrieves the sprint number from the given
     * sprint and calls findSprint with the sprint number.
     * 
     * When the sprint is found, it is returned to the caller.
     * When it is not, null will be returned.
     * 
     * @param sprint sprint to search for.
     * 
     * @return found sprint
     */
    public Sprint findSprint(Sprint sprint) {
        return findSprint(sprint.getNumber());
    }
    
    /**
     * Returns true if the sprint exists in the timeline.
     * 
     * @param number number of sprint to look for
     * 
     * @return whether the sprint exists in the timeline
     */
    public boolean hasSprint(int number) {
        Sprint s = findSprint(number);
        
        return s != null;
    }
    
    /**
     * Returns true if the sprint exists in the timeline.
     * 
     * @param sprint sprint to look for
     * @return whether sprint has been added to the timeline
     */
    public boolean hasSprint(Sprint sprint) {
        return hasSprint(sprint.getNumber());
    }
    
    /**
     * Returns true if sprint is scheduled in the given year.
     * 
     * @param year year to limit search to
     * @param number sprint number to look for.
     * 
     * @return whether sprint is scheduled in year
     */
    public boolean hasSprintInYear(Year year, int number) {
        ArrayList<Integer> sprints = year.sprintNumbers(prefs);
        
        for(int num : sprints) {
            if (num == number) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns true if sprint is scheduled in the given year.
     * 
     * @param year year to limit search to
     * @param sprint sprint to look for
     * 
     * @return whether sprint is scheduled in given year.
     */
    public boolean hasSprintInYear(Year year, Sprint sprint) {
        return hasSprintInYear(year, sprint.getNumber());
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
    
    /**
     * Returns true if any of the sprints are scheduled in the
     * current year.
     * 
     * This iterates over the list and does a sprint check in
     * the year. When it exists, true is returned, otherwise
     * false will be returned when none of the given sprints
     * are found in the year.
     * 
     * @param year year to limit search to
     * @param sprints sprints to search for in the year
     * 
     * @return whether any sprint exists in the year.
     */
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
    
    /**
     * Returns true if the timeline epic has a release milestone
     * at the end.
     * 
     * @param epic epic to check
     * 
     * @return whether there is a release milestone at the end
     */
    public boolean hasRelease(TimelineEpic epic) {
        TimelineRelease found = findRelease(epic);
        
        return found != null;
    }
    
    /**
     * Finds a release for a given epic.
     * 
     * If a release has a pointer to the given epic, then 
     * that release will be returned to the caller. When no release
     * is found, null is returned.
     * 
     * @param epic epic to match to release
     * 
     * @return relevant release milestone
     */
    public TimelineRelease findRelease(TimelineEpic epic) {
        for (TimelineRelease r : getReleases()) {
            if (r.lastEpicId().equals(epic.getEpic().getId())) {
                return r;
            }
        }
        
        return null;
    }
    
    /**
     * Calculates number of used points in the given year.
     * 
     * @param year year to limit count to
     * 
     * @return total number of points used in year.
     */
    public int countUsedPointsInYear(Year year) {
        int count = 0;
        ArrayList<Sprint> all = getSprintsInYear(year);
        
        for (Sprint s : all) {
            count += s.getPoints();
        }
        
        return count;
    }

    /**
     * Adds a timeline epic to the timeline.
     * 
     * @param epic epic to add
     */
    public void addEpic(Epic epic) {
        getEpics().add(new TimelineEpic(epic));
    }
    
    /**
     * Adds a release milestone to the timeline.
     * 
     * @param release release milestone to add
     */
    public void addRelease(Release release) {
        timelineReleases.add(new TimelineRelease(release));
    }
    
    /**
     * Adds a sprint to the timeline.
     * 
     * @param sprint sprint to add to timeline
     */
    public void addSprint(Sprint sprint) {
        sprints.add(sprint);
    }
    
    /**
     * Adds all given sprints to the timeline.
     * 
     * @param sprints sprints to add to timeline
     */
    public void addSprints(ArrayList<Sprint> sprints) {
        for (Sprint s : sprints) {
            addSprint(s);
        }
    }
    
    /**
     * Adds all given releases to the timeline.
     * 
     * @param epic epic to add the milestone to
     * @param lastSprint final sprint in epic to place the milestone.
     */
    public void addReleases(TimelineEpic epic, Sprint lastSprint) {
        if (hasRelease(epic)) {
            TimelineRelease r = findRelease(epic);
            r.setSprintNumber(lastSprint);
        }
    }
    
    /**
     * Returns the last known sprint.
     * 
     * This sprint will include points for the last
     * epic taking into account any points for the same
     * sprint that are scheduled for another epic. Please note
     * that this might not be full.
     * 
     * @return last sprint
     */
    public Sprint getLastSprint() {
        if (getSprints().size() == 0) {
            return null;
        }
        
        return getSprints().get(getSprints().size() - 1);
    }

    /**
     * Calculates and positions sprints and milestones
     * for the epic.
     * 
     * @throws CloneNotSupportedException when unable to clone a sprint
     */
    private void build() throws CloneNotSupportedException {
        int startSprint = prefs.getStartSprintNumber();
        
        Sprint s = new Sprint(startSprint);
        
        for (TimelineEpic te : getEpics()) {
            te.calculateSprints(
                    prefs, 
                    s.getNumber(),
                    s.getPoints()
            );
            
            if (te.getLastSprint() == null) {                
                continue;
            }
            
            addSprints(te.getSprints());
            
            s = getUniqueSprints().get(te.getLastSprint().getNumber());
            addReleases(te, s);
        }
    }
}
