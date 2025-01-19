package com.biggerconcept.timeline.actions.resources;

import com.biggerconcept.doctree.domain.Contact;
import com.biggerconcept.doctree.domain.Group;
import com.biggerconcept.doctree.domain.Node;
import com.biggerconcept.doctree.domain.Node.NodeType;
import com.biggerconcept.doctree.ui.dialogs.ContactDialog;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.IOException;
import javafx.stage.Stage;

/**
 * Add node action.
 * 
 * @author Andrew Bigger
 */
public class AddContact implements Action {
    public void perform(State state, Stage window) 
            throws IOException, CloneNotSupportedException {
        Node item = state.getSelectedDocumentNode();
        
        if (item.getType() != NodeType.GROUP) {
            return;
        }
        
        Contact newContact = new Contact();
        newContact.setCompany(
                state
                    .getOpenDocument()
                    .getPreferences()
                    .getDefaultCompany()
        );
        
        ContactDialog dialog = new ContactDialog(
                state.bundle(),
                newContact,
                (Group) item,
                state.getReportContent()
        );
        
        dialog.show(window);
    }
}
