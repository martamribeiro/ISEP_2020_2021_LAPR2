package app.mappers;

import app.domain.model.TestParameter;
import app.mappers.dto.TestParametersDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marta Ribeiro 1201592
 */
public class TestParameterMapper {

    /**
     * Method for creating a DTO object of a test parameter object
     * @param testParameter the object to become a DTO
     * @return a testParameterDTO
     */
    public TestParametersDTO toDTO(TestParameter testParameter){
        return new TestParametersDTO(testParameter.getParameter(),testParameter.getTestParameterResult());
    }

    /**
     * Method for creating a list of test parameters dto
     * @param testParameters list of test paramters to become a list of dtos
     * @return list of test parameters dto
     */
    public List<TestParametersDTO> toDTO(List<TestParameter> testParameters){
        List<TestParametersDTO> testParametersDTO = new ArrayList<>();
        for (TestParameter testParameter : testParameters){
            testParametersDTO.add(this.toDTO(testParameter));
        }
        return testParametersDTO;
    }

}
