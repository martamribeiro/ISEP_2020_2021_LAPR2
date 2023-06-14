package app.domain.store;

import app.domain.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Store of tests
 *
 * @author João Wolff
 */
public class TestStore {

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
    public Test createTest(String nhsCode, Client associatedClient, TestType testType, List<Parameter> parameters) {
        return new Test(nhsCode, associatedClient, testType, parameters);
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
     * Gets a list of test parameters of a test
     * @param tst test to retrieve list
     * @return list of test parameters
     */
    public List<TestParameter> getTestParameters(Test tst) {
        return new ArrayList<>(tst.getParameters());
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


}
