package app.domain.model;

/**
 * Represents a MyBarcode through:
 * the barcode generated by the API and the barcode number.
 *
 * This class exists in order to allow every Adapter class to return the same type of object.
 * (Adapter Pattern)
 *
 * @author Ana Albergaria
 */

public class MyBarcode {

    /**
     * The barcode of the MyBarcode.
     */
    private Object barcode;

    /**
     * The barcode number of the MyBarcode.
     */
    private String barcodeNumber;

    /**
     * The total number of existing barcodes.
     */
    private static int totalBarcodes = 1;

    /**
     * Builds a Sample's instance receiving:
     * the barcode
     * the barcodeNumber
     *
     * @param barcode the barcode of the MyBarcode
     * @param barcodeNumber the barcode number of the MyBarcode
     */
    public MyBarcode(Object barcode, String barcodeNumber) {
        this.barcode = barcode;
        this.barcodeNumber = barcodeNumber;
        totalBarcodes++;
    }

    /**
     * Builds a MyBarcode's instance with the exact same information
     * of the otherMyBarcode received by parameter,
     * but in a different memory area.
     *
     * @param otherMyBarcode the MyBarcode from which the new instance will get the information
     */
    public MyBarcode(MyBarcode otherMyBarcode) {
        barcode = otherMyBarcode.barcode;
        barcodeNumber = otherMyBarcode.barcodeNumber;
    }

    /**
     * Returns the barcode (generated by the API) of the MyBarcode.
     *
     * @return barcode of the MyBarcode
     */
    public Object getBarcode() {
        return barcode;
    }

    /**
     * Returns the barcode number of the MyBarcode.
     *
     * @return barcode number of the MyBarcode
     */
    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    /**
     * Returns the total number of the barcodes.
     *
     * @return total number of the barcodes
     */
    public static int getTotalBarcodes() {
        return totalBarcodes;
    }

    /**
     * Compares the MyBarcode with the received object.
     *
     * @param otherObject the object to be compared with the MyBarcode
     * @return true if the received object represents other MyBarcode
     * equivalent to the MyBarcode. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject)
            return true;

        if(otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        MyBarcode otherMyBarcode = (MyBarcode) otherObject;

        return this.barcodeNumber.equals(otherMyBarcode.barcodeNumber);
    }
}
