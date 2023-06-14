package app.domain.model;

import app.thirdparty.regressionModels.MultipleLinearRegression;
import app.thirdparty.regressionModels.SimpleLinearRegression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MyRegressionModelTest {
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

    //Equals Method for Simple Linear Regression
    @Test
    public void ensureNotEqualsWithDifferentIntercept() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045,0,900,10, simpleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.25,0.0045,0,900,10, simpleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentSlope() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045,0,900,10, simpleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.005,0,900,10, simpleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentR2() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045,0,900,10, simpleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045,0.5,900,10, simpleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentR2Adjusted() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045,0.5,0,0.4,10, simpleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045,0.5,0.3,10, simpleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentN() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045,0.5,0,0.4,9, simpleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045,0.5,0.4,10, simpleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    //Equals Method for Multiple Linear Regression
    @Test
    public void ensureNotEqualsWithDifferentInterceptForMLR() {
        MyRegressionModel object = new MyRegressionModel(1.235,0.0045, 2.33,0,900,10, multipleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, multipleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentSlopeForMLR() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.045, 2.33,0,900,10, multipleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, multipleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentR2ForMLR() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045, 2.33,0.2,900,10, multipleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, multipleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentR2AdjustedForMLR() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045, 2.33,0.2,9003,10, multipleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045, 2.33,0.2,900,10, multipleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentNForMLR() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045, 2.33,0.2,900,1, multipleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045, 2.33,0.2,900,10, multipleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentSecondVariable() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, multipleLR);
        MyRegressionModel differentObject = new MyRegressionModel(1.2345,0.0045, 2.0,0,900,10, multipleLR);

        boolean result = object.equals(differentObject);

        Assert.assertFalse(result);
    }

    //Other tests for Equals Method
    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, simpleLR);

        boolean result = object.equals(simpleLR);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {
        MyRegressionModel object = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, simpleLR);
        MyRegressionModel nullObject = null;

        boolean result = object.equals(nullObject);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEquals() {
        MyRegressionModel object1 = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, simpleLR);
        MyRegressionModel object2 = new MyRegressionModel(1.2345,0.0045, 2.33,0,900,10, simpleLR);

        boolean result = object1.equals(object2);

        Assert.assertTrue(result);
    }

    //For Critical Values
    @Test
    public void calculateCriticalValFSnedecor() {
        double expected = 4.2565;
        double actual = myRegressionModelWithSLR.calculateCriticalValFSnedecor(2,9,0.05);

        Assert.assertEquals(expected, actual, 0.0001);
    }

    //For Simple Linear Regression
    @Test
    public void calculateCriticalValTStudentForSLR() {
        double expected = 2.306;
        double actual = myRegressionModelWithSLR.calculateCriticalValTStudent(0.05);

        Assert.assertEquals(expected, actual, 0.0001);
    }

    //For Multiple Linear Regression
    @Test
    public void calculateCriticalValTStudentForMLR() {
        double expected = 2.2622;
        double actual = myRegressionModelWithMLR.calculateCriticalValTStudent(0.05);

        Assert.assertEquals(expected, actual, 0.0001);
    }






}