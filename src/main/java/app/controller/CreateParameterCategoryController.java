package app.controller;

import app.domain.model.Company;
import app.domain.model.ParameterCategory;
import app.domain.store.ParameterCategoryStore;

/**
 * Controller class for creating parameter categories.
 *
 * @author João Wolff and Marta Ribeiro
 */
public class CreateParameterCategoryController {

    /**
     * Company instance of the session
     */
    private Company company;

    /**
     * Parameter category to be created
     */
    private ParameterCategory pc;

    /**
     * Empty constructor for having the actual instance of the company when instantiated.
     */
    public CreateParameterCategoryController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Construtor recieving the company as an argument
     *
     * @param company company instance
     */
    public CreateParameterCategoryController(Company company) {
        this.company = company;
        this.pc = null;
    }

    /**
     * Method for creating the parameter category
     *
     * @param code Code of the parameter category
     * @param name Name of the parameter category
     * @return True if successfully created, false otherwise
     */
    public boolean createParameterCategory(String code, String name) {
        ParameterCategoryStore store = this.company.getParameterCategoryStore();
        this.pc = store.createParameterCategory(code, name);
        return store.validateParameterCategory(pc);
    }


    /**
     * Method for saving a parameter category into the list of the store
     *
     * @return True if successfully save the parameter category, false the otherway.
     */
    public boolean saveParameterCategory() {
        ParameterCategoryStore store = this.company.getParameterCategoryStore();
        return store.saveParameterCategory(pc);
    }

}
