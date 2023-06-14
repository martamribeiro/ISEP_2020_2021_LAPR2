package app.domain.store;

import app.domain.model.ParameterCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Store of parameter cateogories
 *
 * @author João Wolff
 */
public class ParameterCategoryStore {

    /**
     * list of parameter categories
     */
    private List<ParameterCategory> parameterCategoriesList = new ArrayList<>();

    public List<ParameterCategory> getParameterCategoriesStore() {
        return parameterCategoriesList;
    }

    /**
     * Creates an instance of parameter category
     * @param code Code of the parameter category
     * @param name Name of the parameter category
     * @return The created parameter category
     */
    public ParameterCategory createParameterCategory(String code, String name){
        return new ParameterCategory(code, name);
    }

    /**
     * Validates if an parameter category is either null or already existent in the list
     * @param pc Parameter category to be validated
     * @return True if it is not null or in the list, false otherwise
     */
    public boolean validateParameterCategory(ParameterCategory pc){
        if (pc == null)
            return false;
        return !this.parameterCategoriesList.contains(pc);
    }

    /**
     * Saves an instance of parameter category in the list of the store
     * @param pc Parameter category to be saved
     * @return True if category is succesfully added, false otherwise
     */
    public boolean saveParameterCategory(ParameterCategory pc){
        if (!validateParameterCategory(pc))
            return false;
        return this.parameterCategoriesList.add(pc);
    }

    /**
     * Gets a parameter categories list acording to recieved list of parameter categories codes
     * @param parameterCategoryCodes List of parameter categories codes
     * @return List of parameter categories
     */
    public List<ParameterCategory> getCategoriesByCode(List<String> parameterCategoryCodes) {
        List<ParameterCategory> selectedCategories = new ArrayList<>();
        for (String item : parameterCategoryCodes) {
            selectedCategories.add(getCategoryByCode(item));
        }
        return selectedCategories;
    }

    /**
     * Gets a parameter category given a parameter category code
     * @param code Code of a parameter category
     * @return Parameter category
     */
    public ParameterCategory getCategoryByCode(String code) {
        for (ParameterCategory pc : parameterCategoriesList) {
            if(pc.getCode().equalsIgnoreCase(code)){
                return pc;
            }
        }
        throw new UnsupportedOperationException("Parameter Category not found!");
    }

}