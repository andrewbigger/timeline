package com.biggerconcept.timeline.actions.resources;

import com.biggerconcept.doctree.domain.Article;
import com.biggerconcept.doctree.domain.Contact;
import com.biggerconcept.doctree.domain.Group;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.doctree.ui.dialogs.ArticleDialog;
import com.biggerconcept.doctree.ui.dialogs.ContactDialog;
import com.biggerconcept.doctree.ui.dialogs.GroupDialog;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.IOException;
import java.util.UUID;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * Edit node group action.
 * 
 * @author Andrew Bigger
 */
public class EditNode implements Action {
    public void perform(State state, Stage window) throws IOException, CloneNotSupportedException {       
       Node item = state.getSelectedDocumentNode();
       
       if (item == state.getDocumentRoot()) {
           return;
       }
       
       Node parent = state.getSelectedDocumentNodeParent();
        
        switch (item.getType()) {
            case GROUP:
                GroupDialog gd = new GroupDialog(
                        state.bundle(),
                        (Group) item,
                        (Group) parent
                );
                
                gd.show(window);
                
                break;
            case ARTICLE:                
                ArticleDialog ad = new ArticleDialog(
                        state.bundle(),
                        (Article) item,
                        (Group) parent
                );
                
                ad.show(window);
                break;
            case CONTACT:
                ContactDialog cd = new ContactDialog(
                
                        state.bundle(),
                        (Contact) item,
                        (Group) parent,
                        state.getReportContent()
                );
                
                cd.show(window);
                break;
        }
    }
}
