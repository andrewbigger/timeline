package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.reports.elements.IElement;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.domain.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Element is a portion of a report
 * 
 * @author Andrew Bigger
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ParagraphElement.class, name = "p"),
    @JsonSubTypes.Type(value = Heading1Element.class, name = "h1"),
    @JsonSubTypes.Type(value = Heading2Element.class, name = "h2"),
    @JsonSubTypes.Type(value = Heading3Element.class, name = "h3"),
    @JsonSubTypes.Type(value = Heading4Element.class, name = "h4"),
    @JsonSubTypes.Type(value = NewLineElement.class, name = "nl"),
    @JsonSubTypes.Type(value = PageBreakElement.class, name = "br"),
    @JsonSubTypes.Type(value = StrongParagraphElement.class, name = "strong"),
    @JsonSubTypes.Type(value = SubtitleParagraphElement.class, name = "subtitle"),
    @JsonSubTypes.Type(value = TitleElement.class, name = "title"),
    @JsonSubTypes.Type(value = TableOfContentsElement.class, name = "toc"),
    @JsonSubTypes.Type(value = ShelfTableElement.class, name = "table")
})
public class Element
        extends com.biggerconcept.appengine.reports.elements.Element 
        implements Cloneable, IElement {
    
    public static Content availableContent(State state) {
        Content content = new Content();
        
        content.addParagraph(new TitleElement(state));
        content.addParagraph(new Heading1Element(state));
        content.addParagraph(new Heading2Element(state));
        content.addParagraph(new Heading3Element(state));
        content.addParagraph(new Heading4Element(state));
        content.addParagraph(new SubtitleParagraphElement(state));
        content.addParagraph(new ParagraphElement(state));
        content.addParagraph(new StrongParagraphElement(state));
        content.addParagraph(new NewLineElement(state));
        content.addParagraph(new PageBreakElement(state));
        
        content.addSection(new TableOfContentsElement(state));
        content.addSection(new ShelfTableElement(state));
        content.addSection(new EpicsTableElement(state));
        content.addSection(new EpicsTimelineTableElement(state));
        content.addSection(new ReleaseTableElement(state));
        content.addSection(new ReleaseTimelineTableElement(state));
        content.addSection(new NotesElement(state));
        
        content.addVariable(
                "velocity", 
                Variables.velocity(state)
        );
        
        content.addVariable(
                "used_sprints", 
                Variables.usedSprints(state)
        );
        
        content.addVariable(
                "total_sprints",
                Variables.totalSprints(state)
        );
        
        content.addVariable(
                "committed_points", 
                Variables.committedPoints(state)
        );
        
        content.addVariable(
                "available_points", 
                Variables.availablePoints(state)
        );
        
        content.addVariable(
                "judgement", 
                Variables.judgement(state)
        );
        
        content.addVariable(
                "view_year", 
                Variables.viewYear(state)
        );
        
        return content;
    }
    
    @JsonIgnore
    private State state;
    
    public Element() {
        super();
    }
    
    public Element(State state) {
        super();
        this.state = state;
    }
    
    @JsonIgnore
    public State getState() {        
        return state;
    }
    
    @JsonIgnore
    public Document getDocument() {
        return state.getOpenDocument();
    }
    
    @JsonIgnore
    public void setState(State value) {
        state = value;
    }
    
    @Override
    public IElementEditorDialog editorDialog(
            ResourceBundle rb, 
            IReport report,
            Content content
    ) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

}
