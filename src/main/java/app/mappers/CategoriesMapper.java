package app.mappers;

import app.mappers.dto.CategoriesDTO;
import app.domain.model.ParameterCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for the Categories' Instances.
 *
 * @author SRC-Code-23
 */
public class CategoriesMapper {

    /**
     * Method for creating a DTO object of a test parameter object
     * @param category the object to become a DTO
     * @return a CategoriesDTO
     */
    public CategoriesDTO toDTO(ParameterCategory category)
    {
        return new CategoriesDTO(category.getName(),category.getCode());
    }

    /**
     * Method for creating a list of test parameters dto
     * @param categories list of test paramters to become a list of dtos
     * @return list of test parameters dto
     */
    public List<CategoriesDTO> toDTO(List<ParameterCategory> categories)
    {
        List<CategoriesDTO> categoriesDTO = new ArrayList<>();
        for(ParameterCategory category : categories)
        {
            categoriesDTO.add(this.toDTO(category));
        }
        return categoriesDTO;
    }
}
