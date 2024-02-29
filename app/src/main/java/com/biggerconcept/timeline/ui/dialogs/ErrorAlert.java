package com.biggerconcept.timeline.ui.dialogs;

import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A standard alert box for controller errors
 * 
 * @author Andrew Bigger
 */
public class ErrorAlert {
    /**
     * Shows an error alert from a given exception.The action gives the alert the ability to display context about
 what controller action has failed.
     * 
     * The alert text will be the exception message.
     * 
     * @param bundle
     * @param message
     * @param ex 
     */
    public static void show(
            ResourceBundle bundle,
            String message,
            Exception ex
    ) {
       show(
               bundle.getString("errors.alert.title"), 
               message, 
               ex.getMessage()
       );
    }
    
    /**
     * Shows an error alert.
     * 
     * This sets the title, header text and content to the given
     * values.
     * 
     * @param title
     * @param header
     * @param content 
     */
    public static void show(
            String title,
            String header,
            String content
    ) {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
