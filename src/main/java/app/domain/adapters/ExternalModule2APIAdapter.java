package app.domain.adapters;

import app.domain.model.MyReferenceValue;
import app.domain.model.Parameter;
import app.domain.interfaces.ExternalModule;
import com.example2.EMRefValue;
import com.example2.ExternalModule2API;

import java.io.Serializable;

/**
 * Adapter class for the blood reference value 1 API
 * Implements the External module interface
 */
public class ExternalModule2APIAdapter implements ExternalModule, Serializable {

    /**
     * Method for getting the Reference value object using the blood external module 1
     * @param parameter parameter to get the reference value for
     * @return the full reference value object
     */
    public MyReferenceValue getReferenceValue(Parameter parameter) {
        ExternalModule2API em2api = new ExternalModule2API();
        EMRefValue refValue = em2api.getReferenceFor(parameter.getPrmCode());
        return new MyReferenceValue(refValue.getMinValue(), refValue.getMaxValue());
    }




}
