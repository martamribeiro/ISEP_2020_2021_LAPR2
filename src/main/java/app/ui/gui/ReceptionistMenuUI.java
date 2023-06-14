package app.ui.gui;

import app.controller.SelectCalController;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;
import app.ui.console.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceptionistMenuUI implements Initializable, Menu {

    private App mainApp;

    @FXML
    private Button exitBtn;

    private SelectCalController ctrl;

    private String labID;


    private final String FXML_PATH = "/fxml/ReceptionistMenu.fxml";

    public String getFXML_PATH() {
        return FXML_PATH;
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public App getMainApp() {
        return this.mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl = new SelectCalController();
        List<ClinicalAnalysisLaboratoryDTO> dto = ctrl.getCalListDTO();
        List<String> ids = new ArrayList<>();
        for (ClinicalAnalysisLaboratoryDTO cal : dto){
            ids.add(cal.getLaboratoryID());
        }
        ChoiceDialog d = new ChoiceDialog(ids.get(0), ids);
        d.setHeaderText("Clinical Analysis Laboratory");
        d.setContentText("Please select the ID of the CAL you are working at:");
        d.showAndWait();
        this.labID = (String) d.getSelectedItem();

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
        this.mainApp.exitSave();
    }

    public void handleRegisterClient(ActionEvent actionEvent) {
        RegisterClientUI registerClientUI = new RegisterClientUI();
        registerClientUI.run();
    }

    public void handleRegisterTest(ActionEvent actionEvent) {
        CreateTestUI createTestUI = new CreateTestUI(labID);
        createTestUI.run();
    }
}
