package app.domain.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class to instantiate a new client of the campany
 *
 * @author Alexandre Dias and João Wolff
 */
public class Client {

    /**
     * Validation constants
     */
    private final static int MAX_AGE = 150;
    private final static int PHONE_NUMBER_LENGHT = 11;
    private final static int CITIZEN_CARD_LENGTH = 16;
    private final static int NHS_AND_TIN_LENGTH = 10;
    private final static int NAME_LENGTH = 35;

    /**
     * Sex by omission
     */

    private final static String OMITTED_SEX = "Omitted";
    /**
     * The clients Citizen Card Number.
     */
    private final String clientsCitizenCardNumber;

    /**
     * The clients NHS Number.
     */
    private final String nhsNumber;

    /**
     * The clients Birth Date
     */
    private final Date birthDate;

    /**
     * The clients Sex.
     */
    private final String sex;

    /**
     * The clients TIN Number.
     */
    private final String tinNumber;

    /**
     * The clients E-mail.
     */
    private final String email;

    /**
     * The clients Name.
     */
    private final String name;

    /**
     * The clients Phone Number.
     */
    private final String phoneNumber;

    /**
     * Constructs an instance of Client receiving as a parameter the clients Citizen Card Number, NHS Number, Birth Date, Sex, TIN Number, E-mail, Name and Phone Number.
     *
     * @param clientsCitizenCardNumber clients Citizen Card Number.
     * @param nhsNumber                clients NHS Number.
     * @param birthDate                clients Birth Date
     * @param sex                      clients Sex.
     * @param tinNumber                clients TIN Number.
     * @param email                    clients E-mail.
     * @param name                     clients Name.
     * @param phoneNumber              clients Phone Number.
     */
    public Client(String clientsCitizenCardNumber, String nhsNumber, Date birthDate, String sex,
                  String tinNumber, String email, String name, String phoneNumber) {

        checkClientsCitizenCardNumber(clientsCitizenCardNumber);
        checknhsNumber(nhsNumber);
        checkSex(sex);
        checkTinNumber(tinNumber);
        checkEmail(email);
        checkName(name);
        checkPhoneNumber(phoneNumber);
        checkBirthDate(birthDate);

        this.clientsCitizenCardNumber = clientsCitizenCardNumber;
        this.nhsNumber = nhsNumber;
        this.birthDate = birthDate;
        this.sex = sex;
        this.tinNumber = tinNumber;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs an instance of Client receiving as a parameter the clients Citizen Card Number, NHS Number, Birth Date, Sex, TIN Number, E-mail, and Name.
     *
     * @param clientsCitizenCardNumber clients Citizen Card Number.
     * @param nhsNumber                clients NHS Number.
     * @param birthDate                clients Birth Date
     * @param tinNumber                clients TIN Number.
     * @param email                    clients E-mail.
     * @param name                     clients Name.
     * @param phoneNumber              clients Phone Number.
     */
    public Client(String clientsCitizenCardNumber, String nhsNumber, Date birthDate,
                  String tinNumber, String email, String name, String phoneNumber) {
        this(clientsCitizenCardNumber, nhsNumber, birthDate, OMITTED_SEX, tinNumber, email, name, phoneNumber);
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    /**
     * Checks if the Clients citizan card number is correct, and if not throws an error message
     *
     * @param clientsCitizenCardNumber citizen card number of client
     */
    private void checkClientsCitizenCardNumber(String clientsCitizenCardNumber) {
        if (StringUtils.isBlank(clientsCitizenCardNumber))
            throw new IllegalArgumentException("Clients Citizen Card Number cannot be blank");
        if ((!clientsCitizenCardNumber.matches("[0-9]+")))
            throw new IllegalArgumentException("Clients Citizen Card Number must be only digits");
        if ((clientsCitizenCardNumber).length() != CITIZEN_CARD_LENGTH)
            throw new IllegalArgumentException("Clients Citizen Card Number must be 16 digits");
    }

    /**
     * Checks if the NHS number is correct, and if not throws an error message
     *
     * @param nhsNumber clients NHS Number.
     */
    private void checknhsNumber(String nhsNumber) {
        if (StringUtils.isBlank(nhsNumber))
            throw new IllegalArgumentException("NHS number cannot be blank");
        if ((!nhsNumber.matches("[0-9]+")))
            throw new IllegalArgumentException("NHS Number must be only digits");
        if ((nhsNumber).length() != NHS_AND_TIN_LENGTH)
            throw new IllegalArgumentException("NHS number must be 10 digits");
    }

    /**
     * Checks if the Birth Date is correct, and if not throws an error message
     *
     * @param birthDate clients Birth Date
     */
    private void checkBirthDate(Date birthDate) {

        if (birthDate == null)
            throw new IllegalArgumentException("Birth Date cannot be blank!");
        if (getYearsDiff(birthDate) > MAX_AGE) {
            throw new IllegalArgumentException("Invalid birth date, the max age is 150 years!");
        }
    }

    public int getYearsDiff(Date birthDate) {
        Date today = new Date();
        long diffInTime = today.getTime() - birthDate.getTime();
        long difference_In_Years
                = (diffInTime
                / (1000L * 60 * 60 * 24 * 365));
        return (int) difference_In_Years;
    }

    /**
     * Checks if the Sex is correct, and if not throws an error message
     *
     * @param sex clients Sex
     */
    private void checkSex(String sex) {
        if (StringUtils.isBlank(sex))
            throw new IllegalArgumentException("Sex cannot be blank");
        if (!sex.equalsIgnoreCase("Male") && !sex.equalsIgnoreCase("Female") && !sex.equals(OMITTED_SEX))
            throw new IllegalArgumentException("Sex must be Male or Female");
    }

    /**
     * Checks if the Tin number is correct, and if not throws an error message
     *
     * @param tinNumber clients TIN Number.
     */
    private void checkTinNumber(String tinNumber) {
        if (StringUtils.isBlank(tinNumber))
            throw new IllegalArgumentException("TIN number cannot be blank");
        if ((!tinNumber.matches("[0-9]+")))
            throw new IllegalArgumentException("TIN Number must be only digits");
        if ((tinNumber).length() != NHS_AND_TIN_LENGTH)
            throw new IllegalArgumentException("TIN number must be 10 digits");

        // falta para o caso de ele por letras e nao numeros - tem de dar erro

    }

    /**
     * Checks if the E-mail is correct, and if not throws an error message
     *
     * @param email clients email
     */
    private void checkEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        if (StringUtils.isBlank(email))
            throw new IllegalArgumentException("Email cannot be blank");
        if (!pat.matcher(email).matches())
            throw new IllegalArgumentException("Invalid Email format");

    }

    /**
     * Checks if the Name is correct, and if not throws an error message
     *
     * @param name clients name
     */
    private void checkName(String name) {
        if (StringUtils.isBlank(name))
            throw new IllegalArgumentException("Name cannot be blank");
        if (name.length() > NAME_LENGTH)
            throw new IllegalArgumentException("Name cannot have more then 35 characteres");
    }

    /**
     * Checks if the Phone number is correct, and if not throws an error message
     *
     * @param phoneNumber clients phone number
     */
    private void checkPhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber))
            throw new IllegalArgumentException("Phone number cannot be blank");
        if ((!phoneNumber.matches("[0-9]+")))
            throw new IllegalArgumentException("phone number must be only digits");
        if ((phoneNumber).length() != PHONE_NUMBER_LENGHT)
            throw new IllegalArgumentException("Phone number must be 11 digits");
    }

    /**
     * Returns the textual description of the Client
     *
     * @return Client characteristics
     */
    @Override
    public String toString() {
        return "Client{" +
                "clientsCitizenCardNumber='" + clientsCitizenCardNumber + '\'' +
                ", nhsNumber='" + nhsNumber + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", sex='" + sex + '\'' +
                ", tinNumber='" + tinNumber + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;

        return Objects.equals(email, client.email) ||
                Objects.equals(phoneNumber, client.phoneNumber) ||
                Objects.equals(tinNumber, client.tinNumber) ||
                Objects.equals(clientsCitizenCardNumber, client.clientsCitizenCardNumber) ||
                Objects.equals(nhsNumber, client.nhsNumber);
    }


}
