package app.controller;

import app.domain.interfaces.SortAlgorithm;
import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.shared.Constants;
import app.domain.sort.comparators.alphabeticalNameClient;
import app.domain.sort.comparators.ascendTinClient;
import app.domain.store.TestStore;
import app.mappers.ClientsMapper;
import app.mappers.TestMapper;
import app.mappers.dto.ClientDTO;
import app.mappers.dto.TestDTO;

import java.util.List;


/**
 * Controller for consulting validated tests historic of clients
 *
 * @author João Wolff
 */
public class ConsultTestByClient {
    /**
     * The company associated to the Controller.
     */
    private Company company;

    /**
     * Builds a Record Samples Controller's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public ConsultTestByClient(Company company) {
        this.company = company;
    }

    /**
     * Empty constructor which gets the company by the App instance.
     */
    public ConsultTestByClient(){
        this(App.getInstance().getCompany());
    }


    /**
     * Method for getting the clients list with validated tests stored by either tin or name ordering.
     * @param compareBy The order that should be followed
     * @return List of clients dto sorted by name or tin
     * @throws ClassNotFoundException if the class name of the external API is not found
     * @throws InstantiationException if the class object of the external API cannot be instantiated
     * @throws IllegalAccessException if there's a method invoked does not have access to the class representing the API
     */
    public List<ClientDTO> getClientsDtoInOrder(String compareBy) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<Client> clients = getClientsWithValidatedTests();
        SortAlgorithm sortAlgorithm = this.company.getSortAlgorithm();
        if(compareBy.equalsIgnoreCase(Constants.TIN_COMPARATOR_ID)){
            sortAlgorithm.sortClientsList(clients, new ascendTinClient());
        }else{
            sortAlgorithm.sortClientsList(clients, new alphabeticalNameClient());
        }
        return getClientsDto(clients);
    }

    /**
     * Method for getting the list of validated tests per client
     * @param clientTin tin of the client that tests should be retrieved
     * @return list of test dto with the validated tests of the client
     */
    public List<TestDTO> getValidatedTestsOfClient(String clientTin){
        TestMapper testMapper = new TestMapper();
        return testMapper.toDTO(getTestsOfClient(clientTin));
    }

    /**
     * Method for getting the list of tests of a client by its tin number
     * @param clientTin client's tin number
     * @return list of test with the tests of the client
     */
    private List<Test> getTestsOfClient(String clientTin){
        TestStore testStore =  this.company.getTestStore();
        return testStore.getValidatedTestsByClientTin(clientTin);
    }

    /**
     * Method for turning a client list into a client Dto list.
     * @param clients list of clients to be turned into a dto
     * @return list of clients dto
     */
    private List<ClientDTO> getClientsDto(List<Client> clients){
        ClientsMapper clientsMapper = new ClientsMapper();
        return clientsMapper.toDTO(clients);
    }

    /**
     * Method for getting the list of clients with validated tests.
     * @return list of clients with validated tests.
     */
    private List<Client> getClientsWithValidatedTests(){
        TestStore testStore =  this.company.getTestStore();
        return testStore.getClientsWithValidatedTests();
    }


}
