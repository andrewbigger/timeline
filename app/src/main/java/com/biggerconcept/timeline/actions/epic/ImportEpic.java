package com.biggerconcept.timeline.actions.epic;

import com.biggerconcept.sdk.exceptions.NoChoiceMadeException;
import com.biggerconcept.sdk.ui.dialogs.OpenFileDialog;
import com.biggerconcept.projectus.domain.Epic;
import com.biggerconcept.projectus.ui.dialogs.EpicChooserDialog;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.State;
import com.biggerconcept.timeline.actions.Action;
import java.io.File;
import java.io.IOException;
import javafx.stage.Stage;

/**
 * Imports epic from Projectus Document
 * 
 * @author Andrew Bigger
 */
public class ImportEpic implements Action {
   public void perform(State state, Stage window) 
           throws NoChoiceMadeException, IOException, CloneNotSupportedException {
       File documentFile = OpenFileDialog.show(
                state.bundle().getString("dialogs.open.title"),
                window,
                App.EXTENSION_FILTER
        );

        com.biggerconcept.projectus.domain.Document doc = 
                com.biggerconcept.projectus.domain.Document.load(
                        documentFile
                );

        EpicChooserDialog chooser = new EpicChooserDialog(
                state.bundle(),
                doc.getEpics()
        );

        Epic chosen = chooser.show(window);
        
        state.getOpenDocument().getShelf().add(chosen);
        state.mapDocumentToWindow();
   } 
}
