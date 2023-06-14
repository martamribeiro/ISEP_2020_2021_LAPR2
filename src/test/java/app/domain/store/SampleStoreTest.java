package app.domain.store;

import app.domain.model.MyBarcode;
import app.domain.model.Sample;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SampleStoreTest {
    private Object barcode;
    private MyBarcode myBarcode;

    @Before
    public void setUp() throws BarcodeException {
        barcode = BarcodeFactory.createUPCA("12345678901");
        myBarcode = new MyBarcode(barcode, "12345678901");
    }

    @Test
    public void createSample() {
        SampleStore sampleStore = new SampleStore();
        Sample expObj = new Sample(myBarcode);
        Sample obj = sampleStore.createSample(myBarcode);

        Assert.assertEquals(expObj, obj);
    }

    @Test
    public void ensureNotPossibleNullSample() {
        SampleStore sampleStore = new SampleStore();
        Sample obj = null;
        boolean result = sampleStore.validateSample(obj);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureAValidSampleIsSaved() {
        SampleStore sampleStore = new SampleStore();
        Sample obj = sampleStore.createSample(myBarcode);

        boolean result = sampleStore.validateSample(obj);

        Assert.assertTrue(result);

    }




}