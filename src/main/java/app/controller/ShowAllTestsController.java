package app.controller;

import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.store.TestStore;
import app.mappers.TestMapper;
import app.mappers.dto.TestDTO;

import java.util.List;

/**
 * Controller class for showing all the Tests created.
 * It was created to facilitate the Many Lab's business management.
 *
 *  @author João Wolff
 *
 */
public class ShowAllTestsController {

    /**
     * The company associated to the Controller.
     */
    private Company company;

    /**
     * Builds an empty constructor for having the actual instance of the company when instantiated.
     */
    public ShowAllTestsController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Builds a Show All Tests Controller's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public ShowAllTestsController(Company company) {
        this.company = company;
    }

    /**
     * Returns the list of the Tests available in the system
     * converted to Dto through a Mapper.
     *
     * @return the list of the Tests Dto available in the system
     */
    public List<TestDTO> getAllTests(){
        TestStore testStore = this.company.getTestStore();
        List<Test> tests = testStore.getTests();

        TestMapper testMapper = new TestMapper();
        return testMapper.toDTO(tests);
    }
}
