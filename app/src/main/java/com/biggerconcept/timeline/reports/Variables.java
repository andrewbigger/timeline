package com.biggerconcept.timeline.reports;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.ui.domain.Timeline;

/**
 * Resolves timeline specific variables
 * 
 * @author Andrew Bigger
 */

public class Variables {
    public static String velocity(State state) {
        try {
            return String.valueOf(
                state
                    .getOpenDocument()
                    .getPreferences()
                    .calculateAveragePointsPerSprint()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    public static String usedSprints(State state) {
        try {
            return String.valueOf(
                    timeline(state).countUsedSprintsInYear(state.getViewYear())
            );
        } catch (Exception ex) {
            return "";
        }
    }

    public static String totalSprints(State state) {
        try {
            int availableSprints = state.getViewYear().countAvailableSprints(
                state.getOpenDocument().getPreferences()
            );
            
            return String.valueOf(availableSprints);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String committedPoints(State state) {
        try {
            return String.valueOf(
                timeline(state).countUsedPointsInYear(state.getViewYear())
            );
        } catch (Exception ex) {
            return "";
        }
    }

    public static String availablePoints(State state) {
        try {
            int points = state.getViewYear().countAvailablePoints(
                    state.getOpenDocument().getPreferences()
            );
            
            return String.valueOf(points);
        } catch (Exception ex) {
            return "";
        }
    }
    
    public static String viewYear(State state) {
        return state.getViewYear().getName();
    }
    
    public static String judgement(State state) {
        return state.getOpenDocument().getJudgement().toString();
    }
    
    private static Timeline timeline(State state) throws CloneNotSupportedException {
        return new Timeline(
                state.getOpenDocument().getEpics(),
                state.getOpenDocument().getReleases(),
                state.getOpenDocument().getPreferences()
        );
    }
}
