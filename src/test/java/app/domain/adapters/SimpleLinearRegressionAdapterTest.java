package app.domain.adapters;

import app.domain.interfaces.RegressionModel;
import app.domain.model.Company;
import app.domain.model.ConfidenceInterval;
import app.domain.model.MyRegressionModel;
import app.thirdparty.regressionModels.SimpleLinearRegression;
import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleLinearRegressionAdapterTest {
    private Company company;
    private RegressionModel regressionModel;
    private MyRegressionModel myRegressionModel;
    private SimpleLinearRegression simpleLR;
    private int historicalPoints;


    @Before
    public void setUp() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        company = new Company("Many Labs", Constants.CLASS_BARCODE_API, Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
        regressionModel = this.company.getChosenRegressionModel("Simple Linear Regression");
        //Ex. 1 TP PL7 MATCP
        double[] x = {825.0, 215.0, 1070.0, 550.0, 480.0, 920.0, 1350.0, 325.0, 670.0, 1215.0};
        double[] y = {3.5, 1.0, 4.0, 2.0, 1.0, 3.0, 4.5, 1.5, 3.0, 5.0};
        //somente para teste
        double[] x0 = {8.0, 9.0, 10.0, 12.0, 11.0, 8.0, 8.0, 10.0, 12.0, 11.0};
        historicalPoints = 10;
        //
        myRegressionModel = regressionModel.getRegressionModel(x, null, y, historicalPoints);
        simpleLR = new SimpleLinearRegression(x, y);
    }

    @Test
    public void getEstimatedPositives() {
        Locale.setDefault(Locale.ENGLISH); //necessary because Jenkins was configured to put numbers in English format.
        //Ex. 1 TP PL7 MATCP
        double[] x = {825.0, 215.0, 1070.0, 550.0, 480.0, 920.0, 1350.0, 325.0, 670.0, 1215.0};
        double[] y = {3.5, 1.0, 4.0, 2.0, 1.0, 3.0, 4.5, 1.5, 3.0, 5.0};
        Double[] xInDouble = {825.0, 215.0, 1070.0, 550.0, 480.0, 920.0, 1350.0, 325.0, 670.0, 1215.0};
        Double[] yInDouble = {3.5, 1.0, 4.0, 2.0, 1.0, 3.0, 4.5, 1.5, 3.0, 5.0};

        List<String> expectedEstimatedPositives = new ArrayList<>();
        expectedEstimatedPositives.add("3.075863");
        expectedEstimatedPositives.add("0.888933");
        expectedEstimatedPositives.add("3.954221");
        expectedEstimatedPositives.add("2.089952");
        expectedEstimatedPositives.add("1.838993");
        expectedEstimatedPositives.add("3.416451");
        expectedEstimatedPositives.add("4.958058");
        expectedEstimatedPositives.add("1.283297");
        expectedEstimatedPositives.add("2.520168");
        expectedEstimatedPositives.add("4.474065");


        MyRegressionModel myRegressionModel = regressionModel.getRegressionModel(x, null, y, 10);
        List<Double> estimatedPositives = regressionModel.getEstimatedPositives(myRegressionModel, xInDouble, yInDouble);

        /*
         * it was necessary to convert to an Array of String because JUNIT doesn't
         * have an Assert.assertEquals with a delta for Double Arrays
         */

        List<String> estimatedPositivesString = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.######");

        for (Double estimatedPositive : estimatedPositives) {
            estimatedPositivesString.add(df.format(estimatedPositive));
        }

        Assert.assertEquals(expectedEstimatedPositives, estimatedPositivesString);
    }

    @Test
    public void getConfidenceInterval() {
        //Arrange
        double x0 = 1000.0;
        double y0 = simpleLR.predict(x0);
        double auxDelta = 0.51334312;
        double confidenceLevel = 0.95;
        ConfidenceInterval expectedConfInt = new ConfidenceInterval(myRegressionModel,y0,auxDelta,confidenceLevel);
        //Act
        ConfidenceInterval confidenceInterval = regressionModel.getConfidenceInterval(myRegressionModel, x0, null, confidenceLevel);
        //Assert
        Assert.assertEquals(expectedConfInt, confidenceInterval);
    }
}