package app.controller;

import app.domain.model.Company;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.store.ParameterCategoryStore;
import app.domain.store.ParameterStore;
import app.mappers.CategoriesMapper;
import app.mappers.dto.CategoriesDTO;
import java.util.List;

/**
 Controller class for creating a new parameter
 @author Marta Ribeiro 1201592
 */
public class CreateParameterController {

    /**
     * The company associated to the Controller.
     */
    private Company company;

    /**
     * The parameter associated to the Controller.
     */
    private Parameter prm;

    /**
     * Builds an empty constructor for having the actual instance of the company when instantiated.
     */
    public CreateParameterController(){
        this(App.getInstance().getCompany());
    }

    /**
     * Builds a Parameter Controller's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public CreateParameterController(Company company){
        this.company = company;
        this.prm = null;
    }

    //US10 SD: 4-8
    /**
     * Retrieves actual parameter categories list.
     *
     * @return parameter categories list.
     */
    public List<ParameterCategory> getParameterCategories() {
        ParameterCategoryStore parameterCategoryStore = this.company.getParameterCategoryStore();
        List<ParameterCategory> categoriesList = parameterCategoryStore.getParameterCategoriesStore();
        return categoriesList;
    }

    //US10 SD: 9-15
    /**
     * Convert parameter categories list into a DTO.
     *
     * @return parameter categories DTO list.
     */
    public List<CategoriesDTO> getParameterCategoriesDTO(){
        CategoriesMapper mapper = new CategoriesMapper();
        return mapper.toDTO(getParameterCategories());
    }

    //US10 SD: 19-24
    /**
     * Creates an instance of parameter type.
     *
     * @param parameterCode the parameter code.
     * @param shortName the parameter name.
     * @param description the parameter description.
     * @param pcCode the code of the parameter category of the parameter.
     */
    public void createParameter(String parameterCode, String shortName, String description, String pcCode){
        ParameterCategoryStore parameterCategoryStore = this.company.getParameterCategoryStore();
        ParameterCategory pc = parameterCategoryStore.getCategoryByCode(pcCode); //vai buscar a cat através do code | o getCategoryByCode está na store
        ParameterStore parameterStore = this.company.getParameterStore();
        this.prm = parameterStore.createParameter(parameterCode, shortName, description, pc);
    }

    //US10 SD: 29-31
    /**
     * Saves current parameter in the parameter store.
     *
     * @return true if the parameter is successfully saved,
     * otherwise return false.
     */
    public boolean saveParameter(){
        ParameterStore parameterStore = this.company.getParameterStore();
        return parameterStore.saveParameter(prm);
    }

}