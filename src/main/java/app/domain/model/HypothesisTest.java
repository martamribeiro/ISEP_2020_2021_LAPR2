package app.domain.model;

import java.io.Serializable;

/**
 * Represents a Hypothesis Test through:
 * a My Regression Model,
 * the observed value regarding the first regression coefficient (tObsA),
 * the observed value regarding the second regression coefficient (tObsB),
 * the observed value regarding the third regression coefficient (tObsC) if applicable,
 * the significance level
 * the critical value for the T-Student Distribution
 *
 * @author Ana Albergaria
 */

public class HypothesisTest implements Serializable {
    /**
     * The myRegressionModel of the HypothesisTest.
     */
    private MyRegressionModel myRegressionModel;
    /**
     * The observed value regarding the first regression coefficient of the HypothesisTest.
     */
    private double tObsA;
    /**
     * The observed value regarding the second regression coefficient of the HypothesisTest.
     */
    private double tObsB;
    /**
     * The observed value regarding the third regression coefficient of the HypothesisTest.
     * (only for MLR)
     */
    private double tObsC;
    /**
     * The signficance level of the HypothesisTest.
     */
    private double significanceLevel;
    /**
     * The critical value for the T-Student Distribution of the HypothesisTest.
     */
    private double critTD;

    //========== ATTRIBUTES FOR THE ANOVA SIGNIFICANCE MODEL ================//

    /**
     * The observed value f for the Test for Significance of Regression.
     */
    private double f;
    /**
     * The critical value for the Fisher-Snedecor Distribution of the HypothesisTest.
     */
    private double critFD;
    /**
     * The numerator of the degrees of freedom of the critical value
     * for the Fisher-Snedecor Distribution.
     */
    private int numeratorDegreesOfFreedom;
    /**
     * The denominator of the degrees of freedom of the critical value
     * for the Fisher-Snedecor Distribution.
     */
    private int denominatorDegreesOfFreedom;
    /**
     * The observed value for the chosen regression coefficient, if applicable.
     */
    private double chosenTObs;
    /**
     * The chosen regression coefficient, if applicable.
     */
    private String chosenRegCoefficient;


    /**
     * CONSTRUCTOR FOR SIMPLE LINEAR REGRESSION
     *
     * Builds a Hypothesis Test's instance receiving:
     * the myRegressionModel
     * the observed value for the first regression coefficient (tObsA)
     * the observed value for the second regression coefficient (tObsB)
     * the significance level
     *
     * @param myRegressionModel the myRegressionModel of the HypothesisTest
     * @param tObsA the observed value for the first regression coefficient of the HypothesisTest
     * @param tObsB the observed value for the second regression coefficient of the HypothesisTest
     * @param significanceLevel the significance level of the HypothesisTest
     */
    public HypothesisTest(MyRegressionModel myRegressionModel,
                          double tObsA,
                          double tObsB,
                          double significanceLevel) {
        this.myRegressionModel = myRegressionModel;
        this.tObsA = tObsA;
        this.tObsB = tObsB;
        this.significanceLevel = significanceLevel;
        this.critTD = myRegressionModel.calculateCriticalValTStudent(significanceLevel);
    }

    /**
     * CONSTRUCTOR FOR MULTIPLE LINEAR REGRESSION
     *
     * Builds a HypothesisTest's instance receiving:
     * the myRegressionModel
     * the observed value for the first regression coefficient (tObsA)
     * the observed value for the second regression coefficient (tObsB)
     * the observed value for the third regression coefficient (tObsC)
     * the significance level
     *
     * @param myRegressionModel the myRegressionModel of the HypothesisTest
     * @param tObsA the observed value for the first regression coefficient of the HypothesisTest
     * @param tObsB the observed value for the second regression coefficient of the HypothesisTest
     * @param tObsC the observed value for the third regression coefficient of the HypothesisTest
     * @param significanceLevel the significance level of the HypothesisTest
     */
    public HypothesisTest(MyRegressionModel myRegressionModel,
                          double tObsA,
                          double tObsB,
                          double tObsC,
                          double significanceLevel) {
        this.myRegressionModel = myRegressionModel;
        this.tObsA = tObsA;
        this.tObsB = tObsB;
        this.tObsC = tObsC;
        this.significanceLevel = significanceLevel;
        this.critTD = myRegressionModel.calculateCriticalValTStudent(significanceLevel);
    }

    /**
     * CONSTRUCTOR FOR THE HYPOTHESIS TEST OF THE ANOVA SIGNIFICANCE MODEL
     *
     * Builds a HypothesisTest's instance receiving:
     * the myRegressionModel
     * the observed value f for the Test for Significance of Regression
     * the numerator of the degrees of freedom of the critical value for the Fisher-Snedecor Distribution
     * the denominator of the degrees of freedom of the critical value for the Fisher-Snedecor Distribution
     * the significance level
     *
     * @param myRegressionModel the myRegressionModel of the HypothesisTest
     * @param f the observed value f for the Test for Significance of Regression of the HypothesisTest
     * @param numeratorDegreesOfFreedom the numerator of the degrees of freedom of the
     *                                  the critical value for the Fisher-Snedecor Distribution
     *                                  of the HypothesisTest
     * @param denominatorDegreesOfFreedom the denominator of the degrees of freedom of the
     *                                    critical value for the Fisher-Snedecor Distribution
     *                                    of the HypothesisTest
     * @param significanceLevel the significance level of the HypothesisTest
     */
    public HypothesisTest(MyRegressionModel myRegressionModel,
                          double f,
                          int numeratorDegreesOfFreedom,
                          int denominatorDegreesOfFreedom,
                          double significanceLevel) {
        this.myRegressionModel = myRegressionModel;
        this.f = f;
        this.numeratorDegreesOfFreedom = numeratorDegreesOfFreedom;
        this.denominatorDegreesOfFreedom = denominatorDegreesOfFreedom;
        this.significanceLevel = significanceLevel;
        this.critFD = myRegressionModel.calculateCriticalValFSnedecor(numeratorDegreesOfFreedom,
                denominatorDegreesOfFreedom, significanceLevel);
    }

    /**
     * CONSTRUCTOR FOR A CHOSEN REGRESSION COEFFICIENT
     *
     * @param myRegressionModel the myRegressionModel of the HypothesisTest
     * @param chosenTObs the observed value for the chosen regression coefficient
     *                   of the HypothesisTest
     * @param significanceLevel the significance level of the HypothesisTest
     */
    public HypothesisTest(MyRegressionModel myRegressionModel,
                          String chosenRegCoefficient,
                          double chosenTObs,
                          double significanceLevel) {
        this.myRegressionModel = myRegressionModel;
        this.chosenRegCoefficient = chosenRegCoefficient;
        this.chosenTObs = chosenTObs;
        this.significanceLevel = significanceLevel;
        this.critTD = myRegressionModel.calculateCriticalValTStudent(significanceLevel);
    }

    /**
     * Returns the decision of the HypothesisTest.
     *
     * @param tObs the observed value regarding a certain regression coefficient
     * @param critTD the critical value for the T-Student Distribution of the HypothesisTest
     * @return the decision of the HypothesisTest
     */
    public String getDecision(double tObs, double critTD) {
        return (Math.abs(tObs) > critTD) ? "Reject H0" : "No Reject H0";
    }

    /**
     * Returns the decision of the HypothesisTest for the Anova Significance Model:
     * Test for Significance of Regression
     *
     * @param critFD the critical value for the Fisher-Snedecor Distribution of the HypothesisTest
     * @return the decision of the Test for Significance of Regression for the Anova Significance Model
     */
    public String getDecisionForAnova(double critFD) {
        return (f > critFD) ? String.format("%.4f > f%.2f,(%d,%d)=%.4f%nReject H0\nThe regression model is significant.", f, significanceLevel, numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, critFD) :
                String.format("%.4f <= f%.2f,(%d,%d)=%.4f%nNo Reject H0\nThe regression model is not significant.", f, significanceLevel, numeratorDegreesOfFreedom, denominatorDegreesOfFreedom, critFD);
    }

    /**
     * Returns the textual description of the HypothesisTest's instance.
     *
     * @return characteristics of the HypothesisTest
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append("Hypothesis tests for regression coefficients\n\n");
        text.append(String.format("--> Significance Level: %.2f%n%n", significanceLevel));
        text.append("H0:a=0 H1:a<>0\n");
        text.append(String.format("t_obs=%.4f%n", tObsA));
        text.append(String.format("Decision: %n%s%n", getDecision(tObsA, critTD)));
        text.append("//\n");
        text.append("H0:b=0 H1:b<>0\n");
        text.append(String.format("t_obs=%.4f%n", tObsB));
        text.append(String.format("Decision: %n%s%n", getDecision(tObsB, critTD)));
        text.append("//\n");
        if(myRegressionModel.getSecondIndVariable() != null) {
            text.append("H0:c=0 H1:c<>0\n");
            text.append(String.format("t_obs=%.4f%n", tObsC));
            text.append(String.format("Decision: %n%s%n", getDecision(tObsC, critTD)));
            text.append("//\n");
        }
        text.append("\n");

        return text.toString();
    }

    /**
     * Returns the textual description of the HypothesisTest's instance
     * for the Anova Significance Model.
     *
     * @return characteristics of the HypothesisTest for the Anova Significance Model
     */
    public String toStringForAnova() {
        StringBuilder text = new StringBuilder();
        text.append(String.format("Decision: f%n"));
        text.append(String.format("%s%n", getDecisionForAnova(critFD)));
        return text.toString();
    }

    /**
     * Returns the textual description of the HypothesisTest's instance
     * for a chosen regression coefficient.
     *
     * @return characteristics of the HypothesisTest of a chosen regression coefficient
     */
    public String toStringForChosenRegCoeffient() {
        StringBuilder text = new StringBuilder();
        text.append("Hypothesis tests for regression coefficients\n\n");
        text.append(String.format("--> Significance Level: %.2f%n%n", significanceLevel));
        text.append(String.format("H0:%s=0 H1:%s<>0\n", chosenRegCoefficient, chosenRegCoefficient));
        text.append(String.format("t_obs=%.4f%n", chosenTObs));
        text.append(String.format("Decision: %n%s%n", getDecision(chosenTObs, critTD)));
        text.append("//\n");
        return text.toString();
    }

}
