package app.domain.adapters;

import app.domain.interfaces.RegressionModel;
import app.domain.model.*;
import app.thirdparty.regressionModels.SimpleLinearRegression;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the SimpleLinearRegression Class.
 * Implements the RegressionModel interface.
 *
 * @author Ana Albergaria
 */
public class SimpleLinearRegressionAdapter implements RegressionModel , Serializable {

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
    @Override
    public Integer getBestXIndex(double[] x1, double[] x2, double[] y) {
        SimpleLinearRegression simpleLRx1 = new SimpleLinearRegression(x1, y);
        SimpleLinearRegression simpleLRx2 = new SimpleLinearRegression(x2, y);
        //FALTA FAZER O SIGNIFICANCE MODEL ANOVA
        int x1Index = 1, x2Index = 2;

        return (simpleLRx1.R2() >= simpleLRx2.R2()) ? x1Index : x2Index;
    }

    /**
     * Returns the Regression Model of the NHS Report to be sent.
     *
     * @param x1 the values of the first independent variable
     * @param x2 the values of the second independent variable
     * @param y the values of the dependent variable
     * @param historicalPoints the historical points
     *
     * @return the Regression Model of the NHS Report to be sent
     */
    @Override
    public MyRegressionModel getRegressionModel(double[] x1, double[] x2, double[] y, int historicalPoints) {
        SimpleLinearRegression simpleLRx1 = new SimpleLinearRegression(x1, y);

        return new MyRegressionModel(simpleLRx1.intercept(), simpleLRx1.slope(),
                simpleLRx1.R2(), simpleLRx1.getR2Adjusted(), historicalPoints, simpleLRx1);
    }

    /**
     * Returns the Hypothesis Test of the NHS Report to be sent.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param significanceLevel the significance level
     *
     * @return the Hypothesis Test of the NHS Report to be sent
     */
    @Override
    public HypothesisTest getHypothesisTest(MyRegressionModel myRegressionModel, double significanceLevel) {
        SimpleLinearRegression simpleLR = (SimpleLinearRegression) myRegressionModel.getRegressionModel();
        //for a, for b
        double tObsA = simpleLR.calculatetObsA(), tObsB = simpleLR.calculateTObsB();
        return new HypothesisTest(myRegressionModel, tObsA, tObsB, significanceLevel);
    }

    /**
     * Returns the Anova Significance Model of the NHS Report to be sent.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param significanceLevel the significance level
     *
     * @return the Anova Significance Model of the NHS Report to be sent
     */
    @Override
    public SignificanceModelAnova getSignificanceModelAnova(MyRegressionModel myRegressionModel, double significanceLevel) {
        SimpleLinearRegression simpleLR = (SimpleLinearRegression) myRegressionModel.getRegressionModel();
        double sr = simpleLR.getSSR(), se = simpleLR.getRSS();
        return new SignificanceModelAnova(myRegressionModel, sr, se, significanceLevel);
    }

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
    @Override
    public List<Double> getEstimatedPositives(MyRegressionModel myRegressionModel, Double[] x1InHistoricalPoints, Double[] x2InHistoricalPoints) {
        SimpleLinearRegression simpleLR = (SimpleLinearRegression) myRegressionModel.getRegressionModel();

        List<Double> estimatedPositives = new ArrayList<>();

        for (int i = 0; i < x1InHistoricalPoints.length; i++) {
            double estimatedValue = simpleLR.predict(x1InHistoricalPoints[i]);
            estimatedPositives.add(estimatedValue);
        }
        return estimatedPositives;
    }

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
    @Override
    public ConfidenceInterval getConfidenceInterval(MyRegressionModel myRegressionModel, Double x1, Double x2, double confidenceLevel) {
        //x1 is x0
        SimpleLinearRegression simpleLR = (SimpleLinearRegression) myRegressionModel.getRegressionModel();
        double y0 = simpleLR.predict(x1);
        double auxDelta = simpleLR.calculateAuxDelta(x1);

        return new ConfidenceInterval(myRegressionModel, y0, auxDelta, confidenceLevel);
    }

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
    @Override
    public List<ConfidenceInterval> getConfidenceIntervalList(MyRegressionModel myRegressionModel, Double[] x1InHistoricalPoints, Double[] x2InHistoricalPoints, double confidenceLevel) {
        int numberOfObservations = myRegressionModel.getNumberOfObservations();
        List<ConfidenceInterval> confidenceIntervals = new ArrayList<>();

        for (int i = 0; i < numberOfObservations; i++) {
            ConfidenceInterval confidenceInterval = getConfidenceInterval(myRegressionModel, x1InHistoricalPoints[i], null, confidenceLevel);
            confidenceIntervals.add(confidenceInterval);
        }
        return confidenceIntervals;
    }

    /**
     * Returns the Hypothesis Test of the NHS Report to be sent for a chosen regression coefficient.
     *
     * @param myRegressionModel the myRegressionModel of the NHS Report
     * @param significanceLevel the significance level
     * @param chosenRegCoefficient the chosen regression coefficient
     *
     * @return the Hypothesis Test for a chosen regression coefficient of the NHS Report to be sent
     */
    @Override
    public HypothesisTest getChosenHypothesisTest(MyRegressionModel myRegressionModel, double significanceLevel, String chosenRegCoefficient) {
        SimpleLinearRegression simpleLR = (SimpleLinearRegression) myRegressionModel.getRegressionModel();
        double chosenTObs = (chosenRegCoefficient.equalsIgnoreCase("a")) ? simpleLR.calculatetObsA() : simpleLR.calculateTObsB();
        return new HypothesisTest(myRegressionModel, chosenRegCoefficient, chosenTObs, significanceLevel);
    }
}

