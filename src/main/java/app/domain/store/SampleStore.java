package app.domain.store;

import app.domain.model.MyBarcode;
import app.domain.model.Sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Sample Store.
 *
 * @author Ana Albergaria
 */

public class SampleStore {

    /**
     * Called method through the Company to create a new Sample containing a myBarcode.
     *
     * @param myBarcode the myBarcode of the Sample
     * @return instance of Sample Class with the myBarcode
     *          received by parameter
     */
    public Sample createSample(MyBarcode myBarcode) {
        return new Sample(myBarcode);
    }

    /**
     * Called method through the Company to validate the Sample created.
     *
     * @param sample new sample created
     *
     * @return true if the new Sample was successfully validated.
     *          Otherwise, returns false.
     */
    public boolean validateSample(Sample sample) {
        if (sample == null)
            return false;
        return true;
    }
}
