package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.ui.dialogs.EpicDialog;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.ui.domain.TimelineEpic;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Opens edit epic dialog for epic modification
 * 
 * @author Andrew Bigger
 */
public class EditEpic implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {

        ObservableList<TimelineEpic> items = state.mainController().epicTableView
                .getSelectionModel()
                .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

        EpicDialog.open(
                state,
                items.get(0).getEpic(),
                state.getOpenDocument().getEpics(), 
                false
        );

        state.mapDocumentToWindow();
    }
}
