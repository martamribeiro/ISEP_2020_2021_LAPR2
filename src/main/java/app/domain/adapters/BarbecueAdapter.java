package app.domain.adapters;

import app.domain.model.MyBarcode;
import app.domain.shared.utils.BarcodeUtils;
import app.domain.interfaces.ExternalAPI;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import java.io.File;
import java.io.IOException;

/**
 * Adapter class for the Barbecue Library which generates barcodes.
 * Implements the External API interface.
 *
 * @author Ana Albergaria
 */
public class BarbecueAdapter implements ExternalAPI {

    /**
     * Method which generates a barcode with a UPC code received by parameter
     * which returns a MyBarcode's instance that receives the generated barcode
     * by the API as well as the barcode number - the UPC code, in this case.
     *
     * @param barcodeNumber the UPC code of the generated barcode
     *
     * @return a MyBarcode's instance that receives the generated barcode by the API as well as the barcode number
     * @throws BarcodeException if the data to be encoded in the barcode is invalid
     */
    @Override
    public MyBarcode getBarcode(String barcodeNumber) throws BarcodeException {
        Barcode barcode = BarcodeFactory.createUPCA(barcodeNumber);
        return new MyBarcode(barcode, barcodeNumber);
    }

    /**
     * Method for saving the generated barcodes from the API into a JPEG image
     * in a specific folder, using the class BarcodeUtils to define that folder.
     *
     * @param myBarcode a MyBarcode's instance that has the generated barcode by the API as well as the barcode number
     * @param code the code of the selected Test for which the samples will be collected
     * @throws IOException if some sort of I/O exception has occurred
     * @throws OutputException if an exception is thrown by the Output service
     */
    @Override
    public void saveImageBarcode(MyBarcode myBarcode, String code) throws IOException, OutputException {
        File imageFolderPath = BarcodeUtils.imageFolderPath(code);
        Barcode barcode = (Barcode) myBarcode.getBarcode();
        barcode.setPreferredBarHeight(100);
        File imgFile = new File(imageFolderPath + "/barcode" + barcode.getData() + ".jpeg");
        BarcodeImageHandler.saveJPEG(barcode, imgFile);
    }

}


