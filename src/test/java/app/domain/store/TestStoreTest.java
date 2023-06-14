package app.domain.store;

import app.controller.App;
import app.controller.ImportTestController;
import app.controller.RecordSamplesController;
import app.domain.model.*;
import app.domain.shared.Constants;
import app.ui.console.utils.TestFileUtils;
import app.mappers.dto.TestFileDTO;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.output.OutputException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class TestStoreTest {

    static private List<Parameter> parametersBlood;
    static private List<Parameter> parametersCovid;
    static private List<ParameterCategory> pcListBlood;
    static private List<ParameterCategory> pcList;
    static private ParameterCategory p1;
    static private ParameterCategory p2;
    static private List<TestType> selectedTT;
    static private TestType t1;
    static private TestType t2;
    static private Date d1;
    static private ClinicalAnalysisLaboratory cal;
    static private ImportTestController importTestCtrl;
    static private TestStore testStore;
    static private Date startDate;

    @BeforeClass
    public static void setUp() throws ParseException, IllegalAccessException, InstantiationException, ClassNotFoundException, BarcodeException, OutputException, IOException {
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

        cal =  new ClinicalAnalysisLaboratory("001DO",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        //for US18 and US19
        TestFileUtils testFileUtils = new TestFileUtils();
        importTestCtrl = new ImportTestController();
        List<TestFileDTO> procedData = testFileUtils.getTestsDataToDto("tests_Covid_short.csv");
        for (TestFileDTO testData : procedData) {
            try{
                importTestCtrl.importTestFromFile(testData);
            }catch (Exception e){

            }

        }
        testStore = App.getInstance().getCompany().getTestStore();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 4);    // janeiro é representado por 0
        cal.set(Calendar.DAY_OF_MONTH, 29);
        startDate = cal.getTime();
        //end US18 and US19


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
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);
        Assert.assertFalse(testStore.saveTest(test));
    }

    @Test
    public void ensureDifferentTestsPassValidation(){
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);
        Client client2 = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test2 = testStore.createTest("123456789011", client, t1, parametersBlood, cal);
        Assert.assertTrue(testStore.saveTest(test2));
    }

    @Test //checks if tests with no sample are being found correctly
    public void ensureTestIsFoundByBarcodeNumber() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        TestStore testStore = new TestStore();
        SampleStore sampleStore = new SampleStore();
        RecordSamplesController recordSamplesController = new RecordSamplesController();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
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
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
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
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);


        assertSame(testStore.getTestByCodeInTestList(test.getCode()), test);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureNotExistentCodeThrowsException() {
        TestStore testStore = new TestStore();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);

        testStore.getTestByCodeInTestList("000000000002");
    }

    @Test
    public void ensureTestNotReadyToDiagnosisFalse() throws BarcodeException {
        TestStore testStore = new TestStore();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        test.addSample(sample);

        assertSame(testStore.getTestsReadyToDiagnose().size(),0);
    }

    @Test
    public void ensureTestReadyToDiagnosisTrue() throws BarcodeException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        TestStore testStore = new TestStore();

        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
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
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);

        Sample sample = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        test.addSample(sample);

        test.addTestResult("ALALA", 23.45, "ug");
    }


    @Test
    public void ensureGetTestParametersByTestReturnsCorrectly() {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);

        Assert.assertEquals(parametersBlood, testStore.getTotalTestParameters(test));
    }


    @Test
    public void ensureGetTestParametersReturnsCorrectly() {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        app.domain.model.Test test = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test);

        Assert.assertEquals(test.getParameters(), testStore.getTestParameters(test));
    }


//========== US16 ================

    /*@Test
    public void getNumberOfCovidTestsRealizedInADay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 4);    // january is represented by 0
        cal.set(Calendar.DAY_OF_MONTH, 20);
        Date date = cal.getTime();
        double expNumber = 15;

        double number = testStore.getNumberOfCovidTestsRealizedInADay(date);

        Assert.assertEquals(expNumber, number, 0.0);
    }*/

    //Test 5
    @Test
    public void testGetNumTestsWaitingForResultsDayOrInterval() throws BarcodeException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");

        app.domain.model.Test test1 = testStore.createTest("123456789011", client, t1, parametersBlood, cal);
        testStore.saveTest(test1);
        Date date1reg = new Date(2020,Calendar.JANUARY, 14,8,0,0);
        test1.setDateOfTestRegistration(date1reg);
        Sample sample1 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        test1.addSample(sample1);
        Date date1s = new Date(2020, Calendar.JANUARY, 15, 8, 0, 0);
        test1.setDateOfSamplesCollection(date1s);
        test1.addTestResult("RBC12", 23.45, "ug");
        test1.addTestResult("WBC12", 23.45, "ug");
        Date date1 = new Date(2020, Calendar.JANUARY, 16, 8, 0, 0);
        test1.setDateOfChemicalAnalysis(date1);
        Report report1 = new Report("Everything is well.");
        test1.addReport(report1);
        Date date1r = new Date(2020,Calendar.JANUARY,18,8,0,0);
        test1.setDateOfDiagnosis(date1r);

        app.domain.model.Test test2 = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test2);
        Date date2reg = new Date(2020,Calendar.JANUARY, 14,19,59,59);
        test2.setDateOfTestRegistration(date2reg);
        Sample sample2 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678902"), "12345678902"));
        test2.addSample(sample2);
        Date date2s = new Date(2020, Calendar.JANUARY, 15, 19, 59, 59);
        test2.setDateOfSamplesCollection(date2s);

        app.domain.model.Test test3 = testStore.createTest("123456789013", client, t1, parametersBlood, cal);
        testStore.saveTest(test3);
        Date date3reg = new Date(2020,Calendar.JANUARY, 14,15,1,26);
        test3.setDateOfTestRegistration(date3reg);

        app.domain.model.Test test4 = testStore.createTest("123456789011", client, t1, parametersBlood, cal);
        testStore.saveTest(test4);
        Date date4reg = new Date(2020,Calendar.JANUARY, 14,8,0,0);
        test4.setDateOfTestRegistration(date4reg);
        Sample sample4 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678904"), "12345678904"));
        test4.addSample(sample4);
        Date date4s = new Date(2020, Calendar.JANUARY, 15, 19, 59, 58);
        test4.setDateOfSamplesCollection(date4s);
        test4.addTestResult("RBC12", 23.45, "ug");
        test4.addTestResult("WBC12", 23.45, "ug");
        test4.addChemicalAnalysisDate();
        Date date4 = new Date(2020, Calendar.JANUARY, 15, 19, 59, 59);
        test4.setDateOfChemicalAnalysis(date4);
        Report report4 = new Report("Everything is well.");
        test4.addReport(report4);
        Date date4r = new Date(2020,Calendar.JANUARY,16,8,30,0);
        test4.setDateOfDiagnosis(date4r);

        Date beginningDay = new Date(2020, Calendar.JANUARY, 15, 8, 0, 0);
        Date endingDay = new Date(2020, Calendar.JANUARY, 15, 19, 59, 59);

        int expectedResult = 4;
        int obtainedResult = testStore.getNumTestsWaitingForResultsDayOrInterval(beginningDay, endingDay);

        Assert.assertEquals(expectedResult, obtainedResult);
    }

    //Test 6
    @Test
    public void testGetNumTestsWaitingForDiagnosisDayOrInterval() throws BarcodeException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");

        app.domain.model.Test test1 = testStore.createTest("123456789011", client, t1, parametersBlood, cal);
        testStore.saveTest(test1);
        Date date1reg = new Date(2020,Calendar.JANUARY, 14,8,0,0);
        test1.setDateOfTestRegistration(date1reg);
        Sample sample1 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        test1.addSample(sample1);
        Date date1s = new Date(2020, Calendar.JANUARY, 15, 8, 0, 0);
        test1.setDateOfSamplesCollection(date1s);
        test1.addTestResult("RBC12", 23.45, "ug");
        test1.addTestResult("WBC12", 23.45, "ug");
        Date date1 = new Date(2020, Calendar.JANUARY, 16, 8, 0, 0);
        test1.setDateOfChemicalAnalysis(date1);
        Report report1 = new Report("Everything is well.");
        test1.addReport(report1);
        Date date1r = new Date(2020,Calendar.JANUARY,18,8,0,0);
        test1.setDateOfDiagnosis(date1r);

        app.domain.model.Test test2 = testStore.createTest("123456789012", client, t1, parametersBlood, cal);
        testStore.saveTest(test2);
        Date date2reg = new Date(2020,Calendar.JANUARY, 14,19,59,59);
        test2.setDateOfTestRegistration(date2reg);
        Sample sample2 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678902"), "12345678902"));
        test2.addSample(sample2);
        Date date2s = new Date(2020, Calendar.JANUARY, 15, 19, 59, 59);
        test2.setDateOfSamplesCollection(date2s);

        app.domain.model.Test test3 = testStore.createTest("123456789013", client, t1, parametersBlood, cal);
        testStore.saveTest(test3);
        Date date3reg = new Date(2020,Calendar.JANUARY, 14,15,1,26);
        test3.setDateOfTestRegistration(date3reg);

        app.domain.model.Test test4 = testStore.createTest("123456789011", client, t1, parametersBlood, cal);
        testStore.saveTest(test4);
        Date date4reg = new Date(2020,Calendar.JANUARY, 14,8,0,0);
        test4.setDateOfTestRegistration(date4reg);
        Sample sample4 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678904"), "12345678904"));
        test4.addSample(sample4);
        Date date4s = new Date(2020, Calendar.JANUARY, 15, 19, 59, 58);
        test4.setDateOfSamplesCollection(date4s);
        test4.addTestResult("RBC12", 23.45, "ug");
        test4.addTestResult("WBC12", 23.45, "ug");
        test4.addChemicalAnalysisDate();
        Date date4 = new Date(2020, Calendar.JANUARY, 15, 19, 59, 59);
        test4.setDateOfChemicalAnalysis(date4);
        Report report4 = new Report("Everything is well.");
        test4.addReport(report4);
        Date date4r = new Date(2020,Calendar.JANUARY,16,8,30,0);
        test4.setDateOfDiagnosis(date4r);

        Date beginningDay = new Date(2020, Calendar.JANUARY, 15, 8, 0, 0);
        Date endingDay = new Date(2020, Calendar.JANUARY, 16, 19, 59, 59);

        int expectedResult = 4;
        int obtainedResult = testStore.getNumTestsWaitingForDiagnosisDayOrInterval(beginningDay, endingDay);

        Assert.assertEquals(expectedResult, obtainedResult);
    }

//========== END US16 ============

//=========== US1 ================

    @Test
    public void testGetTestsByClient() throws BarcodeException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        TestStore testStore = new TestStore();
        Client client = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678601");
        Client client2 = new Client("1234567890123333", "1234567777", d1, "Female", "1234567777", "alexandra@gmail.com", "Alexandra", "12345677777");

        app.domain.model.Test test1 = testStore.createTest("123456789011", client, t1, parametersBlood, cal);
        testStore.saveTest(test1);
        Date date1reg = new Date(2020,Calendar.JANUARY, 14,8,0,0);
        test1.setDateOfTestRegistration(date1reg);

        app.domain.model.Test test4 = testStore.createTest("123456789011", client2, t1, parametersBlood, cal);
        testStore.saveTest(test4);
        Date date4reg = new Date(2020,Calendar.JANUARY, 14,8,1,0);
        test4.setDateOfTestRegistration(date4reg);

        app.domain.model.Test test3 = testStore.createTest("123456789013", client, t1, parametersBlood, cal);
        testStore.saveTest(test3);
        Date date3reg = new Date(2020,Calendar.JANUARY, 14,15,1,26);
        test3.setDateOfTestRegistration(date3reg);

        app.domain.model.Test test2 = testStore.createTest("123456789012", client2, t1, parametersBlood, cal);
        testStore.saveTest(test2);
        Date date2reg = new Date(2020,Calendar.JANUARY, 14,19,59,59);
        test2.setDateOfTestRegistration(date2reg);

        ArrayList<app.domain.model.Test> expectedResult = new ArrayList<>();
        expectedResult.add(test4);
        expectedResult.add(test2);

        ArrayList<app.domain.model.Test> obtainedResult = testStore.getTestsByClient(client2);

        Assert.assertEquals(expectedResult, obtainedResult);
    }

//=========== END US1 ============

    //for US18 and US19


    // Porque dá erro no Jenkins??

    @Test
    public void getObservedPositivesToTableOfValues() throws ParseException, IllegalAccessException, InstantiationException, OutputException, IOException, BarcodeException, ClassNotFoundException {
        int numberOfObservations = 8;
        NHSReportStore nhsReportStore = new NHSReportStore();
        List<String> dates = nhsReportStore.getDatesColumnToTableOfValues(numberOfObservations, startDate);
        int[] expObservedPositives = {5, 6, 4, 6, 11,11, 17, 13};

        int[] observedPositives = testStore.getObservedPositivesToTableOfValues(numberOfObservations, dates);

        System.out.println(observedPositives[0]);
        System.out.println(observedPositives[1]);
        System.out.println(observedPositives[2]);
        System.out.println(observedPositives[3]);
        System.out.println(observedPositives[4]);
        System.out.println(observedPositives[5]);
        System.out.println(observedPositives[6]);
        System.out.println(observedPositives[7]);

        Assert.assertArrayEquals(expObservedPositives, observedPositives);
    }


    @Test
    public void getObservedPositivesInOneWeekTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int actual = testStore.getObservedPositivesInOneWeek(sdf.parse("17/05/2021"), sdf.parse("22/05/2021"));
        int expected = 105;
        Assert.assertEquals(expected, actual);
    }



    @Test
    public void getWeeklyObservedPositivesToTableOfValuesTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        NHSReportStore nhsReportStore = new NHSReportStore();
        List<String> dates = new ArrayList<>();
        dates.add(nhsReportStore.getWeek(sdf.parse("17/05/2021"), sdf.parse("22/05/2021")));
        dates.add(nhsReportStore.getWeek(sdf.parse("24/05/2021"), sdf.parse("29/05/2021")));
        int[] actual = testStore.getWeeklyObservedPositivesToTableOfValues(dates);
        int[] expected = {105, 43};

        Assert.assertArrayEquals(expected, actual);
    }


    @Test
    public void getNumberOfCovidTestsInOneWeek() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        double actual = testStore.getNumberOfCovidTestsInOneWeek(sdf.parse("17/05/2021"), sdf.parse("22/05/2021"));
        double expected = 114.0;
        Assert.assertEquals(actual, expected, 1);
    }

    @Test
    public void getWeeklyNumberOfCovidTestsInHistoricalPointsTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        NHSReportStore nhsReportStore = new NHSReportStore();
        List<String> dates = new ArrayList<>();
        dates.add(nhsReportStore.getWeek(sdf.parse("17/05/2021"), sdf.parse("22/05/2021")));
        dates.add(nhsReportStore.getWeek(sdf.parse("24/05/2021"), sdf.parse("29/05/2021")));
        Double[] actual = testStore.getWeeklyNumberOfCovidTestsInHistoricalPoints(dates);
        Double[] expected = {114.0, 49.0};

        Assert.assertArrayEquals(expected, actual);
    }


    @Test
    public void getMeanAgeInOneWeek() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        double actual = testStore.getMeanAgeInOneWeek(sdf.parse("17/05/2021"), sdf.parse("22/05/2021"));
        double expected = 30.8;
        Assert.assertEquals(expected, actual, 2);
    }

    @Test
    public void getWeeklyMeanAgeInHistoricalPointsTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        NHSReportStore nhsReportStore = new NHSReportStore();
        List<String> dates = new ArrayList<>();
        dates.add(nhsReportStore.getWeek(sdf.parse("17/05/2021"), sdf.parse("22/05/2021")));
        dates.add(nhsReportStore.getWeek(sdf.parse("24/05/2021"), sdf.parse("29/05/2021")));
        Double[] actual = testStore.getWeeklyMeanAgeInHistoricalPoints(dates);
        double [] actual1 = {actual[0], actual[1]};
        double[] expected = {30.8, 26.3};

        Assert.assertArrayEquals(expected, actual1, 2);
    }

    @Test
    public void getMeanAgeOfClientsOfCovidTestsInADay() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 4);    // january is represented by 0
        cal.set(Calendar.DAY_OF_MONTH, 28);
        Date date = cal.getTime();

        double expNumber = 24.83333;
        double number = testStore.getMeanAgeOfClientsOfCovidTestsInADay(date);

        Assert.assertEquals(expNumber, number, 0.0001);
    }

    //Porque dá erro no Jenkins?

    @Test
    public void getObservedPositivesCovidInADay() {
        double expNumber = 5.0;
        double number = testStore.getObservedPositivesCovidInADay(startDate);

        Assert.assertEquals(expNumber, number, 0.0);
    }

    @Test
    public void addWeeklyDataFromDateInterval() throws ParseException {
        List<Double> expCovidTestList = new ArrayList<>();
        List<Double> expMeanAgeList = new ArrayList<>();
        List<Double> expObservedPositives = new ArrayList<>();

        Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/05/2021");
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("28/05/2021");

        List<List<Double>> expectedList = new ArrayList<>();
        expCovidTestList.add(43.0);
        expCovidTestList.add(114.0);
        expCovidTestList.add(159.0);

        expMeanAgeList.add(26.953488372093023);
        expMeanAgeList.add(30.833333333333332);
        expMeanAgeList.add(35.9559748427673);

        expObservedPositives.add(38.0);
        expObservedPositives.add(105.0);
        expObservedPositives.add(153.0);

        expectedList.add(expCovidTestList);
        expectedList.add(expMeanAgeList);
        expectedList.add(expObservedPositives);
        //Act
        List<Double> covidTestList = new ArrayList<>();
        List<Double> meanAgeList = new ArrayList<>();
        List<Double> observedPositives = new ArrayList<>();

        testStore.addWeeklyDataFromDateInterval(beginDate, endDate, covidTestList, meanAgeList, observedPositives);

        List<List<Double>> list = new ArrayList<>();
        list.add(covidTestList);
        list.add(meanAgeList);
        list.add(observedPositives);

        Assert.assertEquals(expectedList, list);


    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkDateIntervalHasMinRange() throws ParseException {
        Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse("18/06/2021");
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("22/06/2021");

        boolean result = testStore.checkDateIntervalHasMinRange(beginDate, endDate);

        Assert.assertFalse(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkDateIntervalHasMinRange2() throws ParseException {

        Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse("14/06/2021");
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("18/06/2021");

        boolean result = testStore.checkDateIntervalHasMinRange(beginDate, endDate);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void checkDateIntervalHasMinRange3() throws ParseException {

        Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse("9/06/2021");
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("16/06/2021");

        boolean result = testStore.checkDateIntervalHasMinRange(beginDate, endDate);

    }

    @Test
    public void checkDateIntervalHasMinRangeTrue() throws ParseException {

        Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse("7/06/2021");
        Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse("19/06/2021");

        boolean result = testStore.checkDateIntervalHasMinRange(beginDate, endDate);

        Assert.assertTrue(result);
    }

    @Test
    public void getFinalBeginDateForWeekData() throws ParseException {
        Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse("7/06/2021");

        Date expectedFinalBeginDate = new SimpleDateFormat("dd/MM/yyyy").parse("7/06/2021");
        Date finalBeginDate = testStore.getFinalBeginDateForWeekData(beginDate);

        Assert.assertEquals(expectedFinalBeginDate, finalBeginDate);
    }

    @Test
    public void getFinalBeginDateForWeekData2() throws ParseException {
        Date beginDate = new SimpleDateFormat("dd/MM/yyyy").parse("15/06/2021");

        Date expectedFinalBeginDate = new SimpleDateFormat("dd/MM/yyyy").parse("21/06/2021");
        Date finalBeginDate = testStore.getFinalBeginDateForWeekData(beginDate);

        Assert.assertEquals(expectedFinalBeginDate, finalBeginDate);
    }

    @Test
    public void getFinalEndDateForWeekData1() throws ParseException {
        Date finalDate = new SimpleDateFormat("dd/MM/yyyy").parse("12/06/2021");

        Date expectedFinalEndDate = new SimpleDateFormat("dd/MM/yyyy").parse("12/06/2021");
        Date finalEndDate = testStore.getFinalEndDateForWeekData(finalDate);

        Assert.assertEquals(expectedFinalEndDate, finalEndDate);
    }

    @Test
    public void getFinalEndDateForWeekData2() throws ParseException {
        Date finalDate = new SimpleDateFormat("dd/MM/yyyy").parse("23/06/2021");

        Date expectedFinalEndDate = new SimpleDateFormat("dd/MM/yyyy").parse("19/06/2021");
        Date finalEndDate = testStore.getFinalEndDateForWeekData(finalDate);

        Assert.assertEquals(expectedFinalEndDate, finalEndDate);
    }







    //end US18 and US19
}