package app.controller;

import app.domain.model.*;
import app.domain.store.ClinicalAnalysisLaboratoryStore;
import app.domain.store.TestTypeStore;
import app.mappers.TestTypeMapper;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;
import app.mappers.dto.TestTypeDTO;

import java.util.List;

/**Controller class for registering a new Clinical Analysis Laboratory
 *
 *  @author Ana Albergaria
 *
 */
public class RegisterNewCalController {

    /**
     * The company associated to the Controller.
     */
    private Company company;

    /**
     * The Clinical Analysis Laboratory associated to the Controller.
     */
    private ClinicalAnalysisLaboratory cal;

    /**
     * Builds an empty constructor for having the actual instance of the company when instantiated.
     */
    public RegisterNewCalController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Builds a Register New Cal Controller's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public RegisterNewCalController(Company company) {
        this.company = company;
        this.cal = null;
    }


    /**
     * Calling method to create a new Clinical Analysis Laboratory
     * through a Dto containing all the requested data:
     * laboratoryID, name, address, phone number, TIN number and the codes of the types of test
     *
     * @param calDto the Clinical Analysis Laboratory Dto
     * @return true if the new Clinical Analysis Laboratory was successfully created.
     *          Otherwise, returns false.
     */
    public boolean createClinicalAnalysisLaboratory(ClinicalAnalysisLaboratoryDTO calDto) {
        List<String> testTypeCodes = calDto.getTestTypeCodes();
        TestTypeStore testTypeStore = this.company.getTestTypeStore();
        List<TestType> selectedTT = testTypeStore.getTestTypesByCode(testTypeCodes);
        ClinicalAnalysisLaboratoryStore calStore = this.company.getCalStore();
        this.cal = calStore.createClinicalAnalysisLaboratory(calDto, selectedTT);
        return calStore.validateClinicalAnalysisLaboratory(cal);
    }

    /**
     * Calling method to save a new Clinical Analysis Laboratory.
     *
     * @return true if the new Clinical Analysis Laboratory was successfully saved.
     *          Otherwise, returns false.
     */
    public boolean saveClinicalAnalysisLaboratory(){
        ClinicalAnalysisLaboratoryStore calStore = this.company.getCalStore();
        return calStore.saveClinicalAnalysisLaboratory(cal);
    }

    /**
     * Calling method to get the list of Test Types available in the system.
     * It makes use of a mapper to transform said list to DTO to reduce coupling.
     *
     * @return true if the list was successfully received.
     *          Otherwise, returns false.
     */
    public List<TestTypeDTO> getTestTypes() {
        TestTypeStore storeTest = this.company.getTestTypeStore();
        List<TestType> listTestType = storeTest.getTestTypes();

        TestTypeMapper mapper = new TestTypeMapper();
        return mapper.toDTO(listTestType);
    }



}
