package com.biggerconcept.timeline.reports.resources;

import com.biggerconcept.sdk.serializers.documents.Doc;
import com.biggerconcept.sdk.serializers.documents.Doc.ParagraphType;
import com.biggerconcept.sdk.doctree.domain.Article;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.reports.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

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
     * @param nodeId linked node ID
     */
    public ArticleElement(State state, UUID nodeId) {
        super(state);
        this.type = ParagraphType.md;
        this.nodeId = nodeId;
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
    public void insertInto(Doc document, HashMap<String, String> vars, Node root) 
            throws IOException {
        Article article = root.findArticleById(getNodeId());

        if (article == null) {
            return;
        }

        document.md(compile(article.getContent(), vars));
    }
    
    /**
     * Overrides the getArgs method to provide preview of content
     * 
     * @return article content.
     */
    @Override
    public String previewContent() {
        try {
            Article article = getRoot().findArticleById(getNodeId());
            
            return article.getContent();
        } catch (Exception ex) {
            return getName();
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
