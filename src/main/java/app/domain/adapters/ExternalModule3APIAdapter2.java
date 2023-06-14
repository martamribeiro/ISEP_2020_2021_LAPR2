package app.domain.adapters;

import app.domain.model.MyReferenceValue;
import app.domain.model.Parameter;
import app.domain.interfaces.ExternalModule;
import com.example1.ExternalModule3API;

import java.io.Serializable;

/**
 * Adapter class for the blood reference value 2 API
 * Implements the External module interface
 */
public class ExternalModule3APIAdapter2 implements ExternalModule, Serializable {

    /**
     * Method for getting the Reference value object using the blood external module 2
     * @param parameter parameter to get the reference value for
     * @return the full reference value object
     */
    public MyReferenceValue getReferenceValue(Parameter parameter) {
        ExternalModule3API em3api = new ExternalModule3API();
        Double minRefValue = em3api.getMinReferenceValue(parameter.getPrmCode(), 12345);
        Double maxRefValue = em3api.getMaxReferenceValue(parameter.getPrmCode(), 12345);

        return new MyReferenceValue(minRefValue, maxRefValue);
    }



}
