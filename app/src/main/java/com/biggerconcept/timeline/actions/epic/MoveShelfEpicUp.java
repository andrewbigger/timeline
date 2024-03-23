package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Move epic priority up
 * 
 * @author Andrew Bigger
 */
public class MoveShelfEpicUp implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException {
         ObservableList<Epic> items = state.mainController().shelfTableView
                        .getSelectionModel()
                        .getSelectedItems();

            if (items.isEmpty()) {
                throw new NoChoiceMadeException();
            }

            int selectedIndex = state.mainController().shelfTableView
                    .getItems()
                    .indexOf(items.get(0));

            int targetIndex = selectedIndex - 1;

            if (targetIndex < 0) {
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
