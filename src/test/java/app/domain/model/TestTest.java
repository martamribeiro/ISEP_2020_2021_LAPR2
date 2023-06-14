package app.domain.model;

import app.domain.shared.Constants;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class TestTest {

    private List<Parameter> parametersBlood;
    private List<Parameter> parametersCovid;
    private List<ParameterCategory> pcListBlood;
    private List<ParameterCategory> pcList;
    private ParameterCategory p1;
    private ParameterCategory p2;
    private TestType t1;
    private TestType t2;
    private Date d1;
    private ClinicalAnalysisLaboratory cal;

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
        Parameter igg = new Parameter("igGAN", "igg", "covidParam", p2);

        parametersBlood.add(rbc);
        parametersBlood.add(wbc);
        parametersCovid.add(igg);

        t1 = new TestType("CODE3","blood test","blood",pcListBlood, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        t2 = new TestType("covid","covid","swab",pcList, Constants.COVID_EXTERNAL_ADAPTER);

        List<TestType> selectedTT = new ArrayList<>();
        selectedTT.add(t1);
        selectedTT.add(t2);

        cal =  new ClinicalAnalysisLaboratory("001DO",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNullParameters(){
        new app.domain.model.Test(null, null, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNullNhs(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        new app.domain.model.Test(null, client, t1, parametersBlood, cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNullTestType(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        new app.domain.model.Test("123456789012", client, null, parametersBlood, cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTestTypeCannotBeNullFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, null, parametersBlood, res,cal, new Date(), new Date(),new Date(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNullClient(){
        new app.domain.model.Test("123456789012", null, t1, parametersBlood, cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNullClientFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        new app.domain.model.Test("123456789012", null, t2, parametersCovid, res,cal, new Date(), new Date(),new Date(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithEmptyResults() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        new app.domain.model.Test("123456789012", client, t1, parametersBlood, Collections.emptyList(), cal, new Date(), new Date(), new Date(), new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureParameterListCannotBeEmpty() {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, Collections.emptyList(), cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureParameterListCannotBeEmptyFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, Collections.emptyList(), res,cal, new Date(), new Date(),new Date(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCalIsNotNull(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCalIsNotNullFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, parametersCovid, res,null, new Date(), new Date(),new Date(), null);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureNotPossibleToFindNotExistentClient(){
        ClientStore clientStore = new ClientStore();
        clientStore.getClientByTinNumber("1234567890");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithUnder12CharsNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("12345678901", client, t1, parametersBlood, cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithUnder12CharsNhsFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("1234567890", client, t2, parametersBlood, res,cal, new Date(), new Date(),new Date(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithMore12CharsNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("1234567890123", client, t1, parametersBlood,cal);
    }

    @Test
    public void createFulltest() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, parametersCovid, res,cal, new Date(), new Date(),new Date(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithMore12CharsNhsFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("1234567890123", client, t2, parametersBlood, res,cal, new Date(), new Date(),new Date(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNotAlphanumericNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("!@#456789012", client, t1, parametersBlood,cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNotAlphanumericNhsFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("!@#456789012", client, t2, parametersBlood, res,cal, new Date(), new Date(),new Date(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithemptyNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("", client, t1, parametersBlood,cal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithEmptyNhsFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("", client, t2, parametersBlood, res,cal, new Date(), new Date(),new Date(), null);
    }


    @Test(expected = UnsupportedOperationException.class)
    public void ensureTestParameterNotFoundWithWrongCode() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood,cal);
        test.addTestResult("notEx", 12.9, "g");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTestParameterNotCreatedWithNullParameterFullConstructor() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Parameter> params = new ArrayList<>();
        params.add(null);
        List<Double> res = new ArrayList<>();
        res.add(1.6);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, params, res,cal, new Date(), new Date(),new Date(), new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTestParameterNotCreatedWithNullParameter() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Parameter> params = new ArrayList<>();
        params.add(null);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, params,cal);
    }


    @Test(expected = NullPointerException.class)
    public void ensureRegDateCannotBeNull() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, parametersCovid, res,cal, null, new Date(),new Date(), null);
    }

    @Test
    public void ensureDateOfChemicalAnalysisCanBeNull() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, parametersCovid, res,cal, new Date(), null,new Date(), new Date());
    }


    @Test
    public void ensureDateOfDiagnosisCanBeNull() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, parametersCovid, res,cal, new Date(), new Date(),null, null);
    }

    @Test
    public void ensureIsTrueForNegativeCovid() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.6);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, parametersCovid, res,cal, new Date(), new Date(),new Date(), new Date());
        Assert.assertTrue(test.hasPositiveResultForCovid());
    }

    @Test
    public void ensureIsFalseForNegativeCovid() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t2, parametersCovid, res,cal, new Date(), new Date(),new Date(), new Date());
        Assert.assertFalse(test.hasPositiveResultForCovid());
    }

    @Test
    public void ensureIsFalseForNotCovidTest() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        res.add(2.7);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood, res,cal, new Date(), new Date(),new Date(), new Date());
        Assert.assertFalse(test.hasPositiveResultForCovid());
    }

    @Test
    public void ensureTestWithResultsIsTrue() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersCovid, res,cal, new Date(), new Date(),new Date(), new Date());
        Assert.assertTrue(test.hasSamplesAnalysed());
    }

    @Test
    public void ensureTestWithNoResultsIsFalse(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersCovid,cal);
        Assert.assertFalse(test.hasSamplesAnalysed());
    }

    @Test
    public void ensureTestWithChemDateIsTrue() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersCovid, res,cal, new Date(), new Date(),new Date(), new Date());
        Assert.assertTrue(test.hasChemDate());
    }

    @Test
    public void ensureTestWithNoChemDateIsFalse() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        List<Double> res = new ArrayList<>();
        res.add(1.3);
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersCovid, res,cal, new Date(), null,new Date(), new Date());
        Assert.assertFalse(test.hasChemDate());
    }

    @Test //this test checks if the generated number is truly sequential, making the boolean conditions for this purpouse.
    public void ensureTestCodeIsSequencial(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        Client client2 = new Client("1234567890123458", "1234567890", d1, "Male", "1234567890", "alex1@gmail.com", "Alex", "12345675901");
        Client client3 = new Client("1234567890123457", "1234567890", d1, "Male", "1234567890", "alex3@gmail.com", "Alex", "12345688901");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood,cal);
        app.domain.model.Test test2 = new app.domain.model.Test("123456789012", client2, t2, parametersCovid,cal);
        app.domain.model.Test test3 = new app.domain.model.Test("123456789012", client3, t1, parametersBlood,cal);

        boolean firstAndSecondCondition = Long.parseLong(test.getCode()) == Long.parseLong(test2.getCode())-1;
        boolean secondAndThirdCondition = Long.parseLong(test2.getCode()) == Long.parseLong(test3.getCode())-1;
        Assert.assertTrue(firstAndSecondCondition && secondAndThirdCondition);
    }

    @Test //this test checks if the generated number is 12 digits long
    public void ensureCodeis12digits(){
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood,cal);

        Assert.assertTrue(test.getCode().length() == 12);
    }

    @Test
    public void ensureNotPossibleToAddNullSample() {
        app.domain.store.TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood,cal);

        Assert.assertFalse(test.addSample(null));
    }

    @Test
    public void ensureTestWithNoSampleIsFound() {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood,cal);

        Assert.assertFalse(test.hasSamples());
    }

    @Test
    public void ensureTestWithSampleIsFound() throws BarcodeException {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood,cal);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));

        test.addSample(sample);
        test.addSampleCollectionDate();

        Assert.assertTrue(test.hasSamples());
    }

    @Test
    public void ensureSampleIsAddedToTest() throws BarcodeException {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood,cal);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));

        test.addSample(sample);
        Assert.assertTrue(test.addSample(sample));
    }





}

