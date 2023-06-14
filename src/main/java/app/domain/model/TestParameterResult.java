package app.domain.model;

/**
 * Class of a Test parameter result which stores also the metric used and the reference value for the parameter
 *
 * @author SRC-Code-23
 */
public class TestParameterResult {

    /**
     * result value of a given parameter of test
     */
    private Double value;

    /**
     * metric being used in current parameter testing
     */
    private String metric;

    /**
     * Reference value of the analysed parameter
     */
    private MyReferenceValue myReferenceValue;

    /**
     * Constructor of the object
     * @param value Result value of a given parameter of test
     * @param metric Metric being used in current parameter testing
     * @param myReferenceValue Reference value of the analysed parameter
     */
    public TestParameterResult(Double value, String metric, MyReferenceValue myReferenceValue) {
        this.value = value;
        this.metric = metric;
        this.myReferenceValue = myReferenceValue;
    }

    /**
     * Get the result value of the test parameter result.
     *
     * @return result value of the test parameter result.
     */
    public Double getResultValue(){
        return value;
    }

    /**
     * Get the result metric of the test parameter result.
     *
     * @return result metric of the test parameter result.
     */
    public String getResultMetric(){
        return metric;
    }

    /**
     * Get the reference value of the test parameter result.
     *
     * @return reference value of the test parameter result.
     */
    public MyReferenceValue getResultReferenceValue(){
        return myReferenceValue;
    }

    /**
     * It returns the textual description of the Test Parameter Result instance.
     *
     * @return characteristics of the Test Parameter Result
     */
    @Override
    public String toString() {
        return String.format("> Value: %s, > Metric: %s, > Ref.Value Min: %s, > Ref.Value Max: %s%n", value, metric, myReferenceValue.getMinValue(), myReferenceValue.getMaxValue());
    }
}
