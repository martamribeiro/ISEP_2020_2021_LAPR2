package app.domain.model;

import java.io.Serializable;

/**
 * Represents a NHS Report through:
 * the My Regression Model,
 * the Hypothesis Test containing the results for all the regression coefficients,
 * the Anova Significance Model of the Regression Model,
 * the Table of Values of the Regression Model
 *
 * @author Ana Albergaria
 */
public class NHSReport implements Serializable {
    /**
     * The myRegressionModel of the NHSReport.
     */
    private MyRegressionModel myRegressionModel;
    /**
     * The hypothesis test of the NHSReport.
     */
    private HypothesisTest hypothesisTest;
    /**
     * The Anova Significance Model of the NHSReport.
     */
    private SignificanceModelAnova modelAnova;
    /**
     * The Table Of Values of the NHSReport.
     */
    private TableOfValues tableOfValues;

    /**
     * Builds a NHSReport's instance receiving:
     * the myRegressionModel,
     * the hypothesis test,
     * the Significance Model Anova,
     * the Table Of Values
     *
     * @param myRegressionModel the myRegressionModel of the NHSReport
     * @param hypothesisTest the hypothesis test of the NHSReport
     * @param modelAnova the Anova Significance Model of the NHSReport
     * @param tableOfValues the Table Of Values of the NHSReport
     */
    public NHSReport(MyRegressionModel myRegressionModel,
                     HypothesisTest hypothesisTest,
                     SignificanceModelAnova modelAnova,
                     TableOfValues tableOfValues) {
        this.myRegressionModel = myRegressionModel;
        this.hypothesisTest = hypothesisTest;
        this.modelAnova = modelAnova;
        this.tableOfValues = tableOfValues;
    }

    /**
     * Returns the myRegressionModel of the NHSReport.
     *
     * @return the myRegressionModel of the NHSReport
     */
    public MyRegressionModel getMyRegressionModel() {
        return myRegressionModel;
    }

    /**
     * Returns the hypothesisTest of the NHSReport.
     *
     * @return the hypothesisTest of the NHSReport
     */
    public HypothesisTest getHypothesisTest() {
        return hypothesisTest;
    }

    /**
     * Returns the Anova Significance Model of the NHSReport.
     *
     * @return the Anova Significance Model of the NHSReport
     */
    public SignificanceModelAnova getModelAnova() {
        return modelAnova;
    }

    /**
     * Returns the Table Of Values of the NHSReport.
     *
     * @return the Table Of Values of the NHSReport
     */
    public TableOfValues getTableOfValues() {
        return tableOfValues;
    }

    /**
     * Returns the textual description of the NHSReport's instance.
     *
     * @return characteristics of the NHSReport
     */
    @Override
    public String toString() {
        return String.format("%s%n%s%n%s%n%s",
                myRegressionModel, hypothesisTest, modelAnova, tableOfValues);
    }

    public String toStringForChosenHypothesisTest() {
        return String.format("%s%n%s%n%s%n%s",
                myRegressionModel, hypothesisTest.toStringForChosenRegCoeffient(), modelAnova, tableOfValues);
    }
}
