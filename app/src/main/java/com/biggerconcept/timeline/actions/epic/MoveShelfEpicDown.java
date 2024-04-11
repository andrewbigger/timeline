package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Move shelf epic priority down
 * 
 * @author Andrew Bigger
 */
public class MoveShelfEpicDown implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {
        ObservableList<Epic> items = state.mainController().shelfTableView
                    .getSelectionModel()
                    .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

        int selectedIndex = state.mainController().shelfTableView
                .getItems()
                .indexOf(items.get(0));

        int targetIndex = selectedIndex + 1;

        if (targetIndex > state.mainController().shelfTableView.getItems().size() - 1) {
            throw new NoChoiceMadeException();
        }

        Collections.swap(
                state.getOpenDocument().getShelf(),
                selectedIndex,
                targetIndex
        );

        state.mapDocumentToWindow();
    }
}
