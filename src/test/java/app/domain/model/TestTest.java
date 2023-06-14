package app.domain.model;

import app.controller.RecordSamplesController;
import app.domain.shared.Constants;
import app.domain.store.ClientStore;
import app.domain.store.SampleStore;
import app.domain.store.TestStore;
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


public class TestTest {

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

        t1 = new TestType("CODE3","blood test","blood",pcListBlood, Constants.BLOOD_EXTERNAL_ADAPTER_3);
        t2 = new TestType("CODE4","covid","swab",pcList, Constants.COVID_EXTERNAL_ADAPTER);

        selectedTT = new ArrayList<>();
        selectedTT.add(t1);
        selectedTT.add(t2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNullParameters(){
        app.domain.model.Test test = new app.domain.model.Test(null, null, null, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureNotPossibleToFindNotExistentClient(){
        ClientStore clientStore = new ClientStore();
        clientStore.getClientByTinNumber("1234567890");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithUnder12CharsNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("12345678901", client, t1, parametersBlood);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithMore12CharsNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("1234567890123", client, t1, parametersBlood);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithNotAlphanumericNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("!@#456789012", client, t1, parametersBlood);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestWithemptyNHScode(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = new app.domain.model.Test("", client, t1, parametersBlood);
    }

    @Test //this test checks if the generated number is truly sequential, making the boolean conditions for this purpouse.
    public void ensureTestCodeIsSequencial(){
        Client client = new Client("1234567890123450", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        Client client2 = new Client("1234567890123458", "1234567890", d1, "Male", "1234567890", "alex1@gmail.com", "Alex", "12345675901");
        Client client3 = new Client("1234567890123457", "1234567890", d1, "Male", "1234567890", "alex3@gmail.com", "Alex", "12345688901");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood);
        app.domain.model.Test test2 = new app.domain.model.Test("123456789012", client2, t2, parametersCovid);
        app.domain.model.Test test3 = new app.domain.model.Test("123456789012", client3, t1, parametersBlood);

        boolean firstAndSecondCondition = Long.parseLong(test.getCode()) == Long.parseLong(test2.getCode())-1;
        boolean secondAndThirdCondition = Long.parseLong(test2.getCode()) == Long.parseLong(test3.getCode())-1;
        Assert.assertTrue(firstAndSecondCondition && secondAndThirdCondition);
    }

    @Test //this test checks if the generated number is 12 digits long
    public void ensureCodeis12digits(){
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood);

        Assert.assertTrue(test.getCode().length() == 12);
    }

    @Test
    public void ensureNotPossibleToAddNullSample() {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);

        Assert.assertFalse(test.addSample(null));
    }

    @Test
    public void ensureTestWithNoSampleIsFound() {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = new app.domain.model.Test("123456789012", client, t1, parametersBlood);

        Assert.assertFalse(test.hasSamples());
    }

    @Test
    public void ensureTestWithSampleIsFound() throws BarcodeException {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));

        test.addSample(sample);
        test.addSampleCollectionDate();

        Assert.assertTrue(test.hasSamples());
    }



}

