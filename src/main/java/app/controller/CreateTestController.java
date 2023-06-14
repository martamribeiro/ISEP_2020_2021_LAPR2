package app.controller;

import app.domain.model.*;
import app.domain.store.*;
import app.mappers.CategoriesMapper;
import app.mappers.ParameterMapper;
import app.mappers.TestTypeMapper;
import app.mappers.dto.CategoriesDTO;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestTypeDTO;

import java.util.List;

/**
 * Controller for creating a new Test object
 *
 * @author João Wolff
 */
public class CreateTestController {

    /**
     * Company instance of the session
     */
    private final Company company;

    /**
     * Test to be created by the controller
     */
    private Test test;

    /**
     * Current clinical analysis laboratory that holds the test
     */
    private ClinicalAnalysisLaboratory currentCal;

    public CreateTestController() {
        this(App.getInstance().getCompany(), null);
    }

    /**
     * Empty constructor for having the actual instance of the company when instantiated.
     */
    public CreateTestController(String currentCalCode) {
        this(App.getInstance().getCompany(), currentCalCode);
    }

    /**
     * Construtor recieving the company as an argument
     *
     * @param company instance of company to be used
     */
    public CreateTestController(Company company, String currentCalCode) {
        this.company = company;
        this.test = null;
        this.currentCal = getCalByCode(currentCalCode);
    }

    /**
     * Method for creating an instance of Test
     * @param nhsCode National health system code of a given test
     * @param tinNumber client's TIN number to be associated with test
     * @param selectedTestTypeCode Type of test's code to be conduted
     * @param selectedParamsCodes List of parameters codes to be measured of a given test
     */
    public boolean createTest(String nhsCode, String tinNumber, String selectedTestTypeCode, List<String> selectedParamsCodes){
        TestStore testStore = this.company.getTestStore();
        ClientStore clientStore = this.company.getClientStore();
        TestTypeStore testTypeStore = this.company.getTestTypeStore();
        ParameterStore parameterStore = this.company.getParameterStore();

        Client associatedClient = clientStore.getClientByTinNumber(tinNumber);
        TestType testType = testTypeStore.getSingleTestTypeByCode(selectedTestTypeCode);
        List<Parameter> parameters = parameterStore.getParamsByCodes(selectedParamsCodes);

        this.test = testStore.createTest(nhsCode, associatedClient, testType, parameters, currentCal);

        return testStore.validateTest(test);
    }

    /**
     * Saves current test in the test store
     *
     * @return True if successfully validated and saved false the otherway
     */
    public boolean saveTest() {
        TestStore testStore = this.company.getTestStore();
        return testStore.saveTest(test);
    }

    /**
     * Retrieves actual test types categories list
     *
     * @return test types list
     */
    private List<TestType> getTestTypes(String selectedCalCode) {
        return currentCal.getSelectedTT();
    }

    /**
     * Convert test types list into a DTO
     *
     * @return Test Types DTO list
     */
    public List<TestTypeDTO> getTestTypesDTO(String selectedCalCode) {
        TestTypeMapper mapper = new TestTypeMapper();
        return mapper.toDTO(getTestTypes(selectedCalCode));
    }

    /**
     * Retrieves parameter category list associated with a test type
     *
     * @return parameter category list
     */
    private List<ParameterCategory> getCategoriesListOfTestType(String selectedTestTypeCode) {
        TestTypeStore testTypeStore = this.company.getTestTypeStore();
        return testTypeStore.getCategoriesByTestTypeCode(selectedTestTypeCode);
    }

    /**
     * Convert parameter categories list into a DTO
     *
     * @return Parameter category DTO list
     */
    public List<CategoriesDTO> getCategoriesListOfTestTypeDTO(String selectedTestTypeCode) {
        CategoriesMapper mapper = new CategoriesMapper();
        return mapper.toDTO(getCategoriesListOfTestType(selectedTestTypeCode));
    }

    /**
     * Method for getting the clinical analysis laboratory by its code
     * @param currentCalCode code of the CAL to be found
     * @return the found CAL
     */
    public ClinicalAnalysisLaboratory getCalByCode(String currentCalCode){
        if(currentCalCode == null) return null;
        ClinicalAnalysisLaboratoryStore calStore = this.company.getCalStore();
        return calStore.getCalByCode(currentCalCode);
    }

    /**
     * Retrieves parameters of a category list
     *
     * @return parameters list
     */
    private List<Parameter> getParametersOfCategories(List<String> selectedCategoriesCodes) {
        ParameterStore parameterStore = this.company.getParameterStore();
        return parameterStore.getParamsByCategories(selectedCategoriesCodes);
    }

    /**
     * Convert parameter list into a DTO
     *
     * @return Parameter DTO list
     */
    public List<ParameterDTO> getParametersOfCategoriesDTO(List<String> selectedCategoriesCodes) {
        ParameterMapper mapper = new ParameterMapper();
        return mapper.toDTO(getParametersOfCategories(selectedCategoriesCodes));
    }

}
