package com.biggerconcept.timeline;

import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.ui.helpers.Date;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import java.util.ResourceBundle;

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
    private String selectedPreferenceTabName;
    private boolean showCounts;
    private Content reportContent;
    
    public State(MainController controller, ResourceBundle rb) {
        mainController = controller;
        bundle = rb;
        openDocument = new Document();
        viewYear = Year.DEFAULT;
        startYear = new Year(
                Date.fromEpoch(openDocument.getPreferences().getStart()),
                openDocument.getPreferences().getStartSprintNumber()
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
    
    public boolean getShowCounts() {
        return showCounts;
    }
    
    public Content getReportContent() {
        if (reportContent == null) {
            reportContent = defaultReportContent();
        }
        
        return reportContent;
    }
    
    public boolean hasSelectedPreferenceTab() {
        return getSelectedPreferenceTabName() != "";
    }
    
    public String getSelectedPreferenceTabName() {
        return selectedPreferenceTabName;
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
    
    public void setShowCounts(boolean value) {
        showCounts = value;
    }
    
    public void setReportContent(Content value) {
        reportContent = value;
    }
    
    public void setSelectedPreferenceTabName(String value) {
        selectedPreferenceTabName = value;
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
    
    public void nextYear(Preferences prefs) {
        viewYear = viewYear.next(prefs);
    }
    
    public void prevYear(Preferences prefs) {
        viewYear = viewYear.previous(prefs);
    }
    
    public void mapDocumentToWindow() throws CloneNotSupportedException {
        mainController().mapDocumentToWindow();
    }
    
    public void mapWindowToDocument() {
        mainController().mapWindowToDocument();
    }
    
    public void releaseOpenEpic() {
        openEpic = null;
    }
    
    public void releaseSelectedPreferenceTab() {
        setSelectedPreferenceTabName("");
    }
    
    public void toggleCounts() {
        showCounts = !showCounts;
    }
    
    private Content defaultReportContent() {
        Content content = new Content();
        
        // TODO: Add timeline sections here
        
        return content;
    }
}
