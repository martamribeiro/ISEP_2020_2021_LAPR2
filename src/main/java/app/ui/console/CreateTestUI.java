package app.ui.console;

import app.controller.CreateTestController;
import app.mappers.dto.CategoriesDTO;
import app.mappers.dto.ParameterDTO;
import app.mappers.dto.TestTypeDTO;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CreateTestUI implements Runnable{
    private CreateTestController ctrl;

    private String selectedCal;

    public CreateTestUI(String selectedCal)
    {
        ctrl = new CreateTestController();
        this.selectedCal = selectedCal;
    }

    @Override
    public void run() {
        boolean success;
        System.out.println("To create a new Test, please insert the requested data.");
        List<String> menu = menuToContinueOrCancel();
        do{
            int index = Utils.showAndSelectIndex(menu, "");
            success = (index == -1) ? true : createTest();
        }while (!success);
        System.out.println("\nTest successfully created!");
    }

    private boolean createTest(){
        boolean success = false;
        boolean confirm;
        try {
            String tinNumber = Utils.readLineFromConsole("Enter client's TIN number: ");
            String nhsCode = Utils.readLineFromConsole("Enter test NHS code: ");
            TestTypeDTO testTypeDTO = (TestTypeDTO) Utils.showAndSelectOne(ctrl.getTestTypesDTO(selectedCal), "Please select the type of test to be performed: ");
            List<String> categoriesCodes = getCategoriesToCodes(testTypeDTO.getCode());
            List<String> selectedParameters = getParametersToCode(categoriesCodes);
            ctrl.createTest(nhsCode, tinNumber, testTypeDTO.getCode(), selectedParameters);
            confirm = Utils.confirm(String.format("Please confirm the data (type `s` if its correct, `n` if it is not):" +
                    "%n TIN number: %s%n NHS code: %s%n Type of test: %s%n codes of the selected parameters: %s%n", tinNumber, nhsCode, testTypeDTO, selectedParameters));
            if(!confirm) throw new Exception("Please enter the correct data");
            success = ctrl.saveTest();
            if(!success) throw new Exception("Test either already existent or null, please try again");
        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            success = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            success = false;
        }
        return success;
    }

    private List<CategoriesDTO> showListAndSelectObjects (List<CategoriesDTO> parameterCategories, List<CategoriesDTO> categoriesDTO){
        CategoriesDTO categoryToAdd = (CategoriesDTO) Utils.showAndSelectOne(categoriesDTO,
                "Please type the number of the category you want to associate to the test :");
        if(categoryToAdd != null)
            parameterCategories.add(categoryToAdd);
        else
            throw new IllegalArgumentException("Operation canceled!");
        boolean addMore = Utils.confirm("If you want to add more categories please type 's', otherwise type 'n'");
        return addMore ? showListAndSelectObjects(parameterCategories, categoriesDTO) : parameterCategories;
    }

    private List<String> getCategoriesToCodes (String selectedTestTypeCode){
        List<CategoriesDTO> parameterCategories = new ArrayList<>();
        parameterCategories = showListAndSelectObjects(parameterCategories, ctrl.getCategoriesListOfTestTypeDTO(selectedTestTypeCode));
        List<String> parameterCategoriesCodes = new ArrayList<>();
        for(CategoriesDTO p: parameterCategories){
            if(!parameterCategoriesCodes.contains(p.getCode()))
                parameterCategoriesCodes.add(p.getCode());
        }
        return parameterCategoriesCodes;
    }

    private List<ParameterDTO> showParamsListToSelect (List<ParameterDTO> parameters, List<ParameterDTO> parameterDTO){
        ParameterDTO categoryToAdd = (ParameterDTO) Utils.showAndSelectOne(parameterDTO,
                "Please type the number of the parameter you want to associate to the test :");
        if(categoryToAdd != null)
            parameters.add(categoryToAdd);
        else
            throw new IllegalArgumentException("Operation canceled!");
        boolean addMore = Utils.confirm("If you want to add more parameters please type 's', otherwise type 'n'");
        return addMore ? showParamsListToSelect(parameters, parameterDTO) : parameters;
    }

    private List<String> getParametersToCode (List<String> selectedCategories){
        List<ParameterDTO> selectedParameters = new ArrayList<>();
        selectedParameters = showParamsListToSelect(selectedParameters, ctrl.getParametersOfCategoriesDTO(selectedCategories));
        List<String> parameterCategoriesCodes = new ArrayList<>();
        for(ParameterDTO p: selectedParameters){
            if(!parameterCategoriesCodes.contains(p.getParameterCode()))
                parameterCategoriesCodes.add(p.getParameterCode());
        }
        return parameterCategoriesCodes;
    }



    private List<String> menuToContinueOrCancel() {
        List<String> menu = new ArrayList<>();
        menu.add("Insert the data");

        return menu;
    }
}
