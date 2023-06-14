package app.ui.gui;

import app.ui.console.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminMenuUI implements Initializable, Menu {

    private App mainApp;

    @FXML
    private Button exitBtn;

    @FXML
    private Button sendNHSReportBtn;

    private final String FXML_PATH = "/fxml/AdminMenu.fxml";

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

    }

    @FXML
    void sendNHSReportAction(ActionEvent event) {
        try {
            NHSReportUI nhsReportUI = (NHSReportUI) this.mainApp.replaceSceneContent("/fxml/NHSReport.fxml");
            nhsReportUI.setMainApp(this.mainApp);
            nhsReportUI.setMyMenu(this);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public void runConsole(Runnable classToRun){
        classToRun.run();
        System.out.println("Operation finished. Back to Graphical Interface");
    }
    public void registerCal(ActionEvent actionEvent) {
        RegisterNewCalUI registerNewCalUI = new RegisterNewCalUI();
        runConsole(registerNewCalUI);
    }

    public void createEmployee(ActionEvent actionEvent) {
        CreateEmployeeUI createEmployeeUI = new CreateEmployeeUI();
        runConsole(createEmployeeUI);
    }

    public void createTestType(ActionEvent actionEvent) {
        CreateTestTypeUI createTestTypeUI = new CreateTestTypeUI();
        runConsole(createTestTypeUI);
    }

    public void createParameterCategory(ActionEvent actionEvent) {
        ParameterCategoryUI parameterCategoryUI = new ParameterCategoryUI();
        runConsole(parameterCategoryUI);
    }

    public void createParameter(ActionEvent actionEvent) {
        CreateParameterUI createParameterUI = new CreateParameterUI();
        runConsole(createParameterUI);
    }
}
