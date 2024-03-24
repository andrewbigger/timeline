package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.ui.dialogs.OpenFileDialog;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.ui.dialogs.EpicChooserDialog;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.ui.domain.TimelineEpic;
import java.io.File;
import java.io.IOException;
import javafx.stage.Stage;

/**
 * Exports epic to Projectus Document
 * 
 * @author Andrew Bigger
 */
public class ExportEpic implements Action {
   public void perform(State state, Stage window) 
           throws NoChoiceMadeException, IOException {
        TimelineEpic selected = (TimelineEpic) state
                .mainController()
                .epicTableView
                .getSelectionModel()
                .getSelectedItem();
        
        Epic chosen;
        
        if (selected == null) {
            EpicChooserDialog chooser = new EpicChooserDialog(
                    state.bundle(),
                    state.getOpenDocument().getEpics()
            );

            chosen = chooser.show(window);
        } else {
            chosen = selected.getEpic();
        }
        
        File documentFile = OpenFileDialog.show(
                state.bundle().getString("dialogs.open.title"),
                window,
                App.EXTENSION_FILTER
        );

        com.biggerconcept.projectus.domain.Document doc = 
                com.biggerconcept.projectus.domain.Document.load(
                        documentFile
                );

        doc.addEpic(chosen);
        doc.save();
   } 
}
