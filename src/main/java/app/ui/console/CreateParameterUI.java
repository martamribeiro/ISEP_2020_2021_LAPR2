package app.ui.console;

import app.controller.CreateParameterController;
import app.mappers.dto.CategoriesDTO;
import app.ui.console.utils.OurUtils;
import app.ui.console.utils.Utils;
import java.util.List;

public class CreateParameterUI implements Runnable {

    private CreateParameterController ctrl;

    public CreateParameterUI() {
        ctrl = new CreateParameterController();
    }

    @Override
    public void run() {
        boolean success;
        boolean confirm;
        List<String> menu = OurUtils.menuToContinueOrCancel();

        do {
            System.out.println("To create a new Parameter, please insert the requested data.");
            do {
                int index = Utils.showAndSelectIndex(menu, ""); //shows parameter categories list and asks to select one
                success = (index == -1) ? true : createParameter();
            } while (!success);
            confirm = Utils.confirm("Do you intend to create more parameters?\n[Type 's' for yes or 'n' for no.]");
        } while (confirm);
        System.out.println("Parameters successfully created!");
    }

    private boolean createParameter() {
        boolean confirmation;
        boolean success;
        try {
            String parameterCode = Utils.readLineFromConsole("Enter parameter code: ");
            String shortName = Utils.readLineFromConsole("Enter parameter name: ");
            String description = Utils.readLineFromConsole("Enter parameter description: ");
            CategoriesDTO category = showListAndSelectOneObject(); //vai buscar a categoria pretendida
            if(category == null)
                throw new IllegalArgumentException("Operation canceled!");
            ctrl.createParameter(parameterCode, shortName, description, category.getCode()); //US10 SD: 19 a 25
            confirmation = Utils.confirm(String.format(">> PARAMETER <<" +
                            "%nPlease confirm the following data:" +
                            "%n> Parameter code: %s;" +
                            "%n> Name: %s;" +
                            "%n> Description: %s;" +
                            "%n> Desired parameter category code: %s." +
                            "%n[Type 's' for correct or 'n' for wrong.]",
                    parameterCode, shortName, description, category.getCode()));
            if (!confirmation) throw new Exception("Please enter the correct data.");
            success = ctrl.saveParameter();
            if (!success) throw new Exception("Parameter either already exists or is null. Please try again.");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            success = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            success = false;
        }
        return success;
    }

    private CategoriesDTO showListAndSelectOneObject() {
        List<CategoriesDTO> categoriesDTO = ctrl.getParameterCategoriesDTO();
        CategoriesDTO category = (CategoriesDTO) Utils.showAndSelectOne(categoriesDTO, "\nEnter the number of the category you want to associate with the parameter: ");
        return category;
    }

}