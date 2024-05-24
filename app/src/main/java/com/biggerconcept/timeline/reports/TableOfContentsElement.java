package com.biggerconcept.timeline.reports;

import com.biggerconcept.appengine.serializers.documents.Doc;

/**
 * Inserts a table of contents into a report
 * 
 * @author Andrew Bigger
 */
public class TableOfContentsElement extends Element {
    public TableOfContentsElement() {
        super();
        this.type = Doc.ParagraphType.toc;
    }
    
    public void insertInto(Doc document) {
        document.toc();
    }
    
    public boolean modifiable() {
        return false;
    }
}
