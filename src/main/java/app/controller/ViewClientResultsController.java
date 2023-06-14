package app.controller;

import app.domain.model.Client;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.ClientStore;
import app.domain.store.TestStore;
import app.mappers.TestMapper;
import app.mappers.dto.TestDTO;

import java.util.*;

/**
 * @author Marta Ribeiro 1201592
 */
public class ViewClientResultsController {

    /**
     * The company associated to the Controller.
     */
    private Company company;

    /**
     * Builds an empty constructor for having the actual instance of the company when instantiated.
     */
    public ViewClientResultsController(){
        this(App.getInstance().getCompany());
    }

    /**
     * Builds a View Client Results Controller's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public ViewClientResultsController(Company company){
        this.company = company;
    }

    /**
     * Retrieves list of client tests with results, ordered by date
     * @param email the client email
     * @return list of client tests with results
     */
    public List<TestDTO> getClientTestsWithResults(String email){
        ClientStore clientStore = this.company.getClientStore();
        Client client = clientStore.getClientByEmail(email);
        TestStore tstStore = this.company.getTestStore();
        List<Test> clientTests = tstStore.getTestsByClient(client);
        ArrayList<Test> desiredList = new ArrayList<>();
        for (Test test : clientTests){
            if (test.hasSamplesAnalysed()){
                desiredList.add(test);
            }
        }

        desiredList.sort(new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                return Long.compare(o1.getDateOfTestRegistration().getTime(), o2.getDateOfTestRegistration().getTime());
            }
        });

        TestMapper mapper = new TestMapper();
        return mapper.toDTO(desiredList);
    }

}
