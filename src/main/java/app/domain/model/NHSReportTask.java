package app.domain.model;

import app.domain.interfaces.RegressionModel;
import app.domain.shared.Constants;
import app.domain.store.NHSReportStore;
import app.domain.store.TestStore;
import com.nhs.report.Report2NHS;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class NHSReportTask extends TimerTask implements Serializable {
    private String regressionModelClass;
    private RegressionModel regressionModel;
    private int historicalPoints;
    private double significanceLevel;
    private double confidenceLevel;
    private String dateInterval;
    private TestStore testStore;
    private NHSReportStore nhsReportStore;
    private NHSReport nhsDayDataReport;
    private NHSReport nhsWeekDataReport;

    public NHSReportTask(String regressionModelClass,
                         String historicalPoints,
                         String significanceLevel,
                         String confidenceLevel,
                         String dateInterval,
                         TestStore testStore,
                         NHSReportStore nhsReportStore) {
        this.regressionModelClass = regressionModelClass;
        this.historicalPoints = Integer.parseInt(historicalPoints);
        this.significanceLevel = Double.parseDouble(significanceLevel);
        this.confidenceLevel = Double.parseDouble(confidenceLevel);
        this.dateInterval = dateInterval;
        this.testStore = testStore;
        this.nhsReportStore = nhsReportStore;
    }

    public NHSReport createNHSDailyReport(String typeOfData) throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException {
        this.regressionModel = getRegressionModel(regressionModelClass);
        List<List<Double>> dataList = getDataListToFitTheModel(typeOfData);
        double[] covidTestsArray = nhsReportStore.getDoubleArrayWithData(dataList, 0);
        double[] meanAgeArray = nhsReportStore.getDoubleArrayWithData(dataList, 1);
        double[] observedPositives = nhsReportStore.getDoubleArrayWithData(dataList, 2);
        Integer bestXIndex = nhsReportStore.getBestXIndex(regressionModel, covidTestsArray, meanAgeArray, observedPositives);

        MyRegressionModel myRegressionModel = getMyRegressionModel(regressionModel, bestXIndex, covidTestsArray, meanAgeArray, observedPositives, historicalPoints);
        HypothesisTest hypothesisTest = nhsReportStore.createHypothesisTest(regressionModel, myRegressionModel, significanceLevel);
        SignificanceModelAnova modelAnova = nhsReportStore.createSignificanceModelAnova(regressionModel, myRegressionModel, significanceLevel);

        Date startDate = nhsReportStore.getStartDate();
        TableOfValues tableOfValues = getTableOfValues(myRegressionModel, bestXIndex, historicalPoints, startDate, typeOfData);

        NHSReport nhsReport = nhsReportStore.createNHSDailyReport(myRegressionModel,hypothesisTest,modelAnova,tableOfValues);
        if(nhsReportStore.validateNHSDailyReport(nhsReport))
            return nhsReport;
        else
            throw new IllegalArgumentException("The Report must be validated in order to be sent!");
    }


    public List<List<Double>> getDataListToFitTheModel(String typeOfData) throws ParseException {
        String[] intervalDatesInString = dateInterval.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date beginDate = sdf.parse(intervalDatesInString[0]), endDate = sdf.parse(intervalDatesInString[1]);
        List<List<Double>> dataList = testStore.getAllDataToFitTheModel(beginDate, endDate, typeOfData);
        return dataList;
    }

    public MyRegressionModel getMyRegressionModel(RegressionModel regressionModel,
                                                  Integer bestXIndex,
                                                  double[] covidTestsArray,
                                                  double[] meanAgeArray,
                                                  double[] observedPositives,
                                                  int historicalPoints) {

        MyRegressionModel myRegressionModel = (bestXIndex == null) ? nhsReportStore.createMyRegressionModel(regressionModel, covidTestsArray, meanAgeArray, observedPositives, historicalPoints) :
                ((bestXIndex == 1) ? nhsReportStore.createMyBestRegressionModel(regressionModel, covidTestsArray, observedPositives, historicalPoints) : nhsReportStore.createMyBestRegressionModel(regressionModel, meanAgeArray, observedPositives, historicalPoints));
        return myRegressionModel;
    }



    public TableOfValues getTableOfValues(MyRegressionModel myRegressionModel,
                                          Integer bestXIndex,
                                          int historicalPoints,
                                          Date startDate,
                                          String typeOfData) throws IllegalAccessException, InstantiationException, ClassNotFoundException, ParseException {

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

        this.regressionModel = getRegressionModel(regressionModelClass);

        Double[] bestXInHistoricalPoints;
        List<Double> estimatedPositives;

        List<ConfidenceInterval> confidenceIntervals;

        if(bestXIndex != null) {
            //for Simple Linear Regression
            if(bestXIndex == 1)
                bestXInHistoricalPoints = nhsReportStore.copyArray(numCovidTestsInHistoricalPoints);
            else
                bestXInHistoricalPoints = nhsReportStore.copyArray(meanAgeInHistoricalPoints);
            estimatedPositives = this.regressionModel.getEstimatedPositives(myRegressionModel, bestXInHistoricalPoints, null);
            confidenceIntervals = getConfidenceIntervalListForTableOfValues(myRegressionModel, regressionModel, bestXInHistoricalPoints, null, bestXIndex);
        } else {
            //for Multiple Linear Regression
            estimatedPositives = this.regressionModel.getEstimatedPositives(myRegressionModel, numCovidTestsInHistoricalPoints, meanAgeInHistoricalPoints);
            confidenceIntervals = getConfidenceIntervalListForTableOfValues(myRegressionModel, regressionModel, numCovidTestsInHistoricalPoints, meanAgeInHistoricalPoints, bestXIndex);
        }

        TableOfValues tableOfValues = nhsReportStore.createTableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        return tableOfValues;
    }

    public List<ConfidenceInterval> getConfidenceIntervalListForTableOfValues(MyRegressionModel myRegressionModel,
                                                                              RegressionModel regressionModel,
                                                                              Double[] x1InHistoricalPoints,
                                                                              Double[] x2InHistoricalPoints,
                                                                              Integer bestXIndex) {
        List<ConfidenceInterval> confidenceIntervals;
        if(bestXIndex != null) //for Simple Linear Regression
            confidenceIntervals = regressionModel.getConfidenceIntervalList(myRegressionModel, x1InHistoricalPoints, null, confidenceLevel);
        else //For Multiple Linear Regression
            confidenceIntervals = regressionModel.getConfidenceIntervalList(myRegressionModel, x1InHistoricalPoints, x2InHistoricalPoints, confidenceLevel);
        return confidenceIntervals;
    }

    public RegressionModel getRegressionModel(String regressionModelCLass) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> oClass = Class.forName(regressionModelCLass);
        return (RegressionModel) oClass.newInstance();
    }

    public void writeEventIntoLogFile() throws IOException {
        Logger logger = Logger.getLogger(NHSReportTask.class.getSimpleName());
        FileHandler fh;

        fh = new FileHandler("./NHSReport.log", true);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        logger.info("NHS Daily Report sent");

        logger.setUseParentHandlers(false);
    }

    static int cont = 0;

    @Override
    public void run() {


        try {
            this.nhsDayDataReport = createNHSDailyReport(Constants.DAY_DATA);
            this.nhsWeekDataReport = createNHSDailyReport(Constants.WEEK_DATA);

            File path = new File("./NHSReport/");
            if(!path.exists())
                    path.mkdir();

            String dataToBeSent = String.format("----------------------> DAY DATA <----------------------%n%n%s%n%n" +
                    "----------------------> WEEK DATA <----------------------%n%n%s", this.nhsDayDataReport.toString(), this.nhsWeekDataReport);

            Report2NHS.writeUsingFileWriter(dataToBeSent);
            writeEventIntoLogFile();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




