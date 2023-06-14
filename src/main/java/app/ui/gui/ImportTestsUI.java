package app.ui.gui;

import app.controller.ImportTestController;
import app.ui.console.utils.TestFileUtils;
import app.mappers.dto.TestFileDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportTestsUI implements Initializable {

    @FXML
    private Text pathOfFile;

    @FXML
    private TextArea txtImportedTests;

    @FXML
    private Label importedTests;

    @FXML
    private Text numberOfTests;

    private App mainApp;

    private Menu myMenu;

    private File inputFile;

    private ImportTestController ctrl;

    private List<TestFileDTO> testsOfFile;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ctrl = new ImportTestController();
        this.testsOfFile = Collections.emptyList();
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public void setMyMenu(Menu menu){
        this.myMenu = menu;
    }

    @FXML
    private void openFileChooser(){
        FileChooser flChooser = FileChooserCsvFiles.createCsvFileChooser();
        File choosenFile = flChooser.showOpenDialog(this.mainApp.getStage());

        if(choosenFile != null){
            inputFile = choosenFile;
            this.pathOfFile.setText(inputFile.toString());
        }else{
            AlertUI.createAlert(Alert.AlertType.ERROR, "Import file", "Failed to import file",
                    "None file selected!").show();
        }

    }

    @FXML
    private void importTests(){
        if(inputFile != null) {
            TestFileUtils testFileUtils = new TestFileUtils();
            testsOfFile = testFileUtils.getTestsDataToDto(inputFile.toString());
            List<TestFileDTO> addedTests = new ArrayList<>();
            for (int i = 0; i < testsOfFile.size(); i++) {
                try {
                    if (!ctrl.importTestFromFile(testsOfFile.get(i)))
                        throw new Exception("Test already existent in the system");
                    addedTests.add(testsOfFile.get(i));
                } catch (Exception e) {
                    System.out.println("Error in line " + (i + 2) + " of csv file");
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }

            if (!addedTests.isEmpty()) {
                this.txtImportedTests.setVisible(true);
                this.importedTests.setVisible(true);
                this.numberOfTests.setText(String.format("%d tests imported", addedTests.size()));
                this.numberOfTests.setVisible(true);
                showImportedTests(addedTests);
            }
        }else{
            AlertUI.createAlert(Alert.AlertType.WARNING, "Import file", "Failed to import file",
                    "Please select a file to be imported.").show();
        }

    }

    private void showImportedTests(List<TestFileDTO> addedTests){
        for(TestFileDTO test : addedTests){
            txtImportedTests.appendText(test.showAddedTest());
        }
    }

    @FXML
    private void returnAction(){
        try {
            LabCoordinatorUI menu = (LabCoordinatorUI) mainApp.replaceSceneContent("/fxml/LabCoordinatorMenu.fxml");
            menu.setMainApp(this.mainApp);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void exitAction(ActionEvent event) {
        this.mainApp.exitSave();
    }


}
