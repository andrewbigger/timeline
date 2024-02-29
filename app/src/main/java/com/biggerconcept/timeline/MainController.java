package com.biggerconcept.timeline;

import com.biggerconcept.timeline.domain.Document;
import com.biggerconcept.timeline.domain.Judgement.Assessment;
import com.biggerconcept.timeline.domain.Year;
import com.biggerconcept.timeline.exceptions.NoChoiceMadeException;
import com.biggerconcept.timeline.platform.OperatingSystem;
import com.biggerconcept.timeline.ui.dialogs.ErrorAlert;
import com.biggerconcept.timeline.ui.dialogs.OpenFileDialog;
import com.biggerconcept.timeline.ui.dialogs.SaveFileDialog;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller for the main view.
 * 
 * @author Andrew Bigger
 */
public class MainController implements Initializable {  
    /**
     * The document being edited in the main window.
     */
    private Document currentDocument;
    
    /**
     * View year.
     */
    private Year viewYear;
    
    /**
     * Extension filter for files.
     */
    private ExtensionFilter fileExtFilter;
    
    /**
     * Resource bundle for application.
     */
    private ResourceBundle bundle;
    
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
    
    /**
     * View year label
     */
    @FXML
    public Label currentYearLabel;
    
    /**
     * Velocity points label
     */
    @FXML
    public Label velocityPointsLabel;
    
    /**
     * Total sprints label.
     */
    @FXML
    public Label totalSprintsLabel;
    
    /**
     * Committed points total
     */
    @FXML
    public Label commitmentPointsLabel;
    
    /**
     * Commitment indicator progress bar
     */
    @FXML
    public ProgressBar commitmentProgress;
    
    /**
     * Available points total
     */
    @FXML
    public Label availableCommitmentLabel;
    
    /**
     * Adds epic to shelf.
     */
    @FXML
    public Button addEpicToShelfButton;
    
    /**
     * Button to remove epic from shelf
     */
    @FXML
    public Button removeEpicFromShelfButton;
    
    /**
     * Button to edit epic on shelf.
     */
    @FXML
    public Button editEpicOnShelfButton;
    
    /**
     * Button to move epic from shelf.
     */
    @FXML
    public Button moveEpicFromShelfButton;
    
    /**
     * Button to move epic to shelf
     */
    @FXML
    public Button moveEpicToShelfButton;
    
    /**
     * Shelf epics table view
     */
    @FXML
    public TableView shelfTableView;
    
    /**
     * Selected epics table view
     */
    @FXML
    public TableView epicTableView;
    
    /**
     * Judgement combo box
     */
    @FXML
    public ComboBox judgementComboBox;
    
    /**
     * Notes text area.
     */
    @FXML
    public TextArea notesTextArea;
    
    /**
     * Open file toolbar button.
     * 
     * When clicked, this should prompt the user to choose a file.
     */
    @FXML
    public Button openFileButton;
    
    /**
     * Save file toolbar button.
     * 
     * When clicked, this serializes the current state of the app to file.
     */
    @FXML
    public Button saveFileButton;
    
    /**
     * Toolbar button for creating a new file.
     * 
     * When clicked this will clear the current object and replace it with
     * a new one.
     */
    @FXML
    public Button newFileButton;
    
    /**
     * Initializes the main window.
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;

        currentDocument = new Document();
        viewYear = Year.DEFAULT;
        fileExtFilter = new ExtensionFilter(
                "JSON File",
                Arrays.asList("json")
        );
        
        if (OperatingSystem.isMac()) {
            mainMenu.useSystemMenuBarProperty().set(true);
            quitMenuItem.visibleProperty().set(false);
        }

        applyTooltips();
        mapDocumentToWindow();
    }

    /**
     * Creates and adds tool tips to UI controls.
     */
    private void applyTooltips() {
        openFileButton.setTooltip(
                new Tooltip(bundle.getString("toolbar.open.tooltip"))
        );
        saveFileButton.setTooltip(
                new Tooltip(bundle.getString("toolbar.save.tooltip"))
        );
        newFileButton.setTooltip(
                new Tooltip(bundle.getString("toolbar.new.tooltip"))
        );
    }
    
    /**
     * Returns the main window stage.
     * 
     * The main window stage is the window that the new file button control is
     * in. 
     * 
     * @return 
     */
    private Stage mainStage() {
        Stage stage = (Stage) newFileButton.getScene().getWindow();
        return stage;
    }
    
    /**
     * Maps given document to window.
     * 
     * @param doc 
     */
    private void mapDocumentToWindow() {
        applyPreferencesToWindow();
        mapYearToWindow();
        mapOutlookToWindow();
        mapShelfToWindow();
        mapEpicsToWindow();
        mapAssessmentsToWindow();
        mapNotesToWindow();
    }
    
    /**
     * Maps year to window
     */
    private void mapYearToWindow() {
        currentYearLabel.setText(viewYear.getName());
    }
    
    /**
     * Maps velocity, available sprints and commitment to window.
     */
    private void mapOutlookToWindow() {
        velocityPointsLabel.setText(
                String.valueOf(
                    currentDocument
                            .getPreferences()
                            .calculateAveragePointsPerSprint()
                )
        );
        
        int availableSprints = viewYear.calculateSprints(
                                currentDocument
                                    .getPreferences()
                                    .getSprintLength()
                        );
        
        totalSprintsLabel.setText(
                String.valueOf(
                     availableSprints   
                )
        );
        
        // TODO: committed points
        
        availableCommitmentLabel.setText(
                String.valueOf(
                        currentDocument
                                .getPreferences()
                                .calculateAvailablePointsIn(availableSprints)
                )
        );
    }
    
    /**
     * Maps shelf epics to window
     */
    private void mapShelfToWindow() {
        
    }
    
    /**
     * Maps selected epics to window
     */
    private void mapEpicsToWindow() {
        
    }
    
    /**
     * Maps assessment options and selection to window
     */
    private void mapAssessmentsToWindow() {
        judgementComboBox.getItems().clear();
        
        for (Assessment a : Assessment.values()) {
            judgementComboBox.getItems().add(a);
        }
        
        judgementComboBox
                .getSelectionModel()
                .select(currentDocument.getJudgement());
    }
    
    /**
     * Maps notes to window
     */
    private void mapNotesToWindow() {
        notesTextArea.setText(currentDocument.getNotes());
    }
    
    /**
     * Applies document settings to window.
     */
    private void applyPreferencesToWindow() {
        // TODO: apply preferences
    }
    
    /**
     * Maps window content to new document object for serialization.
     * 
     * @return 
     */
    private void mapWindowToDocument() {
        mapWindowToShelf();
        mapWindowToEpics();
        mapWindowToJudgement();
        mapWindowToNotes();
    }
    
    /**
     * Maps shelf epics to document
     */
    private void mapWindowToShelf() {
        
    }
    
    /**
     * Maps selected epics to document
     */
    private void mapWindowToEpics() {
        
    }
    
    /**
     * Maps judgement selection to document
     */
    private void mapWindowToJudgement() {
        currentDocument.setJudgement(
                (Assessment) judgementComboBox
                        .getSelectionModel()
                        .getSelectedItem()
        );
    }
    
    /**
     * Maps window notes to document.
     */
    private void mapWindowToNotes() {
        currentDocument.setNotes(
                notesTextArea.getText()
        );
    }
    
    /**
     * Handles adding an epic to the shelf.
     */
    @FXML
    private void handleAddEpicToShelf() {
        
    }
    
    /**
     * Handles removing an epic from the shelf
     */
    @FXML
    private void handleRemoveEpicFromShelf() {
        
    }
    
    /**
     * Handles editing a shelf epic
     */
    @FXML
    private void handleEditEpicOnShelf() {
        
    }
    
    /**
     * Handles editing a selected epic
     */
    @FXML
    private void handleEditEpic() {
        
    }
    
    /**
     * Handles show previous year
     */
    @FXML
    private void handleShowPrevYear() {
        try {
            viewYear = viewYear.previous();
            mapDocumentToWindow();
        } catch (Exception e) {
             ErrorAlert.show(bundle, bundle.getString("errors.viewYear"), e);
        }
    }
    
    /**
     * Handles show next year
     */
    @FXML
    private void handleShowNextYear() {
        try {
            viewYear = viewYear.next();
            mapDocumentToWindow();
        } catch (Exception e) {
             ErrorAlert.show(bundle, bundle.getString("errors.viewYear"), e);
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
            currentDocument = new Document();
            mapDocumentToWindow();
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.new"), e);
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
            File documentFile = OpenFileDialog.show(
                    bundle.getString("dialogs.open.title"),
                    mainStage(),
                    fileExtFilter
            );

            currentDocument = Document.load(documentFile);
            mapDocumentToWindow();
        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (IOException e) {
            ErrorAlert.show(bundle, bundle.getString("errors.open"), e);
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
            if (currentDocument.getFile() == null) {
                File f = SaveFileDialog.show(
                        bundle.getString("dialogs.save.title"),
                        mainStage(),
                        fileExtFilter
                );

                if (f == null) {
                    return;
                }

                currentDocument.setFile(f);
            }
            
            mapWindowToDocument();
            currentDocument.save();

        } catch (NoChoiceMadeException ncm) {
            // do nothing
        } catch (IOException e) {
            ErrorAlert.show(bundle, bundle.getString("errors.saveFile"), e);
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
            URL location = getClass().getResource("/fxml/Preferences.fxml");
            FXMLLoader loader = new FXMLLoader();
        
            loader.setLocation(location);
            loader.setResources(bundle);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
        
            Parent preferencePane = (Parent) loader.load();
        
            PreferencesController controller = (PreferencesController) loader
                .getController();
        
            controller.setDocument(currentDocument);
        
            Stage stage = new Stage();
        
            stage.setAlwaysOnTop(true);
            stage.setScene(new Scene(preferencePane));
            stage.setTitle(bundle.getString("dialogs.preferences.title"));
            stage.initStyle(StageStyle.DECORATED);
            stage.resizableProperty().setValue(false);
            
            stage.showAndWait();
            mapDocumentToWindow();
        } catch (IOException e) {
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
        }
    }
    
    /**
     * Opens about application dialog box.
     */
    @FXML
    private void handleOpenAboutDialog() {
        try {
            Stage aboutStage = new Stage();
        
            Parent aboutPane = FXMLLoader.load(
                getClass().getResource("/fxml/About.fxml"), bundle
            );

            aboutStage.setAlwaysOnTop(true);
            aboutStage.setScene(new Scene(aboutPane));
            aboutStage.initStyle(StageStyle.UTILITY);
            aboutStage.resizableProperty().setValue(false);

            aboutStage.setTitle(
                    bundle.getString("application.about.windowTitle")
            );
            
            aboutStage.showAndWait();
            
            applyPreferencesToWindow();
        } catch (IOException e) {
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
        }
    }
    
    /**
     * Opens help website in default browser.
     * 
     */
    @FXML
    private void handleViewHelp() {
        try {
            OperatingSystem.goToUrl(App.HELP_URL);
        } catch (Exception e) {
            ErrorAlert.show(bundle, bundle.getString("errors.generic"), e);
        }
    }
    
    /**
     * Exits application.
     * 
     * @param event
     */
    @FXML
    private void handleApplicationExit() {
        System.exit(0);
    }

}
