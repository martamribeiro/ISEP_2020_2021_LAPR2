package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Represents an Organization Role through:
 * a description.
 */
public class OrgRole implements Serializable {

    /**
     * The organization role description.
     */
    private String description;

    /**
     * Builds an organization role instance receiving:
     * the description.
     *
     * @param description the organization role description.
     */
    public OrgRole(String description) {
        checkDescription(description);
        this.description = description;
    }

    /**
     * Checks if the organization role description is valid:
     * - it isn't blank;
     * - it has up to 15 chars;
     * - it has only letters.
     *
     * @param description the organization role description.
     */
    private void checkDescription(String description){
        if (StringUtils.isBlank(description))
            throw new IllegalArgumentException("Organization Role cannot be blank.");
        if (description.length()>15)
            throw new IllegalArgumentException("Organization Role Description must have up to 15 chars.");
        if (!description.matches("[a-zA-Z\\s]*$"))
            throw new IllegalArgumentException("Organization Role Description can only have letters.");
    }

    /**
     * Returns the organization role description.
     *
     * @return the organization role description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the textual description of the organization role.
     *
     * @return characteristics of the organization role.
     */
    @Override
    public String toString() {
        return String.format("Organization Role: %s", description);
    }

    /**
     * Compares two organization roles with each other.
     *
     * @param otherObject the organization role to be compared to.
     *
     * @return true if the two organization roles have the same description,
     * otherwise it returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject)
            return true;

        if(otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        OrgRole otherOrgRole = (OrgRole) otherObject;

        return this.description.equalsIgnoreCase(otherOrgRole.description);
    }
}
