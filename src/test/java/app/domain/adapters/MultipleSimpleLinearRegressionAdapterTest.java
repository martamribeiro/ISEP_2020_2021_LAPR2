package app.domain.adapters;

import app.domain.interfaces.RegressionModel;
import app.domain.model.Company;
import app.domain.model.ConfidenceInterval;
import app.domain.model.MyRegressionModel;
import app.thirdparty.regressionModels.MultipleLinearRegression;
import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MultipleSimpleLinearRegressionAdapterTest {
    private Company company;
    private RegressionModel regressionModel;
    private MyRegressionModel myRegressionModel;
    private MyRegressionModel myRegressionModel2;
    private MultipleLinearRegression multipleLR;
    private int historicalPoints;

    @Before
    public void setUp() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        company = new Company("Many Labs", Constants.CLASS_BARCODE_API, Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_MULTIPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
        regressionModel = this.company.getChosenRegressionModel(Constants.CLASS_MULTIPLE_REGRESSION_MODEL);
        //MATCP - Teórica Regressão Linear Múltipla
        double[] x1 = {80.0, 93.0, 100.0, 82.0, 90.0, 99.0, 81.0, 96.0, 94.0, 93.0, 97.0, 95.0};
        double[] x2 = {8.0, 9.0, 10.0, 12.0, 11.0, 8.0, 8.0, 10.0, 12.0, 11.0, 13.0, 11.0};
        double[] y = {2256.0, 2340.0, 2426.0, 2293.0, 2330.0, 2368.0, 2250.0, 2409.0, 2364.0, 2379.0, 2440.0, 2364.0};
        historicalPoints = 10;
        //
        myRegressionModel = regressionModel.getRegressionModel(x1, x2, y, 12);
        multipleLR = new MultipleLinearRegression(x1, x2, y);

        //MATCP - TP PL8 ex 1
        double[] xa = {4.0, 5.0, 5.5, 7.0, 6.0, 5.0, 7.0, 8.0, 8.5, 9.0};
        double[] xb = {36.0, 33.0, 37.0, 37.0, 34.0, 32.0, 36.0, 35.0, 38.0, 39.0};
        double[] ya = {4.0, 4.5, 5.0, 6.5, 7.0, 7.8, 7.5, 8.0, 8.0, 8.5};
        myRegressionModel2 = regressionModel.getRegressionModel(xa, xb, ya, 10);
    }

    @Test
    public void getConfidenceInterval() {
        //Arrange
        double y0 = 2241.90597157;
        double auxDelta = 22.5859909;
        double confidenceLevel = 0.95;
        ConfidenceInterval expectedConfInt = new ConfidenceInterval(myRegressionModel,y0,auxDelta,confidenceLevel);
        //Act
        ConfidenceInterval confidenceInterval = regressionModel.getConfidenceInterval(myRegressionModel, 80.0, 8.0, confidenceLevel);
        //Assert
        Assert.assertEquals(expectedConfInt, confidenceInterval);
    }

    @Test
    public void getConfidenceInterval2() {
        //Arrange
        double y0 = 5.48267573696152;
        double auxDelta = 0.92467027062118;
        double confidenceLevel = 0.95;
        ConfidenceInterval expectedConfInt = new ConfidenceInterval(myRegressionModel2,y0,auxDelta,confidenceLevel);
        //Act
        ConfidenceInterval confidenceInterval = regressionModel.getConfidenceInterval(myRegressionModel2, 5.5, 36.0, confidenceLevel);

        //Assert
        Assert.assertEquals(expectedConfInt, confidenceInterval);
    }
}