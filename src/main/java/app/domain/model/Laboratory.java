package app.domain.model;

import org.apache.commons.lang3.StringUtils;

/**
 * This class allows the construction of a class hierarchy
 * to represent different types of laboratories.
 * Specifies common characteristics to all the
 * hierarchy classes.
 *
 * @author Ana Albergaria
 */

public class Laboratory {
    /**
     * The name of the Laboratory.
     */
    private String name;

    /**
     * The address of the Laboratory.
     */
    private String address;

    /**
     * The phone number the Laboratory.
     */
    private String phoneNumber;

    /**
     * The TIN number of the Laboratory.
     */
    private String numTIN;

    /**
     * Maximum length the name can have.
     */
    private static final int NAME_MAX_LENGTH = 20;

    /**
     * Maximum length the address can have.
     */
    private static final int ADDRESS_MAX_LENGTH = 30;

    /**
     * Length of the phone number.
     */
    private static final int PHONE_NUMBER_LENGTH = 11;

    /**
     * Length of the TIN number.
     */
    private static final int TIN_NUMBER_LENGTH = 10;


    /**
     * Builds a Laboratory's instance receiving:
     * the name, the address, the phone number, the TIN number
     *
     * @param name the name of the Laboratory
     * @param address the address of the Laboratory
     * @param phoneNumber the phone number of the Laboratory
     * @param numTIN the TIN number of the Laboratory
     */
    public Laboratory(String name, String address, String phoneNumber, String numTIN) {
        checkNameRules(name);
        checkAddressRules(address);
        checkPhoneNumberRules(phoneNumber);
        checkTINNumberRules(numTIN);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.numTIN = numTIN;
    }

    /**
     * Returns the name of the Laboratory
     *
     * @return name of the Laboratory
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the address of the Laboratory
     *
     * @return address of the Laboratory
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the phone number of the Laboratory
     *
     * @return phone number of the Laboratory
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the TIN number of the Laboratory
     *
     * @return TIN number of the Laboratory
     */
    public String getNumTIN() {
        return numTIN;
    }

    /**
     * Returns true if the name received in the parameter respects
     * all the rules.
     * It returns false as soon as one of these conditions are not verified
     * by the name provided:
     * - It is blank
     * - It doesn't contain only letters
     * - Its length is greater than 20
     *
     * @param name name of the Laboratory
     *
     * @return true if the name respects all the rules,
     *         otherwise returns false
     */
    public void checkNameRules(String name) {
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("Name cannot be blank.");
        if (name.length() > NAME_MAX_LENGTH)
            throw new IllegalArgumentException("Name cannot have more than 20 characters.");
    }

    /**
     * Returns true if the address received in the parameter respects
     * all the rules.
     * It returns false as soon as one of these conditions are not verified
     * by the address provided:
     * - It is blank
     * - Its length is greater than 30
     *
     * @param address address of the Laboratory
     *
     * @return true if the address respects all the rules,
     *         otherwise returns false
     */
    public void checkAddressRules(String address) {
        if (StringUtils.isBlank(address))
            throw new IllegalArgumentException("Address cannot be blank.");
        if (address.length() > ADDRESS_MAX_LENGTH)
            throw new IllegalArgumentException("Address cannot have more than 30 characters.");
    }

    /**
     * Returns true if the phone number received in the parameter respects
     * all the rules.
     * It returns false as soon as one of these conditions are not verified
     * by the phone number provided:
     * - It is blank
     * - It doesn't contain only digits
     * - Its length is different than 11
     *
     * @param phoneNumber phone number of the Laboratory
     *
     * @return true if the phone number respects all the rules,
     *         otherwise returns false
     */
    public void checkPhoneNumberRules(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber))
            throw new IllegalArgumentException("Phone Number cannot be blank.");
        //if ((!phoneNumber.chars().allMatch(Character::isDigit)))
        if ((!phoneNumber.matches("[0-9]+")))
            throw new IllegalArgumentException("Phone Number must only contain digits.");
        if (phoneNumber.length() != PHONE_NUMBER_LENGTH)
            throw new IllegalArgumentException("Phone Number must contain exactly 11 digits.");

    }

    /**
     * Returns true if the TIN number received in the parameter respects
     * all the rules.
     * It returns false as soon as one of these conditions are not verified
     * by the TIN number provided:
     * - It is blank
     * - It doesn't contain only digits
     * - Its length is different than 10
     *
     * @param numTIN TIN number of the Laboratory
     *
     * @return true if the TIN number respects all the rules,
     *         otherwise returns false
     */
    public void checkTINNumberRules(String numTIN) {
        if (StringUtils.isBlank(numTIN))
            throw new IllegalArgumentException("TIN Number cannot be blank.");
        //if ((!numTIN.chars().allMatch(Character::isDigit)))
        if ((!numTIN.matches("[0-9]+")))
            throw new IllegalArgumentException("TIN Number must only contain digits.");
        if (numTIN.length() != TIN_NUMBER_LENGTH)
            throw new IllegalArgumentException("TIN Number must contain exactly 10 digits.");
    }

    /**
     * Compares the Laboratory with the received object.
     *
     * @param otherObject the object to be compared with the Laboratory
     * @return true if the received object represents other Laboratory
     * equivalent to the Laboratory. Otherwise, returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject)
            return true;

        if(otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        Laboratory otherLaboratory = (Laboratory) otherObject;

        return this.name.equalsIgnoreCase(otherLaboratory.name) &&
                this.address.equalsIgnoreCase(otherLaboratory.address) &&
                this.phoneNumber.equalsIgnoreCase(otherLaboratory.phoneNumber) &&
                this.numTIN.equalsIgnoreCase(otherLaboratory.numTIN);
    }








}
