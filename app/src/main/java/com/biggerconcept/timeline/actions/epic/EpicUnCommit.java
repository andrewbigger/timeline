package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.ui.domain.TimelineEpic;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Remove commitment to epic
 * 
 * @author Andrew Bigger
 */
public class EpicUnCommit implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {
        ObservableList<TimelineEpic> items = state.mainController().epicTableView
                .getSelectionModel()
                .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

       for (TimelineEpic e : items) {
           state.getOpenDocument().unCommitToEpic(e.getEpic());
       }

       state.mapDocumentToWindow();
    }
}
