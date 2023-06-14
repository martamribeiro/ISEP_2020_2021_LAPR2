package app.domain.model;

import app.domain.interfaces.RegressionModel;
import app.thirdparty.regressionModels.SimpleLinearRegression;
import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TableOfValuesTest {
    private Company company;
    private RegressionModel regressionModel;
    private MyRegressionModel myRegressionModel;
    private MyRegressionModel otherMyRegressionModel;
    private SimpleLinearRegression simpleLR;
    private int historicalPoints;
    private List<String> dates = new ArrayList<>();
    private int[] observedPositives;
    List<Double> estimatedPositives = new ArrayList<>();
    private List<ConfidenceInterval> confidenceIntervals = new ArrayList<>();

    @Before
    public void setUp() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        company = new Company("Many Labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
        regressionModel = this.company.getChosenRegressionModel(Constants.CLASS_SIMPLE_REGRESSION_MODEL);
        //Ex. 1 TP PL7 MATCP
        double[] x = {825.0, 215.0, 1070.0, 550.0, 480.0, 920.0, 1350.0, 325.0, 670.0, 1215.0};
        double[] y = {3.5, 1.0, 4.0, 2.0, 1.0, 3.0, 4.5, 1.5, 3.0, 5.0};
        //somente para teste
        double[] x0 = {8.0, 9.0, 10.0, 12.0, 11.0, 8.0, 8.0, 10.0, 12.0, 11.0};
        historicalPoints = 10;
        //
        myRegressionModel = regressionModel.getRegressionModel(x, x0, y, historicalPoints);
        otherMyRegressionModel = regressionModel.getRegressionModel(x, x0, y, 3);
        simpleLR = new SimpleLinearRegression(x, y);
        dates.add("13/06/2021");
        dates.add("12/06/2021");
        dates.add("11/06/2021");
        observedPositives = new int[]{2, 3, 4};
        estimatedPositives.add(2.3);
        estimatedPositives.add(3.4);
        estimatedPositives.add(1.2);
        ConfidenceInterval c1 = new ConfidenceInterval(myRegressionModel, 2.5, 2.44, 0.05);
        ConfidenceInterval c2 = new ConfidenceInterval(myRegressionModel, 2, 2.44, 0.05);
        ConfidenceInterval c3 = new ConfidenceInterval(myRegressionModel, 2.5, 2.14, 0.05);
        confidenceIntervals.add(c1);
        confidenceIntervals.add(c2);
        confidenceIntervals.add(c3);
    }

    @Test
    public void ensureEquals() {
        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        TableOfValues otherTableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);

        boolean result = tableOfValues.equals(otherTableOfValues);

        Assert.assertTrue(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentMyRegressionModel() {
        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        TableOfValues otherTableOfValues = new TableOfValues(otherMyRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);

        boolean result = tableOfValues.equals(otherTableOfValues);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentDates() {
        List<String> otherDates = new ArrayList<>();
        otherDates.add("13/06/2021");
        otherDates.add("12/06/2021");
        otherDates.add("01/06/2021");
        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        TableOfValues otherTableOfValues = new TableOfValues(myRegressionModel, otherDates, observedPositives, estimatedPositives, confidenceIntervals);

        boolean result = tableOfValues.equals(otherTableOfValues);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentObservedPositives() {
        int[] otherPositives = {1,2,3};

        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        TableOfValues otherTableOfValues = new TableOfValues(myRegressionModel, dates, otherPositives, estimatedPositives, confidenceIntervals);

        boolean result = tableOfValues.equals(otherTableOfValues);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentEstimatedPositives() {
        List<Double> otherEstimatedPositives = new ArrayList<>();
        otherEstimatedPositives.add(2.3);
        otherEstimatedPositives.add(3.4);

        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        TableOfValues otherTableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, otherEstimatedPositives, confidenceIntervals);

        boolean result = tableOfValues.equals(otherTableOfValues);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNotEqualsWithDifferentConfidenceIntervals() {
        List<ConfidenceInterval> otherConfidenceIntervals = new ArrayList<>();

        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        TableOfValues otherTableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, otherConfidenceIntervals);

        boolean result = tableOfValues.equals(otherTableOfValues);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {
        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);

        boolean result = tableOfValues.equals(simpleLR);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {
        TableOfValues tableOfValues = new TableOfValues(myRegressionModel, dates, observedPositives, estimatedPositives, confidenceIntervals);
        TableOfValues otherTableOfValues = null;

        boolean result = tableOfValues.equals(otherTableOfValues);

        Assert.assertFalse(result);
    }






}