package app.domain.store;

import app.domain.model.ParameterCategory;
import app.domain.model.TestType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Store of test types
 *
 * @author João Wolff
 */
public class TestTypeStore implements Serializable {
    /**
     * List of test types performed by the company
     */
    private List<TestType> testTypeList = new ArrayList<>();

    /**
     * Test type class creator with full constructor
     *
     * @param code Test type's code
     * @param description Test type's description
     * @param collectingMethod Test type's collecting methods
     * @param selectedCategories Test type's categories list
     * @return created test type reference
     */
    public TestType createTestType(String code, String description, String collectingMethod, List<ParameterCategory> selectedCategories, String classNameOfApi) {
        return new TestType(code, description, collectingMethod, selectedCategories, classNameOfApi);
    }


    public List<TestType> getTestTypes() {
        return new ArrayList<>(testTypeList);
    }


    /**
     * Validation of test type instance relative to list of test types. checking for null and duplicity
     *
     * @param testType test type to be validated
     * @return true for success and false for fail
     */
    public boolean validateTestType(TestType testType) {
        if (testType == null)
            return false;
        return !this.testTypeList.contains(testType);
    }

    /**
     * Save of test type instance inside the list of test type store, checking for validation before
     *
     * @param testType test type to be saved to the list
     * @return true for success and false for fail
     */
    public boolean saveTestType(TestType testType) {
        if (!validateTestType(testType))
            return false;
        return this.testTypeList.add(testType);
    }

    /**
     * Method for geting test types in the store list relatively to list of test type codes
     *
     * @param testTypeCodes List of test type codes to be gotten
     * @return List of test types
     */
    public List<TestType> getTestTypesByCode(List<String> testTypeCodes) {
        List<TestType> selectedTT = new ArrayList<>();
        for (String item : testTypeCodes) {
            selectedTT.add(getSingleTestTypeByCode(item));
        }
        return selectedTT;
    }

    /**
     * Get test acording to code of test type
     *
     * @param code code of test type to be retrieved
     * @return test type reference
     */
    public TestType getSingleTestTypeByCode(String code) {
        for (TestType tt : testTypeList) {
            if (tt.getCode().equalsIgnoreCase(code)) {
                return tt;
            }
        }
        throw new UnsupportedOperationException("Test Type not found with given code: " + code);
    }

    /**
     * Method for getting the list of categories of a type of test by the code of the testtype object.
     *
     * @param code code of the test type to be found.
     * @return a copy of the list of categories of the found test type.
     */
    public List<ParameterCategory> getCategoriesByTestTypeCode(String code){
        return getSingleTestTypeByCode(code).getSelectedCategories();
    }
}
