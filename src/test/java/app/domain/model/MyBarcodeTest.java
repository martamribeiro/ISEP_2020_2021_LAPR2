package app.domain.model;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MyBarcodeTest {
    private Object barcode;

    @Before
    public void setUp() throws BarcodeException {
        barcode = BarcodeFactory.createUPCA("12345678901");
    }


    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {
        MyBarcode myBarcode = new MyBarcode(barcode, "12345678901");
        Sample s1 = new Sample(myBarcode);

        boolean resultDifferentClasses = s1.equals(myBarcode);

        Assert.assertFalse(resultDifferentClasses);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {

        MyBarcode mb1 = new MyBarcode(barcode, "12345678901");
        MyBarcode mb2 = null;

        boolean resultWithNull = mb1.equals(mb2);

        Assert.assertFalse(resultWithNull);
    }



}