package app.ui.gui;

import app.ui.console.RecordResultsUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChemTechUI implements Initializable, Menu {

    private App mainApp;

    private final String FXML_PATH = "/fxml/ChemistryTechMenu.fxml";

    @Override
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public String getFXML_PATH() {
        return FXML_PATH;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void consultClientsData(){
        try {
            ConsultTestsUI importTests = (ConsultTestsUI) mainApp.replaceSceneContent("/fxml/ConsultClientsTests.fxml");
            importTests.setMainApp(this.mainApp);
            importTests.setMyMenu(this);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void addTestResult(){
        RecordResultsUI recordResultsUI = new RecordResultsUI();
        recordResultsUI.run();
        System.out.println("Operation finished. Back to Graphical Interface");
    }

    @FXML
    void exitAction(ActionEvent event) {
        this.mainApp.exitSave();
    }

    @FXML
    public void logoutAction(){
        app.controller.App.getInstance().doLogout();
        this.mainApp.toMainScene();
    }
}
