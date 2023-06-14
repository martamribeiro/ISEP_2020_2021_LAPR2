package app.controller;

import app.domain.model.Company;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.domain.shared.Constants;
import app.domain.store.ParameterCategoryStore;
import app.domain.store.TestTypeStore;
import app.mappers.CategoriesMapper;
import app.mappers.dto.CategoriesDTO;

import java.util.List;

/**
 * Controller for creating a type of test
 *
 * @author João Wolff
 */
public class CreateTestTypeController {

    /**
     * Company instance of the session
     */
    private final Company company;

    /**
     * Test type to be created by the controller
     */
    private TestType testType;

    /**
     * Empty constructor for having the actual instance of the company when instantiated.
     */
    public CreateTestTypeController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Construtor recieving the company as an argument
     *
     * @param company instance of company to be used
     */
    public CreateTestTypeController(Company company) {
        this.company = company;
        this.testType = null;
    }

    /**
     * Creates an instance of test type
     *
     * @param code                    Test type's code
     * @param description             Test type's description
     * @param collectingMethod        Test type's collecting methods
     * @param selectedCategoriesCodes Test type's categories ids list
     * @return True if succesfully created and false if not
     */
    public boolean createTestType(String code, String description, String collectingMethod, List<String> selectedCategoriesCodes, String nameOfExternalModule) {
        ParameterCategoryStore parameterCategoryStore = this.company.getParameterCategoryStore();
        TestTypeStore testTypeStore = this.company.getTestTypeStore();

        List<ParameterCategory> selectedCategories = parameterCategoryStore.getCategoriesByCode(selectedCategoriesCodes);
        String classNameOfApi = getExternalModuleNameByIndex(nameOfExternalModule);

        this.testType = testTypeStore.createTestType(code, description, collectingMethod, selectedCategories, classNameOfApi);
        return testTypeStore.validateTestType(testType);
    }

    /**
     * Saves current test type in the test type store
     *
     * @return True if successfully validated and saved false the otherway
     */
    public boolean saveTestType() {
        TestTypeStore testTypeStore = this.company.getTestTypeStore();
        return testTypeStore.saveTestType(testType);
    }

    private String getExternalModuleNameByIndex(String nameOfExternalModule){
        return nameOfExternalModule.equals(Constants.COVID_MODULE_NAME) ? Constants.COVID_EXTERNAL_ADAPTER :
                nameOfExternalModule.equals(Constants.BLOOD_MODULE_1_NAME) ? Constants.BLOOD_EXTERNAL_ADAPTER_2 :
                        nameOfExternalModule.equals(Constants.BLOOD_MODULE_2_NAME) ? Constants.BLOOD_EXTERNAL_ADAPTER_3 :
                                null;
    }

    /**
     * Retrieves actual parameter categories list
     *
     * @return parameter categories list
     */
    public List<ParameterCategory> getParameterCategories() {
        ParameterCategoryStore parameterCategoryStore = this.company.getParameterCategoryStore();
        return parameterCategoryStore.getParameterCategoriesStore();
    }

    /**
     * Convert parameter categories list into a DTO
     *
     * @return Parameter categories DTO list
     */
    public List<CategoriesDTO> getParameterCategoriesDTO() {
        CategoriesMapper mapper = new CategoriesMapper();
        return mapper.toDTO(getParameterCategories());
    }

}
