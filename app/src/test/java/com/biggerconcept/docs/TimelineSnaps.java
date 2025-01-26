package com.biggerconcept.docs;

import com.biggerconcept.appengine.ui.Theme;
import com.biggerconcept.appengine.ui.helpers.Date;
import com.biggerconcept.snapporazi.DocImg;
import com.biggerconcept.timeline.App;
import com.biggerconcept.timeline.MainController;
import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.projectus.domain.Epic;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static java.util.concurrent.TimeUnit.SECONDS;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Documentation images for Timeline.
 * 
 * @author Andrew Bigger
 */
public class TimelineSnaps extends ApplicationTest {
    /**
     * Image camera
     */
    private DocImg camera;
    
    /**
     * Root node
     */
    private Parent root;
    
    /**
     * Window
     */
    private Scene scene;
    
    /**
     * Window controller
     */
    private MainController controller;
    
    /**
     * Setup for documentation images.
     * 
     * @param stage application window
     * 
     * @throws Exception when there is an error setting up scene
     */
    @Override
    public void start(Stage stage) throws Exception {
        camera = new DocImg("timeline", getClass());
        
        ResourceBundle bundle = ResourceBundle.getBundle("strings", App.EN_US);
        
        URL location = getClass().getResource("/fxml/Main.fxml");
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(location);
        loader.setResources(bundle);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        
        root = loader.load();
        controller = (MainController) loader.getController();

        scene = new Scene(root);
        Theme.apply(getClass(), scene);
        
        stage.setScene(scene);
        stage.setWidth(1500);
        stage.setHeight(800);
        stage.show();
    }
    
    /**
     * Generates all documentation base images
     * 
     * @throws Exception when unable to perform action
     */
    @Test
    public void snap() throws Exception {
        snapEmptyWindow();
        snapWindow();
    }
    
    /**
     * Capture empty window.
     * 
     * @throws Exception when unable to perform action.
     */
    @Test
    public void snapEmptyWindow() throws Exception {
        camera.setFeature("empty");
        camera.capture("window");
    }
    
    /**
     * Capture window with example locale.
     * 
     * @throws Exception when unable to perform action
     */
    @Test
    public void snapWindow() throws Exception {
       loadExample();
       
       camera.setFeature("open-document");
       camera.capture("window");
    }
    
    /**
     * Loads example locale into window.
     */
    private void loadExample() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    File exampleFile = new File(
                            getClass()
                                    .getResource("/example.projectus")
                                    .getFile()
                    );

                    controller.openDocument(exampleFile);
                } catch (Exception ex) {
                    // do nothing
                }
            }
        });
            
        sleep(1, SECONDS);
    }

}
