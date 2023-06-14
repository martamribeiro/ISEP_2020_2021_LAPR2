package app.mappers;

import app.domain.model.ClinicalAnalysisLaboratory;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for the Clinical Analysis Laboratories' Instances.
 *
 * @author Ana Albergaria, João Wolff
 */
public class ClinicalAnalysisLaboratoryMapper {

    /**
     * Method for creating a DTO object of a test parameter object
     * @param cal the object to become a DTO
     * @return a ClinicalAnalysisLaboratoryDTO
     */
    public ClinicalAnalysisLaboratoryDTO toDTO(ClinicalAnalysisLaboratory cal) {
        return new ClinicalAnalysisLaboratoryDTO(cal.getLaboratoryID(), cal.getName(), cal.getAddress(),
                cal.getPhoneNumber(), cal.getNumTIN(), cal.getSelectedTT(), cal.getCalTestList());
    }

    /**
     * Method for creating a list of Clinical Analysis Laboratories Dto
     * @param calTestList list of Clinical Analysis Laboratories to become a list of Dtos
     * @return list of Clinical Analysis Laboratories Dto
     */
    public List<ClinicalAnalysisLaboratoryDTO> toDTO(List<ClinicalAnalysisLaboratory> calTestList) {
        List<ClinicalAnalysisLaboratoryDTO> calTestListDTOS = new ArrayList<>();
        for (ClinicalAnalysisLaboratory cal : calTestList) {
            calTestListDTOS.add(this.toDTO(cal));
        }
        return calTestListDTOS;
    }
}

