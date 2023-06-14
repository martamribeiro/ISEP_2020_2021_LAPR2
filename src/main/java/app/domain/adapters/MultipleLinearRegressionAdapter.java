package app.domain.adapters;

import app.domain.interfaces.RegressionModel;
import app.domain.model.ConfidenceInterval;
import app.domain.model.HypothesisTest;
import app.domain.model.MyRegressionModel;
import app.domain.model.SignificanceModelAnova;
import app.thirdparty.regressionModels.MultipleLinearRegression;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for the MultipleLinearRegression Class.
 * Implements the RegressionModel interface.
 *
 * @author Ana Albergaria
 */
public class MultipleLinearRegressionAdapter implements RegressionModel, Serializable {

    /**
     * Returns the index of the best independent variable between two Simple Linear Regressions
     * through evaluating which model is the best.
     * In MLR, this doesn't apply therefore returns null.
     *
     * @param x1 the values of the first independent variable
     * @param x2 the values of the second independent variable
     * @param y the values of the dependent variable
     *
     * @return null because it's a Multiple Linear Regression
     */
    @Override
    public Integer getBestXIndex(double[] x1, double[] x2, double[] y) {
        return null;
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
        MultipleLinearRegression multipleLR = new MultipleLinearRegression(x1, x2, y);
;
        return new MyRegressionModel(multipleLR.getB0(), multipleLR.getB1(), multipleLR.getB2(), multipleLR.getR2(), multipleLR.getR2Adjusted(), historicalPoints, multipleLR);
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
        MultipleLinearRegression multipleLR = (MultipleLinearRegression) myRegressionModel.getRegressionModel();
        double tObsB0 = multipleLR.calculateTObsBj(0);
        double tObsB1 = multipleLR.calculateTObsBj(1);
        double tObsB2 = multipleLR.calculateTObsBj(2);

        return new HypothesisTest(myRegressionModel, tObsB0, tObsB1, tObsB2, significanceLevel);
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
        MultipleLinearRegression multipleLR = (MultipleLinearRegression) myRegressionModel.getRegressionModel();
        double sr = multipleLR.getSR(), se = multipleLR.getSE();

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
        MultipleLinearRegression multipleLR = (MultipleLinearRegression) myRegressionModel.getRegressionModel();

        List<Double> estimatedPositives = new ArrayList<>();

        for (int i = 0; i < x1InHistoricalPoints.length; i++) {
            double estimatedValue = multipleLR.predict(x1InHistoricalPoints[i], x2InHistoricalPoints[i]);
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
        MultipleLinearRegression multipleLR = (MultipleLinearRegression) myRegressionModel.getRegressionModel();
        double y0 = multipleLR.predict(x1, x2);
        double[] x0T = {1.0, x1, x2};
        double auxDelta = multipleLR.calculateAuxDelta(x0T);

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
            ConfidenceInterval confidenceInterval = getConfidenceInterval(myRegressionModel, x1InHistoricalPoints[i], x2InHistoricalPoints[i], confidenceLevel);
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
        MultipleLinearRegression multipleLR = (MultipleLinearRegression) myRegressionModel.getRegressionModel();
        double chosenTObs;
        if(chosenRegCoefficient.equalsIgnoreCase("a"))
            chosenTObs = multipleLR.calculateTObsBj(0);
        else if(chosenRegCoefficient.equalsIgnoreCase("b"))
            chosenTObs = multipleLR.calculateTObsBj(1);
        else
            chosenTObs = multipleLR.calculateTObsBj(2);

        return new HypothesisTest(myRegressionModel, chosenRegCoefficient, chosenTObs, significanceLevel);
    }
}
