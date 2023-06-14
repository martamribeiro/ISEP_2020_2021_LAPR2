package app.domain.model;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import java.io.Serializable;

/**
 * Represents a MyRegressionModel through:
 * the intercept, the slope, the second independent variable (if it exists - for MLR),
 * the correlation coefficient r (if it exists - for SLR),
 * the determination coefficient r2, the adjusted r2,
 * the number of observations and
 * the regression model to be used (SLR or MLR) to do all the necessary calculus.
 *
 * This class exists in order to allow every Adapter class to return the same type of object.
 * (Adapter Pattern)
 *
 * LEGEND:
 * - SLR: Simple Linear Regression
 * - MLR: Multiple Linear Regression
 *
 * @author Ana Albergaria
 */
public class MyRegressionModel implements Serializable {
    /**
     * The intercept of the MyRegressionModel.
     */
    private final double intercept; //intercept - y, a
    /**
     * The slope of the MyRegressionModel.
     */
    private final double slope; //slope - x, b
    /**
     * The second independent variable of the MyRegressionModel, if it exists.
     * (only applicable for MLR)
     */
    private Double secondIndVariable;
    /**
     * The correlation coefficient of the MyRegressionModel, if it exists.
     * (only applicable for SLR)
     */
    private double r;
    /**
     * The determination coefficient of the MyRegression Model.
     */
    private final double r2;
    /**
     * The r2 adjusted of the MyRegressionModel.
     */
    private final double r2Adjusted;
    /**
     * The number of the observations of the MyRegressionModel.
     */
    private final int numberOfObservations;
    /**
     * The regression model (SLR or MLR) to be used to do all the necessary calculus
     * of the MyRegressionModel.
     */
    private final Object regressionModel;

    /**
     * The number of the Regression Coefficients of the Multiple Linear Regression.
     * (it doesn't include B0)
     *
     */
    private static final int MLR_NUM_REG_COEFFICIENTS = 2;

    /**
     * CONSTRUCTOR FOR MULTIPLE LINEAR REGRESSION
     *
     * Builds a MyRegression's instance receiving:
     * the intercept
     * the slope
     * the second independent variable
     * the r2
     * the r2 adjusted
     * the number of observations
     * the regression model to be used
     *
     * @param intercept the intercept of the MyRegressionModel
     * @param slope the slope of the MyRegressionModel
     * @param secondIndVariable the second independent variable of the MyRegressionModel
     * @param r2 the determination coefficient of the MyRegression Model
     * @param r2Adjusted the r2 adjusted of the MyRegressionModel
     * @param numberOfObservations the number of the observations of the MyRegressionModel
     * @param regressionModel the regression model to be used (MLR) to do
     *                        all the necessary calculus of the MyRegressionModel.
     */
    public MyRegressionModel(double intercept,
                             double slope,
                             Double secondIndVariable,
                             double r2,
                             double r2Adjusted,
                             int numberOfObservations,
                             Object regressionModel) {
        this.intercept = intercept;
        this.slope = slope;
        this.secondIndVariable = secondIndVariable;
        this.r2 = r2;
        this.r2Adjusted = r2Adjusted;
        this.numberOfObservations = numberOfObservations;
        this.regressionModel = regressionModel;
    }

    /**
     * CONSTRUCTOR FOR SIMPLE LINEAR REGRESSION
     *
     * Builds a MyRegression's instance receiving:
     * the intercept
     * the slope
     * the r2
     * the r2 adjusted
     * the number of observations
     * the regression model to be used
     *
     * @param intercept the intercept of the MyRegressionModel
     * @param slope the slope of the MyRegressionModel
     * @param r2 the determination coefficient of the MyRegression Model
     * @param r2Adjusted the r2 adjusted of the MyRegressionModel
     * @param numberOfObservations the number of the observations of the MyRegressionModel
     * @param regressionModel the regression model to be used (SLR) to do
     *                        all the necessary calculus of the MyRegressionModel.
     */
    public MyRegressionModel(double intercept,
                             double slope,
                             double r2,
                             double r2Adjusted,
                             int numberOfObservations,
                             Object regressionModel) {
        this.intercept = intercept;
        this.slope = slope;
        this.r2 = r2;
        this.r = Math.sqrt(r2);
        this.r2Adjusted = r2Adjusted;
        this.numberOfObservations = numberOfObservations;
        this.regressionModel = regressionModel;
    }

    /**
     * Returns the slope of the MyRegressionModel.
     *
     * @return the slope of the MyRegressionModel
     */
    public double getSlope() {
        return slope;
    }

    /**
     * Returns the number of observations of the MyRegressionModel.
     *
     * @return the number of observations of the MyRegressionModel
     */
    public int getNumberOfObservations() {
        return numberOfObservations;
    }

    /**
     * Returns the second independent variable of the MyRegressionModel.
     *
     * @return the second independent variable of the MyRegressionModel
     */
    public Double getSecondIndVariable() {
        return secondIndVariable;
    }

    /**
     * Returns the regression model to be used (SLR or MLR) of the MyRegressionModel.
     *
     * @return the regression model to be used (SLR or MLR) of the MyRegressionModel
     */
    public Object getRegressionModel() {
        return regressionModel;
    }

    /**
     * Finds the critical value for the Fisher-Snedecor Distribution.
     *
     * @param numeratorDegreesOfFreedom the degrees of freedom of the numerator
     * @param denominatorDegreesOfFreedom the degrees of freedom of the the denominator
     * @param significanceLevel the significance level
     *
     * @return the critical value for the Fisher-Snedecor Distribution
     */
    public double calculateCriticalValFSnedecor(int numeratorDegreesOfFreedom,
                                                int denominatorDegreesOfFreedom,
                                                double significanceLevel) {
        FDistribution fd = new FDistribution(numeratorDegreesOfFreedom,denominatorDegreesOfFreedom);
        double alphaFD = significanceLevel;
        double critFD = fd.inverseCumulativeProbability(1- alphaFD);
        return critFD;
    }

    /**
     * Finds the critical value for the T-Student Distribution.
     *
     * @param significanceLevel the significance level
     *
     * @return the critical value for the T-Student Distribution
     */
    public double calculateCriticalValTStudent(double significanceLevel) {
        int n = numberOfObservations;
        int degreesOfFreedom = (secondIndVariable == null) ? n-2 : n-(MLR_NUM_REG_COEFFICIENTS + 1);

        double critTD;
        TDistribution td = new TDistribution(degreesOfFreedom);
        double alphaTD = 1 - (significanceLevel / 2);
        if(alphaTD > 0.5) {
            critTD = td.inverseCumulativeProbability(alphaTD);
        } else {
            critTD = td.inverseCumulativeProbability(1 - alphaTD);
        }
        return critTD;
    }

    /**
     * Returns the textual description of the MyRegressionModel instance.
     *
     * @return characteristics of the MyRegressionModel
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("The regression model fitted using data from the interval\n");
        if(secondIndVariable == null)
            text.append(String.format("^y=%.4fx + %.4f%n//%n", slope, intercept));
        else
            text.append(String.format("^y=%.4f + %.4f x1 + %.4f x2%n", slope, intercept, secondIndVariable)); //corrigir - RLMúltipla
        text.append(String.format("Other statistics%nR2 = %.4f%nR2 adjusted = %.4f%n", r2, r2Adjusted));
        if(secondIndVariable == null)
            text.append(String.format("R = %.4f%n", r));
        text.append("//\n");

        return text.toString();
    }

    /**
     * Compares the MyRegressionModel with the received object.
     *
     * @param otherObject the object to be compared with the MyRegressionModel
     * @return true if the received object represents other MyRegressionModel
     * equivalent to the MyRegressionModel. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject)
            return true;

        if(otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        MyRegressionModel otherMyRegressionModel = (MyRegressionModel) otherObject;

        if(secondIndVariable == null) {
            return this.intercept == otherMyRegressionModel.intercept &&
                    this.slope == otherMyRegressionModel.slope &&
                    this.r == otherMyRegressionModel.r &&
                    this.r2 == otherMyRegressionModel.r2 &&
                    this.r2Adjusted == otherMyRegressionModel.r2Adjusted &&
                    this.numberOfObservations == otherMyRegressionModel.numberOfObservations;
        } else {
            return this.intercept == otherMyRegressionModel.intercept &&
                    this.slope == otherMyRegressionModel.slope &&
                    this.secondIndVariable.equals(otherMyRegressionModel.secondIndVariable) &&
                    this.r2 == otherMyRegressionModel.r2 &&
                    this.r2Adjusted == otherMyRegressionModel.r2Adjusted &&
                    this.numberOfObservations == otherMyRegressionModel.numberOfObservations;
        }

    }
}
