package app.mappers.dto;

import app.domain.model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SRC-Code-23
 */
public class TestDTO {

    /**
     * Auto generated sequencial number with 12 digits
     */
    private String code;

    /**
     * National health system code of a given test
     */
    private String nhsCode;

    /**
     * Client object which has solicited a test
     */
    private Client client;

    /**
     * Type of test to be conduted
     */
    private TestType testType;

    /**
     * Samples which the test contains
     */
    private List<Sample> samples;

    /**
     * List of parameters to be measured of a given test
     */
    private List<TestParameter> testParameters;

    /**
     * Report of test results
     */
    private Report diagnosisReport;

    /**
     * Date of test registration
     */
    private final Date dateOfTestRegistration;

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
     * Constructor only without samples, since in the business process they are added later on
     * @param nhsCode National health system code of a given test
     * @param client Client object which has solicited a test
     * @param testType Type of test to be conduted
     * @param parameters List of parameters to be measured of a given test
     */
    public TestDTO(String code,
                   String nhsCode,
                   Client client,
                   TestType testType,
                   List<TestParameter> parameters,
                   List<Sample> samples,
                   Report diagnosisReport,
                   Date dateOfTestRegistration,
                   Date dateOfSamplesCollection,
                   Date dateOfChemicalAnalysis,
                   Date dateOfDiagnosis,
                   Date dateOfValidation) {
        totalTests++;
        this.code = code;
        this.nhsCode = nhsCode;
        this.client = client;
        this.testType = testType;
        this.testParameters = parameters;
        this.samples = samples;
        this.diagnosisReport = diagnosisReport;
        this.dateOfTestRegistration = dateOfTestRegistration;
        this.dateOfSamplesCollection = dateOfSamplesCollection;
        this.dateOfChemicalAnalysis = dateOfChemicalAnalysis;
        this.dateOfDiagnosis = dateOfDiagnosis;
        this.dateOfValidation = dateOfValidation;
    }

    /**
     * Returns the code of the Test Dto.
     *
     * @return code of the Test Dto
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the description of the test type of the Test Dto.
     *
     * @return description of the test type of the Test Dto
     */
    public String getTestTypeDescription(){
        return testType.getDescription();
    }

    /**
     * Returns the date of test registration of the Test Dto.
     *
     * @return date of test registration of the Test Dto
     */
    public Date getDateOfTestRegistration(){
        return dateOfTestRegistration;
    }

    public String getReport(){
        return diagnosisReport.getReportText();
    }

    /**
     * Returns the date of test registration of the Test Dto, in the String format.
     *
     * @return the date of test registration of the Test Dto, in the String format
     */
    public String getStringDateOfTestRegistration(){
        return dateOfTestRegistration.toString();
    }

    /**
     * Returns the names of the test parameters of the Test DTO.
     *
     * @return the names of the test parameters of the Test DTO
     */
    public List<String> getTestParametersName(){
        List<String> parametersName = new ArrayList<>();
        String toAdd;
        for (int i = 0; i < testParameters.size(); i++) {
            toAdd=testParameters.get(i).getParameter().getShortName();
            parametersName.add(toAdd);
        }
        return parametersName;
    }

    /**
     * Returns the results of the test parameters of the Test DTO.
     *
     * @return the results of the test parameters of the Test DTO
     */
    public List<Double> getTestParametersResult(){
        List<Double> parametersResult = new ArrayList<>();
        Double toAdd;
        for (int i = 0; i < testParameters.size(); i++) {
            toAdd=testParameters.get(i).getTestParameterResult().getResultValue();
            parametersResult.add(toAdd);
        }
        return parametersResult;
    }

    /**
     * Returns the metrics of the test parameters of the Test DTO.
     *
     * @return the metrics of the test parameters of the Test DTO
     */
    public List<String> getTestParametersMetric(){
        List<String> parametersMetric = new ArrayList<>();
        String toAdd;
        for (int i = 0; i < testParameters.size(); i++) {
            toAdd=testParameters.get(i).getTestParameterResult().getResultMetric();
            parametersMetric.add(toAdd);
        }
        return parametersMetric;
    }

    /**
     * Returns the minimum acceptable values of the test parameters of the Test DTO.
     *
     * @return the minimum acceptable values of the test parameters of the Test DTO
     */
    public List<Double> getTestParametersReferenceValueMin(){
        List<Double> parametersMinRef = new ArrayList<>();
        Double toAdd;
        for (int i = 0; i < testParameters.size(); i++) {
            toAdd=testParameters.get(i).getTestParameterResult().getResultReferenceValue().getMinValue();
            parametersMinRef.add(toAdd);
        }
        return parametersMinRef;
    }

    /**
     * Returns the maximum acceptable values of the test parameters of the Test DTO.
     *
     * @return the maximum acceptable values of the test parameters of the Test DTO
     */
    public List<Double> getTestParametersReferenceValueMax(){
        List<Double> parametersMaxRef = new ArrayList<>();
        Double toAdd;
        for (int i = 0; i < testParameters.size(); i++) {
            toAdd=testParameters.get(i).getTestParameterResult().getResultReferenceValue().getMaxValue();
            parametersMaxRef.add(toAdd);
        }
        return parametersMaxRef;
    }

    /**
     * It returns the relevant textual description of the Test Dto instance.
     *
     * @return relevant characteristics of the Test Dto
     */
    @Override
    public String toString() {
        return String.format(">> TEST CODE %s%n > NHS Code: %s%n > Client name: %s%n > Test Type: %s%n",
                code, nhsCode, client.getName(), testType);
    }

    /**
     * It returns the complete textual description of the Test Dto instance.
     *
     * @return complete characteristics of the Test Dto
     */
    public String toStringWithAllData() {
        List<TestParameter> copyTP = new ArrayList<>(testParameters);

        StringBuilder s = new StringBuilder();
        for (TestParameter testParameter : copyTP) {
            s.append(testParameter);
            s.append("\n");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String printDateOfSamplesCollection = (dateOfSamplesCollection == null) ? "n/a" : sdf.format(dateOfSamplesCollection);
        String printDateOfChemicalAnalysis = (dateOfChemicalAnalysis == null) ? "n/a" : sdf.format(dateOfChemicalAnalysis);
        String printDateOfDiagnosis = (dateOfDiagnosis == null) ? "n/a" : sdf.format(dateOfDiagnosis);
        String printDateOfValidation = (dateOfValidation == null) ? "n/a" : sdf.format(dateOfValidation);

        return String.format(">> TEST CODE %s%n > NHS Code: %s%n > Client name: %s%n > Test Type: %s%n" +
                        " > Parameters: %n%n%s > Number of Samples Collected: %d%n > Diagnosis Report: %s%n" +
                        " > Date Of Test Registration: %s%n > Date Of Samples Collection: %s%n" +
                        " > Date of Chemical Analysis: %s%n > Date Of Diagnosis: %s%n > Date Of Validation: %s%n",
                code, nhsCode, client.getName(), testType, s, samples.size(), diagnosisReport,
                sdf.format(dateOfTestRegistration), printDateOfSamplesCollection, printDateOfChemicalAnalysis, printDateOfDiagnosis, printDateOfValidation);
    }

    public String showAllButReport() {
        List<TestParameter> copyTP = new ArrayList<>(testParameters);

        StringBuilder s = new StringBuilder();
        for (TestParameter testParameter : copyTP) {
            s.append(testParameter);
            s.append("\n");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String printDateOfSamplesCollection = (dateOfSamplesCollection == null) ? "n/a" : sdf.format(dateOfSamplesCollection);
        String printDateOfChemicalAnalysis = (dateOfChemicalAnalysis == null) ? "n/a" : sdf.format(dateOfChemicalAnalysis);
        String printDateOfDiagnosis = (dateOfDiagnosis == null) ? "n/a" : sdf.format(dateOfDiagnosis);
        String printDateOfValidation = (dateOfValidation == null) ? "n/a" : sdf.format(dateOfValidation);

        return String.format(">> TEST CODE %s%n > NHS Code: %s%n > Client name: %s%n > Test Type: %s%n" +
                        " > Parameters: %n%n%s > Number of Samples Collected: %d%n" +
                        " > Date Of Test Registration: %s%n > Date Of Samples Collection: %s%n" +
                        " > Date of Chemical Analysis: %s%n > Date Of Diagnosis: %s%n > Date Of Validation: %s%n%n%n",
                code, nhsCode, client.getName(), testType, s, samples.size(),
                sdf.format(dateOfTestRegistration), printDateOfSamplesCollection, printDateOfChemicalAnalysis, printDateOfDiagnosis, printDateOfValidation);
    }
}
