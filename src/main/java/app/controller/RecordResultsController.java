package app.controller;

import app.domain.model.Company;
import app.domain.model.Parameter;
import app.domain.model.Test;
import app.domain.model.TestParameterResult;
import app.domain.store.TestStore;
import app.mappers.ParameterMapper;
import app.mappers.dto.ParameterDTO;

import java.util.List;

/**
 * Class of the mvc controller of the functionality of recording results of a test.
 */
public class RecordResultsController {

    //addTestResult(parameterCode, result, metric)

    /**
     * The company associated to the Controller
     */
    private Company company;

    /**
     * The test parameter result associated to the Controller.
     */
    private TestParameterResult result;

    private Test test;

    /**
     * Builds an empty constructor for having the actual instance of the company when instanciated.
     */
    public RecordResultsController(){
        this(App.getInstance().getCompany());
    }

    /**
     * Builds a Result Controller's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public RecordResultsController(Company company){
        this.company = company;
        this.result = null;
    }


    /**
     * Gets all the parameters of a specific Test by the barcode number of it's sample
     * @param barcodeNumber barcode of the sample of a test to be searched
     * @return lsit of Dto of the parameters of the found test
     */
    public List<ParameterDTO> getTotalTestParameters(String barcodeNumber) {
        TestStore testStore = this.company.getTestStore();
        this.test = testStore.getTestByBarcodeNumber(barcodeNumber);
        if(!this.test.hasSamplesAnalysed()) {
            List<Parameter> listTotalTestParameters = testStore.getTotalTestParameters(test);
            ParameterMapper mapper = new ParameterMapper();
            return mapper.toDTO(listTotalTestParameters);
        }else
            throw new UnsupportedOperationException("Searched test already has results of samples!");
    }

    /**
     * Method for adding a test result to the current test
     * @param parameterCode code of the parameter to being analysed
     * @param result result of the analysis
     * @param metric metric being used in the analysis
     * @throws IllegalAccessException if there's a method invoked does not have access to the class representing the API
     * @throws ClassNotFoundException if the class name of the external API is not found
     * @throws InstantiationException if the class object of the external API cannot be instantiated
     */
    public void addTestResult(String parameterCode, Double result, String metric) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        this.test.addTestResult(parameterCode, result, metric);
    }

    /**
     * Method for adding the date of the chemical analysis after adding all the results of a test.
     */
    public void addChemicalAnalysisDate() {
        this.test.addChemicalAnalysisDate();
    }


}
