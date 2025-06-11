package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.sdk.ui.dialogs.YesNoPrompt;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Removes epic from shelf
 * 
 * @author Andrew Bigger
 */
public class RemoveShelfEpic implements Action {
    public void perform(State state, Stage window) 
            throws NoChoiceMadeException, CloneNotSupportedException {
        ObservableList<Epic> items = state.mainController().shelfTableView
                .getSelectionModel()
                .getSelectedItems();

        if (items.isEmpty()) {
            throw new NoChoiceMadeException();
        }

        ButtonType answer = YesNoPrompt.show(
                Alert.AlertType.CONFIRMATION,
                state.bundle().getString(
                        "project.dialogs.removeEpic.title"
                ),

                state.bundle().getString(
                        "project.dialogs.removeEpic.description"
                )
        );

        if (answer == ButtonType.YES) {
            for (Epic e : items) {
                state.getOpenDocument().removeFromShelf(e);
            }
        }

        state.mapDocumentToWindow();
    }
}
