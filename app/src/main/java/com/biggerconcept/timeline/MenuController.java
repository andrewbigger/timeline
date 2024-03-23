package com.biggerconcept.timeline;

import com.biggerconcept.appengine.exceptions.NoChoiceMadeException;
import com.biggerconcept.appengine.platform.OperatingSystem;
import com.biggerconcept.appengine.ui.dialogs.ErrorAlert;
import com.biggerconcept.timeline.actions.Action;
import com.biggerconcept.timeline.actions.application.ExitApplication;
import com.biggerconcept.timeline.actions.application.OpenAboutDialog;
import com.biggerconcept.timeline.actions.application.OpenHelpPage;
import com.biggerconcept.timeline.actions.application.OpenPreferences;
import com.biggerconcept.timeline.actions.document.CreateDocument;
import com.biggerconcept.timeline.actions.document.OpenDocument;
import com.biggerconcept.timeline.actions.document.SaveDocument;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Application Menu Controller
 * 
 * @author Andrew Bigger
 */
public class MenuController implements Initializable {
    private State state;
    private Stage window;
    private MainController mainController;
    
    /**
     * Creates a disabled hidden faux menu for dialogs.
     * 
     * @param controller controller
     * @param rb application resource bundle
     * @param target target element
     */
    public static void loadMenu(
            Initializable controller,
            ResourceBundle rb,
            BorderPane target
    ) {
        try {
            URL location = controller.getClass().getResource("/fxml/Menu.fxml");
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(location);
            loader.setResources(rb);
            loader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent menu = (Parent) loader.load();
            
            MenuController menuController = (MenuController) loader.getController();
            menuController.disable();
            menuController.hide();
            
            target.setTop(menu);
        } catch (IOException ex) {
            System.exit(0);
        }
    }
    
    /**
     * Loads menu into border pane
     * 
     * @param controller main window controller
     * @param state application state
     * @param window main window
     * @param target target element to render menu into
     */
    public static void loadMenu(
            MainController controller,
            State state,
            Stage window,
            BorderPane target
    ) {
        try {
            URL location = controller.getClass().getResource("/fxml/Menu.fxml");
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(location);
            loader.setResources(state.bundle());
            loader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent menu = (Parent) loader.load();
            
            MenuController menuController = (MenuController) loader.getController();
            menuController.setMainController(controller);
            menuController.setState(state);
            menuController.setWindow(window);
            
            target.setTop(menu);
        } catch (IOException ex) {
            System.exit(0);
        }
    }
    
    /**
     * Application menu.
     */
    @FXML
    public MenuBar mainMenu;
    
    /**
     * Application exit menu item.
     */
    @FXML
    public MenuItem quitMenuItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (OperatingSystem.isMac()) {
            mainMenu.useSystemMenuBarProperty().set(true);
            quitMenuItem.visibleProperty().set(false);
        }
    }
    
    /**
     * Sets main window controller for menu
     * 
     * @param value controller
     */
    public void setMainController(MainController value) {
        mainController = value;
    }
    
    /**
     * Sets application state
     * 
     * @param value state
     */
    public void setState(State value) {
        state = value;
    }
    
    /**
     * Sets window
     * 
     * @param value window
     */
    public void setWindow(Stage value) {
        window = value;
    }
    
    public void hide() {
        mainMenu.setVisible(false);
    }
    
    /**
     * Disables all menu items
     */
    public void disable() {
        for (Menu m : mainMenu.getMenus()) {
            for (MenuItem i : m.getItems()) {
                i.setDisable(true);
            }
        }
    }
    
    /**
     * Enables menu items
     */
    public void enable() {
        for (Menu m : mainMenu.getMenus()) {
            for (MenuItem i : m.getItems()) {
                i.setDisable(false);
            }
        }
    }
    
    /**
     * Creates a new document.
     * 
     * This will replace the currentDocument with a new one, having the effect
     * of creating a new locale file.
     */
    @FXML
    private void handleCreateNewDocument() {
        try {
            perform(CreateDocument.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.new"),
                    e
            );
        }
    }
    
    /**
     * Opens a document.
     * 
     * Shows open file dialog to allow user to choose a file.
     * 
     * If there is a choice then the document is deserialized and loaded
     * into memory.
     * 
     * If there is an error deserializing the document a error alert will be
     * shown.
     * 
     * When no choice is made, nothing will be done.
     */
    @FXML
    private void handleOpenDocument() {
        try {
            perform(OpenDocument.class);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.open"),
                    e
            );
        }
    }
    
    /**
     * Saves open document.
     * 
     * When the file is not set, a save file dialog is shown. If a location is
     * not picked, then nothing will happen.
     * 
     * The document will then be saved to disk.
     */
    @FXML
    private void handleSaveDocument() {
        try {
            perform(SaveDocument.class);
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.saveFile"),
                    e
            );
        }
    }
    
    /**
     * Shows preference dialog.The contents of the dialog are loaded from the 
     * pane FXML.
     * 
     * A call to setup dialog ensures that the dialog is configured 
     * appropriately before it is shown.
     * 
     */
    @FXML
    private void handleOpenPreferencesDialog() {
        try {
            perform(OpenPreferences.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Opens about application dialog box.
     */
    @FXML
    private void handleOpenAboutDialog() {
        try {
            perform(OpenAboutDialog.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Opens help website in default browser.
     */
    @FXML
    private void handleViewHelp() {
        try {
            perform(OpenHelpPage.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Exits application.
     * 
     * @param event
     */
    @FXML
    private void handleApplicationExit() {
        try {
            perform(ExitApplication.class);
        } catch (Exception e) {
            ErrorAlert.show(
                    state.bundle(),
                    state.bundle().getString("errors.generic"),
                    e
            );
        }
    }
    
    /**
     * Performs action
     * 
     * @param action action class to perform
     * @throws Exception any exception action may throw
     */
    private void perform(Class action) 
            throws Exception {
        Action act = (Action) action.newInstance();
        act.perform(state, window);
    }
}
