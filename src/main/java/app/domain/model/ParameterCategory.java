package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Class to generate a parameter category
 *
 * @author João Wolff and Marta Ribeiro
 */
public class ParameterCategory implements Serializable {
    final static int CODE_LENGTH = 5;
    final static int NAME_LENGTH = 10;

    /**
     * Code of the parameter category
     */
    private String code;
    /**
     * Name of the parameter category
     */
    private String name;

    /**
     * Constructor of a parameter category
     *
     * @param code Code of the parameter category
     * @param name Name of the parameter category
     */
    public ParameterCategory(String code, String name) {
        checkCodeRules(code);
        checkNameRules(name);
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    /**
     * Check the validations of the code respecting the criterias
     * @param code Code of the parameter category
     */
    private void checkCodeRules(String code) {
        if (StringUtils.isBlank(code))
            throw new IllegalArgumentException("Code cannot be blank.");
        if ((code.length() != CODE_LENGTH || !StringUtils.isAlphanumeric(code)))
            throw new IllegalArgumentException("Code must have 5 alphanumeric characters.");
    }

    /**
     * Check the validations of the name respecting the criterias
     * @param name Name of the parameter category
     */
    public void checkNameRules(String name) {
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("Name cannot be blank.");
        if (name.length() > NAME_LENGTH)
            throw new IllegalArgumentException("Name cannot have more then 10 characters.");
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        ParameterCategory otherParameterCategory = (ParameterCategory) otherObject;

        return  this.code.equalsIgnoreCase(otherParameterCategory.code);
    }

    @Override
    public String toString() {
        return String.format("Code: %s%nName: %s%n", code, name);
    }

}
