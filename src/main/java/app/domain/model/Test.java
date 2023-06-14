package app.domain.model;

import app.domain.interfaces.ExternalModule;
import app.domain.shared.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Class of the Test to be performed to a client
 *
 * @author João Wolff
 */
public class Test implements Serializable {

    /**
     * Max lenght of nhs code field
     */
    private static final int NHS_CODE_MAX_LENGTH = 12;

    /**
     * Auto generated sequencial number with 12 digits
     */
    private final String code;

    /**
     * National health system code of a given test
     */
    private final String nhsCode;

    /**
     * Client object which has solicited a test
     */
    private final Client client;

    /**
     * Type of test to be conduted
     */
    private final TestType testType;

    /**
     * Report of test results
     */
    private Report diagnosisReport;

    /**
     * Samples which the test contains
     */
    private List<Sample> samples;

    /**
     * List of parameters to be measured of a given test
     */
    private final List<TestParameter> testParameters;

    /**
     * Date of test registration
     */
    private Date dateOfTestRegistration;

    /**
     * Date of samples collection
     */
    private Date dateOfSamplesCollection;

    /**
     * Date of chemical analysis
     */
    private Date dateOfChemicalAnalysis;

    /**
     * Date of diagnosis
     */
    private Date dateOfDiagnosis;

    /**
     * Date of validation
     */
    private Date dateOfValidation;


    /**
     * Number of existing tests.
     */
    private static int totalTests = 0;

    /**
     * Laboratory in which a test was created
     */

    private ClinicalAnalysisLaboratory cal;

    /**
     * Constructor only without samples, since in the business process they are added later on,
     * also generates the attribute code, test registration time and makes the list of parameter into test parameters
     * @param nhsCode National health system code of a given test
     * @param client Client object which has solicited a test
     * @param testType Type of test to be conduted
     * @param parameters List of parameters to be measured of a given test
     */
    public Test(String nhsCode, Client client, TestType testType, List<Parameter> parameters, ClinicalAnalysisLaboratory cal) {
        checkNhsCode(nhsCode);
        checkTestType(testType);
        checkParameters(parameters);
        checkCal(cal);
        checkClient(client);
        totalTests++;
        this.code = generateCode();
        this.nhsCode = nhsCode;
        this.client = client;
        this.testType = testType;
        this.testParameters = addTestParameters(parameters);
        this.cal = cal;
        this.dateOfTestRegistration = Calendar.getInstance().getTime();
        this.diagnosisReport = null;
        this.samples = new ArrayList<>();
        this.dateOfSamplesCollection = null;
        this.dateOfChemicalAnalysis = null;
        this.dateOfDiagnosis = null;
        this.dateOfValidation = null;
    }

    /**
     * Constructor only without samples, since in the business process they are added later on,
     * also generates the attribute code, test registration time and makes the list of parameter into test parameters
     * @param nhsCode National health system code of a given test
     * @param client Client object which has solicited a test
     * @param testType Type of test to be conduted
     * @param parameters List of parameters to be measured of a given test
     */
    public Test(String nhsCode, Client client, TestType testType, List<Parameter> parameters,List<Double> paramsResults,
                ClinicalAnalysisLaboratory cal, Date testRegDate, Date testChemDate, Date testDiagnosisDate, Date testValidationDate) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        checkNhsCode(nhsCode);
        checkTestType(testType);
        checkParameters(parameters);
        checkCal(cal);
        checkClient(client);
        checkParameterResults(parameters, paramsResults);
        totalTests++;
        this.code = generateCode();
        this.nhsCode = nhsCode;
        this.client = client;
        this.testType = testType;
        this.testParameters = addTestParameterWithResults(parameters, paramsResults);
        this.cal = cal;
        this.dateOfTestRegistration = (Date)testRegDate.clone();
        this.diagnosisReport = null;
        this.samples = new ArrayList<>();
        this.dateOfSamplesCollection = testChemDate == null ? null : (Date)testChemDate.clone();;
        this.dateOfChemicalAnalysis = testChemDate == null ? null : (Date)testChemDate.clone();
        this.dateOfDiagnosis = testDiagnosisDate == null ? null : (Date)testDiagnosisDate.clone();
        this.dateOfValidation = testValidationDate == null ? null :(Date)testValidationDate.clone();
    }

    
    public void checkTestType(TestType testType){
        if(Objects.isNull(testType)){
            throw new IllegalArgumentException("Test type cannot be null");
        }
    }
    
    public void checkParameters(List<Parameter> parameters){
        if(parameters.isEmpty()){
            throw new IllegalArgumentException("Parameter list cannot be empty"); 
        }
    }
    
    public void checkCal(ClinicalAnalysisLaboratory cal){
        if(Objects.isNull(cal)){
            throw new IllegalArgumentException("Clinical analysis laboratory cannot be null");
        }
    }

    public void checkClient(Client client){
        if(Objects.isNull(client)){
            throw new IllegalArgumentException("Client cannot be null");
        }
    }

    public void checkParameterResults(List<Parameter> params, List<Double> results){
        if(params.size() != results.size()){
            throw new IllegalArgumentException("Each param must have it respective result, even if it is null");
        }
    }

    /**
     * Nhs code attribute validation for having non alphanumeric characters, more or less then 12 characters or blank
     *
     * @param code Test type's code
     */
    private void checkNhsCode(String code) {
        if (StringUtils.isBlank(code))
            throw new IllegalArgumentException("Nhs code cannot be blank.");
        if ((code.length() != NHS_CODE_MAX_LENGTH))
            throw new IllegalArgumentException("Nhs code must hold 12 alphanumeric characters");
        if (!StringUtils.isAlphanumeric(code))
            throw new IllegalArgumentException("Nhs code must only have alphanumeric characters.");
    }


    /**
     * Returns the code of the test.
     *
     * @return code of the test
     */
    public String getCode(){
        return code;
    }

    /**
     * Returns the list of samples of the test
     *
     * @return list of samples of the test
     */
    public List<Sample> getSamples() {
        return new ArrayList<>(samples);
    }

    /**
     * Returns the Diagnosis Report of the test.
     *
     * @return the Diagnosis Report of the test
     */
    public Report getDiagnosisReport(){
        return diagnosisReport;
    }

    public Date getDateOfValidation() {
        return dateOfValidation;
    }

    /**
     * Returns the NHS Code of the test.
     *
     * @return the NHS code of the test.
     */
    public String getNhsCode() {
        return nhsCode;
    }

    public String getCalId() {
        return cal.getLaboratoryID();
    }

    /**
     * Returns the client who will do the test.
     *
     * @return the client who will do the test
     */
    public Client getClient() {
        return client;
    }

    /**
     * Returns the test type of the test
     *
     * @return test type of the test
     */
    public TestType getTestType() {
        return testType;
    }

    /**
     * Returns the list of Test Parameters of the test.
     *
     * @return list of Test Parameters of the test
     */
    public List<TestParameter> getParameters() {
        return new ArrayList<>(testParameters);
    }

    /**
     * Returns the date of test registration.
     *
     * @return date of test registration
     */
    public Date getDateOfTestRegistration() {
        return dateOfTestRegistration;
    }

    /**
     * Returns the date of the samples collection.
     *
     * @return date of the samples collection
     */
    public Date getDateOfSamplesCollection() {
        return dateOfSamplesCollection;
    }

    /**
     * Returns the date of the chemical analysis.
     *
     * @return date of the chemical analysis
     */
    public Date getDateOfChemicalAnalysis() {
        return dateOfChemicalAnalysis;
    }

    /**
     * Returns the date of the diagnosis.
     *
     * @return the date of the diagnosis
     */
    public Date getDateOfDiagnosis() {
        return dateOfDiagnosis;
    }

    public void setDateOfSamplesCollection(Date dateOfSamplesCollection) {
        this.dateOfSamplesCollection = (Date)dateOfSamplesCollection.clone();
    }

    public void setDateOfChemicalAnalysis(Date dateOfChemicalAnalysis) {
        this.dateOfChemicalAnalysis = (Date)dateOfChemicalAnalysis.clone();
    }

    public void setDateOfDiagnosis(Date dateOfDiagnosis) {
        this.dateOfDiagnosis = (Date)dateOfDiagnosis.clone();
    }

    public void setDateOfTestRegistration(Date dateOfTestRegistration) {
        this.dateOfTestRegistration = (Date)dateOfTestRegistration.clone();
    }

    public void setDateOfValidation(Date dateOfValidation) {
        this.dateOfValidation = (Date)dateOfValidation.clone();
    }

    /**
     * Method that returns the Test Parameter of the Parameter whose code is received by parameter.
     *
     * @param parameterCode
     *
     * @return Test Parameter of the Parameter whose code is received by parameter
     */
    private TestParameter getTestParameterFor(String parameterCode) {
        for (TestParameter testParameter : this.testParameters) {
            if(parameterCode.equals(testParameter.getParameter().getPrmCode())) {
                return testParameter;
            }
        }
        throw new UnsupportedOperationException("Test Parameter not found!");
    }

    /**
     * Method for adding a sample object to the Test
     * @param sample sample to be added
     * @return true if successfully added false otherwise
     */
    public boolean addSample(Sample sample) {
        if(sample != null)
            return this.samples.add(sample);
        return false;
    }

    /**
     * Adds the Sample Collecting Date to the Test for which the samples where collected.
     */
    public void addSampleCollectionDate(){
        this.dateOfSamplesCollection = Calendar.getInstance().getTime();
    }

    /**
     * Adds the current date to the dateOfChemicalAnalysis attribute
     */
    public void addChemicalAnalysisDate(){
        this.dateOfChemicalAnalysis = Calendar.getInstance().getTime();
    }

    public boolean isOfClient(String tinNumber){
        return this.client.getTinNumber().equals(tinNumber);
    }

    /**
     * Generates a sequencial code based on the number of existing tests
     * @return 12 digits sequencial number of current test.
     */
    private String generateCode(){
        return String.format("%012d", totalTests);
    }


    private List<TestParameter> addTestParameterWithResults(List<Parameter> parameters, List<Double> results) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        List<TestParameter> testParametersToBeAdded = new ArrayList<>();
        for(int i = 0; i<parameters.size();i++){
            if(parameters.get(i) == null) throw new IllegalArgumentException("Parameter cannot be null");
            MyReferenceValue refValue = getRefValueOfParameter(parameters.get(i));
            TestParameterResult testResult = new TestParameterResult(results.get(i), "", refValue);
            testParametersToBeAdded.add(new TestParameter(parameters.get(i), testResult));
        }
        return testParametersToBeAdded;
    }


    /**
     * Method for turning a list of Parameter objects into test parameters object which are stored into the test
     * @param parameters parameters to be tested
     * @return list of test parameters objects
     */
    private List<TestParameter> addTestParameters(List<Parameter> parameters){
        List<TestParameter> testParametersToBeAdded = new ArrayList<>();
        for(Parameter parameter : parameters){
            if(parameter == null) throw new IllegalArgumentException("Parameter cannot be null");
            testParametersToBeAdded.add(new TestParameter(parameter));
        }
        return testParametersToBeAdded;
    }

    public boolean hasChemDate() {
        return this.dateOfChemicalAnalysis != null;
    }

    /**
     * Method to verify if the test has Samples collected.
     *
     * @return true if the test has samples collected.
     *          Otherwise, it returns false.
     */
    public boolean hasSamples() {
        return this.samples.size() > 0;
    }

    /**
     * Method to verify if the test has Samples analysed.
     *
     * @return true if the test has samples analysed,
     * otherwise return false.
     */
    public boolean hasSamplesAnalysed(){
        int f = 0;
        List<TestParameter> testParameters = this.getParameters();
        for (TestParameter testParameter : testParameters){
            if (testParameter.getTestParameterResult() == null){
                f++;
            }
        }
        return f == 0;
    }

    public boolean isValidated() {
        return this.dateOfValidation != null;
    }

    public boolean isCovidTest() {
        return this.getTestType().getClassNameOfApi().equals(Constants.COVID_EXTERNAL_ADAPTER);
    }

    /*
    to be used in US19
    WARNING - confirm if the test should only have results OR must be VALIDATED and correct it
     */
    public boolean hasPositiveResultForCovid() {
        if(this.isCovidTest() && this.isValidated()) {
            List<TestParameter> testParameterList = this.getParameters();
            return testParameterList.get(0).getTestParameterResult().getResultValue() > 1.4;
        }
        return false;
    }

    /**
     * Method to store the test report object into the test.
     *
     * @param report the test report.
     */
    public void addReport(Report report) {
        this.diagnosisReport = report;
        this.dateOfDiagnosis = Calendar.getInstance().getTime();
        this.dateOfValidation = Calendar.getInstance().getTime();
    }

    /**
     * Adds the test result object to a given test parameter of the test
     * @param parameterCode code of the parameter to being analysed
     * @param result result of the analysis
     * @param metric metric being used in the analysis
     * @throws IllegalAccessException if there's a method invoked does not have access to the class representing the API
     * @throws ClassNotFoundException if the class name of the external API is not found
     * @throws InstantiationException if the class object of the external API cannot be instantiated
     */
    public void addTestResult(String parameterCode, Double result, String metric) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        TestParameter testParameter = getTestParameterFor(parameterCode);
        Parameter selectedParameter = testParameter.getParameter();
        MyReferenceValue refValue = getRefValueOfParameter(selectedParameter);
        testParameter.addResult(result, metric, refValue);
    }

    private MyReferenceValue getRefValueOfParameter(Parameter parameter) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ExternalModule api = this.testType.getExternalModule();
        return api.getReferenceValue(parameter);
    }

    /**
     * It returns the textual description of the Test instance.
     *
     * @return characteristics of the Test
     */
    @Override
    public String toString() {
        return String.format("TEST CODE %s%n* NHS Code: %s%n* Client name: %s%n* Test Type: %s%n* Collection Method: %s%n* " +
                        "Parameters: %s%nDate of test registration:%s%nDate of sample collection:%s%nDate of chemical analysis: %s%nDate of diagnosis: %s%n",
                code, nhsCode, client.getName(), testType, testType.getCollectingMethod(), testParameters,
                dateOfTestRegistration, dateOfSamplesCollection, dateOfChemicalAnalysis, dateOfDiagnosis);
    }

    /**
     * Compares the Test with the received object.
     *
     * @param o the object to be compared with the Test
     * @return true if the received object represents other Test
     * equivalent to the Test. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;
        Test test = (Test) o;
        return nhsCode.equalsIgnoreCase(test.nhsCode) &&
                client.equals(test.client) &&
                testType.equals(test.testType) &&
                Objects.equals(dateOfTestRegistration, test.dateOfTestRegistration) &&
                Objects.equals(dateOfChemicalAnalysis, test.dateOfChemicalAnalysis) &&
                Objects.equals(dateOfDiagnosis, test.dateOfDiagnosis) &&
                Objects.equals(dateOfValidation, test.dateOfValidation);
    }
}
