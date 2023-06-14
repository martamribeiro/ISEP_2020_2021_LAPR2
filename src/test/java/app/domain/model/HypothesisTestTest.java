package app.domain.model;

import app.thirdparty.regressionModels.SimpleLinearRegression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class HypothesisTestTest {
    private MyRegressionModel myRegressionModelWithSLR;
    private SimpleLinearRegression simpleLR;

    @Before
    public void setUp() {
        //Ex. 1 TP PL7 MATCP
        double[] x = {825.0, 215.0, 1070.0, 550.0, 480.0, 920.0, 1350.0, 325.0, 670.0, 1215.0};
        double[] y = {3.5, 1.0, 4.0, 2.0, 1.0, 3.0, 4.5, 1.5, 3.0, 5.0};
        simpleLR = new SimpleLinearRegression(x, y);
        myRegressionModelWithSLR = new MyRegressionModel(1.2345,0.0045,0,900,10, simpleLR);
    }

    @Test
    public void getDecisionPositive() {
        double tObsA = 2.399, tObsB = 2.2, critTD = 2.306;
        HypothesisTest hypothesisTest = new HypothesisTest(myRegressionModelWithSLR, tObsA, tObsB, 0.05);
        String expected = "Reject H0";
        String actual = hypothesisTest.getDecision(tObsA,critTD);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getDecisionNegative() {
        double tObsA = 2.399, tObsB = 2.2, critTD = 2.306;
        HypothesisTest hypothesisTest = new HypothesisTest(myRegressionModelWithSLR, tObsA, tObsB, 0.05);
        String expected = "No Reject H0";
        String actual = hypothesisTest.getDecision(tObsB,critTD);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getDecisionEqual() {
        double tObsA = 2.399, tObsB = 2.306, critTD = 2.306;
        HypothesisTest hypothesisTest = new HypothesisTest(myRegressionModelWithSLR, tObsA, tObsB, 2.36,0.05);
        String expected = "No Reject H0";
        String actual = hypothesisTest.getDecision(tObsB,critTD);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getDecisionForAnovaPositive() {
        Locale.setDefault(Locale.ENGLISH); //because of Jenkins
        double f = 20, critFD = 4.256494729093686;
        HypothesisTest hypothesisTest = new HypothesisTest(myRegressionModelWithSLR,f,2,9,0.05);
        String expected = String.format("20.0000 > f0.05,(2,9)=4.2565%nReject H0\nThe regression model is significant.");
        String actual = hypothesisTest.getDecisionForAnova(critFD);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getDecisionForAnovaNegative() {
        Locale.setDefault(Locale.ENGLISH); //because of Jenkins
        double f = 3, critFD = 4.256494729093686;
        HypothesisTest hypothesisTest = new HypothesisTest(myRegressionModelWithSLR,f,2,9,0.05);
        String expected = String.format("3.0000 <= f0.05,(2,9)=4.2565%nNo Reject H0\nThe regression model is not significant.");
        String actual = hypothesisTest.getDecisionForAnova(critFD);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getDecisionForAnovaEqual() {
        Locale.setDefault(Locale.ENGLISH); //because of Jenkins
        double f = 4.256494729093686, critFD = 4.256494729093686;
        HypothesisTest hypothesisTest = new HypothesisTest(myRegressionModelWithSLR,f,2,9,0.05);
        String expected = String.format("4.2565 <= f0.05,(2,9)=4.2565%nNo Reject H0\nThe regression model is not significant.");
        String actual = hypothesisTest.getDecisionForAnova(critFD);

        Assert.assertEquals(expected, actual);
    }
}