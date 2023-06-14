package app.domain.model;

import app.thirdparty.regressionModels.MultipleLinearRegression;
import app.thirdparty.regressionModels.SimpleLinearRegression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SignificanceModelAnovaTest {
    private MyRegressionModel myRegressionModelWithSLR;
    private MyRegressionModel myRegressionModelWithMLR;
    private SimpleLinearRegression simpleLR;
    private MultipleLinearRegression multipleLR;

    @Before
    public void setUp() {
        //Ex. 1 TP PL7 MATCP
        double[] x = {825.0, 215.0, 1070.0, 550.0, 480.0, 920.0, 1350.0, 325.0, 670.0, 1215.0};
        double[] y = {3.5, 1.0, 4.0, 2.0, 1.0, 3.0, 4.5, 1.5, 3.0, 5.0};
        //MATCP - Teórica Regressão Linear Múltipla
        double[] x1 = {80.0, 93.0, 100.0, 82.0, 90.0, 99.0, 81.0, 96.0, 94.0, 93.0, 97.0, 95.0};
        double[] x2 = {8.0, 9.0, 10.0, 12.0, 11.0, 8.0, 8.0, 10.0, 12.0, 11.0, 13.0, 11.0};
        double[] y1 = {2256.0, 2340.0, 2426.0, 2293.0, 2330.0, 2368.0, 2250.0, 2409.0, 2364.0, 2379.0, 2440.0, 2364.0};
        simpleLR = new SimpleLinearRegression(x, y);
        multipleLR = new MultipleLinearRegression(x1, x2, y1);
        myRegressionModelWithSLR = new MyRegressionModel(1.2345,0.0045,0,900,10, simpleLR);
        myRegressionModelWithMLR = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,12, multipleLR);
    }

    @Test
    public void ensureNotEqualsWithDifferentMyRegressionModel() {
        SignificanceModelAnova modelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.559, 0.05);
        SignificanceModelAnova otherModelAnova = new SignificanceModelAnova(myRegressionModelWithMLR, 0.7833, 0.559, 0.05);

        boolean result = modelAnova.equals(otherModelAnova);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentSR() {
        SignificanceModelAnova modelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.559, 0.05);
        SignificanceModelAnova otherModelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.733, 0.559, 0.05);

        boolean result = modelAnova.equals(otherModelAnova);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentSE() {
        SignificanceModelAnova modelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.559, 0.05);
        SignificanceModelAnova otherModelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.449, 0.05);

        boolean result = modelAnova.equals(otherModelAnova);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {
        SignificanceModelAnova modelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.559, 0.05);

        boolean result = modelAnova.equals(simpleLR);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {
        SignificanceModelAnova modelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.559, 0.05);
        SignificanceModelAnova otherModelAnova = null;

        boolean result = modelAnova.equals(otherModelAnova);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEquals() {
        SignificanceModelAnova modelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.559, 0.05);
        SignificanceModelAnova otherModelAnova = new SignificanceModelAnova(myRegressionModelWithSLR, 0.7833, 0.559, 0.05);

        boolean result = modelAnova.equals(otherModelAnova);

        Assert.assertTrue(result);
    }


}