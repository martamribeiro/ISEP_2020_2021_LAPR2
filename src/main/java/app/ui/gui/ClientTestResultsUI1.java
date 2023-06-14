package app.ui.gui;

import app.controller.ViewClientResultsController;
import app.mappers.dto.TestDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marta Ribeiro 1201592
 */
public class ClientTestResultsUI1 implements Initializable {

    private App mainApp;

    public App getMainApp() {
        return this.mainApp;
    }

    public void setMainApp(App mainApp){
        this.mainApp=mainApp;
    }

    private ClientMenuUI clientMenuUI;
    private ViewClientResultsController controller;

    private List<TestDTO> clientTests;
    private List<String> testParametersName;
    private List<Double> testParametersResult;
    private List<String> testParametersMetric;
    private List<Double> testParametersMinRef;
    private List<Double> testParametersMaxRef;

    private ArrayList<String> stringsForListview = new ArrayList<>();

    public void setClientMenuUI(ClientMenuUI clientMenuUI) {
        this.clientMenuUI = clientMenuUI;
    }

    public void setmListView(ListView<String> mListView) {
        this.mListView = mListView;
    }

    @FXML
    private ListView<String> mListView;

    @FXML
    private Button exitBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private TextArea resultText;

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

    @FXML
    void returnAction(ActionEvent event) {
        try {
            ClientMenuUI clientMenuUI = (ClientMenuUI) this.mainApp.replaceSceneContent("/fxml/ClientMenu.fxml");
            clientMenuUI.setMainApp(mainApp);
            clientMenuUI.setMainUI(this.clientMenuUI.getMainUI());
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void selectTest(MouseEvent event) {
        String selectedItem = mListView.getSelectionModel().getSelectedItem();
        int num = -1;
        for (int i = 0; i < stringsForListview.size(); i++) {
            if (stringsForListview.get(i).equals(selectedItem)){
                num=i;
            }
        }
        if (num!=-1) {
            testParametersName = clientTests.get(num).getTestParametersName();
            testParametersMetric = clientTests.get(num).getTestParametersMetric();
            testParametersResult = clientTests.get(num).getTestParametersResult();
            testParametersMinRef = clientTests.get(num).getTestParametersReferenceValueMin();
            testParametersMaxRef = clientTests.get(num).getTestParametersReferenceValueMax();
            String text = "";
            for (int i = 0; i < testParametersName.size(); i++) {
                text = text + testParametersName.get(i) + " (" + testParametersMetric.get(i) + ")" + "\n ↪ Result: " +
                        testParametersResult.get(i) + "\n ↪ Normal Values: " + testParametersMinRef.get(i) + " - " +
                        testParametersMaxRef.get(i) + "\n ↪ Report: " + clientTests.get(num).getReport() + "\n";
            }
            resultText.setText(text);
        } else {
            resultText.setText("There are no results to show.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.controller=new ViewClientResultsController();
    }

    public void setClientTests(List<TestDTO> clientTests) {
        this.clientTests = clientTests;
    }

    public void getClientTests(){
        clientTests= controller.getClientTestsWithResults(this.clientMenuUI.getMainUI().getEmail());
        setClientTests(clientTests);
        populateData(clientTests);
    }

    private void populateData(List<TestDTO> testsWithResults){
        String toAdd;
        for (int i = 0; i < testsWithResults.size(); i++) {
            toAdd=testsWithResults.get(i).getTestTypeDescription() + "\n ↪ " + testsWithResults.get(i).getStringDateOfTestRegistration();
            stringsForListview.add(toAdd);
        }
        for (String string : stringsForListview) {
            mListView.getItems().add(string);
        }
        setmListView(mListView);
    }

}