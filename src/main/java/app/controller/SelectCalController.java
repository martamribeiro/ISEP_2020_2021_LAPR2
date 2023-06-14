package app.controller;

import app.domain.model.ClinicalAnalysisLaboratory;
import app.domain.model.Company;
import app.domain.store.ClinicalAnalysisLaboratoryStore;
import app.mappers.ClinicalAnalysisLaboratoryMapper;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;

import java.util.List;
/**
 * Controller class for showing the list of available Clinical Analysis Laboratories
 * to the Receptionist and Medical Lab Technician.
 *
 *  @author Ana Albergaria, João Wolff
 *
 */

public class SelectCalController {

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
    public SelectCalController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Builds a Record Samples Controller's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public SelectCalController(Company company) {
        this.company = company;
        this.cal = null;
    }

    /**
     * Returns the list of the Clinical Analysis Laboratories available in the system
     * converted to Dto through a Mapper.
     *
     * @return the list of the Clinical Analysis Laboratories Dto available in the system
     */
    public List<ClinicalAnalysisLaboratoryDTO> getCalListDTO() {
        ClinicalAnalysisLaboratoryMapper mapper = new ClinicalAnalysisLaboratoryMapper();
        return mapper.toDTO(getCalList());
    }

    /**
     * Returns the list of the Clinical Analysis Laboratories available in the system
     *
     * @return the list of the Clinical Analysis Laboratories available in the system
     */
    public List<ClinicalAnalysisLaboratory> getCalList() {
        ClinicalAnalysisLaboratoryStore calStore = this.company.getCalStore();
        return calStore.getCalList();
    }
}
