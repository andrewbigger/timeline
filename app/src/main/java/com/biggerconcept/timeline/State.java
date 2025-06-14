package com.biggerconcept.timeline;

import com.biggerconcept.sdk.reports.IReport;
import com.biggerconcept.sdk.reports.elements.Content;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Preferences;
import com.biggerconcept.timeline.domain.Year;
import com.biggerconcept.timeline.reports.Element;
import com.biggerconcept.timeline.reports.Report;
import java.util.ResourceBundle;
import javafx.scene.control.TreeItem;

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
    
    public State(MainController controller, ResourceBundle rb) {
        mainController = controller;
        bundle = rb;
        openDocument = new Document();
        viewYear = Year.DEFAULT;
        startYear = openDocument.getPreferences().startYear();
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
        return Element.availableContent(this);
    }
    
    public boolean hasSelectedPreferenceTab() {
        return getSelectedPreferenceTabName() != "";
    }
    
    public String getSelectedPreferenceTabName() {
        return selectedPreferenceTabName;
    }
    
    public void setOpenDocument(Document value) {
        openDocument = value;
        startYear = openDocument.getPreferences().startYear();
        viewYear = startYear;
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
        setReportDocument();
    }
    
    public void mapWindowToDocument() {
        mainController().mapWindowToDocument();
        setReportDocument();
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
    
    public void setReportDocument() {
        for (IReport r : openDocument.getPreferences().getReports()) {
            Report rpt = (Report) r;
            rpt.setState(this);
        }
    }
    
    public Node getSelectedDocumentNode() {
        TreeItem<Node> selected = (TreeItem) mainController().resourcesTreeView
                .getSelectionModel()
                .getSelectedItem();
        
        if (selected == null) {
            return getDocumentRoot();
        }
        
        return selected.getValue();
    }
    
    public Node getDocumentRoot() {
        return getOpenDocument().getResources();
    }
    
    public Node getSelectedDocumentNodeParent() {
        TreeItem<Node> selected = (TreeItem) mainController().resourcesTreeView
                .getSelectionModel()
                .getSelectedItem();
        
        TreeItem<Node> selectedParent = selected.getParent();
        
        Node parent = selectedParent.getValue();
        return parent;
    }

}
