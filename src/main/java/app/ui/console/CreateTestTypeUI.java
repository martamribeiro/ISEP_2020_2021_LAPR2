package app.ui.console;

import app.controller.CreateTestTypeController;
import app.domain.shared.Constants;
import app.mappers.dto.CategoriesDTO;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CreateTestTypeUI implements Runnable{
    private CreateTestTypeController ctrl;

    public CreateTestTypeUI()
    {
        ctrl = new CreateTestTypeController();
    }

    @Override
    public void run() {
        boolean success;
        System.out.println("To create a new Test Type, please insert the requested data.");
        List<String> menu = menuToContinueOrCancel();
        do{
            int index = Utils.showAndSelectIndex(menu, "");
            success = (index == -1) ? true : createTestType();
        }while (!success);
        System.out.println("\nTest type successfully created!");
    }

    private boolean createTestType(){
        boolean success = false;
        boolean confirm;
        try {
            String code = Utils.readLineFromConsole("Enter code ");
            String description = Utils.readLineFromConsole("Enter description: ");
            String collectingMethod = Utils.readLineFromConsole("Enter collecting method: ");
            List<String> categoriesCodes = getCategoriesToCodes();
            String externalModuleName = getExternalModuleName();
            ctrl.createTestType(code, description, collectingMethod, categoriesCodes, externalModuleName);
            confirm = Utils.confirm(String.format("Please confirm the data (type `s` if its correct, `n` if it is not):" +
                    "%n code: %s%n description: %s%n collecting method: %s%n codes of the categories: %s%n selected module: %s%n",
                        code, description, collectingMethod, categoriesCodes, externalModuleName));
            if(!confirm) throw new Exception("Please enter the correct data");
            success = ctrl.saveTestType();
            if(!success) throw new Exception("Test type either already existent or null, please try again");
        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            success = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            success = false;
        }
        return success;
    }

    private List<CategoriesDTO> showListAndSelectObjects (List<CategoriesDTO> parameterCategories){
        List<CategoriesDTO> categoriesDTO =  ctrl.getParameterCategoriesDTO();
        CategoriesDTO categoryToAdd = (CategoriesDTO) Utils.showAndSelectOne(categoriesDTO,
                "Please type the number of the category you want to associate to the test type:");
        if(categoryToAdd != null)
            parameterCategories.add(categoryToAdd);
        else
            throw new IllegalArgumentException("Operation canceled!");
        boolean addMore = Utils.confirm("If you want to add more categories please type 's', otherwise type 'n'");
        return addMore ? showListAndSelectObjects(parameterCategories) : parameterCategories;
    }

    private List<String> getCategoriesToCodes (){
        List<CategoriesDTO> parameterCategories = new ArrayList<>();
        parameterCategories = showListAndSelectObjects(parameterCategories);
        List<String> parameterCategoriesCodes = new ArrayList<>();
        for(CategoriesDTO p: parameterCategories){
            if(!parameterCategoriesCodes.contains(p.getCode()))
                parameterCategoriesCodes.add(p.getCode());
        }
        return parameterCategoriesCodes;
    }

    private String getExternalModuleName(){
        List<String> externalModulesList = new ArrayList<>();
        externalModulesList.add(Constants.COVID_MODULE_NAME);
        externalModulesList.add(Constants.BLOOD_MODULE_1_NAME);
        externalModulesList.add(Constants.BLOOD_MODULE_2_NAME);

        return (String) Utils.showAndSelectOne(externalModulesList, "Please select the module that should be used to analyse this type of test's parameters: ");
    }


    private List<String> menuToContinueOrCancel() {
        List<String> menu = new ArrayList<>();
        menu.add("Insert the data");

        return menu;
    }
}
