package app.mappers;

import app.domain.model.Parameter;
import app.mappers.dto.ParameterDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for the Parameter' Instances.
 *
 * @author SRC-Code-23
 */
public class ParameterMapper {
    /**
     * Method for creating a DTO object of a Parameter object
     * @param param the object to become a DTO
     * @return a Parameter DTO
     */
    public ParameterDTO toDTO(Parameter param) {
        return new ParameterDTO(param.getPrmCode(), param.getShortName(), param.getDescription(), param.getPc());
    }

    /**
     * Method for creating a list of Parameters Dto
     * @param params list of Parameters to become a list of Dtos
     * @return list of Parameters Dto
     */
    public List<ParameterDTO> toDTO(List<Parameter> params) {
        List<ParameterDTO> parameterDTOS = new ArrayList<>();
        for (Parameter param : params) {
            parameterDTOS.add(this.toDTO(param));
        }
        return parameterDTOS;
    }

}
