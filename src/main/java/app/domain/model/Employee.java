package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Represents an Employee through:
 * an employee ID, a name, an address, a phone number, an email,
 * a SOC code and a role.
 */
public class Employee implements Serializable {

    /**
     * The employee ID.
     */
    private final String employeeID;

    /**
     * The employee name.
     */
    private final String name;

    /**
     * The employee address.
     */
    private final String address;

    /**
     * The employee phone number.
     */
    private final String phoneNumber;

    /**
     * The employee email.
     */
    private final String email;

    /**
     * The employee SOC code.
     */
    private final String socCode;

    /**
     * The employee role.
     */
    private final OrgRole role;

    /**
     * Number of existing employees.
     */
    private static int totalEmployees = 0;

    /**
     * Builds an employee instance receiveing:
     * the employee role, ID, name, address, phone number, email and SOC code.
     * It also generates an employee ID.
     *
     * @param role the employee role.
     * @param name the employee name.
     * @param address the employee address.
     * @param phoneNumber the employee phone number.
     * @param email the employee email.
     * @param socCode the employee SOC code.
     */
    public Employee(OrgRole role,
                    String name,
                    String address,
                    String phoneNumber,
                    String email,
                    String socCode) {
        checkRoleRules(role);
        checkNameRules(name);
        checkAddressRules(address);
        checkPhoneNumberRules(phoneNumber);
        checkEmailRules(email);
        checkSocCodeRules(socCode);
        this.role = role;
        this.employeeID = generateEmployeeID(name);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.socCode = socCode;
        totalEmployees++;
    }

    /**
     * Generates an employee ID based on the employee name
     * and the number of employees in the company.
     *
     * @param name the employee name.
     *
     * @return generated employee ID.
     */
    public String generateEmployeeID(String name){
        String employeeID = "";
        String[] nameArray = name.split(" ");
        for (int i = 0; i < nameArray.length; i++) {
            String word = nameArray[i];
            employeeID = employeeID + word.charAt(0);
        }
        int num = totalEmployees + 1;
        String str = String.format("%05d", num);
        employeeID = employeeID + str;
        return employeeID;
    }

    /**
     * Checks if the employee role is valid:
     * - the role exists in the system.
     * Essa verificação esta estranaha, o employee não conhece todas roles do sistema o array vai ser sempre vazio
     * @param role the employee role.
     */
    private void checkRoleRules(OrgRole role){
        /*if (!this.roles.contains(role))
            throw new IllegalArgumentException("The typed role doesn't exist in the system.");*/
        if (role == null)
            throw new IllegalArgumentException("The organization role cannot be null.");
    }

    /**
     * Checks if the employee name is valid:
     * - the name cannot be blank;
     * - the name most have up to 35 chars.
     *
     * @param name the employee name.
     */
    private void checkNameRules(String name){
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("Name cannot be blank.");
        if (name.length()>35)
            throw new IllegalArgumentException("Name must have up to 35 chars.");
    }

    /**
     * Checks if the employee address is valid:
     * - the address cannot be blank;
     * - the address must have up to 30 chars.
     *
     * @param address the employee address.
     */
    private void checkAddressRules(String address){
        if (StringUtils.isBlank(address))
            throw new IllegalArgumentException("Address cannot be blank.");
        if (address.length()>30)
            throw new IllegalArgumentException("Address must have up to 30 chars.");
    }

    /**
     * Checks if the employee phone number is valid:
     * - the phone number cannot be blank;
     * - the phone number must have 10 digits;
     * - the phone number must only have numbers.
     *
     * @param phoneNumber the employee phone number.
     */
    private void checkPhoneNumberRules(String phoneNumber){
        if (StringUtils.isBlank(phoneNumber))
            throw new IllegalArgumentException("Phone Number cannot be blank.");
        if (phoneNumber.length()!=10)
            throw new IllegalArgumentException("Phone Number must have 10 digits.");
        if (!phoneNumber.matches("[0-9]+"))
            throw new IllegalArgumentException("Phone Number must only have numbers.");
    }

    /**
     * Cheks if the employee email is valid:
     *
     * @param email the employee email.
     */
    private void checkEmailRules(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        if (StringUtils.isBlank(email))
            throw new IllegalArgumentException("Email cannot be blank.");
        if(!pat.matcher(email).matches())
            throw new IllegalArgumentException("Invalid Email format.");
    }

    //SEGUNDO A INTERNET ACHO QUE SÃO 6 DIGITS, MAS NAS PERGUNTAS ALGUÉM DISSE 4 E O CLIENTE NÃO CORRIGIU

    /**
     * Checks if the SOC code is valid:
     * - the SOC code cannot be blank;
     * - the SOC code must have 4 digits;
     * - the SOC code must only have numbers.
     *
     * @param socCode the employee SOC code.
     */
    private void checkSocCodeRules(String socCode){
        if (StringUtils.isBlank(socCode))
            throw new IllegalArgumentException("SOC Code cannot be blank.");
        if (socCode.length()!=4)
            throw new IllegalArgumentException("SOC Code must have 4 digits.");
        if (!socCode.matches("[0-9]+"))
            throw new IllegalArgumentException("SOC Code must only have numbers.");
    }



    /**
     * Gets the employee name.
     *
     * @return the employee name.
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the employee email.
     *
     * @return the employee email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the employee role.
     *
     * @return the employee role.
     */
    public OrgRole getRole() {
        return role;
    }

    /**
     * Returns the textual description of the employee instance.
     *
     * @return characteristics of the employee.
     */
    @Override
    public String toString() {
        return String.format("EMPLOYEE%n%s%nEmployee ID: %s%nName: %s%nAddress: %s%n" +
                "Phone Number: %s%nE-mail: %10s%nStandard Occupational Classification (SOC) Code: %s%n",
                role, employeeID, name, address, phoneNumber, email, socCode);
    }

    /**
     * Compares two employees with each other.
     *
     * @param otherObject the employee to be compared to.
     *
     * @return true if the two employees are equal,
     * otherwise it returns false.
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject)
            return true;

        if(otherObject == null || this.getClass() != otherObject.getClass())
            return false;

        Employee otherEmployee = (Employee) otherObject;

        return  this.name.equalsIgnoreCase(otherEmployee.name) &&
                this.address.equalsIgnoreCase(otherEmployee.address) &&
                this.phoneNumber.equalsIgnoreCase(otherEmployee.phoneNumber) &&
                this.email.equalsIgnoreCase(otherEmployee.email) &&
                this.socCode.equalsIgnoreCase(otherEmployee.socCode) &&
                this.role.equals(otherEmployee.role);
    }
}
