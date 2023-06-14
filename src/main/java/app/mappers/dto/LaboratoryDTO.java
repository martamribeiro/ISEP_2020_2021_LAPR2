package app.mappers.dto;

/**
 * @author Ana Albergaria 1201518
 */

public class LaboratoryDTO {
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
     * Builds a Laboratory's instance receiving:
     * the name, the address, the phone number, the TIN number
     *
     * @param name the name of the Laboratory
     * @param address the address of the Laboratory
     * @param phoneNumber the phone number of the Laboratory
     * @param numTIN the TIN number of the Laboratory
     */
    public LaboratoryDTO(String name, String address, String phoneNumber, String numTIN) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.numTIN = numTIN;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getNumTIN() {
        return numTIN;
    }

    @Override
    public String toString() {
        return String.format("CLINICAL ANALYSIS LABORATORY%n> Name: %s%n"
                + "> Address: %s%n> PhoneNumber: %s%n> TIN Number: %s%n", name, address, phoneNumber, numTIN);
    }
}
