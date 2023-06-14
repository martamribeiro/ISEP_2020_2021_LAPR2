package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Clinical Analysis Laboratory through:
 * a Laboratory ID, a name, an address, a phone number, a TIN number and the type of tests
 *
 * @author Ana Albergaria
 */

public class ClinicalAnalysisLaboratory extends Laboratory {
    /**
     * The laboratory ID of the Clinical Analysis Laboratory.
     */
    private String laboratoryID;

    /**
     * The type of tests the Clinical Analysis Laboratory operates.
     */
    private List<TestType> selectedTT;

    /**
     * The tests the Clinical Analysis Laboratory performs.
     */
    private List<Test> calTestList;


    /**
     * The length of the laboratory ID.
     */
    private static final int LABORATORY_ID_LENGTH = 5;

    /**
     * Builds a Clinical Analysis Laboratory's instance receiving:
     * the Laboratory ID, the name, the address, the phone number, the TIN number and
     * the type of tests
     *
     * @param laboratoryID the laboratory ID of the Clinical Analysis Laboratory
     * @param name         the name of the Clinical Analysis Laboratory
     * @param address      the address of the Clinical Analysis Laboratory
     * @param phoneNumber  the phone number of the Clinical Analysis Laboratory
     * @param numTIN       the TIN number of the Clinical Analysis Laboratory
     * @param selectedTT   the type of tests the Clinical Analysis Laboratory operates
     */
    public ClinicalAnalysisLaboratory(String laboratoryID,
                                      String name,
                                      String address,
                                      String phoneNumber,
                                      String numTIN,
                                      List<TestType> selectedTT) {
        super(name, address, phoneNumber, numTIN);
        checkLaboratoryIDRules(laboratoryID);
        checkSelectedTestTypesRules(selectedTT);
        this.laboratoryID = laboratoryID;
        this.selectedTT = new ArrayList<>(selectedTT);
        this.calTestList = new ArrayList<>();
    }

    /**
     * Returns the laboratory ID of the Clinical Analysis Laboratory
     *
     * @return laboratory ID of the Clinical Analysis Laboratory
     */
    public String getLaboratoryID() {
        return laboratoryID;
    }

    public List<TestType> getSelectedTT() {
        return selectedTT;
    }

    public List<Test> getCalTestList() {
        return calTestList;
    }

        /**
         * Returns true if the laboratory ID received in the parameter respects
         * all the rules.
         * It returns false as soon as one of these conditions are not verified
         * by the laboratory ID provided:
         * - It is blank
         * - It has a length different than 5
         * - It doesn't contain only alphanumeric characters.
         *
         * @param laboratoryID the laboratory ID of the Clinical Analysis Laboratory
         *
         * @return true if the laboratory ID respects all the rules,
         *         otherwise returns false
         */
        private void checkLaboratoryIDRules (String laboratoryID){
            if (StringUtils.isBlank(laboratoryID))
                throw new IllegalArgumentException("Laboratory ID cannot be blank.");
            if ((laboratoryID.length() != LABORATORY_ID_LENGTH))
                throw new IllegalArgumentException("Laboratory ID cannot have more or less than five alphanumeric characters.");
            if (!StringUtils.isAlphanumeric(laboratoryID))
                throw new IllegalArgumentException("Laboratory ID must only have alphanumeric characters.");
        }

        /**
        * Method for adding tests to the current clinical analysis laboratory object
        * @param test test to be added to the list
        * @return true if successfully added false otherwise
        */
        public boolean addTest(Test test){
            if (!validateTest(test))
                return false;
            return this.calTestList.add(test);
        }

        /**
         * Validates the test to be added into the clinical analysis laboratory
         * @param test test to be validated
         * @return true if not null nor contained in the list, false otherwise
         */
        public boolean validateTest(Test test){
            if(test == null)
                return false;
            return !this.calTestList.contains(test);
        }
        /**
         * Returns true if the list of the Test Types received in the parameter respects
         * all the rules.
         * It returns false as soon as one of these conditions are not verified
         * by the List of Test Types provided:
         * - It is null or it is empty
         *
         * @param selectedTT the type of tests the Clinical Analysis Laboratory operates
         *
         *
         * @return true if the list of Test Types respects all the rules,
         *         otherwise returns false
         */
        private void checkSelectedTestTypesRules (List < TestType > selectedTT) {
            if (selectedTT == null || selectedTT.isEmpty())
                throw new IllegalArgumentException("The list containing the Types of Test cannot be blank.");
        }

        /**
         * Compares the Clinical Analysis Laboratory with the received object.
         *
         * @param otherObject the object to be compared with the Clinical Analysis Laboratory
         * @return true if the received object represents other Clinical Analysis Laboratory
         * equivalent to the Clinical Analysis Laboratory. Otherwise, returns false.
         */
        @Override
        public boolean equals (Object otherObject){
            if (!super.equals(otherObject))
                return false;

            ClinicalAnalysisLaboratory instance = (ClinicalAnalysisLaboratory) otherObject;

            return this.laboratoryID.equalsIgnoreCase(instance.laboratoryID) &&
                    this.selectedTT.equals(instance.selectedTT);
        }


    }
