package com.biggerconcept.timeline.actions.resources;

import com.biggerconcept.sdk.doctree.domain.Node;
import static com.biggerconcept.sdk.doctree.domain.Node.NodeType.GROUP;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.stage.Stage;

/**
 * Remove node action.
 * 
 * @author Andrew Bigger
 */
public class RemoveNode implements Action {
    public void perform(State state, Stage window) throws CloneNotSupportedException {
        Node item = state.getSelectedDocumentNode();
        
        if (item == null) {
            return;
        }
        
        if (item == state.getDocumentRoot()) {
            return;
        }
        
        Node parent = state.getSelectedDocumentNodeParent();
       
        if (parent == null) {
            return;
        }
        
        switch (item.getType()) {
            case GROUP:
                parent.remove(item);
                break;
            case ARTICLE:
                parent.remove(item);
                break;
            case CONTACT:
                parent.remove(item);
                break;
        }
    }
}
