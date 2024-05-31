package com.biggerconcept.timeline.reports;

import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.ui.domain.Timeline;

/**
 * Resolves timeline specific variables
 * 
 * @author Andrew Bigger
 */

public class Variables {
    /**
     * Retrieves velocity (average points per sprint) from the
     * open document for use as a report variable.
     * 
     * If the velocity is unable to be calculated, a blank string will
     * be returned.
     * 
     * @param state application state
     * 
     * @return velocity as a string
     */
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
    
    /**
     * Retrieves used sprint count from the open document for use as a report
     * variable.
     * 
     * If the used sprint count is unable to be calculated, a blank string will
     * be returned.
     * 
     * @param state application state
     * 
     * @return used sprint count as a string
     */
    public static String usedSprints(State state) {
        try {
            return String.valueOf(
                    timeline(state).countUsedSprintsInYear(state.getViewYear())
            );
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Retrieves total sprint count from the open document for use as a report
     * variable.
     * 
     * If the total sprint count is unable to be calculated, a blank string will
     * be returned.
     * 
     * @param state application state
     * 
     * @return total sprint count as a string
     */
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

    /**
     * Retrieves committed point count from the open document for use as a 
     * report variable.
     * 
     * If the committed point count is unable to be calculated, a blank string 
     * will be returned.
     * 
     * @param state application state
     * 
     * @return committed point count as a string
     */
    public static String committedPoints(State state) {
        try {
            return String.valueOf(
                timeline(state).countUsedPointsInYear(state.getViewYear())
            );
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Retrieves available point count from the open document for use as a 
     * report variable.
     * 
     * If the available point count is unable to be calculated, a blank string 
     * will be returned.
     * 
     * @param state application state
     * 
     * @return available point count as a string
     */
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
    
    /**
     * Retrieves current view year name from the open document for use as a
     * report variable.
     * 
     * If the view year name is unable to be calculated, a blank string 
     * will be returned.
     * 
     * @param state application state
     * 
     * @return view year name as a string
     */
    public static String viewYear(State state) {
        return state.getViewYear().getName();
    }
    
    /**
     * Retrieves judgment from the open document for use as a report variable.
     * 
     * If the judgment is unable to be retrieved, a blank string 
     * will be returned.
     * 
     * @param state application state
     * 
     * @return judgment as a string
     */
    public static String judgement(State state) {
        return state.getOpenDocument().getJudgement().toString();
    }
    
    /**
     * Returns extra small task size number as a variable
     * 
     * @param state application state
     * 
     * @return extra small task size
     */
    public static String xsTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getExtraSmallTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns small task size number as a variable
     * 
     * @param state application state
     * 
     * @return small task size
     */
    public static String sTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getSmallTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns medium task size number as a variable
     * 
     * @param state application state
     * 
     * @return medium task size
     */
    public static String mTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getMediumTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns large task size number as a variable
     * 
     * @param state application state
     * 
     * @return large task size
     */
    public static String lTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getLargeTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns extra large task size number as a variable
     * 
     * @param state application state
     * 
     * @return extra large task size
     */
    public static String xlTaskSize(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getExtraLargeTaskSize()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Returns sprint length number as a variable
     * 
     * @param state application state
     * 
     * @return sprint length
     */
    public static String sprintLength(State state) {
        try {
            return String.valueOf(
                    state
                            .getOpenDocument()
                            .getPreferences()
                            .getSprintLength()
            );
        } catch (Exception ex) {
            return "";
        }
    }
    
    /**
     * Constructs a timeline.
     * 
     * When the state is not set, an empty timeline will be returned to avoid
     * a crash when writing the report.
     * 
     * @return epic timeline
     * 
     * @throws CloneNotSupportedException when unable to clone objects
     */
    private static Timeline timeline(State state) throws CloneNotSupportedException {
        return new Timeline(
                state.getOpenDocument().getEpics(),
                state.getOpenDocument().getReleases(),
                state.getOpenDocument().getPreferences()
        );
    }
}
