package com.biggerconcept.timeline.domain;

import org.junit.Before;
import org.junit.Test;

import com.biggerconcept.appengine.ui.helpers.Date;
import com.biggerconcept.projectus.domain.Size;

import java.time.LocalDate;
import static org.junit.Assert.*;

public class PreferencesTest {
    private Preferences preferences;

    @Before
    public void setUp() {
        preferences = Preferences.defaultPreferences();
    }

    @Test
    public void testDefaultPreferences() {
        assertEquals(Size.DEFAULT_XS_TASK_SIZE, preferences.getExtraSmallTaskSize());
        assertEquals(Size.DEFAULT_S_TASK_SIZE, preferences.getSmallTaskSize());
        assertEquals(Size.DEFAULT_M_TASK_SIZE, preferences.getMediumTaskSize());
        assertEquals(Size.DEFAULT_L_TASK_SIZE, preferences.getLargeTaskSize());
        assertEquals(Size.DEFAULT_XL_TASK_SIZE, preferences.getExtraLargeTaskSize());
        assertEquals(Preferences.DEFAULT_REF_SPRINT_ONE, preferences.getRefSprintOne());
        assertEquals(Preferences.DEFAULT_REF_SPRINT_TWO, preferences.getRefSprintTwo());
        assertEquals(Preferences.DEFAULT_REF_SPRINT_THREE, preferences.getRefSprintThree());
        assertEquals(Preferences.DEFAULT_REF_SPRINT_FOUR, preferences.getRefSprintFour());
        assertEquals(Preferences.DEFAULT_SPRINT_LENGTH, preferences.getSprintLength());
        assertEquals(Preferences.DEFAULT_START_SPRINT_NUMBER, preferences.getStartSprintNumber());
    }

    @Test
    public void testEstimateFor() {
        assertEquals(Size.DEFAULT_XS_TASK_SIZE, preferences.estimateFor(Size.TaskSize.XS));
        assertEquals(Size.DEFAULT_S_TASK_SIZE, preferences.estimateFor(Size.TaskSize.S));
        assertEquals(Size.DEFAULT_M_TASK_SIZE, preferences.estimateFor(Size.TaskSize.M));
        assertEquals(Size.DEFAULT_L_TASK_SIZE, preferences.estimateFor(Size.TaskSize.L));
        assertEquals(Size.DEFAULT_XL_TASK_SIZE, preferences.estimateFor(Size.TaskSize.XL));
    }

    @Test
    public void testGetStart() {
        long currentEpoch = Date.toEpoch(LocalDate.now());
        assertEquals(currentEpoch, preferences.getStart());
    }

    @Test
    public void testGetStartSprintNumber() {
        assertEquals(Preferences.DEFAULT_START_SPRINT_NUMBER, preferences.getStartSprintNumber());
    }

    @Test
    public void testGetRefSprintOne() {
        assertEquals(Preferences.DEFAULT_REF_SPRINT_ONE, preferences.getRefSprintOne());
    }

    @Test
    public void testGetRefSprintTwo() {
        assertEquals(Preferences.DEFAULT_REF_SPRINT_TWO, preferences.getRefSprintTwo());
    }

    @Test
    public void testGetRefSprintThree() {
        assertEquals(Preferences.DEFAULT_REF_SPRINT_THREE, preferences.getRefSprintThree());
    }

    @Test
    public void testGetRefSprintFour() {
        assertEquals(Preferences.DEFAULT_REF_SPRINT_FOUR, preferences.getRefSprintFour());
    }

    @Test
    public void testGetSprintLength() {
        assertEquals(Preferences.DEFAULT_SPRINT_LENGTH, preferences.getSprintLength());
    }

    @Test
    public void testGetReports() {
        assertNotNull(preferences.getReports());
        assertEquals(0, preferences.getReports().size());
    }

    @Test
    public void testSetExtraSmallSize() {
        int newSize = 5;
        preferences.setExtraSmallSize(newSize);
        assertEquals(newSize, preferences.getExtraSmallTaskSize());
    }

    @Test
    public void testSetSmallSize() {
        int newSize = 10;
        preferences.setSmallSize(newSize);
        assertEquals(newSize, preferences.getSmallTaskSize());
    }

    @Test
    public void testSetMediumSize() {
        int newSize = 15;
        preferences.setMediumSize(newSize);
        assertEquals(newSize, preferences.getMediumTaskSize());
    }

    @Test
    public void testSetLargeSize() {
        int newSize = 20;
        preferences.setLargeSize(newSize);
        assertEquals(newSize, preferences.getLargeTaskSize());
    }
}
