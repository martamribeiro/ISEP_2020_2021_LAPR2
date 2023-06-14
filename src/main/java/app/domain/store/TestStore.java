package app.domain.store;

import app.domain.model.*;
import app.domain.shared.Constants;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Store of tests
 *
 * @author SRC-Code-23
 */
public class TestStore implements Serializable {

    /**
     * List of all existing tests
     */
    private List<Test> testList = new ArrayList<>();

    /**
     * Method for creating an instance of Test
     *
     * @param nhsCode          National health system code of a given test
     * @param associatedClient client object which has solicited a test
     * @param testType         Type of test to be conduted
     * @param parameters       List of parameters to be measured of a given test
     */
    public Test createTest(String nhsCode, Client associatedClient, TestType testType, List<Parameter> parameters, ClinicalAnalysisLaboratory cal) {
        return new Test(nhsCode, associatedClient, testType, parameters, cal);
    }

    /**
     * Method for creating an instance of Test
     *
     * @param nhsCode          National health system code of a given test
     * @param associatedClient client object which has solicited a test
     * @param testType         Type of test to be conduted
     * @param parameters       List of parameters to be measured of a given test
     */
    public Test createTest(String nhsCode, Client associatedClient, TestType testType, List<Parameter> parameters,List<Double> paramsResults,
                           ClinicalAnalysisLaboratory cal, Date testRegDate, Date testChemDate, Date testDiagnosisDate, Date testValidationDate) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        return new Test(nhsCode, associatedClient, testType, parameters, paramsResults, cal, testRegDate, testChemDate, testDiagnosisDate, testValidationDate);
    }

    /**
     * Validation of test instance relative to list of tests. checking for null and duplicity
     *
     * @param test test to be validated
     * @return true for success and false for fail
     */
    public boolean validateTest(Test test) {
        if (test == null)
            return false;
        return !this.testList.contains(test);
    }

    /**
     * Save of test instance inside the list of test store, checking for validation before saving.
     *
     * @param test test to be saved to the list
     * @return true for success and false for fail
     */
    public boolean saveTest(Test test) {
        if (!validateTest(test))
            return false;
        return this.testList.add(test);
    }

    /**
     * Gets all tests stored in the store
     * @return list of tests
     */
    public List<Test> getTests() {
        return new ArrayList<>(testList);
    }

    /**
     * Gets the number of tests that were waiting for results on a specific day/interval
     * @return number of tests that were waiting for results on a specific day/interval
     */
    public int getNumTestsWaitingForResultsDayOrInterval(Date beginningDay, Date endingDay){
        int num = 0;
        Date date1, date2;
        for (Test test : testList) {
            date2 = test.getDateOfTestRegistration();
            date1 = test.getDateOfChemicalAnalysis();
            if (date2!=null && !date2.equals(date1)) {
                if ((date2.before(beginningDay) && (date1==null || date1.after(beginningDay))) ||
                date2.equals(beginningDay) ||
                        (date2.after(beginningDay) && date2.before(endingDay))){
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * Gets the number of tests that were waiting for diagnosis on a specific day/interval
     * @return number of tests that were waiting for diagnosis on a specific day/interval
     */
    public int getNumTestsWaitingForDiagnosisDayOrInterval(Date beginningDay, Date endingDay){
        int num = 0;
        Date date1, date2;
        for (Test test : testList) {
            date2 = test.getDateOfTestRegistration();
            date1 = test.getDateOfDiagnosis();
            if (date2!=null && !date2.equals(date1)) {
                if ((date2.before(beginningDay) && (date1==null || date1.after(beginningDay))) ||
                        date2.equals(beginningDay) ||
                        (date2.after(beginningDay) && date2.before(endingDay))){
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * gets the tests which are ready to be diagnosed
     * @return list of ready to diagnosis tests
     */
    public List<Test> getTestsReadyToDiagnose() {
        List<Test> listTestsReadyToDiagnose = new ArrayList<>();

        for (Test test : testList) {
            if (test.hasSamplesAnalysed() && (test.getDiagnosisReport() == null))
                listTestsReadyToDiagnose.add(test);
        }
        return listTestsReadyToDiagnose;
    }

    /**
     * Gets the tests by its code
     * @param code code of the test to be found
     * @return Found test
     */
    public Test getTestByCode(String code) {
        for (Test tst : getTestsReadyToDiagnose()) {
            if (tst.getCode().equalsIgnoreCase(code)) {
                return tst;
            }
        }
        throw new UnsupportedOperationException("Test not found in ready to diagnose list!");
    }

    public ArrayList<Test> getTestsByClient(Client client){
        ArrayList<Test> clientTests = new ArrayList<Test>();
        for (Test tst : testList){
            if (tst.getClient().equals(client)){
                clientTests.add(tst);
            }
        }
        return clientTests;
    }

    /**
     * Method for getting the tests in validated state for a given client's tin number
     * @param tinNumber tin number of client to get the tests of.
     * @return list of validated tests of the client.
     */
    public List<Test> getValidatedTestsByClientTin(String tinNumber){
        List<Test> clientTests = new ArrayList<>();
        for (Test tst : testList){
            if (tst.isOfClient(tinNumber) && tst.isValidated()){
                clientTests.add(tst);
            }
        }
        return clientTests;
    }

    /**
     * Method for getting a test by it's code in the tests list stored in the testStore
     * @param code code of the test to be found
     * @return found Test object with given code
     */
    public Test getTestByCodeInTestList(String code) {
        for (Test test : testList) {
            if (test.getCode().equalsIgnoreCase(code))
                return test;
        }
        throw new UnsupportedOperationException("Test not found!");
    }

    /**
     * Gets the number of tests that were registered between the desired interval of time
     *
     * @param beginningDay the beginning date of the desired interval of time
     * @param endingDay the end date of the desired interval of time
     * @return the number of tests that were registered between the desired interval of time
     */
    public int getNumberOfTestsByIntervalDateOfTestRegistration(Date beginningDay, Date endingDay) {
        int num = 0;
        for (Test test : testList) {
            if ((test.getDateOfTestRegistration().after(beginningDay) && test.getDateOfTestRegistration().before(endingDay))
            || (test.getDateOfTestRegistration().equals(beginningDay)) || (test.getDateOfTestRegistration().equals(endingDay)))
                num++;
        }
        return num;
    }

    /**
     * Gets the number of tests that were diagnosed between the desired interval of time
     *
     * @param beginningDay the beginning date of the desired interval of time
     * @param endingDay the end date of the desired interval of time
     * @return the number of tests that were diagnosed between the desired interval of time
     */
    public int getNumberOfTestsByIntervalDateOfDiagnosis(Date beginningDay, Date endingDay){
        //because it only becomes available to the client after the diagnosis
        int num = 0;
        for (Test test : testList) {
            if ((test.getDateOfValidation().after(beginningDay) && test.getDateOfValidation().before(beginningDay)) || (test.getDateOfValidation().equals(beginningDay)) || (test.getDateOfValidation().equals(endingDay)))
                num++;
        }
        return num;
    }

    /**
     * Gets a list of test parameters of a test
     * @param tst test to retrieve list
     * @return list of test parameters
     */
    public List<TestParameter> getTestParameters(Test tst) {
        return new ArrayList<>(tst.getParameters());
    }

    /**
     * Method for getting list of tests in the store list with no samples collected.
     *
     *
     * @return list of tests with no samples
     */
    public List<Test> getTestsWithNoSamples(String laboratoryID) {
        List<Test> listTestsNoSamples = new ArrayList<>();

        for (Test test : testList) {
            if (!test.hasSamples() && test.getCalId().equals(laboratoryID))
                listTestsNoSamples.add(test);
        }
        return listTestsNoSamples;
    }

    /**
     * Method for getting all the clients with tests in validated state
     * @return list of client object with who have validated tests stored
     */
    public List<Client> getClientsWithValidatedTests (){
        List<Client> clientsWithValidatedTests  = new ArrayList<>();
        for(Test test : testList){
            if(test.isValidated()){
                clientsWithValidatedTests.add(test.getClient());
            }
        }
        return clientsWithValidatedTests;
    }

    /**
     * Gets a list of the parameters of the test parameters of an specified test object
     * @param test test object to find parameters
     * @return list of parameters
     */
    public List<Parameter> getTotalTestParameters(Test test) {
        List<Parameter> listTotalTestParameters = new ArrayList<>();

        for (TestParameter testParameter : test.getParameters())
            listTotalTestParameters.add(testParameter.getParameter());

        return listTotalTestParameters;
    }


    /**
     * Gets a test object by its sample barcode number
     * @param barcodeNumber barcode number to find in the tests
     * @return found test
     */
    public Test getTestByBarcodeNumber(String barcodeNumber) {
        for (Test test : testList) {
            if (testHasBarcodeNumber(test, barcodeNumber))
                return test;
        }
        throw new UnsupportedOperationException("Test not found!");
    }

    /**
     * Checks if an specified test has a given barcode number
     * @param test test to search barcode number
     * @param barcodeNumber barcode number to be searched
     * @return true if has barcode number, false otherwise
     */
    private boolean testHasBarcodeNumber(Test test, String barcodeNumber) {
        for (Sample sample : test.getSamples()) {
            if (sample.getMyBarcode().getBarcodeNumber().equals(barcodeNumber))
                return true;
        }
        return false;
    }

    /**
     * Returns a list containing the observed Positive cases in the historical points.
     *
     * @param numberOfObservations number of observations
     * @param dates dates of the historical points
     *
     * @return a list containing the observed Positive cases in the historical points
     */
    public int[] getObservedPositivesToTableOfValues(int numberOfObservations,
                                                    List<String> dates) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        int[] observedPositives = new int[numberOfObservations];
        int indexDate = 0;

        List<Test> testListCopy = new CopyOnWriteArrayList<>(testList);
        for (Iterator<Test> iterator = testListCopy.iterator(); iterator.hasNext();) {
            Test test = iterator.next();
            if(test.hasPositiveResultForCovid()) {
                for (int i = 0; i < dates.size(); i++) {
                    String testDateOfRegistration = sdf.format(test.getDateOfTestRegistration());
                    if(testDateOfRegistration.equals(dates.get(i))) {
                        indexDate = i;
                        observedPositives[indexDate]++;
                    }
                }
            }
        }
        return observedPositives;
    }

    /**
     * Returns a list containing the weekly observed Positive cases in the historical points.
     *
     * @param dates weeks of the historical points
     * @throws ParseException if the date to be compared is not valid
     *
     * @return a list containing the weekly observed Positive cases in the historical points
     */
    public int[] getWeeklyObservedPositivesToTableOfValues(List<String> dates) throws ParseException {
        int[] observedPositives = new int[dates.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < dates.size(); i++) {
            String[] intervalDatesInString = dates.get(i).split("-");
            Date beginDate = sdf.parse(intervalDatesInString[0]), endDate = sdf.parse(intervalDatesInString[1]);
            observedPositives[i] = getObservedPositivesInOneWeek(beginDate, endDate);
        }
        return observedPositives;
    }

    /**
     * Returns the observed positive Covid-19 cases in a certain week.
     *
     * @param beginDate initial date
     * @param endDate final date
     *
     * @return observed positive Covid-19 cases in a certain week
     */
    public int getObservedPositivesInOneWeek(Date beginDate, Date endDate) {
        int weeklyPositives = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        Date auxEndDate = cal.getTime();
        while(!beginDate.after(auxEndDate) && !endDate.before(auxEndDate)) {
            weeklyPositives += getObservedPositivesCovidInADay(auxEndDate);
            cal.add(Calendar.DAY_OF_MONTH,-1);
            auxEndDate = cal.getTime();
        }
        return weeklyPositives;
    }

    /**
     * Returns the number of Covid Tests Realized on a certain day.
     *
     * @param date date of the day
     *
     * @return the number of Covid Tests Realized on a certain day
     */
    public double getNumberOfCovidTestsRealizedInADay(Date date) {
        double testsInADay = 0;
        List<Test> testListCopy = new CopyOnWriteArrayList<>(testList);
        for (Iterator<Test> iterator = testListCopy.iterator(); iterator.hasNext();) {
            Test test = iterator.next();
            if (test.isCovidTest() && test.isValidated() && checkIfDatesAreEqual(test.getDateOfTestRegistration(), date)) {
                testsInADay++;
            }
        }
        return testsInADay;
    }

    /**
     * Returns the mean age of Clients who did Covid-test in a certain day.
     *
     * @param date date of the day
     *
     * @return the mean age of Clients who did Covid-test in a certain day
     */
    public double getMeanAgeOfClientsOfCovidTestsInADay(Date date) {
        return (getNumClientsWithValidatedTestsInADay(date) != 0)
                ? getSumOfClientAgesInADay(date) / getNumClientsWithValidatedTestsInADay(date)
                    : 0;
    }

    /**
     * Returns the number of Clients which did validated tests in a day.
     *
     * @param date date of the day
     *
     * @return the number of Clients which did validated tests in a day
     */
    public double getNumClientsWithValidatedTestsInADay(Date date){
        double numClients = 0;
        List<Test> testListCopy = new CopyOnWriteArrayList<>(testList);
        for (Iterator<Test> iterator = testListCopy.iterator(); iterator.hasNext();) {
            Test test = iterator.next();
            if (test.isCovidTest() && test.isValidated() && checkIfDatesAreEqual(test.getDateOfTestRegistration(), date)) {
                numClients++;
            }
        }
        return numClients;
    }

    /**
     * Returns the sum of Client's ages in a day.
     *
     * @param date date of the day
     *
     * @return the sum of Client's ages in a day
     */
    public double getSumOfClientAgesInADay(Date date){
        double sumAges = 0;
        List<Test> testListCopy = new CopyOnWriteArrayList<>(testList);
        for (Iterator<Test> iterator = testListCopy.iterator(); iterator.hasNext();) {
            Test test = iterator.next();
            if (test.isCovidTest() && test.isValidated() && checkIfDatesAreEqual(test.getDateOfTestRegistration(), date)) {
                sumAges += test.getClient().getAge();
            }
        }
        return  sumAges;
    }

    /**
     * Returns the observed Covid-19 cases in a certain day.
     *
     * @param date date of the day
     *
     * @return the observed Covid-19 cases in a certain day
     */
    public double getObservedPositivesCovidInADay(Date date) {
        double positives = 0;
        List<Test> testListCopy = new CopyOnWriteArrayList<>(testList);
        for (Iterator<Test> iterator = testListCopy.iterator(); iterator.hasNext();) {
            Test test = iterator.next();
            if (test.hasPositiveResultForCovid() && test.isValidated() && checkIfDatesAreEqual(test.getDateOfValidation(), date)) {
                positives++;
            }
        }
        return positives;
    }

    /**
     * Returns a list containing 3 lists in a certain date interval:
     * the list of the Covid-tests realized,
     * the list of the Mean Age of the clients which did said tests,
     * the list of the observed Positives
     *
     * @param beginDate initial date
     * @param endDate final date
     * @param typeOfData the type of data (day or week)
     *
     * @return a list containing the 3 lists required in a certain date interval
     */
    public List< List<Double> > getAllDataToFitTheModel(Date beginDate, Date endDate, String typeOfData) {
        Calendar auxEndDate = Calendar.getInstance();
        auxEndDate.setTime(endDate);

        List<Double> covidTestList = new ArrayList<>();
        List<Double> meanAgeList = new ArrayList<>();
        List<Double> observedPositives = new ArrayList<>();

        if(typeOfData.equals(Constants.DAY_DATA)) {
            addAllDataFromDateInterval(beginDate, endDate, covidTestList, meanAgeList, observedPositives);
        } else {
            if(checkDateIntervalHasMinRange(beginDate, endDate)) {
                beginDate = getFinalBeginDateForWeekData(beginDate);
                endDate = getFinalEndDateForWeekData(endDate);
            }
            addWeeklyDataFromDateInterval(beginDate, endDate, covidTestList, meanAgeList, observedPositives);
        }


        List< List<Double> > dataList = new ArrayList<>();
        dataList.add(covidTestList);
        dataList.add(meanAgeList);
        dataList.add(observedPositives);

        return dataList;
    }

    /**
     * Fills all the required arrays containing the daily data to fit the regression model.
     *
     * @param beginDate initial date
     * @param endDate final date
     * @param covidTestList list of the Covid-19 tests
     * @param meanAgeList list of the Mean Age of Clients
     * @param observedPositives list of observed Positives
     */
    public void addAllDataFromDateInterval(Date beginDate,
                                           Date endDate,
                                           List<Double> covidTestList,
                                           List<Double> meanAgeList,
                                           List<Double> observedPositives) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        Date auxEndDate = cal.getTime();

        while(!beginDate.after(auxEndDate) && !endDate.before(auxEndDate)) {
            double testsInADay = getNumberOfCovidTestsRealizedInADay(auxEndDate);
            covidTestList.add(testsInADay);
            double meanAgeInADay = getMeanAgeOfClientsOfCovidTestsInADay(auxEndDate);
            meanAgeList.add(meanAgeInADay);
            double observedPositivesInADay = getObservedPositivesCovidInADay(auxEndDate);
            observedPositives.add(observedPositivesInADay);
            cal.add(Calendar.DAY_OF_MONTH,-1);
            if ((cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
                cal.add(Calendar.DAY_OF_MONTH,-1);
            auxEndDate = cal.getTime();
        }
    }

    /**
     * Fills all the required arrays containing the weekly data to fit the regression model.
     *
     * @param beginDate initial date
     * @param endDate final date
     * @param covidTestList list of the Covid-19 tests
     * @param meanAgeList list of the Mean Age of Clients
     * @param observedPositives list of observed Positives
     */
    public void addWeeklyDataFromDateInterval(Date beginDate,
                                              Date endDate,
                                              List<Double> covidTestList,
                                              List<Double> meanAgeList,
                                              List<Double> observedPositives) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        Date auxEndDate = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -4);
        Date auxBeginDate = cal.getTime();

        while(!beginDate.after(auxEndDate) && !endDate.before(auxEndDate)) {
            double testsInAWeek = getNumberOfCovidTestsInOneWeek(auxBeginDate, auxEndDate);
            covidTestList.add(testsInAWeek);
            double meanAgeInAWeek = getMeanAgeInOneWeek(auxBeginDate, auxEndDate);
            meanAgeList.add(meanAgeInAWeek);
            double observedPositivesInAWeek = getObservedPositivesInOneWeek(auxBeginDate, auxEndDate);
            observedPositives.add(observedPositivesInAWeek);
            cal.add(Calendar.DAY_OF_MONTH, -2);
            auxEndDate = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, -5);
            auxBeginDate = cal.getTime();
        }
    }

    /**
     * Returns the number of validated tests in the  daily historical points.
     *
     * @param dates list of dates of the historical points
     *
     * @return the number of validated tests in the historical points
     */
    public Double[] getNumberOfCovidTestsInHistoricalPoints(List<String> dates) {
        double[] covidTestsInHistoricalPointsPrimitive = new double[dates.size()];
        int indexDate = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<Test> testListCopy = new CopyOnWriteArrayList<>(testList);
        for (Iterator<Test> iterator = testListCopy.iterator(); iterator.hasNext();) {
            Test test = iterator.next();
            if(test.isCovidTest() && test.isValidated()) {
                for (int i = 0; i < dates.size(); i++) {
                    String testDateOfRegistration = sdf.format(test.getDateOfTestRegistration());
                    if (testDateOfRegistration.equals(dates.get(i))) {
                        indexDate = i;
                        covidTestsInHistoricalPointsPrimitive[indexDate]++;
                    }
                }
            }
        }
        Double[] covidTestsInHistoricalPoints = turnPrimitiveIntoDoubleArray(covidTestsInHistoricalPointsPrimitive);
        return covidTestsInHistoricalPoints;
    }

    /**
     * Returns the number of validated tests in the weekly historical points.
     *
     * @param dates list of dates of the historical points
     *
     * @return the number of validated tests in the weekly historical points
     *
     * @throws ParseException if the date to be compared is not valid
     */
    public Double[] getWeeklyNumberOfCovidTestsInHistoricalPoints(List<String> dates) throws ParseException {
        double[] covidTestsInHistoricalPointsPrimitive = new double[dates.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < dates.size(); i++) {
            String[] intervalDatesInString = dates.get(i).split("-");
            Date beginDate = sdf.parse(intervalDatesInString[0]), endDate = sdf.parse(intervalDatesInString[1]);
            covidTestsInHistoricalPointsPrimitive[i] = getNumberOfCovidTestsInOneWeek(beginDate, endDate);
        }
        Double[] covidTestsInHistoricalPoints = turnPrimitiveIntoDoubleArray(covidTestsInHistoricalPointsPrimitive);
        return covidTestsInHistoricalPoints;
    }

    /**
     * Returns the number of Covid Tests realized in one week.
     *
     * @param beginDate initial date
     * @param endDate final date
     *
     * @return the number of Covid Tests realized in one week
     */
    public double getNumberOfCovidTestsInOneWeek(Date beginDate, Date endDate) {
        int weeklyTests = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        Date auxEndDate = cal.getTime();
        while(!beginDate.after(auxEndDate) && !endDate.before(auxEndDate)) {
            weeklyTests += getNumberOfCovidTestsRealizedInADay(auxEndDate);
            cal.add(Calendar.DAY_OF_MONTH,-1);
            auxEndDate = cal.getTime();
        }
        return weeklyTests;
    }

    /**
     * Returns the mean age of Clients in the daily historical points
     *
     * @param dates list of dates of the historical points
     *
     * @return the mean age of Clinets in the daily historical points
     */
    public Double[] getMeanAgeInHistoricalPoints(List<String> dates) {
        Double[] meanAgeInHistoricalPoints = new Double[dates.size()];
        double sumAges = 0, numClients = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < dates.size(); i++) {
            for (Test test : testList) {
                if(test.isCovidTest() && test.isValidated()) {
                    String testDateOfRegistration = sdf.format(test.getDateOfTestRegistration());
                    if(testDateOfRegistration.equals(dates.get(i))) {
                        sumAges += test.getClient().getAge();
                        numClients++;
                    }
                }
            }
            if(numClients == 0)
                meanAgeInHistoricalPoints[i] = 0.0;
            else
                meanAgeInHistoricalPoints[i] = sumAges / numClients; //antes só tinha esta linha
            sumAges = 0;
            numClients = 0;
        }
        return meanAgeInHistoricalPoints;
    }

    /**
     * Returns the mean age of the Clinets in weekly historical points.
     *
     * @param dates list of dates of the historical points
     *
     * @return the mean age of the Clinets in weekly historical points
     *
     * @throws ParseException if the data to be compared is not valid
     */
    public Double[] getWeeklyMeanAgeInHistoricalPoints(List<String> dates) throws ParseException {
        Double[] meanAgeInHistoricalPoints = new Double[dates.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < dates.size(); i++) {
            String[] intervalDatesInString = dates.get(i).split("-");
            Date beginDate = sdf.parse(intervalDatesInString[0]), endDate = sdf.parse(intervalDatesInString[1]);
            meanAgeInHistoricalPoints[i] = getMeanAgeInOneWeek(beginDate, endDate);
        }
        return meanAgeInHistoricalPoints;
    }


    public double getMeanAgeInOneWeek(Date beginDate, Date endDate) {
        int sumAges = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        Date auxEndDate = cal.getTime();
        double numClients=0;
        while(!beginDate.after(auxEndDate) && !endDate.before(auxEndDate)) {
            sumAges += getSumOfClientAgesInADay(auxEndDate);
            numClients += getNumClientsWithValidatedTestsInADay(auxEndDate);
            cal.add(Calendar.DAY_OF_MONTH,-1);
            auxEndDate = cal.getTime();
        }
        return sumAges / numClients;
    }


    /**
     * Returns true if the dates are equal in day, month and year.
     *
     * @param date date
     * @param otherDate date to be compared with
     *
     * @return Returns true if the dates are equal in day, month and year.
     *          Otherwise, returns false.
     */
    public boolean checkIfDatesAreEqual(Date date, Date otherDate) {
        Calendar cal = Calendar.getInstance(), otherCal = Calendar.getInstance();
        cal.setTime(date);
        otherCal.setTime(otherDate);
        return cal.get(Calendar.DAY_OF_MONTH) == otherCal.get(Calendar.DAY_OF_MONTH) &&
                cal.get(Calendar.MONTH) == otherCal.get(Calendar.MONTH) &&
                cal.get(Calendar.YEAR) == otherCal.get(Calendar.YEAR);
    }

    /**
     * Returns a Double[] array containing the elements of a double[] array
     * received by parameter
     *
     * @param array array whose elements are to be copied
     *
     * @returna Double[] array containing the elements of a double[] array received by parameter
     */
    public Double[] turnPrimitiveIntoDoubleArray(double[] array) {
        Double[] wishedArray = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            wishedArray[i] = array[i];
        }
        return wishedArray;
    }

    /**
     * Returns true if the date interval to fit the model has the
     * minimum range required in order to provide accurate and
     * correct data for the model
     *
     * @param beginDate the initial date
     * @param endDate the final date
     *
     * @return true if the date interval to fit the model has the
     * minimum range required. Otherwise, returns false.
     */
    public boolean checkDateIntervalHasMinRange(Date beginDate, Date endDate) {
        Calendar auxInitialDate = Calendar.getInstance();
        auxInitialDate.setTime(beginDate);
        int cont = 0;

        while(auxInitialDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            auxInitialDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        Date finalBeginDate = auxInitialDate.getTime();

        while(!finalBeginDate.after(endDate) || checkIfDatesAreEqual(finalBeginDate, endDate)) {
            cont++;
            auxInitialDate.add(Calendar.DAY_OF_MONTH, 1);
            finalBeginDate = auxInitialDate.getTime();
        }

        if(cont < Constants.MINIMUM_ALLOWED_WEEK_DAYS)
            throw new UnsupportedOperationException("For Week data, you must select a range in which at least 2 COMPLETE week (Monday-Saturday) fits in it!");

        return true;
    }

    /**
     * Returns the final initial date for providing data to fit the regression model.
     *
     * @param beginDate the initial date provided
     *
     * @return final initial date
     */
    public Date getFinalBeginDateForWeekData(Date beginDate) {
        Calendar auxInitialDate = Calendar.getInstance();
        auxInitialDate.setTime(beginDate);
        while(auxInitialDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            auxInitialDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return auxInitialDate.getTime();
    }

    /**
     * Returns the final end date for providing data to fit the regression model.
     *
     * @param endDate the end date provided
     *
     * @return final end date
     */
    public Date getFinalEndDateForWeekData(Date endDate) {
        Calendar auxFinalDate = Calendar.getInstance();
        auxFinalDate.setTime(endDate);
        while(auxFinalDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            auxFinalDate.add(Calendar.DAY_OF_MONTH, -1);
        }
        return auxFinalDate.getTime();
    }


}
