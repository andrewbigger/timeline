package com.biggerconcept.timeline.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class JudgementTest {
    @Test
    public void testDefaultAssessment() {
        assertEquals(Judgement.Assessment.NONE, Judgement.DEFAULT_ASSESSMENT);
    }

    @Test
    public void testAssessmentEnumValues() {
        assertEquals(3, Judgement.Assessment.values().length);
        assertArrayEquals(new Judgement.Assessment[] {
                Judgement.Assessment.NONE,
                Judgement.Assessment.ACCEPTED,
                Judgement.Assessment.REJECTED
        }, Judgement.Assessment.values());
    }
}