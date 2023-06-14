package app.domain.store;

import app.domain.model.ClinicalAnalysisLaboratory;
import app.domain.model.Test;
import app.domain.model.TestType;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Clinical Analysis Laboratory Store
 *
 * @author Ana Albergaria
 */
public class ClinicalAnalysisLaboratoryStore implements Serializable {
    /**
     * The Company's list of Clinical Analysis Laboratories
     */
    private List<ClinicalAnalysisLaboratory> calList = new ArrayList<>();

    /**
     * Called method through the RegisterNewCalController to create a new Clinical Analysis Laboratory
     * through a Dto containing all the requested data:
     * laboratoryID, name, address, phone number, TIN number and the codes of the types of test
     *
     * @param calDTO
     * @return instance of ClinicalAnalysisLaboratory Class with the information provided by the Dto
     *          received by parameter
     */
    public ClinicalAnalysisLaboratory createClinicalAnalysisLaboratory(ClinicalAnalysisLaboratoryDTO calDTO, List<TestType> selectedTT) {
        return new ClinicalAnalysisLaboratory(calDTO.getLaboratoryID(), calDTO.getName(),
                calDTO.getAddress(), calDTO.getPhoneNumber(), calDTO.getNumTIN(), selectedTT);
    }

    /**
     * Called method through the RegisterNewCalController to validate a new Clinical Analysis Laboratory.
     *
     * @param cal
     * @return true if the new Clinical Analysis Laboratory was successfully validated.
     *          Otherwise, returns false.
     */
    public boolean validateClinicalAnalysisLaboratory(ClinicalAnalysisLaboratory cal){
        if(cal == null)
            return false;
        checkCalDuplicates(cal);
        return ! this.calList.contains(cal);
    }

    /**
     * Called method through the RegisterNewCalController to save a new Clinical Analysis Laboratory.
     *
     * @param cal
     * @return true if the new Clinical Analysis Laboratory was successfully saved.
     *          Otherwise, returns false.
     */
    public boolean saveClinicalAnalysisLaboratory(ClinicalAnalysisLaboratory cal){
        if (!validateClinicalAnalysisLaboratory(cal))
            return false;

        return this.calList.add(cal);
    }

    /**
     * Global validation method regarding the creation of a new Clinical Analysis Laboratory.
     * Its goal is to make sure there aren't any Clinical Analysis Laboratories with the same:
     * laboratory ID or address or phone number or TIN number.
     *
     * It receives in the parameter the Clinical Analysis Laboratory to be created.
     *
     * @param cal
     */
    public void checkCalDuplicates(ClinicalAnalysisLaboratory cal) {
        for (ClinicalAnalysisLaboratory item : calList) {
            if(cal.getLaboratoryID().equalsIgnoreCase(item.getLaboratoryID()))
                throw new IllegalArgumentException("Laboratory ID already registered in the system.");
            if(cal.getAddress().equalsIgnoreCase(item.getAddress()))
                throw new IllegalArgumentException("Address already registered in the system.");
            if(cal.getPhoneNumber().equals(item.getPhoneNumber()))
                throw new IllegalArgumentException("Phone Number already registered in the system.");
            if(cal.getNumTIN().equals(item.getNumTIN()))
                throw new IllegalArgumentException("TIN Number already registered in the system.");
        }
    }

    public List<ClinicalAnalysisLaboratory> getCalList() {
        return new ArrayList<>(calList);
    }


    /**
     * Method for getting the Clinical Analysis Laboratory by its laboratory ID.
     *
     * @param laboratoryID the laboratory ID of the Clinical Analysis Laboratory
     *
     * @return a Clinical Analysis Laboratory Object which has the laboratory ID received by parameter
     */
    public ClinicalAnalysisLaboratory getCalByCode(String laboratoryID) {
        for (ClinicalAnalysisLaboratory cal : calList) {
            if(cal.getLaboratoryID().equalsIgnoreCase(laboratoryID))
                return cal;
        }
        throw new UnsupportedOperationException("Clinical Analysis Laboratory not found!");
    }
}
