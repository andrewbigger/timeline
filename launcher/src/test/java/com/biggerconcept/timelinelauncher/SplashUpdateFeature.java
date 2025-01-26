package com.biggerconcept.timelinelauncher;

import fxlauncher.FXManifest;
import static java.util.concurrent.TimeUnit.SECONDS;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import com.biggerconcept.timeline.launcher.Splash;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class SplashUpdateFeature extends ApplicationTest {
    
    @Override
    public void start(Stage stage) throws Exception {   
        StackPane root = new StackPane();
        stage.setScene(new Scene(root));

        Splash ui = new Splash();
        ui.init(stage);
        ui.createLoader();

        Parent updater = ui.createUpdater(new FXManifest());
        root.getChildren().addAll(updater);

        stage.show();
    }
    
    @Test
    public void display_UpdateScreen() throws Exception {
        System.out.println("Show update splash screen");
       
        sleep(1, SECONDS);
    }
    
    @Test
    public void test_SplashHeaderContents() {
        System.out.println("Splash screen has expected content in header");
        
        verifyThat("#titleLabel", hasText(Splash.APP_NAME));
        verifyThat("#statusLabel", hasText(Splash.UPDATE_STATUS));
    }
    
    @Test
    public void test_SplashProgressIndicator() {
        System.out.println("Splash screen has progress indicator");
        
        verifyThat("#updateProgressBar", isVisible());
    }
    
    @Test
    public void test_SplashFooterContents() {
        System.out.println("Splash screen has expected content in footer");
        
        verifyThat("#footerLabel", hasText(Splash.FOOTER_TEXT));
    }

}
