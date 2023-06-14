package app.controller;

import app.domain.interfaces.RegressionModel;
import app.domain.model.*;
import app.domain.shared.Constants;
import app.domain.store.NHSReportStore;
import app.domain.store.TestStore;
import app.mappers.dto.TestFileDTO;
import app.ui.console.utils.TestFileUtils;
import com.nhs.report.Report2NHS;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SendNHSReportController {
    private Company company;
    private NHSReport nhsReport;

    public SendNHSReportController() {
        this(App.getInstance().getCompany());
    }

    public SendNHSReportController(Company company) {
        this.company = company;
        this.nhsReport = null;
    }

    public boolean createNHSDailyReport(Date currentDate,
                                        String typeOfData,
                                        int historicalPoints,
                                        Date beginDate,
                                        Date endDate,
                                        String chosenRegressionModelClass,
                                        String chosenVariable,
                                        double significanceLevel,
                                        double confidenceLevel,
                                        String chosenRegCoefficient) throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException, BarcodeException, OutputException, IOException {

        RegressionModel chosenRegressionModel = this.company.getChosenRegressionModel(chosenRegressionModelClass);

        List<List<Double>> dataList = getDataListToFitTheModel(beginDate, endDate, typeOfData);
        NHSReportStore nhsReportStore = this.company.getNhsReportStore();
        double[] covidTestsArray = nhsReportStore.getDoubleArrayWithData(dataList, 0);
        double[] meanAgeArray = nhsReportStore.getDoubleArrayWithData(dataList, 1);
        double[] observedPositives = nhsReportStore.getDoubleArrayWithData(dataList, 2);

        MyRegressionModel myRegressionModel = getMyRegressionModel(chosenRegressionModel, chosenVariable, covidTestsArray, meanAgeArray, observedPositives, historicalPoints);
        HypothesisTest hypothesisTest = chosenRegressionModel.getChosenHypothesisTest(myRegressionModel, significanceLevel, chosenRegCoefficient);
        SignificanceModelAnova modelAnova = chosenRegressionModel.getSignificanceModelAnova(myRegressionModel, significanceLevel);

        Date startDate = nhsReportStore.getStartDateForSelectedDate(currentDate);
        TableOfValues tableOfValues = getTableOfValues(myRegressionModel, chosenRegressionModel, chosenVariable, typeOfData, historicalPoints, startDate, confidenceLevel);

        this.nhsReport = nhsReportStore.createNHSDailyReport(myRegressionModel,hypothesisTest,modelAnova,tableOfValues);
        return nhsReportStore.validateNHSDailyReport(nhsReport);
    }


    public List<List<Double>> getDataListToFitTheModel(Date beginDate,
                                                       Date endDate,
                                                       String typeOfData) {
        TestStore testStore = this.company.getTestStore();
        List<List<Double>> dataList = testStore.getAllDataToFitTheModel(beginDate, endDate, typeOfData);
        return dataList;
    }

    public MyRegressionModel getMyRegressionModel(RegressionModel chosenRegressionModel,
                                                  String chosenVariable,
                                                  double[] covidTestsArray,
                                                  double[] meanAgeArray,
                                                  double[] observedPositives,
                                                  int historicalPoints) {

        NHSReportStore nhsReportStore = this.company.getNhsReportStore();
        MyRegressionModel myRegressionModel;
        if(!chosenVariable.isEmpty()) {
            //for Simple Linear Regression
            myRegressionModel = (chosenVariable.equalsIgnoreCase(Constants.TEST_VARIABLE)) ?
                    chosenRegressionModel.getRegressionModel(covidTestsArray, meanAgeArray, observedPositives, historicalPoints) :
                    chosenRegressionModel.getRegressionModel(meanAgeArray, covidTestsArray, observedPositives, historicalPoints);
        } else {
            //for Multiple Linear Regression
            myRegressionModel = chosenRegressionModel.getRegressionModel(covidTestsArray, meanAgeArray, observedPositives, historicalPoints);
        }
        return myRegressionModel;
    }


    public TableOfValues getTableOfValues(MyRegressionModel myRegressionModel,
                                          RegressionModel chosenRegressionModel,
                                          String chosenVariable,
                                          String typeOfData,
                                          int historicalPoints,
                                          Date startDate,
                                          double confidenceLevel) throws ParseException {

        NHSReportStore nhsReportStore = this.company.getNhsReportStore();
        TestStore testStore = this.company.getTestStore();
        List<String> dates;
        int[] observedPositives;
        Double[] numCovidTestsInHistoricalPoints;
        Double[] meanAgeInHistoricalPoints;

        if(typeOfData.equalsIgnoreCase(Constants.DAY_DATA)) {
            dates = nhsReportStore.getDatesColumnToTableOfValues(historicalPoints, startDate);
            observedPositives = testStore.getObservedPositivesToTableOfValues(historicalPoints, dates);
            numCovidTestsInHistoricalPoints = testStore.getNumberOfCovidTestsInHistoricalPoints(dates);
            meanAgeInHistoricalPoints = testStore.getMeanAgeInHistoricalPoints(dates);
        } else {
            dates = nhsReportStore.getWeeksColumnToTableOfValues(historicalPoints, startDate);
            observedPositives = testStore.getWeeklyObservedPositivesToTableOfValues(dates);
            numCovidTestsInHistoricalPoints = testStore.getWeeklyNumberOfCovidTestsInHistoricalPoints(dates);
            meanAgeInHistoricalPoints = testStore.getWeeklyMeanAgeInHistoricalPoints(dates);
        }

        Double[] chosenVariableArray;
        List<Double> estimatedPositives;
        List<ConfidenceInterval> confidenceIntervals;

        if(!chosenVariable.isEmpty()) { //for Simple Linear Regression
            if(chosenVariable.equalsIgnoreCase("Covid-19 Tests Realized"))
                chosenVariableArray = nhsReportStore.copyArray(numCovidTestsInHistoricalPoints);
            else
                chosenVariableArray = nhsReportStore.copyArray(meanAgeInHistoricalPoints);
            estimatedPositives = chosenRegressionModel.getEstimatedPositives(myRegressionModel, chosenVariableArray, null);
            confidenceIntervals = getConfidenceIntervalListForTableOfValues(myRegressionModel, chosenRegressionModel, chosenVariableArray, null, chosenVariable, confidenceLevel);
        } else { //for Multiple Linear Regression
            estimatedPositives = chosenRegressionModel.getEstimatedPositives(myRegressionModel, numCovidTestsInHistoricalPoints, meanAgeInHistoricalPoints);
            confidenceIntervals = getConfidenceIntervalListForTableOfValues(myRegressionModel, chosenRegressionModel, numCovidTestsInHistoricalPoints, meanAgeInHistoricalPoints, chosenVariable, confidenceLevel);
        }

        TableOfValues tableOfValues = nhsReportStore.createTableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        return tableOfValues;
    }

    public List<ConfidenceInterval> getConfidenceIntervalListForTableOfValues(MyRegressionModel myRegressionModel,
                                                                              RegressionModel regressionModel,
                                                                              Double[] x1InHistoricalPoints,
                                                                              Double[] x2InHistoricalPoints,
                                                                              String chosenVariable,
                                                                              double confidenceLevel) {
        List<ConfidenceInterval> confidenceIntervalList;
        if(!chosenVariable.isEmpty()) //for Simple Linear Regression
            confidenceIntervalList = regressionModel.getConfidenceIntervalList(myRegressionModel, x1InHistoricalPoints, null, confidenceLevel);
        else //for Multiple Linear Regression
            confidenceIntervalList = regressionModel.getConfidenceIntervalList(myRegressionModel, x1InHistoricalPoints, x2InHistoricalPoints, confidenceLevel);
        return confidenceIntervalList;
    }

    public void sendNHSReport() {
        File path = new File("./NHSReport/");
        if(!path.exists())
            path.mkdir();

        Report2NHS.writeUsingFileWriter(this.nhsReport.toStringForChosenHypothesisTest());
    }

}
