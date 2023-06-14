package app.mappers.dto;

import app.domain.model.MyReferenceValue;
import app.domain.model.Parameter;
import app.domain.model.TestParameterResult;

/**
 * @author Marta Ribeiro 1201592
 */
public class TestParametersDTO {

    /**
     * The parameter of the test parameter.
     */
    private Parameter parameter;

    /**
     * The result of the test parameter.
     */
    private TestParameterResult testResult;

    /**
     * Build a test parameter instance receiving:
     * the parameter and the result.
     *
     * @param parameter the parameter of the test parameter.
     * @param testResult the result of the test parameter.
     */
    public TestParametersDTO(Parameter parameter, TestParameterResult testResult){
        this.parameter = parameter;
        this.testResult = testResult;
    }

    /**
     * Returns the parameter of the test parameter.
     *
     * @return the parameter of the test parameter.
     */
    public Parameter getParameter(){
        return parameter;
    }

    /**
     * Returns the result of the test parameter.
     *
     * @return the result of the test parameter.
     */
    public TestParameterResult getTestParameterResult(){
        return testResult;
    }

    /**
     * Returns the value of the test parameter result.
     *
     * @return the value of the test parameter result.
     */
    public Double getResultValue(){
        return testResult.getResultValue();
    }

    /**
     * Returns the metric of the test parameter result.
     *
     * @return the metric of the test parameter result.
     */
    public String getResultMetric(){
        return testResult.getResultMetric();
    }

    /**
     * Returns the reference value of the test parameter result.
     *
     * @return the reference value of the test parameter result.
     */
    public Double getResultReferenceValueMin(){
        return testResult.getResultReferenceValue().getMinValue();
    }

    public Double getResultReferenceValueMax(){
        return testResult.getResultReferenceValue().getMaxValue();
    }
}
