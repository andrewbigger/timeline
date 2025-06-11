package com.biggerconcept.timeline.actions.release;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.domain.Release;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Handles removal of a release
 * 
 * @author Andrew Bigger
 */
public class RemoveRelease implements Action {
    public void perform(State state, Stage window) throws NoChoiceMadeException {
        ObservableList<Release> items = state.mainController().releasesTableView
                .getSelectionModel()
                .getSelectedItems();
        
        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }
        
        ButtonType answer = com.biggerconcept.sdk.ui.dialogs.YesNoPrompt.show(
                Alert.AlertType.CONFIRMATION,
                state.bundle().getString("releases.dialogs.remove.title"),
                state.bundle().getString("releases.dialogs.remove.description")
        );
        
        if (answer == ButtonType.YES) {
            for (Release r : items) {
                state.getOpenDocument().getReleases().remove(r);
            }
        }
    }
}
