package com.biggerconcept.timeline.reports;

import com.biggerconcept.timeline.reports.paragraphs.Heading1Element;
import com.biggerconcept.timeline.reports.paragraphs.ParagraphElement;
import com.biggerconcept.timeline.reports.paragraphs.CodeElement;
import com.biggerconcept.timeline.reports.paragraphs.StrongParagraphElement;
import com.biggerconcept.timeline.reports.paragraphs.Heading4Element;
import com.biggerconcept.timeline.reports.paragraphs.Heading3Element;
import com.biggerconcept.timeline.reports.paragraphs.SubtitleParagraphElement;
import com.biggerconcept.timeline.reports.paragraphs.NewLineElement;
import com.biggerconcept.timeline.reports.paragraphs.PageBreakElement;
import com.biggerconcept.timeline.reports.paragraphs.TitleElement;
import com.biggerconcept.timeline.reports.paragraphs.Heading2Element;
import com.biggerconcept.timeline.reports.paragraphs.MarkdownElement;
import com.biggerconcept.timeline.reports.sections.ReleaseTimelineTableElement;
import com.biggerconcept.timeline.reports.sections.EpicsTimelineTableElement;
import com.biggerconcept.timeline.reports.sections.NotesElement;
import com.biggerconcept.timeline.reports.sections.EpicsTableElement;
import com.biggerconcept.timeline.reports.sections.EpicsOutlineElement;
import com.biggerconcept.timeline.reports.sections.TableOfContentsElement;
import com.biggerconcept.timeline.reports.sections.ReleaseTableElement;
import com.biggerconcept.timeline.reports.sections.ShelfTableElement;
import com.biggerconcept.timeline.reports.sections.ReleaseOutlineElement;
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
    @JsonSubTypes.Type(value = ShelfTableElement.class, name = "table"),
    @JsonSubTypes.Type(value = MarkdownElement.class, name="md"),
    @JsonSubTypes.Type(value = CodeElement.class, name="code")
})
public class Element
        extends com.biggerconcept.appengine.reports.elements.Element 
        implements Cloneable, IElement {
    
    /**
     * Constructs set of paragraphs, sections and variables for
     * the report builder.
     * 
     * @param state application state
     * 
     * @return source content
     */
    public static Content availableContent(State state) {
        Content content = new Content();
        
        content.addParagraph(new TitleElement(state));
        content.addParagraph(new Heading1Element(state));
        content.addParagraph(new Heading2Element(state));
        content.addParagraph(new Heading3Element(state));
        content.addParagraph(new Heading4Element(state));
        content.addParagraph(new SubtitleParagraphElement(state));
        content.addParagraph(new ParagraphElement(state));
        content.addParagraph(new CodeElement(state));
        content.addParagraph(new MarkdownElement(state));
        content.addParagraph(new StrongParagraphElement(state));
        content.addParagraph(new NewLineElement(state));
        content.addParagraph(new PageBreakElement(state));
        
        content.addSection(new TableOfContentsElement(state));
        content.addSection(new ShelfTableElement(state));
        content.addSection(new EpicsTableElement(state));
        content.addSection(new EpicsTimelineTableElement(state));
        content.addSection(new EpicsOutlineElement(state));
        content.addSection(new ReleaseTableElement(state));
        content.addSection(new ReleaseTimelineTableElement(state));
        content.addSection(new ReleaseOutlineElement(state));
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
        
        content.addVariable(
                "xs_task_size", 
                Variables.xsTaskSize(state)
        );
        
        content.addVariable(
                "s_task_size", 
                Variables.sTaskSize(state)
        );
        
        content.addVariable(
                "m_task_size", 
                Variables.mTaskSize(state)
        );
        
        content.addVariable(
                "l_task_size", 
                Variables.lTaskSize(state)
        );
        
        content.addVariable(
                "xl_task_size", 
                Variables.xlTaskSize(state)
        );
        
        content.addVariable(
                "sprint_length", 
                Variables.sprintLength(state)
        );
        
        return content;
    }
    
    /**
     * Pointer to application state.
     */
    @JsonIgnore
    private State state;
    
    /**
     * Default constructor.
     */
    public Element() {
        super();
    }
    
    /**
     * State based constructor.
     * 
     * @param state application state
     */
    public Element(State state) {
        super();
        this.state = state;
    }
    
    /**
     * Getter for state.
     * 
     * This is not to be serialized to document as it
     * will create circular dependencies.
     * 
     * @return application state
     */
    @JsonIgnore
    public State getState() {        
        return state;
    }
    
    /**
     * Getter for open document.
     * 
     * This will retrieve the open document from application state.
     * 
     * @return current document.
     */
    @JsonIgnore
    public Document getDocument() {
        return state.getOpenDocument();
    }
    
    /**
     * Setter for state.
     * 
     * @param value new application state
     */
    @JsonIgnore
    public void setState(State value) {
        state = value;
    }
    
    /**
     * Constructs and returns an editor dialog for the current element.
     *
     * This will throw an unsupported operation exception if not overridden
     * in the child element.
     * 
     * @param rb Application resource bundle
     * @param report Active report
     * @param content Report content
     * 
     * @return Editor dialog
     * 
     * @throws IOException when unable to read from file
     */
    @Override
    public IElementEditorDialog editorDialog(
            ResourceBundle rb, 
            IReport report,
            Content content
    ) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Insertion callback.
     * 
     * This will insert the element into the report document.
     * 
     * This will throw an UnsupportedOperationException if it is not
     * overridden in the child element.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException if unable to read file
     */
    @Override
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

}
