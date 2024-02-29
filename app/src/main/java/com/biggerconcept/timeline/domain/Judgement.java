package com.biggerconcept.timeline.domain;

/**
 * Class that represents an assessment of a timeline
 * @author Andrew Bigger
 */
public class Judgement {
    public static Assessment DEFAULT_ASSESSMENT = Assessment.NONE;
    public static enum Assessment {
        NONE,
        ACCEPTED,
        REJECTED,
    }
}
