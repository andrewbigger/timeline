package com.biggerconcept.timeline.domain;

import org.junit.Before;
import org.junit.Test;

import com.biggerconcept.projectus.domain.Epic;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class ReleaseTest {
    private Release release;
    private ArrayList<UUID> epicIds;

    @Before
    public void setUp() {
        release = new Release();
        epicIds = new ArrayList<>();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(release.getId());
        assertEquals(0, release.getNumber());
        assertEquals("", release.getName());
        assertEquals("", release.getDescription());
        assertNotNull(release.getEpicIds());
        assertTrue(release.getEpicIds().isEmpty());
    }

    @Test
    public void testSettersAndGetters() {
        UUID id = UUID.randomUUID();
        int number = 1;
        String name = "Release 1";
        String description = "This is release 1";
        ArrayList<UUID> epicIds = new ArrayList<>();
        epicIds.add(UUID.randomUUID());

        release.setId(id);
        release.setNumber(number);
        release.setName(name);
        release.setDescription(description);
        release.setEpicIds(epicIds);

        assertEquals(id, release.getId());
        assertEquals(number, release.getNumber());
        assertEquals(name, release.getName());
        assertEquals(description, release.getDescription());
        assertEquals(epicIds, release.getEpicIds());
    }

    @Test
    public void testAddEpic() {
        Epic epic = new Epic();
        UUID epicId = epic.getId();

        release.addEpic(epic);

        assertTrue(release.getEpicIds().contains(epicId));
    }

    @Test
    public void testRemoveEpic() {
        Epic epic = new Epic();
        UUID epicId = epic.getId();

        release.addEpic(epic);
        release.removeEpic(epic);

        assertFalse(release.getEpicIds().contains(epicId));
    }

    @Test
    public void testEpics() {
        Document doc = new Document();
        Epic epic1 = new Epic();
        Epic epic2 = new Epic();
        doc.addEpic(epic1);
        doc.addEpic(epic2);

        release.addEpic(epic1);
        release.addEpic(epic2);

        ArrayList<Epic> expectedEpics = new ArrayList<>();
        expectedEpics.add(epic1);
        expectedEpics.add(epic2);

        assertEquals(expectedEpics, release.epics(doc));
    }

    @Test
    public void testLastEpicId() {
        Epic epic1 = new Epic();
        Epic epic2 = new Epic();

        release.addEpic(epic1);
        release.addEpic(epic2);

        assertEquals(epic2.getId(), release.lastEpicId());
    }
}
