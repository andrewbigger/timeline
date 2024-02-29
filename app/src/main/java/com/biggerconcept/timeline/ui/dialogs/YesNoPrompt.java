package com.biggerconcept.timeline.ui.dialogs;

import com.biggerconcept.timeline.exceptions.NoChoiceMadeException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Standard yes/no choice dialog.
 * 
 * @author Andrew Bigger
 */
public class YesNoPrompt {
    /**
     * Creates and displays a yes no dialog box.
     * 
     * This dialog is for asking a user a yes or no question.
     * 
     * The dialog is created and shown, and the result is returned to the user.
     * 
     * If there is no choice made then a NoChoiceException will be thrown.
     * 
     * @param type
     * @param header
     * @param question
     * @return
     * @throws NoChoiceMadeException 
     */
    public static ButtonType show(
            Alert.AlertType type,
            String header, 
            String question
    ) 
            throws NoChoiceMadeException {
        
        Alert confirm = new Alert(
                type,
                question,
                ButtonType.YES, ButtonType.NO);
        
        confirm.setHeaderText(header);
        confirm.showAndWait();
        
        if (confirm.getResult() == ButtonType.NO) {
            throw new NoChoiceMadeException();
        }
        
        return confirm.getResult();
    }
}
