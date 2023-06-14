package app.controller;

import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.shared.utils.PasswordUtils;
import app.domain.store.ClientStore;
import auth.AuthFacade;

import java.io.IOException;
import java.util.Date;

/**
 * Controller class for creating Clients and turning them into users of the system
 *
 * @author Alexandre Dias and João Wolff
 */
public class RegisterClientController {

    /**
     * Company instance of the session
     */
    private final Company company;

    /**
     * Client to be regitered
     */
    private Client cl;

    /**
     * Password to be generated
     */
    private String generatedPassword;

    /**
     * Empty constructor for having the actual instance of the company when instantiated.
     */
    public RegisterClientController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Construtor recieving the company as an argument
     *
     * @param application company instance
     */
    public RegisterClientController(Company application) {
        this.company = application;
        this.cl = null;
    }

    /**
     * Creates an client instance with all arguments
     *
     * @param clientsCitizenCardNumber clients Citizen Card Number.
     * @param nhsNumber                clients NHS Number.
     * @param birthDate                clients Birth Date
     * @param sex                      clients Sex.
     * @param tinNumber                clients TIN Number.
     * @param email                    clients E-mail.
     * @param name                     clients Name.
     * @param phoneNumber              clients Phone Number.
     * @return true if success, false if fails
     */
    public boolean registerClient(String clientsCitizenCardNumber, String nhsNumber, Date birthDate, String sex,
                                  String tinNumber, String email, String name, String phoneNumber) {
        ClientStore store = this.company.getClientStore();
        this.cl = store.registerClient(clientsCitizenCardNumber, nhsNumber, birthDate, sex,
                tinNumber, email, name, phoneNumber);
        return store.validateClient(cl);
    }

    /**
     * Creates an client instance with all arguments but sex(optional)
     *
     * @param clientsCitizenCardNumber clients Citizen Card Number.
     * @param nhsNumber                clients NHS Number.
     * @param birthDate                clients Birth Date
     * @param tinNumber                clients TIN Number.
     * @param email                    clients E-mail.
     * @param name                     clients Name.
     * @param phoneNumber              clients Phone Number.
     * @return true if success, false if fails
     */
    public boolean registerClient(String clientsCitizenCardNumber, String nhsNumber, Date birthDate,
                                  String tinNumber, String email, String name, String phoneNumber) {
        ClientStore store = this.company.getClientStore();
        this.cl = store.registerClient(clientsCitizenCardNumber, nhsNumber, birthDate,
                tinNumber, email, name, phoneNumber);
        return store.validateClient(cl);
    }

    /**
     * Saves the client in the store list
     *
     * @return true if success, false if fails
     */
    public boolean saveClient() {
        ClientStore store = this.company.getClientStore();
        return store.saveClient(cl);
    }

    /**
     * Makes an client and user of the system and writes its generated password to a file.
     *
     * @return true if success and false if fails.
     * @throws IOException if cannot write into the file.
     */
    public boolean makeClientAnUserAndSendPassword() throws IOException {
        if (makeClientAnUser())
            return PasswordUtils.writePassword(generatedPassword, cl.getEmail());
        return false;
    }

    /**
     * Makes the client an user of the system
     *
     * @return true if success and false if fails.
     */
    private boolean makeClientAnUser() {
        this.generatedPassword = PasswordUtils.generateRandomPassword();
        AuthFacade authFacade = this.company.getAuthFacade();
        return authFacade.addUser(cl.getName(), cl.getEmail(), generatedPassword);
    }

    public Client getClient() {
        return cl;
    }

}
