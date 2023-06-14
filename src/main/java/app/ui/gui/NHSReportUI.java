package app.ui.gui;

import app.controller.SendNHSReportController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NHSReportUI implements Initializable, Menu {

    private App mainApp;

    private Menu myMenu;

    @FXML
    private Button exitBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private DatePicker currentDate;

    @FXML
    private ToggleGroup tgTypeOfData;

    @FXML
    private RadioButton dayRadioBtn;

    @FXML
    private RadioButton weekRadioBtn;

    @FXML
    private TextField historicalPoints;

    @FXML
    private DatePicker initialDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField significanceLevel;

    @FXML
    private TextField confidenceLevel;

    @FXML
    private ChoiceBox<String> regressionChoiceBox;

    @FXML
    private ChoiceBox<String> regCoefficientsChoiceBox;

    @FXML
    private ChoiceBox<String> variableChoiceBox;

    private String chosenOption;

    private final String[] regressionModels = {"Simple Linear Regression", "Multiple Linear Regression"};
    private final String[] independentVariables = {"Covid-19 Tests Realized", "Mean Age Of Clients"};
    private final String[] regressionCoefficients = {"a", "b", "c"};
    private final String[] regressionCoefficientsForSLR = {"a", "b"};

    @FXML
    private Button sendBtn;

    private SendNHSReportController controller;

    private final String FXML_PATH = "./fxml/NHSReport.fxml";

    @Override
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    public void setMyMenu(Menu menu){
        this.myMenu = menu;
    }

    @Override
    public String getFXML_PATH() {
        return FXML_PATH;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.regressionChoiceBox.getItems().addAll(regressionModels);
        this.variableChoiceBox.getItems().addAll(independentVariables);
        this.variableChoiceBox.setDisable(true);
        this.regCoefficientsChoiceBox.setDisable(true);
        regressionChoiceBox.setOnAction(this::getButtons);
        this.controller = new SendNHSReportController();
    }


    public void setChosenOption(String chosenOption) {
        this.chosenOption = chosenOption;
    }

    public void getButtons(ActionEvent event){
        String chosenRegressionModel = regressionChoiceBox.getValue();
        setChosenOption(chosenRegressionModel);
        if (chosenOption.equals("Simple Linear Regression")) {
            this.variableChoiceBox.setDisable(false);
            this.regCoefficientsChoiceBox.setDisable(false);
            regCoefficientsChoiceBox.getItems().clear();
            regCoefficientsChoiceBox.getItems().addAll(regressionCoefficientsForSLR);
        } else {
            this.variableChoiceBox.setDisable(true);
            this.regCoefficientsChoiceBox.setDisable(false);
            regCoefficientsChoiceBox.getItems().clear();
            regCoefficientsChoiceBox.getItems().addAll(regressionCoefficients);
        }

    }

    @FXML
    void sendReportAction(ActionEvent event) {
        try {
            LocalDate currentDateValue = this.currentDate.getValue();
            Date currentDate = Date.from(currentDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String typeOfData = (this.dayRadioBtn.isSelected()) ? this.dayRadioBtn.getText() : weekRadioBtn.getText();
            int historicalPoints = Integer.parseInt(this.historicalPoints.getText());
            LocalDate initalDateValue = this.initialDate.getValue();
            Date beginDate = Date.from(initalDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
            LocalDate endDateValue = this.endDate.getValue();
            Date endDate = Date.from(endDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
            String chosenRegressionModelClass = this.regressionChoiceBox.getValue();
            String chosenRegCoefficient = this.regCoefficientsChoiceBox.getValue();

            String chosenVariable = "";
            if(this.regressionChoiceBox.getValue().equalsIgnoreCase("Simple Linear Regression"))
                chosenVariable = this.variableChoiceBox.getValue();


            double significanceLevel = Double.parseDouble(this.significanceLevel.getText());
            double confidenceLevel = Double.parseDouble(this.confidenceLevel.getText());


            if(historicalPoints <= 2 && chosenRegressionModelClass.equalsIgnoreCase("Simple Linear Regression"))
                throw new UnsupportedOperationException("The historical points for SLR must be greater than 2!");
            if(historicalPoints <= 3 && chosenRegressionModelClass.equalsIgnoreCase("Multiple Linear Regression"))
                throw new UnsupportedOperationException("The historical points for MLR must be greater than 3!");
            if(beginDate.after(endDate) || beginDate.equals(endDate))
                throw new UnsupportedOperationException("The initial date must be before the end date!");


            boolean success = this.controller.createNHSDailyReport(currentDate,
                    typeOfData, historicalPoints, beginDate, endDate,
                    chosenRegressionModelClass, chosenVariable, significanceLevel, confidenceLevel, chosenRegCoefficient);



            if(success) {
                this.controller.sendNHSReport();
                AlertUI.createAlert(Alert.AlertType.INFORMATION, mainApp.getTITLE(), "Operation success!",
                        "NHS Report successfully sent!").show();
            } else {
                AlertUI.createAlert(Alert.AlertType.ERROR, mainApp.getTITLE(), "Error on data",
                        "Something went wrong! Please, try again.").show();
            }







        } catch (NumberFormatException nfe) {
            AlertUI.createAlert(Alert.AlertType.ERROR, mainApp.getTITLE(), "Error on data",
                    "Make sure the numbers only contain digits and cannot be blank!").show();
        } catch (UnsupportedOperationException uoe) {
            AlertUI.createAlert(Alert.AlertType.ERROR, mainApp.getTITLE(), "Error on data",
                    uoe.getMessage()).show();
        } catch (NullPointerException npe) {
            AlertUI.createAlert(Alert.AlertType.ERROR, mainApp.getTITLE(), "Error on data",
                    "Every date is mandatory!").show();
        } catch(Exception e) {
            AlertUI.createAlert(Alert.AlertType.ERROR, mainApp.getTITLE(), "Error on data",
                    e.getMessage()).show();
        }

    }

    public void exitAction(ActionEvent actionEvent) {
        this.mainApp.exitSave();
    }

    public void returnAction(ActionEvent actionEvent) {
        try {
            AdminMenuUI menu = (AdminMenuUI) mainApp.replaceSceneContent("/fxml/AdminMenu.fxml");
            menu.setMainApp(this.mainApp);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

