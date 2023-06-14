package app.domain.model;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SampleTest {
    private Object barcode;
    private MyBarcode myBarcode;
    private MyBarcode otherMyBarcode;

    @Before
    public void setUp() throws BarcodeException {
        barcode = BarcodeFactory.createUPCA("12345678901");
        myBarcode = new MyBarcode(barcode, "12345678901");
        otherMyBarcode = new MyBarcode(barcode, "0000000000");
    }


    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {

        Sample s1 = new Sample(myBarcode);

        boolean resultDifferentClasses = s1.equals(myBarcode);

        Assert.assertFalse(resultDifferentClasses);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {

        Sample s1 = new Sample(myBarcode);
        Sample s2 = null;

        boolean resultWithNull = s1.equals(s2);

        Assert.assertFalse(resultWithNull);
    }

    @Test
    public void twoEqualsSamples() {

        Sample s1 = new Sample(myBarcode);
        Sample s2 = new Sample(myBarcode);

        boolean result = s1.equals(s2);

        Assert.assertTrue(result);
    }

    @Test
    public void twoDifferentSamples() {

        Sample s1 = new Sample(myBarcode);
        Sample s2 = new Sample(otherMyBarcode);

        boolean result = s1.equals(s2);

        Assert.assertFalse(result);
    }




}