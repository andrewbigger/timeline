package com.biggerconcept.timeline;

import com.biggerconcept.sdk.preferences.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import com.biggerconcept.sdk.ui.Theme;
import com.biggerconcept.sdk.ui.Ui;

import javafx.stage.FileChooser.ExtensionFilter;

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
     * File extension filter
     */
    public static final ExtensionFilter EXTENSION_FILTER = new ExtensionFilter(
            "JSON File",
             Arrays.asList("json")
    );
    
    /**
     * Starts Timeline
     * 
     * Loads the main view from resources, applies styles and sets the
     * window title and dimensions.
     * 
     * Then the stage will be shown.
     * 
     * @param stage main window
     * @throws IOException when unable to load fxml
     */
    @Override
    public void start(Stage stage) throws IOException {        
        ResourceBundle bundle = ResourceBundle.getBundle("strings", EN_US);
        
        Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/Main.fxml"),
                bundle
        );
        
        Scene scene = new Scene(root);
        
        if (config().isTrue("darkMode")) {
            Theme.applyDark(getClass(), scene);
        } else {
            Theme.apply(getClass(), scene);
        }

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        
        Ui.applyDefaultDimensions(stage);
        
        stage.show();
    }

    /**
     * Main
     * 
     * Launches Timeline FX application
     * 
     * @param args start args
     */
    public static void main(String[] args) {
        launch();
    }
    
    /**
     * Global application configuration
     * 
     * @return application configuration
     */
    public static Config config() {
        return Config.load("com.biggerconcept.Timeline");
    }

}
