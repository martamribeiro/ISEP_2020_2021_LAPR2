package app.domain.store;

import app.domain.interfaces.RegressionModel;
import app.domain.model.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Represents the NHS Report Store
 *
 * @author Ana Albergaria
 */
public class NHSReportStore implements Serializable {

    /**
     * Method which returns the NHS Report to be sent, receiving:
     * the myRegressionModel, the hypothesisTest,
     * the modelAnova and the TableOfValues
     *
     *
     * @param myRegressionModel the Regression Model used to compute the data
     * @param hypothesisTest the Hypothesis Test of the Regression Model
     * @param modelAnova the Anova Significance Model of the Regression Model
     * @param tableOfValues the Table Of Values of the Regression Model
     *
     * @return the NHS Report to be sent
     */
    public NHSReport createNHSDailyReport(MyRegressionModel myRegressionModel, HypothesisTest hypothesisTest, SignificanceModelAnova modelAnova, TableOfValues tableOfValues) {
        return new NHSReport(myRegressionModel, hypothesisTest, modelAnova, tableOfValues);
    }

    /**
     * Method which validates if the NHS Report
     *
     * @param nhsReport the NHS Report to be validated
     *
     * @return true if the new NHS Report was successfully validated.
     *          Otherwise, returns false.
     */
    public boolean validateNHSDailyReport(NHSReport nhsReport) {
        return nhsReport != null && nhsReport.getMyRegressionModel() != null &&
                nhsReport.getHypothesisTest() != null && nhsReport.getModelAnova() != null && nhsReport.getTableOfValues() != null;
    }

    /**
     * Returns the index of the variable of the best Regression Model.
     *
     * @param regressionModel the class of the Regression Model who will obtain the result
     * @param x1 the values of the first independent variable
     * @param x2 the values of the second independent variable
     * @param y the values of the dependent variable
     *
     * @return index of the varibale of the best Regression Model.
     *          Returns null if the Regression Model is Multiple Linear Regression.
     */
    public Integer getBestXIndex(RegressionModel regressionModel,
                             double[] x1,
                             double[] x2,
                             double[] y) {

        return regressionModel.getBestXIndex(x1, x2, y);
    }

    /**
     * Returns the MyRegressionModel.
     *
     * @param regressionModel the class of the Regression Model who will obtain the result
     * @param bestX the values of the best independent variable
     * @param y the values of the dependent variable
     * @param historicalPoints the historical points
     *
     * @return the myRegressionModel
     */
    public MyRegressionModel createMyBestRegressionModel(RegressionModel regressionModel,
                                                         double[] bestX,
                                                         double[] y,
                                                         int historicalPoints) {
        MyRegressionModel myRegressionModel = regressionModel.getRegressionModel(bestX, null, y, historicalPoints);
        return myRegressionModel;
    }

    public MyRegressionModel createMyRegressionModel(RegressionModel regressionModel,
                                                     double[] x1,
                                                     double[] x2,
                                                     double[] y,
                                                     int historicalPoints) {

        MyRegressionModel myRegressionModel = regressionModel.getRegressionModel(x1,
                x2, y, historicalPoints);
        return myRegressionModel;
    }

    /**
     * Returns the Hypothesis Test of the myRegressionModel.
     *
     * @param regressionModel the class of the Regression Model who will obtain the result
     * @param myRegressionModel the myRegressionModel
     * @param significanceLevel the significance level
     *
     * @return the Hypothesis Test of the myRegressionModel
     */
    public HypothesisTest createHypothesisTest(RegressionModel regressionModel, MyRegressionModel myRegressionModel, double significanceLevel) {
        return regressionModel.getHypothesisTest(myRegressionModel, significanceLevel);
    }

    /**
     * Returns the SignificanceModelAnova.
     *
     * @param regressionModel the class of the Regression Model who will obtain the result
     * @param myRegressionModel the myRegressionModel
     * @param significanceLevel the significance level
     *
     * @return the SignificanceModelAnova of the myRegressionModel
     */
    public SignificanceModelAnova createSignificanceModelAnova(RegressionModel regressionModel, MyRegressionModel myRegressionModel, double significanceLevel) {
        return regressionModel.getSignificanceModelAnova(myRegressionModel, significanceLevel);
    }

    /**
     * Returns the TableOfValues of the myRegressionModel
     *
     * @param myRegressionModel the myRegressionModel
     * @param dates the dates of the historical points
     * @param observedPositives the observed positives
     * @param estimatedPositives the estimated positives
     * @param confidenceIntervals the confidence intervals
     *
     * @return the TableOfValues of the myRegressionModel
     */
    public TableOfValues createTableOfValues(MyRegressionModel myRegressionModel, List<String> dates, int[] observedPositives, List<Double> estimatedPositives, List<ConfidenceInterval> confidenceIntervals) {
        return new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
    }

    /**
     * Extracts the wished list from the list which contains all the lists
     * with the 3 variables
     *
     * @param covidTestAndMeanAgeList total list
     * @param index index of the list to be extracted
     *
     * @return the wished list from the list which contains all the lists
     */
    public double[] getDoubleArrayWithData(List<List<Double>> covidTestAndMeanAgeList, int index) {
        double[] wishedArray = new double[covidTestAndMeanAgeList.get(index).size()];
        for (int i = 0; i < wishedArray.length; i++) {
            wishedArray[i] = covidTestAndMeanAgeList.get(index).get(i);
        }
        return wishedArray;
    }

    /**
     * Returns a copy of a Double[] array
     *
     * @param array the array whose content is to be copied
     *
     * @return a copy of a Double[] array
     */
    public Double[] copyArray(Double[] array) {
        Double[] wishedArray = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            wishedArray[i] = array[i];
        }
        return wishedArray;
    }

    /**
     * Returns the daily dates to Table of Values.
     *
     *
     * @param numberOfObservations number of observations
     * @param currentDate current Date
     *
     * @return the daily dates to Table of Values
     */
    public List<String> getDatesColumnToTableOfValues(int numberOfObservations,
                                                        Date currentDate) {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);

        for (int i = 0; i < numberOfObservations; i++) {
            dates.add(sdf.format(currentDate));
            cal.add(Calendar.DAY_OF_MONTH,-1);
            if ((cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
                cal.add(Calendar.DAY_OF_MONTH,-1);
            currentDate = cal.getTime();
        }
        return dates;
    }

    /**
     * Returns the weekly dates to Table of Values.
     *
     *
     * @param numberOfObservations number of observations
     * @param currentDate current Date
     *
     * @return the weekly dates to Table of Values
     */
    public List<String> getWeeksColumnToTableOfValues(int numberOfObservations,
                                                      Date currentDate) {
        List<String> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        while ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        Date endDate = cal.getTime();
        for (int i = 0; i < numberOfObservations; i++) {
            cal.add(Calendar.DAY_OF_MONTH, -5);
            Date initialDate = cal.getTime();
            dates.add(getWeek(initialDate, endDate));
            cal.add(Calendar.DAY_OF_MONTH, -2);
            endDate = cal.getTime();
        }

        return dates;
    }

    /**
     * Returns the string with the weeks in the format "DD/MM/YYYY-DD/MM/YYYY".
     *
     * @param initialDate initial Date
     * @param endDate end Date
     *
     * @return the string with the weeks in the format "DD/MM/YYYY-DD/MM/YYYY"
     */
    public String getWeek(Date initialDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%s-%s", sdf.format(initialDate), sdf.format(endDate));
    }

    /**
     * Get start date for US19
     *
     * @return start date for US19
     */
    public Date getStartDate() {
        Date currentDate = new Date();
        Calendar oneDayBefore = Calendar.getInstance();
        oneDayBefore.setTime(currentDate);
        oneDayBefore.add(Calendar.DAY_OF_MONTH, -1);
        if ((oneDayBefore.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
            oneDayBefore.add(Calendar.DAY_OF_MONTH, -1);

        return oneDayBefore.getTime();
    }

    /**
     * Get start date for US18 (selected date)
     *
     * @param currentDate current date
     *
     * @return start date for US18 (selected date)
     */
    public Date getStartDateForSelectedDate(Date currentDate) {
        Calendar oneDayBefore = Calendar.getInstance();
        oneDayBefore.setTime(currentDate);
        oneDayBefore.add(Calendar.DAY_OF_MONTH, -1);
        if ((oneDayBefore.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
            oneDayBefore.add(Calendar.DAY_OF_MONTH, -1);

        return oneDayBefore.getTime();
    }
}
