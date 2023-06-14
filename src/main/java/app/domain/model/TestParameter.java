package app.domain.model;

import java.io.Serializable;

/**
 * Container of one of the test parameters and its test result
 *
 * @author SRC-Code-23
 */
public class TestParameter implements Serializable {

    /**
     * Parameter to be evaluated for the a test.
     */
    private Parameter parameter;
    /**
     * Result of the parameter testing evaluation.
     */
    private TestParameterResult testResult;

    /**
     * Constructor for a test parameter only contains the parameter cause the result of the testing
     * is never added in the same time as the parameter.
     * @param parameter parameter to be tested.
     */
    public TestParameter(Parameter parameter){
        this.parameter = parameter;
        testResult = null;
    }

    public TestParameter(Parameter parameter, TestParameterResult testParameterResult){
        this.parameter = parameter;
        testResult = testParameterResult;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public TestParameterResult getTestParameterResult(){
        return testResult;
    }

    /**
     * Method for creating and associating an TestParameterResult to the current TestParameter
     * @param result Testing result value
     * @param metric Metric of the result evaluated
     * @param refValue reference value gotten in external module
     */
    public void addResult(Double result, String metric, MyReferenceValue refValue) {
        this.testResult = new TestParameterResult(result, metric, refValue);
    }

    /**
     * It returns the textual description of the Test Parameter instance.
     *
     * @return characteristics of the Test Parameter
     */
    @Override
    public String toString() {
        return String.format("Test Parameter:%s%nTest result: %s%n", parameter, testResult);
    }
}
