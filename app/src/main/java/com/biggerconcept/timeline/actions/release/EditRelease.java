package com.biggerconcept.timeline.actions.release;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.domain.Release;
import com.biggerconcept.timeline.ui.dialogs.ReleaseDialog;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 * Opens edit release dialog for release modification
 * 
 * @author Andrew Bigger
 */
public class EditRelease implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {

        ObservableList<Release> items = state.mainController().releasesTableView
                .getSelectionModel()
                .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

        ReleaseDialog.open(
            state,
            items.get(0),
            state.getOpenDocument().getReleases(), 
            false
        );

        state.mapDocumentToWindow();
    }
}
