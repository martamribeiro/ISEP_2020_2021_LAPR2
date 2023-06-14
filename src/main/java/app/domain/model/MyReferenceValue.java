package app.domain.model;

import java.io.Serializable;

/**
 * Class of the generalized reference value class.
 */
public class MyReferenceValue implements Serializable {
    /**
     * Reference value for minimum acceptable value of a parameter
     */
    private Double minValue;

    /**
     * Reference value for maximum acceptable value of a parameter
     */
    private Double maxValue;

    /**
     * Constructor of the refenrece value object
     * @param minValue Reference value for minimum acceptable value of a parameter
     * @param maxValue Reference value for maximum acceptable value of a parameter
     */
    public MyReferenceValue(Double minValue, Double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Double getMinValue() {
        return minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }
}
