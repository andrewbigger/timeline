package com.biggerconcept.timeline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Timeline application
 * 
 * @author Andrew Bigger
 */
public class App extends Application {
    /**
     * Default application locale.
     * 
     * This is for hard coding the locale into en US.
     */
    public static final Locale EN_US = new Locale("en", "US");

    /**
     * Application help and support website.
     */
    public static final String HELP_URL = "https://docs.biggerconcept.com";
    
    /**
     * Starts Timeline
     * 
     * Loads the main view from resources, applies styles and sets the
     * window title and dimensions.
     * 
     * Then the stage will be shown.
     * 
     * @param stage
     * @throws IOException 
     */
    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("strings", EN_US);
        
        Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/Main.fxml"),
                bundle
        );
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/fxml/Main.css");
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.setMinHeight(600);
        stage.setMinWidth(901);
        stage.show();
    }

    /**
     * Main
     * 
     * Launches Timeline FX application
     * 
     * @param args 
     */
    public static void main(String[] args) {
        launch();
    }

}
