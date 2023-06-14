package app.ui.gui;

import app.controller.CompanyPerformanceAnalysisController;
import app.domain.model.Company;
import app.domain.model.CompanyPerformance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marta Ribeiro 1201592
 */
public class CheckCompanyPerformanceUI1 implements Initializable {

    private App mainApp;

    public App getMainApp(){
        return this.mainApp;
    }

    public void setMainApp(App mainApp){
        this.mainApp=mainApp;
    }

    private LabCoordinatorUI lcUI;

    public void setLcUI(LabCoordinatorUI lcUI) {
        this.lcUI = lcUI;
    }

    public LabCoordinatorUI getLcUI() {
        return lcUI;
    }

    private CompanyPerformanceAnalysisController controller;

    private String chosenOption;

    public void setChosenOption(String chosenOption) {
        this.chosenOption = chosenOption;
    }

    public String getChosenOption() {
        return chosenOption;
    }

    private Date singleDateD = null;

    public void setSingleDateD(Date singleDateD) {
        this.singleDateD = singleDateD;
    }

    private Date beginningDateD = null;

    public void setBeginningDateD(Date beginningDateD) {
        this.beginningDateD = beginningDateD;
    }

    private Date endingDateD = null;

    public void setEndingDateD(Date endingDateD) {
        this.endingDateD = endingDateD;
    }

    private final String[] options = {"A Day", "An Interval"};
    private final String[] optionsAlg = {"Benchmark Algorithm", "Brute-Force Algorithm"};

    private String chosenAlg;

    public String getChosenAlg() {
        return chosenAlg;
    }

    public void setChosenAlg(String chosenAlg) {
        this.chosenAlg = chosenAlg;
    }

    private Date referenceDate = null;

    public void setReferenceDate(Date referenceDate) {
        this.referenceDate = referenceDate;
    }

    private Date analysisBegDate;
    private Date analysisEndDate;

    public void setAnalysisBegDate(Date analysisBegDate) {
        this.analysisBegDate = analysisBegDate;
    }

    public void setAnalysisEndDate(Date analysisEndDate) {
        this.analysisEndDate = analysisEndDate;
    }

    public Date getAnalysisBegDate() {
        return analysisBegDate;
    }

    public Date getAnalysisEndDate() {
        return analysisEndDate;
    }

    private CompanyPerformance companyPerformance;

    public void setCompanyPerformance(CompanyPerformance companyPerformance) {
        this.companyPerformance = companyPerformance;
    }

    public CompanyPerformance getCompanyPerformance() {
        return companyPerformance;
    }

    private Company company;

    public void setCompany(Company company) {
        this.company = company;
    }

    @FXML
    private Button exitBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private DatePicker endingDate;

    @FXML
    private ChoiceBox<String> intervalOption;

    @FXML
    private Button analyseBtn;

    @FXML
    private DatePicker beginningDate;

    @FXML
    private DatePicker singleDate;

    @FXML
    private ChoiceBox<String> algorithmOption;

    @FXML
    void pickBeginningDate(ActionEvent event) {
        LocalDate begDate = beginningDate.getValue();
        beginningDateD = Date.from(begDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        setBeginningDateD(beginningDateD);
    }

    @FXML
    void pickEndingDate(ActionEvent event) {
        LocalDate endDate = endingDate.getValue();
        endingDateD = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        setEndingDateD(endingDateD);
    }

    @FXML
    void pickSingleDate(ActionEvent event) {
        LocalDate singDate = singleDate.getValue();
        singleDateD = Date.from(singDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        setSingleDateD(singleDateD);
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

    @FXML
    void returnAction(ActionEvent event) {
        try {
            LabCoordinatorUI labCoordinatorUI = (LabCoordinatorUI) this.mainApp.replaceSceneContent("/fxml/LabCoordinatorMenu.fxml");
            labCoordinatorUI.setMainApp(mainApp);
            labCoordinatorUI.setMainUI(this.lcUI.getMainUI());
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void analyseAction(ActionEvent event) {
        if (singleDateD==null && beginningDateD!=null && endingDateD!=null){
            analysisBegDate = new Date(beginningDateD.getYear(), beginningDateD.getMonth(), beginningDateD.getDate(), 8,0,0);
            setAnalysisBegDate(analysisBegDate);
            analysisEndDate = new Date(endingDateD.getYear(), endingDateD.getMonth(), endingDateD.getDate(), 19,59,59);
            setAnalysisEndDate(analysisEndDate);
        } else if (singleDateD!=null && beginningDateD==null && endingDateD==null){
            analysisBegDate = new Date(singleDateD.getYear(), singleDateD.getMonth(), singleDateD.getDate(), 8,0,0);
            setAnalysisBegDate(analysisBegDate);
            analysisEndDate = new Date(singleDateD.getYear(), singleDateD.getMonth(), singleDateD.getDate(), 19,59,59);
            setAnalysisEndDate(analysisEndDate);
        }
        setCompany(controller.getCompany());
        companyPerformance = company.createCompanyPerformance(analysisBegDate,analysisEndDate,chosenAlg);
        setCompanyPerformance(companyPerformance);
        controller.setCompanyPerformance(companyPerformance);
        referenceDate=java.util.Calendar.getInstance().getTime();
        referenceDate.setHours(0);
        referenceDate.setMinutes(0);
        referenceDate.setSeconds(0);
        setReferenceDate(referenceDate);
        if (((singleDateD!=null && singleDateD.getDay()!=0 && singleDateD.before(referenceDate)) || (beginningDateD!=null && endingDateD!=null && endingDateD.before(referenceDate)
                && beginningDateD.before(endingDateD) && (beginningDateD.getYear()!=endingDateD.getYear() ||
                beginningDateD.getMonth()!=endingDateD.getMonth() || beginningDateD.getDate()!=endingDateD.getDate()))) &&
                (chosenOption.equals("A Day") || chosenOption.equals("An Interval")) && (chosenAlg.equals("Benchmark Algorithm") ||
                chosenAlg.equals("Brute-Force Algorithm"))) {
            try {
                CheckCompanyPerformanceUI2 checkCompanyPerformanceUI2 = (CheckCompanyPerformanceUI2) this.mainApp.replaceSceneContent("/fxml/CheckCompanyPerformance2.fxml");
                checkCompanyPerformanceUI2.setMainApp(this.mainApp);
                checkCompanyPerformanceUI2.setCheckCompPerUI1(this);
                checkCompanyPerformanceUI2.setController(this.controller);
                checkCompanyPerformanceUI2.enableBtns();
                checkCompanyPerformanceUI2.analyseCompany();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
        } else {
            createAlert1().showAndWait();
        }
    }

    private Alert createAlert1(){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Many Labs Application");
        alert.setHeaderText("Insufficient or Wrong Data");
        alert.setContentText("All fields should be correctly filled, before analysing!");

        return alert;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.controller=new CompanyPerformanceAnalysisController();
        intervalOption.setOnAction(this::getButtons);
        algorithmOption.setOnAction(this::keepChosenAlg);
    }

    public void addOptions(){
        intervalOption.getItems().addAll(options);
        algorithmOption.getItems().addAll(optionsAlg);
    }

    public void getButtons(ActionEvent event){
        String option = intervalOption.getValue();
        setChosenOption(option);
        if (chosenOption.equals("A Day")){
            singleDate.setDisable(false);
            beginningDate.setDisable(true);
            endingDate.setDisable(true);
            setEndingDateD(null);
            setBeginningDateD(null);
        } else if(chosenOption.equals("An Interval")){
            singleDate.setDisable(true);
            beginningDate.setDisable(false);
            endingDate.setDisable(false);
            setSingleDateD(null);
        }
    }

    public void keepChosenAlg(ActionEvent event){
        String chosenAlg = algorithmOption.getValue();
        setChosenAlg(chosenAlg);
    }

}
