package com.biggerconcept.timeline.actions.docs;

import com.biggerconcept.doctree.domain.Group;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.doctree.domain.Node.NodeType;
import com.biggerconcept.doctree.ui.dialogs.GroupDialog;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.IOException;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * Add node action.
 * 
 * @author Andrew Bigger
 */
public class AddGroup implements Action {
    public void perform(State state, Stage window) 
            throws IOException, CloneNotSupportedException {
        Node item = state.getSelectedDocumentNode();
        
        if (item.getType() != NodeType.GROUP) {
            return;
        }
        
        GroupDialog dialog = new GroupDialog(
                state.bundle(),
                new Group(""), 
                (Group) item
        );
        
        dialog.show(window);
    }
}
