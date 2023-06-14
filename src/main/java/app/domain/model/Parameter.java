package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Represents a Parameter through:
 * a code, a name, a description and a parameter category.
 *
 * @author Marta Ribeiro 1201592
 */

public class Parameter implements Serializable {

    /**
     * Reference value for checking parameter code length.
     */
    final static int CODE_LENGTH = 5;

    /**
     * Reference value for checking parameter name max length.
     */
    final static int NAME_MAX_LENGTH = 8;

    /**
     * Reference value for checking parameter description max length.
     */
    final static int DESCRIPTION_MAX_LENGTH = 20;

    /**
     * The parameter code.
     */
    private String parameterCode;

    /**
     * The parameter name.
     */
    private String shortName;

    /**
     * The parameter description.
     */
    private String description;

    /**
     * The parameter category of the parameter.
     */
    private ParameterCategory pc;

    /**
     * Builds a parameter instance receiving:
     * the parameter code, name, description and parameter category.
     *
     * @param parameterCode the parameter code.
     * @param shortName the parameter name.
     * @param description the parameter description.
     * @param pc the parameter category of the parameter.
     */
    public Parameter(String parameterCode, String shortName, String description, ParameterCategory pc){
        checkParameterCodeRules(parameterCode);
        checkShortNameRules(shortName);
        checkDescriptionRules(description);
        checkParameterCategory(pc);
        this.parameterCode = parameterCode;
        this.shortName = shortName;
        this.description = description;
        this.pc = pc;
    }

    /**
     * Returns the parameter code.
     *
     * @return parameter code.
     */
    public String getPrmCode(){
        return parameterCode;
    }

    /**
     * Returns the parameter name.
     *
     * @return parameter name.
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Returns the parameter description.
     *
     * @return parameter description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the parameter category of the parameter.
     *
     * @return parameter category of the parameter.
     */
    public ParameterCategory getPc() {
        return pc;
    }

    /**
     * Returns the textual description of the parameter instance.
     *
     * @return characteristics of the parameter.
     */
    @Override
    public String toString(){
        return ">> PARAMETER \"" + shortName + "\" <<" +
                "\n   > Parameter code: " + parameterCode + ";" +
                "\n   > Description: " + description + ";" +
                "\n   > Parameter category code: " + pc.getCode() + ";" +
                "\n   > Parameter category name: " + pc.getName() + ".";
    }

    /**
     * Checks if the parameter category is valid:
     * - it isn't null.
     *
     * @param pc the parameter category.
     */
    private void checkParameterCategory(ParameterCategory pc){
        if (pc == null)
            throw new IllegalArgumentException("The inserted parameter category code doesn't exist.");
    }

    /**
     * Checks if the parameter code received respects all the rules:
     * - it isn't blank;
     * - it has a length of 5 chars.
     *
     * @param parameterCode the parameter code.
     */
    private void checkParameterCodeRules(String parameterCode){
        if (StringUtils.isBlank(parameterCode))
            throw new IllegalArgumentException("Code cannot be blank.");
        if (parameterCode.length()!=CODE_LENGTH)
            throw new IllegalArgumentException("Code must have 5 chars.");
    }

    /**
     * Checks if the parameter name received respects all the rules:
     * - it isn't blank;
     * it hasn't a length longer than 8.
     *
     * @param shortName the parameter name.
     */
    private void checkShortNameRules(String shortName){
        if (StringUtils.isBlank(shortName))
            throw new IllegalArgumentException("Name cannot be blank.");
        if (shortName.length()>NAME_MAX_LENGTH)
            throw new IllegalArgumentException("Name must have up to 8 chars.");
    }

    /**
     * Checks if the parameter description respects all the rules:
     * - it isn't blank;
     * . it hasn't a length longer than 20.
     *
     * @param description the parameter description.
     */
    private void checkDescriptionRules(String description){
        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("Description cannot be blank.");
        if (description.length()>DESCRIPTION_MAX_LENGTH)
            throw new IllegalArgumentException("Description must have up to 20 chars.");
    }

    /**
     * Compares two parameters with each other.
     *
     * @param parameterObject the parameter to be compared to.
     *
     * @return true if the two parameters are equal,
     * otherwise it returns false.
     */
    @Override
    public boolean equals(Object parameterObject) {
        if (this == parameterObject) return true;
        if (parameterObject == null || getClass() != parameterObject.getClass()) return false;
        Parameter parameterToCompare = (Parameter) parameterObject;
        return parameterToCompare.getPrmCode().equals(this.getPrmCode());
    }

}