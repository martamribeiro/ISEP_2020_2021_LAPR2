package app.mappers.dto;

import app.domain.model.*;

import java.util.ArrayList;
import java.util.List;

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
    private final String dateOfTestRegistration;

    /**
     * Date of samples collection
     */
    private String dateOfSamplesCollection;

    /**
     * Date of chemical analysis
     */
    private String dateOfChemicalAnalysis;

    /**
     * Date of diagnosis
     */
    private String dateOfDiagnosis;

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
                   String dateOfTestRegistration,
                   String dateOfSamplesCollection,
                   String dateOfChemicalAnalysis,
                   String dateOfDiagnosis) {
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

        return String.format(">> TEST CODE %s%n > NHS Code: %s%n > Client name: %s%n > Test Type: %s%n" +
                        " > Parameters: %n%n%s > Number of Samples Collected: %d%n > Diagnosis Report: %s%n" +
                        " > Date Of Test Registration: %s%n > Date Of Samples Collection: %s%n" +
                        " > Date of Chemical Analysis: %s%n > Date Of Diagnosis: %s%n",
                code, nhsCode, client.getName(), testType, s, samples.size(), diagnosisReport,
                dateOfTestRegistration, dateOfSamplesCollection, dateOfChemicalAnalysis, dateOfDiagnosis);
    }
}
