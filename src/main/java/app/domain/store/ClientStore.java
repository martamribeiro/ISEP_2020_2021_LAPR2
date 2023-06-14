package app.domain.store;

import app.domain.model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Class for storing Clients and its needed methods
 *
 * @author João Wolff and Alexandre Dias
 */
public class ClientStore {

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

    /**
     * Creates an client instance with all arguments but sex(optional)
     * @param clientsCitizenCardNumber clients Citizen Card Number.
     * @param nhsNumber                clients NHS Number.
     * @param birthDate                clients Birth Date
     * @param tinNumber                clients TIN Number.
     * @param email                    clients E-mail.
     * @param name                     clients Name.
     * @param phoneNumber              clients Phone Number.
     * @return the created client
     */
    public Client registerClient(String clientsCitizenCardNumber, String nhsNumber, Date birthDate,
                                                  String tinNumber, String email, String name, String phoneNumber) {
        return new Client(clientsCitizenCardNumber, nhsNumber, birthDate,
                tinNumber, email, name, phoneNumber);
    }

    /**
     * Validates a client relatively to the store, namely if it already exists or if it is null
     * @param cl Client to be validates
     * @return True if it is not null and not already in the list and false the otherway
     */
    public boolean validateClient(Client cl) {
        if (cl == null)
            return false;

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
        return this.clientList.add(cl);
    }

    public Client getClientByTinNumber(String tinNumber){
        for (Client client : clientList){
            if(client.getTinNumber().equals(tinNumber)){
                return client;
            }
        }
        throw new UnsupportedOperationException("There are no client registered with given TIN number: " + tinNumber);
    }

    public List<Client> getClients() {
        return new ArrayList<>(clientList);
    }

}
