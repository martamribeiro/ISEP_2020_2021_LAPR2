package app.domain.shared.utils;

import app.domain.model.MyBarcode;

import java.io.File;

/**
 * Class for:
 * - generating the barcode number
 * - defining the path of the folder each barcode image will be saved into
 *
 * @author Ana Albergaria
 */
public class BarcodeUtils {
    /**
     * Method to generate a sequential UPC code for the barcode
     *
     * @return a UPC code
     */
    public static String generateBarcodeNumber() {
        int totalBarcodes = MyBarcode.getTotalBarcodes();
        String s = String.format("%011d", totalBarcodes);

        if(s.length() > 11)
            throw new IllegalArgumentException("Impossible to generate more Barcodes with a unique barcode number.");

        return s;
    }

    /**
     * Method for defining the path of the folder each barcode image will be saved into
     *
     * @param code code of the Test whose samples are being collected
     *
     * @return path of the folder each barcode image will be saved into
     */
    public static File imageFolderPath(String code) {
        File path = new File("./Samples Barcodes/Test-Code" + code);
        if(!path.exists())
            path.mkdirs();

        return path;
    }

}
