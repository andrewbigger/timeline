package com.biggerconcept.timeline.actions.release;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.domain.Release;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Move epic priority down
 * 
 * @author Andrew Bigger
 */
public class MoveReleaseDown implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {
        ObservableList<Release> items = state.mainController().releasesTableView
                    .getSelectionModel()
                    .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

        int selectedIndex = state.mainController().releasesTableView
                .getItems()
                .indexOf(items.get(0));

        int targetIndex = selectedIndex + 1;

        if (targetIndex > state
                .mainController()
                .releasesTableView
                .getItems()
                .size() - 1) {
            throw new NoChoiceMadeException();
        }

        Collections.swap(
                state.getOpenDocument().getReleases(),
                selectedIndex,
                targetIndex
        );

        state.mapDocumentToWindow();
        
        state
                .mainController()
                .releasesTableView
                .getSelectionModel()
                .select(targetIndex);
    }
}
