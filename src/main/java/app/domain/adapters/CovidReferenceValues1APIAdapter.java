package app.domain.adapters;

import app.domain.model.MyReferenceValue;
import app.domain.model.Parameter;
import app.domain.interfaces.ExternalModule;
import com.example3.CovidReferenceValues1API;

/**
 * Adapter class for the covid reference value 1 API
 * Implements the External module interface
 */
public class CovidReferenceValues1APIAdapter implements ExternalModule {

    /**
     * Method for getting the Reference value object using the covid external module
     * @param parameter parameter to get the reference value for
     * @return the full reference value object
     */
    public MyReferenceValue getReferenceValue(Parameter parameter) {
        CovidReferenceValues1API crv1api = new CovidReferenceValues1API();
        Double minRefValue = crv1api.getMinReferenceValue(parameter.getPrmCode(), 12345);
        Double maxRefValue = crv1api.getMaxReferenceValue(parameter.getPrmCode(), 12345);
        return new MyReferenceValue(minRefValue, maxRefValue);
    }
}
