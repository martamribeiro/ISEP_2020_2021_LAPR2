package app.domain.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Represents a Confidence Interval through:
 * a My Regression Model,
 * the predicted value (y0),
 * the confidence level,
 * the error (delta),
 * the limit inferior,
 * the limit superior
 *
 * @author Ana Albergaria
 */

public class ConfidenceInterval implements Serializable {
    /**
     * The MyRegressionModel of the ConfidenceInterval.
     */
    private MyRegressionModel myRegressionModel;
    /**
     * The predicted value of the ConfidenceInterval.
     */
    private double y0;
    /**
     * Partial value of the error (without the critical value) of the ConfidenceInterval.
     */
    private double auxDelta;
    /**
     * The confidence level of the ConfidenceInterval.
     */
    private double confidenceLevel;
    /**
     * The error of the ConfidenceInterval.
     */
    private double delta;
    /**
     * The limit inferior of the ConfidenceInterval.
     */
    private double limInf;
    /**
     * The limit superior of the ConfidenceInterval.
     */
    private double limSup;

    /**
     * Builds a Confidence Interval's instance receiving:
     * the myRegressionModel
     * the y0
     * the auxDelta
     * the confidence level
     *
     * @param myRegressionModel the myRegressionModel of the ConfidenceInterval
     * @param y0 the predicted value of the ConfidenceInterval
     * @param auxDelta the partial value of the error (without the critical value)
     *                 of the ConfidenceInterval
     * @param confidenceLevel the confidence level of the ConfidenceInterval
     */
    public ConfidenceInterval(MyRegressionModel myRegressionModel,
                              double y0,
                              double auxDelta,
                              double confidenceLevel) {
        this.myRegressionModel = myRegressionModel;
        this.y0 = y0;
        this.auxDelta = auxDelta;
        this.confidenceLevel = confidenceLevel;
        double critTD = myRegressionModel.calculateCriticalValTStudent(1.0 - confidenceLevel);
        this.delta = critTD * auxDelta;
        this.limInf = y0 - delta;
        this.limSup = y0 + delta;
    }

    /**
     * Returns the confidence level of the ConfidenceInterval.
     *
     * @return the confidence level of the ConfidenceInterval
     */
    public double getConfidenceLevel() {
        return confidenceLevel;
    }

    /**
     * Returns the textual description of the Confidence Interval's instance.
     *
     * @return characteristics of the ConfidenceInterval
     */
    @Override
    public String toString() {
        return String.format("%.4f-%.4f", limInf, limSup);
    }

    /**
     * Compares the ConfidenceInterval with the received object.
     *
     * @param otherObject the object to be compared with the ConfidenceInterval
     * @return true if the received object represents other ConfidenceInterval
     * equivalent to the ConfidenceInterval. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject)
            return true;

        if(otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        ConfidenceInterval otherConfidenceInterval = (ConfidenceInterval) otherObject;

        DecimalFormat df = new DecimalFormat("#.######");

        return df.format(this.limInf).equals(df.format(otherConfidenceInterval.limInf)) &&
                df.format(this.limSup).equals(df.format(otherConfidenceInterval.limSup));
    }
}




