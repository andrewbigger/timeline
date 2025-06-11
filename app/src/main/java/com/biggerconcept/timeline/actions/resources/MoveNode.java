package com.biggerconcept.timeline.actions.resources;

import com.biggerconcept.sdk.doctree.domain.Article;
import com.biggerconcept.sdk.doctree.domain.Group;
import com.biggerconcept.sdk.doctree.domain.Node;
import com.biggerconcept.sdk.doctree.ui.dialogs.ArticleDialog;
import com.biggerconcept.sdk.doctree.ui.dialogs.GroupDialog;
import com.biggerconcept.sdk.doctree.ui.dialogs.MoveNodeDialog;
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
public class MoveNode implements Action {
    public void perform(State state, Stage window) throws IOException, CloneNotSupportedException {       
       Node item = state.getSelectedDocumentNode();
       
       if (item == state.getDocumentRoot()) {
           return;
       }
       
       Node parent = state.getSelectedDocumentNodeParent();
       
       MoveNodeDialog mn = new MoveNodeDialog(
               state.bundle(),
               item,
               parent,
               state.getDocumentRoot()
       );
       
       mn.show(window);
    }
}
