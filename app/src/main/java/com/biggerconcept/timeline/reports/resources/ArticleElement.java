package com.biggerconcept.timeline.reports.resources;

import com.biggerconcept.appengine.serializers.documents.Doc;
import com.biggerconcept.appengine.serializers.documents.Doc.ParagraphType;
import com.biggerconcept.doctree.domain.Article;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.HashMap;

/**
 * Insert linked node into a report
 * 
 * @author Andrew Bigger
 */
public class ArticleElement extends Element {
    /**
     * Default constructor
     */
    public ArticleElement() {
        super();
    }
    
    /**
     * Application state constructor
     * 
     * @param state application state
     */
    public ArticleElement(State state) {
        super(state);
        this.type = ParagraphType.md;
    }
    
    /**
     * Full constructor.
     * 
     * @param state application state
     * @param node linked node
     */
    public ArticleElement(State state, Node node) {
        super(state);
        this.type = ParagraphType.md;
        this.node = node;
    }
    
    /**
     * Inserts markdown paragraphs into a report document.
     * 
     * @param document report document
     * @param vars content variables
     * 
     * @throws IOException when unable to write file
     */
    @Override
    public void insertInto(Doc document, HashMap<String, String> vars) 
            throws IOException {
        try {
            Article article = (Article) this.node;
            document.md(compile(article.getContent(), vars));
        } catch(Exception ex) {
            return;
        }
    }
    
    /**
     * Modifiable indicator.
     * 
     * Articles are linked to nodes, so they should not be modifiable.
     * 
     * @return modifiable
     */
    public boolean modifiable() {
        return false;
    }
    
}
