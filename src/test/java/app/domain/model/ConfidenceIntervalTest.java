package app.domain.model;

import app.thirdparty.regressionModels.SimpleLinearRegression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfidenceIntervalTest {
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
    public void ensureEquals() {
        ConfidenceInterval c1 = new ConfidenceInterval(myRegressionModelWithSLR, 2.5, 2.44, 0.05);
        ConfidenceInterval c2 = new ConfidenceInterval(myRegressionModelWithSLR, 2.5, 2.44, 0.05);

        boolean result = c1.equals(c2);

        Assert.assertTrue(result);
    }

    @Test
    public void ensureNotEquals() {
        ConfidenceInterval c1 = new ConfidenceInterval(myRegressionModelWithSLR, 2.5, 2.44, 0.05);
        ConfidenceInterval c2 = new ConfidenceInterval(myRegressionModelWithSLR, 2.5, 2.4, 0.05);

        boolean result = c1.equals(c2);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {
        ConfidenceInterval c1 = new ConfidenceInterval(myRegressionModelWithSLR, 2.5, 2.44, 0.05);

        boolean result = c1.equals(simpleLR);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {
        ConfidenceInterval c1 = new ConfidenceInterval(myRegressionModelWithSLR, 2.5, 2.44, 0.05);
        ConfidenceInterval c2 = null;

        boolean result = c1.equals(c2);

        Assert.assertFalse(result);
    }



}