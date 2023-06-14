package app.domain.store;

import app.domain.model.Client;
import app.domain.shared.utils.PasswordUtils;
import app.mappers.dto.ClientDTO;
import auth.AuthFacade;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Class for storing Clients and its needed methods
 *
 * @author João Wolff and Alexandre Dias
 */
public class ClientStore implements Serializable {

    private AuthFacade auth;

    public ClientStore(AuthFacade auth){
        this.auth = auth;
    }

    public ClientStore(){
        this.auth = new AuthFacade();
    }

    /**
     * List of clients
     */
    private ArrayList<Client> clientList = new ArrayList<>();

    /**
     * Creates an client instance with all arguments
     * @param clientsCitizenCardNumber clients Citizen Card Number.
     * @param nhsNumber                clients NHS Number.
     * @param birthDate                clients Birth Date
     * @param sex                      clients Sex.
     * @param tinNumber                clients TIN Number.
     * @param email                    clients E-mail.
     * @param name                     clients Name.
     * @param phoneNumber              clients Phone Number.
     * @return the created client
     */
    public Client registerClient(String clientsCitizenCardNumber, String nhsNumber, Date birthDate, String sex,
                                                  String tinNumber, String email, String name, String phoneNumber) {
        return new Client(clientsCitizenCardNumber, nhsNumber, birthDate, sex,
                tinNumber, email, name, phoneNumber);
    }


    public Client registerClient(ClientDTO clientDTO) {
        return new Client(clientDTO.getClientsCitizenCardNumber(),clientDTO.getNhsNumber(), clientDTO.getBirthDate(),
                clientDTO.getTinNumber(), clientDTO.getEmail(), clientDTO.getName(), clientDTO.getPhoneNumber());
    }

    /**
     * Validates a client relatively to the store, namely if it already exists or if it is null
     * @param cl Client to be validates
     * @return True if it is not null and not already in the list and false the otherway
     */
    public boolean validateClient(Client cl) {
        if (cl == null)
            return false;
        if(auth.existsUser(cl.getEmail()))
            throw new IllegalArgumentException("Client's email already registered in the system.: " + cl.getEmail());
        return !this.clientList.contains(cl);
    }

    /**
     * Saves the client in the clients list of the store
     * @param cl Client to be saved
     * @return True if it passes the validation and gets added, false the otherway
     */
    public boolean saveClient(Client cl) {
        if (!validateClient(cl))
            return false;
        String generatedPassword = PasswordUtils.generateRandomPassword();
        if(this.clientList.add(cl)) {
            return makeClientAnUserAndSendPassword(cl.getName(),cl.getEmail(), generatedPassword);
        }
        return false;
    }

    /**
     * Makes an client and user of the system and writes its generated password to a file.
     *
     * @return true if success and false if fails.
     * @throws IOException if cannot write into the file.
     */
    public boolean makeClientAnUserAndSendPassword(String name, String email, String pwd) {
        if (makeClientAnUser(name, email, pwd))
            try {
                return PasswordUtils.writePassword(pwd, email);
            }catch (IOException e){
                e.printStackTrace();
            }
        return false;
    }


    /**
     * Makes the client an user of the system
     *
     * @return true if success and false if fails.
     */
    private boolean makeClientAnUser(String name,String email, String pwd) {
        return auth.addUserWithRole(name, email, pwd, "CLIENT");
    }

    public Client getClientByTinNumber(String tinNumber){
        for (Client client : clientList){
            if(client.getTinNumber().equals(tinNumber)){
                return client;
            }
        }
        throw new UnsupportedOperationException("There are no client registered with given TIN number: " + tinNumber);
    }

    public Client getClientByEmail(String email){
        for (Client client : clientList){
            if(client.getEmail().equals(email)){
                return client;
            }
        }
        throw new UnsupportedOperationException("There are no client registered with given email: " + email);
    }

    public List<Client> getClients() {
        return new ArrayList<>(clientList);
    }

}
