package app.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a Table of Values through:
 * a My Regression Model,
 * the dates (in days or weeks) corresponding to the historical points,
 * the observed Covid-19 tests positives
 * the estimated Covid-19 tests positives
 * the prediction confidence intervals
 *
 * @author Ana Albergaria
 */

public class TableOfValues implements Serializable {
    /**
     * The MyRegressionModel of the TableOfValues.
     */
    private MyRegressionModel myRegressionModel;
    /**
     * The dates (in days or weeks) corresponding to the historical points.
     */
    private List<String> dates;
    /**
     * The observed Covid-19 tests positives.
     */
    private int[] observedPositives;
    /**
     * The estimated Covid-19 tests positives.
     */
    List<Double> estimatedPositives;
    /**
     * The prediction confidence intervals
     */
    private List<ConfidenceInterval> confidenceIntervals;

    /**
     * Builds a Table Of Value's instance receiving:
     * the myRegressionModel
     * the dates
     * the observed positives
     * the estimated positives
     * the confidence intervals
     *
     * @param myRegressionModel the myRegressionModel of the TableOfValues
     * @param dates the dates (in days or weeks) corresponding to the historical points
     *              of the TableOfValues
     * @param observedPositives the observed Covid-19 tests positives of the TableOfValues
     * @param estimatedPositives the estimated Covid-19 tests positives of the TableOfValues
     * @param confidenceIntervals the prediction confidence intervals
     */
    public TableOfValues(MyRegressionModel myRegressionModel,
                         List<String> dates,
                         int[] observedPositives,
                         List<Double> estimatedPositives,
                         List<ConfidenceInterval> confidenceIntervals) {
        this.myRegressionModel = myRegressionModel;
        this.dates = dates;
        this.observedPositives = observedPositives;
        this.estimatedPositives = estimatedPositives;
        this.confidenceIntervals = confidenceIntervals;
    }

    /**
     * Returns the confidence level in percentage.
     *
     * @return the confidence level in percentage
     */
    public String getConfidenceLevelInPercentage() {
        double confidenceLevel = confidenceIntervals.get(0).getConfidenceLevel() * 100;
        return String.valueOf(confidenceLevel);
    }

    /**
     * Returns the textual description of the TableOfValues' instance.
     *
     * @return characteristics of the TableOfValues
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append(String.format("%-25s%-50s%-63s%-50s%n", "Date", "Number of OBSERVED positive cases", "Number of ESTIMATED/EXPECTED positive cases", getConfidenceLevelInPercentage() + "% intervals"));
        for (int i = 0; i < myRegressionModel.getNumberOfObservations(); i++) {
            text.append(String.format("%-40s%-50d%-49.4f%-35s%n", dates.get(i), observedPositives[i], estimatedPositives.get(i), confidenceIntervals.get(i).toString()));
        }
        return text.toString();
    }

    /**
     * Compares the TableOfValues with the received object.
     *
     * @param otherObject the object to be compared with the TableOfValues
     * @return true if the received object represents other TableOfValues
     * equivalent to the TableOfValues. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject)
            return true;

        if(otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        TableOfValues otherTableOfValues = (TableOfValues) otherObject;

        return this.myRegressionModel.equals(otherTableOfValues.myRegressionModel) &&
                this.dates.equals(otherTableOfValues.dates) &&
                this.observedPositives.equals(otherTableOfValues.observedPositives) &&
                this.estimatedPositives.equals(otherTableOfValues.estimatedPositives) &&
                this.confidenceIntervals.equals(otherTableOfValues.confidenceIntervals);
    }
}
