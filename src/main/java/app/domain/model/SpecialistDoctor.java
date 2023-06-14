package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Represents a Specialist Doctor through:
 * a name, an address, a phone number, an email, a SOC code and a doctor index number.
 */
public class SpecialistDoctor extends Employee implements Serializable {

    /**
     * The specialist doctor's doctor index number.
     */
    private String doctorIndexNumber;

    /**
     * Builds a specialist doctor instance receiving:
     * the role, name, address, phone number, email, SOC code and doctor index number.
     *
     * @param role the specialist doctor's role.
     * @param name the specialist doctor's name.
     * @param address the specialist doctor's address.
     * @param phoneNumber the specialist doctor's phone number.
     * @param email the specialist doctor's email.
     * @param socCode the specialist doctor's SOC code.
     * @param doctorIndexNumber the specialist doctor's doctor index number.
     */
    public SpecialistDoctor(OrgRole role,
                            String name,
                            String address,
                            String phoneNumber,
                            String email,
                            String socCode,
                            String doctorIndexNumber) {
        super(role, name, address, phoneNumber, email, socCode);
        checkDoctorIndexNumberRules(doctorIndexNumber);
        this.doctorIndexNumber = doctorIndexNumber;
    }

    /**
     * Checks if the specialist doctor's doctor index number is valid:
     * - it isn't blank;
     * - it has 6 digits.
     *
     * @param doctorIndexNumber the specialist doctor's doctor index number.
     */
    private void checkDoctorIndexNumberRules(String doctorIndexNumber){
        if (StringUtils.isBlank(doctorIndexNumber))
            throw new IllegalArgumentException("Doctor Index Number cannot be blank.");
        if (doctorIndexNumber.length()!=6)
            throw new IllegalArgumentException("Doctor Index Number must have 6 digits.");
    }

    /**
     * Returns the textual description of the specialist doctor.
     *
     * @return characteristic of the specialist doctor.
     */
    @Override
    public String toString() {
        return String.format("%s%nDoctor Index Number%s%n", super.toString(), doctorIndexNumber);
    }

    /**
     * Compares two specialist doctors with each other.
     *
     * @param otherObject the specialist doctor to be compared to.
     *
     * @return true if the two specialist doctor have the same doctor index number,
     * otherwise it returns false.
     */
    @Override
    public boolean equals (Object otherObject){
        if (!super.equals(otherObject))
            return false;

        SpecialistDoctor instance = (SpecialistDoctor) otherObject;

        return this.doctorIndexNumber.equalsIgnoreCase(instance.doctorIndexNumber);
    }

}
