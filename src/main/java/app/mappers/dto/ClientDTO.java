package app.mappers.dto;

import java.util.Date;

public class ClientDTO {
    private final static String OMITTED_SEX = "Omitted";

    private final String clientsCitizenCardNumber;

    private final String nhsNumber;

    private final Date birthDate;

    private final String sex;

    private final String tinNumber;

    private final String email;

    private final String name;

    private final String phoneNumber;

    public ClientDTO(String clientsCitizenCardNumber, String nhsNumber, Date birthDate, String sex,
                  String tinNumber, String email, String name, String phoneNumber) {

        this.clientsCitizenCardNumber = clientsCitizenCardNumber;
        this.nhsNumber = nhsNumber;
        this.birthDate = birthDate;
        this.sex = sex;
        this.tinNumber = tinNumber;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public ClientDTO(String clientsCitizenCardNumber, String nhsNumber, Date birthDate,
                  String tinNumber, String email, String name, String phoneNumber) {
        this(clientsCitizenCardNumber, nhsNumber, birthDate, OMITTED_SEX, tinNumber, email, name, phoneNumber);
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public String getClientsCitizenCardNumber() {
        return clientsCitizenCardNumber;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("Client: %s%nTin: %s%nEmail: %s%n", name, tinNumber, email);
    }
}
