package app.domain.interfaces;

import app.domain.model.MyReferenceValue;
import app.domain.model.Parameter;

/**
 * Interface to be used on the adapters of the available external modules of reference values
 */
public interface ExternalModule {
    /**
     * Method for returning a MyReferenceValue object for an given parameter
     * @param parameter given parameter to get the reference value for
     * @return the reference value object
     */
    public abstract MyReferenceValue getReferenceValue(Parameter parameter);
}
