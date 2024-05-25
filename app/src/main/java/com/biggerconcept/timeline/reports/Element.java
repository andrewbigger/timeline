package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.reports.IReport;
import com.biggerconcept.appengine.reports.elements.Content;
import com.biggerconcept.appengine.reports.elements.IElement;
import com.biggerconcept.appengine.reports.ui.dialogs.IElementEditorDialog;
import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.timeline.domain.Document;
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
    
    public static Content availableContent() {
        Content content = new Content();
        
        content.addParagraph(new TitleElement());
        content.addParagraph(new Heading1Element());
        content.addParagraph(new Heading2Element());
        content.addParagraph(new Heading3Element());
        content.addParagraph(new Heading4Element());
        content.addParagraph(new SubtitleParagraphElement());
        content.addParagraph(new ParagraphElement());
        content.addParagraph(new StrongParagraphElement());
        content.addParagraph(new NewLineElement());
        content.addParagraph(new PageBreakElement());
        
        content.addSection(new TableOfContentsElement());
        content.addSection(new ShelfTableElement());
        content.addSection(new ReleaseTableElement());
        content.addSection(new NotesElement());
        
        return content;
    }
    
    private Document document;
    
    protected Document getDocument() {
        return document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public IElementEditorDialog editorDialog(ResourceBundle rb, IReport report) 
            throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }

}
