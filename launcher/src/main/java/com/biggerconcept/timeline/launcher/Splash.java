package com.biggerconcept.timeline.launcher;

import fxlauncher.FXManifest;
import fxlauncher.UIProvider;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Loader splash screen for launching the latest version of
 * the Timeline application.
 * 
 * This splash screen shows the title of the application and a progress
 * bar.
 * 
 * It checks for the latest version of the application on the distribution
 * server. When the current version installed is the latest, then the 
 * local version is loaded.
 * 
 * When the server has another, more up to date version, then that version
 * is pulled down and started.
 * 
 * @author Andrew Bigger
 */
public class Splash implements UIProvider {
    /**
     * String with the application name.
     */
    public static final String APP_NAME = "Timeline";
    
    /**
     * Status string while application is starting.
     */
    public static final String LOAD_STATUS = "Firing up...";
    
    /**
     * Status string while the application is updating.
     */
    public static final String UPDATE_STATUS = "Updating, please wait...";
    
    /**
     * Copyright text for splash screen footer.
     */
    public static final String FOOTER_TEXT = 
            "Bigger Concept";
            
    /**
     * Splash screen stage
     */
    private Stage stage;
    
    /**
     * Splash screen progress bar.
     * 
     * This will be determinate while updating, and indeterminate when 
     * starting the locally cached version of the application.
     */
    private ProgressBar updateProgress;
    
    /**
     * Initializes splash screen stage.
     * 
     * This sets the stage scene to the given stage. Splash screen
     * styles are applied, and the progress bar is initialized.
     * 
     * This overrides the init method of the fxlauncher.
     * 
     * @param stage 
     */
    @Override
    public void init(Stage stage) {
        this.stage = stage;
        stage
            .getScene()
            .getStylesheets()
            .add(getClass()
                .getResource("/styles/Styles.css")
                .toExternalForm()
            );
        
         updateProgress = new ProgressBar();
         updateProgress.setId("updateProgressBar");
    }
    
    /**
     * Builds splash screen.
     * 
     * This overrides the launcher default splash screen.
     * 
     * The splash screen is a border pane, with the header and
     * status indicator centered.
     * 
     * This overrides the createLoader method of the fxlauncher.
     * 
     * @return 
     */
    @Override
    public Parent createLoader() {
        stage.setTitle("Loading " + APP_NAME);
        
        BorderPane loadPane = buildSplashPane();
        
        loadPane.setCenter(
                buildHeader(
                        APP_NAME,
                        LOAD_STATUS,
                        buildLoadIndicator()
                )
        );
        
        return loadPane;
    }
    
    /**
     * Builds update splash screen.
     * 
     * This screen is shown when the application transitions to
     * an updating mode. It will be shown while the new version is
     * retrieved from the server.
     * 
     * The update splash is a border pane, similar to the splash screen
     * but with text indicating that an update is being applied.
     * 
     * @param manifest
     * @return 
     */
    @Override
    public Parent createUpdater(FXManifest manifest) {
        stage.setTitle("Updating " + APP_NAME);
        
        BorderPane updatePane = buildSplashPane();
        
        updatePane.setCenter(
                buildHeader(
                        APP_NAME,
                        UPDATE_STATUS,
                        updateProgress
                )
        );
        
        return updatePane;
    }

    /**
     * Sets the progress of the update.
     * 
     * This sets the progress of the update to the progress reported
     * by the underlying fxlauncher class.
     * 
     * @param progress 
     */
    @Override
    public void updateProgress(double progress) {
        updateProgress.setProgress(progress);
    }
    
        private BorderPane buildSplashPane() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("splash");
        
        root.setBottom(buildFooter());
        
        return root;
    }
    
    /**
     * Builds a splash screen load progress indicator.
     * 
     * By default this will be an indeterminate loader.
     * 
     * The ID is set to properly set the color of the loader.
     * 
     * @return 
     */
    private Control buildLoadIndicator() {
        ProgressIndicator loadIndicator = new ProgressIndicator();
        loadIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        loadIndicator.getStyleClass().add("prog");
        loadIndicator.setId("loadIndicator");
        
        return loadIndicator;
    }
    
    /**
     * Builds splash screen header VBox.
     * 
     * The header includes application information.
     * 
     * @param title
     * @param status
     * @param indicator
     * @return 
     */
    private VBox buildHeader(String title, String status, Control indicator) {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPrefSize(300, 500);
        
        BorderPane icon = new BorderPane();
        icon.setCenter(
                new ImageView(
                        new Image(
                                getClass()
                                        .getResourceAsStream("/SplashHeader.png")
                        )
                )
        );

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("title");
        titleLabel.setId("titleLabel");
        
        Label statusLabel = new Label(status);
        statusLabel.setId("statusLabel");
        
        header.getChildren().addAll(icon, titleLabel, statusLabel, indicator);
        
        return header;
    }
    
    /**
     * Builds splash screen footer.
     * 
     * Generally this holds the vendor name.
     * 
     * @return 
     */
    private VBox buildFooter() {
        VBox footer = new VBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPrefSize(300, 40);
        footer.getStyleClass().add("footer");
        
        Label footerLabel = new Label(FOOTER_TEXT);
        footerLabel.setId("footerLabel");
        
        footer.getChildren().add(footerLabel);
        
        return footer;
    }
}
