package app.ui.gui;

import app.controller.SelectCalController;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;
import app.ui.console.RecordSamplesUI;
import app.ui.console.WriteReportUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpecDoctorUI implements Initializable, Menu {

    private App mainApp;

    private final String FXML_PATH = "/fxml/SpecDoctor.fxml";

    public App getMainApp() {
        return this.mainApp;
    }

    public void setMainApp(App mainApp){
        this.mainApp=mainApp;
    }

    public String getFXML_PATH(){
        return FXML_PATH;
    }

    @FXML
    private Button logoutBtn;

    @FXML
    private Button exitBtn;

    /**
     * Initializes the UI class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void logoutAction(ActionEvent event) {
        try {
            MainUI mainUI = (MainUI) this.mainApp.replaceSceneContent("/fxml/Login.fxml");
            mainUI.setMainApp(mainApp);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void exitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Many Labs Application");
        alert.setHeaderText("Action confirmation.");
        alert.setContentText("Do you really wish to exit the application?");

        ((Labeled) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Labeled) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");

        if (alert.showAndWait().get() == ButtonType.OK) {
            this.mainApp.exitSave();
        }
    }


    public void handleWriteReport(ActionEvent actionEvent) {
        WriteReportUI writeReportUI = new WriteReportUI();
        writeReportUI.run();
        System.out.println("Operation finished. Back to Graphical Interface");
    }
}