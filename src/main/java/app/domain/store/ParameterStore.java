package app.domain.store;

import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes some of the company responsabilities regarding parameters,
 * in order to achieve high cohesion and low coupling.
 *
 * @author Marta Ribeiro 1201592
 */
public class ParameterStore implements Serializable {

    /**
     * List of existing parameters.
     */
    private List<Parameter> prmList = new ArrayList<>();

    /**
     * Creates parameter reference.
     *
     * @param parameterCode the parameter code.
     * @param shortName the parameter name.
     * @param description the parameter description.
     * @param pc the parameter category of the parameter.
     *
     * @return created parameter reference.
     */
    public Parameter createParameter(String parameterCode, String shortName, String description, ParameterCategory pc){
        return new Parameter(parameterCode, shortName, description, pc);
    }

    /**
     * Validates parameter instance globally,
     * checking if the parameter is null or duplicated.
     *
     * @param prm the parameter.
     *
     * @return true if the parameter isn't null or duplicated,
     * otherwise returns false.
     */
    public boolean validateParameter(Parameter prm){
        if (prm == null)
            return false;
        return !this.prmList.contains(prm);
    }

    /**
     * Saves parameter instance the parameter store list,
     * validating the parameter globally before doing so.
     *
     * @param prm the parameter.
     *
     * @return true if the parameter is successfully validated,
     * otherwise return false.
     */
    public boolean saveParameter(Parameter prm){
        if (!validateParameter(prm))
            return false;
        return this.prmList.add(prm);
    }

    public List<Parameter> getParamsByCodes(List<String> selectedParamCodes){
        List<Parameter> selectedParameters = new ArrayList<>();
        for (String item : selectedParamCodes) {
            selectedParameters.add(getParamBycode(item));
        }
        return selectedParameters;
    }

    private Parameter getParamBycode(String paramCode) {
        for (Parameter param : prmList) {
            if (param.getPrmCode().equalsIgnoreCase(paramCode)) {
                return param;
            }
        }
        throw new UnsupportedOperationException("There are no parameters with given code: " + paramCode);
    }

    public List<Parameter> getParamsByCategories(List<String> selectedCategoriesCodes){
        List<Parameter> selectedParameters = new ArrayList<>();
        for (String item : selectedCategoriesCodes) {
            getParamByCategory(item,selectedParameters);
        }
        return selectedParameters;
    }

    private void getParamByCategory(String categoryCode, List<Parameter> selectedParameters) {
        for (Parameter param : prmList) {
            if (param.getPc().getCode().equalsIgnoreCase(categoryCode)) {
                 selectedParameters.add(param);
            }
        }

    }

    /**
     * Transforms the parameter list into an array.
     *
     * @return parameter store list contents as an array.
     */
    public Parameter[] toArray() {
        Parameter[] array = new Parameter[this.prmList.size()];
        return this.prmList.toArray(array);
    }

    public List<Parameter> getPrmList() {
        return prmList;
    }
}
