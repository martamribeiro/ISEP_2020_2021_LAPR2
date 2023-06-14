package app.mappers.dto;

import app.domain.model.Test;
import app.domain.model.TestType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ana Albergaria 1201518
 */

public class ClinicalAnalysisLaboratoryDTO extends LaboratoryDTO {
    /**
     * The laboratory ID of the Clinical Analysis Laboratory.
     */
    private String laboratoryID;

    /**
     * The type of tests the Clinical Analysis Laboratory operates.
     */
    private List<String> testTypeCodes;

    private List<TestType> selectedTT;

    private List<Test> calTestList;

    /**
     * Builds a Clinical Analysis Laboratory's instance receiving:
     * the Laboratory ID, the name, the address, the phone number, the TIN number and
     * the type of tests
     *
     * @param laboratoryID the laboratory ID of the Clinical Analysis Laboratory
     * @param name the name of the Clinical Analysis Laboratory
     * @param address the address of the Clinical Analysis Laboratory
     * @param phoneNumber the phone number of the Clinical Analysis Laboratory
     * @param numTIN the TIN number of the Clinical Analysis Laboratory
     * @param testTypeCodes the codes of the selected types of tests the Clinical Analysis Laboratory operates
     */
    public ClinicalAnalysisLaboratoryDTO(String laboratoryID, String name, String address, String phoneNumber, String numTIN, List<String> testTypeCodes) {
        super(name, address, phoneNumber, numTIN);
        this.laboratoryID = laboratoryID;
        this.testTypeCodes = new ArrayList<>(testTypeCodes);
    }

    public ClinicalAnalysisLaboratoryDTO(String laboratoryID, String name, String address, String phoneNumber, String numTIN, List<TestType> selectedTT, List<Test> calTestList) {
        super(name, address, phoneNumber, numTIN);
        this.laboratoryID = laboratoryID;
        this.selectedTT = selectedTT;
        this.calTestList = calTestList;
    }

    /**
     * Returns the laboratory ID of the Clinical Analysis Laboratory
     *
     * @return laboratory ID of the Clinical Analysis Laboratory
     */
    public String getLaboratoryID() {
        return laboratoryID;
    }

    /**
     * Returns the codes of the selected test types of the Clinical Analysis Laboratory
     *
     * @return codes of test types of the Clinical Analysis Laboratory
     */
    public List<String> getTestTypeCodes() {
        return new ArrayList<>(testTypeCodes);
    }

    /**
     * It returns the textual description of the Clinical Analysis Laboratory instance.
     *
     * @return characteristics of the Clinical Analysis Laboratory
     */
    @Override
    public String toString() {
        List<TestType> copy = new ArrayList<>(selectedTT);

        StringBuilder s = new StringBuilder();
        for (TestType tt : copy) {
            s.append("  >> ");
            s.append("Code: ");
            s.append(tt.getCode());
            s.append(", ");
            s.append("Description: ");
            s.append(tt.getDescription());
            s.append("\n");
        }

        List<Test> copyCalTestsList = new ArrayList<>(calTestList);

        StringBuilder sCalTestsList = new StringBuilder();
        for (Test test : copyCalTestsList) {
            sCalTestsList.append("  >> ");
            sCalTestsList.append("Code: ");
            sCalTestsList.append(test.getCode());
            sCalTestsList.append(", ");
            sCalTestsList.append("Nhs Code: ");
            sCalTestsList.append(test.getNhsCode());
            sCalTestsList.append(", ");
            sCalTestsList.append("Test Type: ");
            sCalTestsList.append(test.getTestType().getCode());
            sCalTestsList.append("\n");
        }



        return String.format("%s> Laboratory ID: %s%n> Test Types: %n%s> Tests: %n%s",
                super.toString(), laboratoryID, s, sCalTestsList);
    }
}
