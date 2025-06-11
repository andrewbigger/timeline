package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.ui.dialogs.EpicDialog;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Opens edit epic dialog for epic modification
 * 
 * @author Andrew Bigger
 */
public class EditShelfEpic implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {

        ObservableList<Epic> items = state.mainController().shelfTableView
                .getSelectionModel()
                .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

        EpicDialog.open(
            state,
            items.get(0),
            state.getOpenDocument().getShelf(), 
            false
        );

        state.mapDocumentToWindow();
    }
}
