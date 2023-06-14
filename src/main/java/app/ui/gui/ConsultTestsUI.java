package app.ui.gui;

import app.controller.ConsultTestByClient;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.TestDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultTestsUI implements Initializable {

    @FXML
    private RadioButton tin;

    @FXML
    private RadioButton name;

    @FXML
    private ListView<ClientDTO> clientsList = new ListView<>();

    @FXML
    private TextArea showTests;

    private App mainApp;

    private Menu myMenu;

    private ConsultTestByClient ctrl;

    List<ClientDTO> clientsDto;

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public void setMyMenu(Menu menu){
        this.myMenu = menu;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ctrl = new ConsultTestByClient();
        this.clientsDto = new ArrayList<>();
    }

    @FXML
    public void nameSelected(){
        this.tin.setSelected(false);
    }

    @FXML
    public void selectedTin(){
        this.name.setSelected(false);
    }

    @FXML
    private void searchForClients() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if(!name.isSelected() && !tin.isSelected()){
            AlertUI.createAlert(Alert.AlertType.WARNING, "Many labs", "No order selected!", "Please select by which attribute the clients should be ordered.").show();
        }else {
            clientsDto = ctrl.getClientsDtoInOrder(getSelectedOrder());
        }
        ObservableList<ClientDTO> items = FXCollections.observableArrayList();
        items.addAll(clientsDto);
        clientsList.setItems(items);
    }

    private String getSelectedOrder(){
        System.out.println(tin.getId());
        return tin.isSelected() ? tin.getId() : name.getId();
    }

    @FXML
    private void selectedClient(){
        int index = clientsList.getSelectionModel().getSelectedIndex();
        if(index != -1){
            List<TestDTO> tests = ctrl.getValidatedTestsOfClient(clientsDto.get(index).getTinNumber());
            for(TestDTO test: tests){
                showTests.appendText(test.showAllButReport());
            }
        }
    }

    @FXML
    private void returnAction(){
        try {
            ChemTechUI menu = (ChemTechUI) mainApp.replaceSceneContent("/fxml/ChemistryTechMenu.fxml");
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
