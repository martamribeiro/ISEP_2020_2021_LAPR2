package app.domain.interfaces;

import app.domain.model.MyBarcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.IOException;

/**
 * Interface to be used on the adapters of the available external APIs for generating barcodes.
 *
 * @author Ana Albergaria
 */
public interface ExternalAPI {
    /**
     * Abstract method to be used by an Adapter Class which generates a barcode with a UPC code received by parameter
     * which returns a MyBarcode's instance that receives the generated barcode
     * by the API as well as the barcode number - the UPC code, in this case.
     *
     * @param barcodeNumber the UPC code of the generated barcode
     *
     * @return a MyBarcode's instance that receives the generated barcode by the API as well as the barcode number
     * @throws BarcodeException if the data to be encoded in the barcode is invalid
     */
    public abstract MyBarcode getBarcode(String barcodeNumber) throws BarcodeException;

    /**
     * Abstract Method to be used by an Adapter Class for saving the generated barcodes from the API into a JPEG image
     * in a specific folder, using the class BarcodeUtils to define that folder.
     *
     * @param myBarcode a MyBarcode's instance that has the generated barcode by the API as well as the barcode number
     * @param code the code of the selected Test for which the samples will be collected
     * @throws IOException if some sort of I/O exception has occurred
     * @throws OutputException if an exception is thrown by the Output service
     */
    public abstract void saveImageBarcode(MyBarcode myBarcode, String code) throws IOException, OutputException;
}


