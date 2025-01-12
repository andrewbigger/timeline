package com.biggerconcept.timeline.domain;

import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.domain.Task;
import com.biggerconcept.timeline.domain.Judgement.Assessment;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class DocumentTest {
    private Document document;
    private File testFile;

    @Before
    public void setUp() {
        document = new Document();
        testFile = new File("test.json");
        document.setFile(testFile);
    }
//
//    @Test
//    public void testLoad() throws IOException {
//        Document loadedDoc = Document.load(testFile);
//        assertEquals(document.getFile(), loadedDoc.getFile());
//    }

//    @Test
//    public void testSave() throws IOException {
//        document.save();
//        Document loadedDoc = Document.load(testFile);
//        assertEquals(document.getFile(), loadedDoc.getFile());
//    }

    @Test
    public void testGetFile() {
        assertEquals(testFile, document.getFile());
    }

    @Test
    public void testGetPreferences() {
        Preferences defaultPrefs = Preferences.defaultPreferences();
        document.setPreferences(defaultPrefs);
        assertEquals(defaultPrefs, document.getPreferences());
    }

    @Test
    public void testGetShelf() {
        ArrayList<Epic> shelf = document.getShelf();
        assertNotNull(shelf);
        assertTrue(shelf.isEmpty());
    }

    @Test
    public void testGetEpics() {
        ArrayList<Epic> epics = document.getEpics();
        assertNotNull(epics);
        assertTrue(epics.isEmpty());
    }

    @Test
    public void testGetJudgement() {
        Assessment defaultAssessment = Judgement.DEFAULT_ASSESSMENT;
        assertEquals(defaultAssessment, document.getJudgement());
    }

    @Test
    public void testGetNotes() {
        assertNull(document.getNotes());
    }

    @Test
    public void testGetReleases() {
        ArrayList<Release> releases = document.getReleases();
        assertNotNull(releases);
        assertTrue(releases.isEmpty());
    }

    @Test
    public void testGetLastEpicIdentifier() {
        assertEquals(0, document.getLastEpicIdentifier());
    }

    @Test
    public void testSetFile() {
        File newFile = new File("newFile.json");
        document.setFile(newFile);
        assertEquals(newFile, document.getFile());
    }

    @Test
    public void testSetPreferences() {
        Preferences newPrefs = new Preferences();
        document.setPreferences(newPrefs);
        assertEquals(newPrefs, document.getPreferences());
    }

    @Test
    public void testSetShelf() {
        ArrayList<Epic> newShelf = new ArrayList<>();
        document.setShelf(newShelf);
        assertEquals(newShelf, document.getShelf());
    }

    @Test
    public void testSetEpics() {
        ArrayList<Epic> newEpics = new ArrayList<>();
        document.setEpics(newEpics);
        assertEquals(newEpics, document.getEpics());
    }

    @Test
    public void testRemoveFromShelf() {
        Epic epic = new Epic();
        document.getShelf().add(epic);
        document.removeFromShelf(epic);
        assertFalse(document.getShelf().contains(epic));
    }

    @Test
    public void testCommitToEpic() {
        Epic epic = new Epic();
        document.commitToEpic(epic);
        assertTrue(document.getEpics().contains(epic));
        assertFalse(document.getShelf().contains(epic));
    }

    @Test
    public void testUnCommitToEpic() {
        Epic epic = new Epic();
        document.getEpics().add(epic);
        document.unCommitToEpic(epic);
        assertFalse(document.getEpics().contains(epic));
        assertTrue(document.getShelf().contains(epic));
    }


    // @Test
    // public void testSetJudgement() {  
    //     Assessment newAssessment = nnt(newAssessme  nt);
    //     assertEquals(newAssessment, doc  ument.getJudgement());
    // }

    @Test
    public void testSetNotes() {
        String newNotes = "New notes";
        document.setNotes(newNotes);
        assertEquals(newNotes, document.getNotes());
    }

    @Test
    public void testSetReleases() {
        ArrayList<Release> newReleases = new ArrayList<>();
        document.setReleases(newReleases);
        assertEquals(newReleases, document.getReleases());
    }

    @Test
    public void testDefaultTitle() {
        document.setFile(null);
        assertEquals("", document.title());
    }

    @Test
    public void testFileTitle() {
        File f = new File("test.json");
        document.setFile(f);
        assertEquals("test.json", document.title());
    }

    @Test
    public void testRebuildIdentifiers() {
        document.rebuildIdentifiers();
        // Add assertions here
    }

    @Test
    public void testFindEpicById() {
        UUID id = UUID.randomUUID();
        Epic epic = new Epic();
        epic.setId(id);
        document.getEpics().add(epic);
        assertEquals(epic, document.findEpicById(id));
    }

    @Test
    public void testCalculateShelfPoints() {
        com.biggerconcept.projectus.domain.Preferences prefs = new com.biggerconcept.projectus.domain.Preferences();
        int points = document.calculateShelfPoints(prefs);
        assertEquals(0, points);
    }

    @Test
    public void testAvailableEpicsForRelease() {
        ArrayList<Epic> availableEpics = document.availableEpicsForRelease();
        assertNotNull(availableEpics);
        assertTrue(availableEpics.isEmpty());
    }
}
