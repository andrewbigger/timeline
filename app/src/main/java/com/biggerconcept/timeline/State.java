package com.biggerconcept.timeline;

import com.biggerconcept.appengine.ui.helpers.Date;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Year;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 * Application State
 * 
 * @author Andrew Bigger
 */
public class State {
    private MainController mainController;
    private Document openDocument;
    private Epic openEpic;
    private Year viewYear;
    private Year startYear;
    private ResourceBundle bundle;
    
    public State(MainController controller, ResourceBundle rb) {
        mainController = controller;
        bundle = rb;
        openDocument = new Document();
        viewYear = Year.DEFAULT;
        startYear = new Year(
                Date.fromEpoch(openDocument.getPreferences().getStart())
        );
    }
    
    public Document getOpenDocument() {
        return openDocument;
    }
    
    public Epic getOpenEpic() {
        return openEpic;
    }
    
    public Year getViewYear() {
        return viewYear;
    }
    
    public Year getStartYear() {
        return startYear;
    }
    
    public void setOpenDocument(Document value) {
        openDocument = value;
    }
    
    public void setOpenEpic(Epic value) {
        openEpic = value;
    }
    
    public void setViewYear(Year value) {
        viewYear = value;
    }
    
    public MainController mainController() {
        return mainController;
    }
    
    public ResourceBundle bundle() {
        return bundle;
    }
    
    public void reset() {
        openDocument = new Document();
        viewYear = Year.DEFAULT;
    }
    
    public void nextYear() {
        viewYear = viewYear.next();
    }
    
    public void prevYear() {
        viewYear = viewYear.previous();
    }
    
    public void mapDocumentToWindow() {
        mainController().mapDocumentToWindow();
    }
    
    public void mapWindowToDocument() {
        mainController().mapWindowToDocument();
    }
    
    public void releaseOpenEpic() {
        openEpic = null;
    }
}
