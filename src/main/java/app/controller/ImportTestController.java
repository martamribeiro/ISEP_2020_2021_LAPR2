package app.controller;

import app.domain.model.Company;
import app.mappers.dto.TestFileDTO;
import net.sourceforge.barbecue.BarcodeException;
/**
 * Controller class to coordenate the import of a file of tests
 *
 * @author João Wolff
 */
public class ImportTestController {

    /**
     * Company instance of the session
     */
    private final Company company;

    /**
     * Controller for creating the test instances
     */
    private CreateFullTestController testController;

    /**
     * Controller for registering a client if needed
     */
    private RegisterClientController clientController;

    /**
     * Empty constructor for having the actual instance of the company when instantiated.
     */
    public ImportTestController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Construtor recieving the company as an argument
     *
     * @param company instance of company to be used
     */
    public ImportTestController(Company company) {
        this.company = company;
        this.clientController = new RegisterClientController();
        this.testController = new CreateFullTestController();
    }

    /**
     * Method for importing a test of a testFileDto
     * @param testFileDTO testfile dto that contains all needed data for creating an instance of test
     * @return true if successfully imports the test and false other wise
     * @throws ClassNotFoundException if the class name of the external API is not found
     * @throws InstantiationException if the class object of the external API cannot be instantiated
     * @throws IllegalAccessException if there's a method invoked does not have access to the class representing the API
     * @throws BarcodeException  if the data to be encoded in the barcode is invalid
     */
    public boolean importTestFromFile(TestFileDTO testFileDTO) throws IllegalAccessException, ClassNotFoundException, InstantiationException, BarcodeException {
        boolean existsClient = clientController.registerClient(testFileDTO.getClientDTO());
        if(existsClient)
            clientController.saveClient();
        testController.createTest(testFileDTO);
        return testController.saveTest();
    }

}
