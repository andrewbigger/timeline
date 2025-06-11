package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Move epic priority down
 * 
 * @author Andrew Bigger
 */
public class MoveEpicDown implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {
        ObservableList<Epic> items = state.mainController().epicTableView
                    .getSelectionModel()
                    .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

        int selectedIndex = state.mainController().epicTableView
                .getItems()
                .indexOf(items.get(0));

        int targetIndex = selectedIndex + 1;

        if (targetIndex > state
                .mainController()
                .epicTableView
                .getItems()
                .size() - 1) {
            throw new NoChoiceMadeException();
        }

        Collections.swap(
                state.getOpenDocument().getEpics(),
                selectedIndex,
                targetIndex
        );

        state.mapDocumentToWindow();
        
        state.mainController().epicTableView.getSelectionModel().select(targetIndex);
    }
}
