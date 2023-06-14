package app.domain.interfaces;

import app.domain.model.ConfidenceInterval;
import app.domain.model.HypothesisTest;
import app.domain.model.MyRegressionModel;
import app.domain.model.SignificanceModelAnova;

import java.util.List;

/**
 * Interface to be used on the adapters of the available Regression Models.
 *
 * @author Ana Albergaria
 */

public interface RegressionModel {
    /**
     * Returns the index of the best independent variable between two Simple Linear Regressions
     * through evaluating which model is the best.
     *
     * @param x1 the values of the first independent variable
     * @param x2 the values of the second independent variable
     * @param y the values of the dependent variable
     *
     * @return index of the best independent variable
     */
    public abstract Integer getBestXIndex(double[] x1, double[] x2, double[] y);

    /**
     * Returns the Regression Model of the NHS Report to be  automatically sent.
     *
     * @param x1 the values of the first independent variable
     * @param x2 the values of the second independent variable
     * @param y the values of the dependent variable
     * @param historicalPoints the historical points
     *
     * @return the Regression Model of the NHS Report to be sent
     */
    public abstract MyRegressionModel getRegressionModel(double[] x1, double[] x2, double[] y, int historicalPoints);

    /**
     * Returns the Hypothesis Test of the NHS Report to be sent.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param significanceLevel the significance level
     *
     * @return the Hypothesis Test of the NHS Report to be sent
     */
    public abstract HypothesisTest getHypothesisTest(MyRegressionModel myRegressionModel, double significanceLevel);

    /**
     * Returns the Anova Significance Model of the NHS Report to be sent.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param significanceLevel the significance level
     *
     * @return the Anova Significance Model of the NHS Report to be sent
     */
    public abstract SignificanceModelAnova getSignificanceModelAnova(MyRegressionModel myRegressionModel, double significanceLevel);

    /**
     * Returns the estimated Covid-19 positives regarding the MyRegressionModel
     * of the NHSReport to be sent.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param x1InHistoricalPoints values of the first independent variable
     *                             during the defined historical points
     * @param x2InHistoricalPoints values of the second independent variable
     *                             during the defined historical points
     *
     * @return estimated Covid-19 positives regarding the MyRegressionModel of the NHSReport
     */
    public abstract List<Double> getEstimatedPositives(MyRegressionModel myRegressionModel, Double[] x1InHistoricalPoints, Double[] x2InHistoricalPoints);

    /**
     * Returns a Confidence Interval of the MyRegressionModel of the NHSReport
     * to be sent.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param x1 the value of the first independent variable
     * @param x2 the value of the second independent variable
     * @param confidenceLevel the confidence level
     *
     * @return a Confidence Interval of the MyRegressionModel of the NHSReport
     */
    public abstract ConfidenceInterval getConfidenceInterval(MyRegressionModel myRegressionModel, Double x1, Double x2, double confidenceLevel);

    /**
     * Returns the list of Confidence Intervals of the MyRegressionModel of the NHSReport
     * to be sent
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param x1InHistoricalPoints the values of the first independent variable
     * @param x2InHistoricalPoints the values of the second independent variable
     * @param confidenceLevel the confidence level
     *
     * @return the list of Confidence Intervals of the MyRegressionModel of the NHSReport
     */
    public abstract List<ConfidenceInterval> getConfidenceIntervalList(MyRegressionModel myRegressionModel, Double[] x1InHistoricalPoints, Double[] x2InHistoricalPoints, double confidenceLevel);

    /**
     * Returns the Hypothesis Test for the parameter chosen by the administrator of the NHS Report to be sent.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param significanceLevel the significance level
     * @param chosenRegCoefficient the chosen parameter
     *
     * @return the Hypothesis Test of the NHS Report to be sent
     *
     */
    public abstract HypothesisTest getChosenHypothesisTest(MyRegressionModel myRegressionModel, double significanceLevel, String chosenRegCoefficient);
}

