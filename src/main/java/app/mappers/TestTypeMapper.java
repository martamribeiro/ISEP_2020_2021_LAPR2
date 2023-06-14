package app.mappers;

import app.mappers.dto.TestTypeDTO;
import app.domain.model.TestType;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for the Test Types' Instances.
 *
 * @author SRC-Code-23
 */
public class TestTypeMapper {
    /**
     * Method for creating a DTO object of a Test Type object
     * @param tt the object to become a DTO
     * @return a Test Type DTO
     */
    public TestTypeDTO toDTO(TestType tt) {
        return new TestTypeDTO(tt.getCode(), tt.getDescription(),
                                tt.getCollectingMethod(), tt.getSelectedCategories());
    }

    /**
     * Method for creating a list of Test Types Dto
     * @param testTypes list of Test Types to become a list of Dtos
     * @return list of Test Types Dto
     */
    public List<TestTypeDTO> toDTO(List<TestType> testTypes) {
        List<TestTypeDTO> testTypesDTOS = new ArrayList<>();
        for (TestType testType : testTypes) {
            testTypesDTOS.add(this.toDTO(testType));
        }
        return testTypesDTOS;
    }

}
