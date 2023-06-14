package app.ui.gui;

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

/**
 * @author Marta Ribeiro 1201592
 */
public class ClientMenuUI implements Initializable, Menu {

    private App mainApp;

    public App getMainApp() {
        return this.mainApp;
    }

    public void setMainApp(App mainApp){
        this.mainApp=mainApp;
    }

    private final String FXML_PATH = "/fxml/ClientMenu.fxml";

    public String getFXML_PATH(){
        return FXML_PATH;
    }

    public void setMainUI(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    private MainUI mainUI;

    public MainUI getMainUI(){
        return this.mainUI;
    }

    @FXML
    private Button logoutBtn;

    @FXML
    private Button updateClientDataBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button viewTestResultBtn;

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
    void viewTestResultAction(ActionEvent event) {
        try {
            ClientTestResultsUI1 clientTestResultsUI1 = (ClientTestResultsUI1) this.mainApp.replaceSceneContent("/fxml/ClientTestResults1.fxml");
            clientTestResultsUI1.setMainApp(this.mainApp);
            clientTestResultsUI1.setClientMenuUI(this);
            clientTestResultsUI1.getClientTests();

        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void updateClientDataAction(ActionEvent event) {

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

}