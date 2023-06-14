package app.mappers;

import app.domain.model.Test;
import app.mappers.dto.TestDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for the Tests' Instances.
 *
 * @author SRC-Code-23
 */
public class TestMapper {
    /**
     * Method for creating a DTO object of a Test object
     * @param test the object to become a DTO
     * @return a Test DTO
     */
    public TestDTO toDTO(Test test) {
        return new TestDTO(test.getCode(), test.getNhsCode(), test.getClient(),
                test.getTestType(), test.getParameters(), test.getSamples(),
                test.getDiagnosisReport(), test.getDateOfTestRegistration(),
                test.getDateOfSamplesCollection(), test.getDateOfChemicalAnalysis(),
                test.getDateOfDiagnosis(), test.getDateOfValidation());
    }

    /**
     * Method for creating a list of Tests Dto
     * @param tests list of Tests to become a list of Dtos
     * @return list of Tests Dto
     */
    public List<TestDTO> toDTO(List<Test> tests) {
        List<TestDTO> testDTOS = new ArrayList<>();
        for (Test test : tests) {
            testDTOS.add(this.toDTO(test));
        }
        return testDTOS;
    }
}
