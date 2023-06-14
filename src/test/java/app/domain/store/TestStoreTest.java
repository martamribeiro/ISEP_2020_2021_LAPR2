package app.domain.store;

import app.controller.RecordSamplesController;
import app.domain.model.*;
import app.domain.shared.Constants;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TestStoreTest {

    private List<Parameter> parametersBlood;
    private List<Parameter> parametersCovid;
    private List<ParameterCategory> pcListBlood;
    private List<ParameterCategory> pcList;
    private ParameterCategory p1;
    private ParameterCategory p2;
    private List<TestType> selectedTT;
    private TestType t1;
    private TestType t2;
    private Date d1;

    @Before
    public void setUp() throws ParseException {
        parametersBlood = new ArrayList<>();
        parametersCovid = new ArrayList<>();

        d1 = new SimpleDateFormat("dd/MM/yyyy").parse("08/08/2001");

        pcListBlood = new ArrayList<>();
        p1 = new ParameterCategory("CODE1","Hemogram");
        pcListBlood.add(p1);
        Parameter rbc = new Parameter("RBC12", "rbc", "redbloodcells", p1);
        Parameter wbc = new Parameter("WBC12", "wbc", "whitebloodcells", p1);

        pcList = new ArrayList<>();
        p2 = new ParameterCategory("CODE1","covid");
        pcList.add(p2);
        Parameter igg = new Parameter("IGG12", "igg", "covidParam", p2);

        parametersBlood.add(rbc);
        parametersBlood.add(wbc);
        parametersCovid.add(igg);

        t1 = new TestType("CODE3","blood test","blood",pcListBlood, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        t2 = new TestType("CODE4","covid","swab",pcList, Constants.COVID_EXTERNAL_ADAPTER);

        selectedTT = new ArrayList<>();
        selectedTT.add(t1);
        selectedTT.add(t2);

    }

    @Test
    public void ensureNullTestNotStored(){
        TestStore testStore = new TestStore();
        Assert.assertFalse(testStore.saveTest(null));
    }

    @Test
    public void ensureEqualTestsAreNotSaved(){
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);
        Assert.assertFalse(testStore.saveTest(test));
    }

    @Test
    public void ensureDifferentTestsPassValidation(){
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);
        Client client2 = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test2 = testStore.createTest("123456789011", client, t1, parametersBlood);
        Assert.assertTrue(testStore.saveTest(test2));
    }

    @Test //checks if tests with no sample are being found correctly
    public void ensureTestIsFoundByBarcodeNumber() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        TestStore testStore = new TestStore();
        SampleStore sampleStore = new SampleStore();
        RecordSamplesController recordSamplesController = new RecordSamplesController();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        MyBarcode myBarcode = recordSamplesController.getBarcode();
        Sample sample = sampleStore.createSample(myBarcode);

        test.addSample(sample);

        assertSame(testStore.getTestByBarcodeNumber(myBarcode.getBarcodeNumber()), test);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureNotExistentBarcodeNumThrowsException() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        TestStore testStore = new TestStore();
        SampleStore sampleStore = new SampleStore();
        RecordSamplesController recordSamplesController = new RecordSamplesController();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        MyBarcode myBarcode = recordSamplesController.getBarcode();
        Sample sample = sampleStore.createSample(myBarcode);

        test.addSample(sample);

        testStore.getTestByBarcodeNumber("012345678912");
    }

    @Test //checks if tests with no sample are being found correctly
    public void ensureTestsAreFoundByCode()  {
        TestStore testStore = new TestStore();
        SampleStore sampleStore = new SampleStore();
        RecordSamplesController recordSamplesController = new RecordSamplesController();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);


        assertSame(testStore.getTestByCodeInTestList(test.getCode()), test);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureNotExistentCodeThrowsException() {
        TestStore testStore = new TestStore();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        testStore.getTestByCodeInTestList("000000000002");
    }

    @Test
    public void ensureTestNotReadyToDiagnosisFalse() throws BarcodeException {
        TestStore testStore = new TestStore();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        test.addSample(sample);

        assertSame(testStore.getTestsReadyToDiagnose().size(),0);
    }

    @Test
    public void ensureTestReadyToDiagnosisTrue() throws BarcodeException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        TestStore testStore = new TestStore();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        test.addSample(sample);

        test.addTestResult("RBC12", 23.45, "ug");
        test.addTestResult("WBC12", 23.45, "ug");
        test.addChemicalAnalysisDate();

        assertEquals(1, testStore.getTestsReadyToDiagnose().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureAddTestResultForInexistentCodeThrowsException() throws BarcodeException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        TestStore testStore = new TestStore();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        test.addSample(sample);

        test.addTestResult("ALALA", 23.45, "ug");
    }


    @Test
    public void ensureGetTestParametersByTestReturnsCorrectly() {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        Assert.assertEquals(parametersBlood, testStore.getTotalTestParameters(test));
    }


    @Test
    public void ensureGetTestParametersReturnsCorrectly() {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);
        testStore.saveTest(test);

        Assert.assertEquals(test.getParameters(), testStore.getTestParameters(test));
    }
}