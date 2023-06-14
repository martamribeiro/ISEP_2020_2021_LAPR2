package app.ui.console;

import app.controller.CreateParameterCategoryController;
import app.ui.console.utils.OurUtils;
import app.ui.console.utils.Utils;

import java.util.List;

public class ParameterCategoryUI implements Runnable{
    CreateParameterCategoryController crtl;

    public ParameterCategoryUI(){
        crtl = new CreateParameterCategoryController();
    };

    @Override
    public void run() {
        boolean success;
        List<String> menu = OurUtils.menuToContinueOrCancel();

        System.out.println("To create a new Parameter Category, please insert the requested data.");
        do{
            int index = Utils.showAndSelectIndex(menu, "");
            success = (index == -1) ? true : createParameterCategory();
        }while (!success);

    }

    private boolean createParameterCategory (){
        boolean success;
        boolean confirm;
        try {
            String code = Utils.readLineFromConsole("Enter category code ");
            String name = Utils.readLineFromConsole("Enter category name: ");
            crtl.createParameterCategory(code, name);
            confirm = Utils.confirm(String.format("Please confirm the data (type `s` if its correct, `n` if it is not):%nName: %s%nCode: %s%n", name, code));
            if(!confirm) throw new Exception("Please, insert again the data you wish.");
            success = crtl.saveParameterCategory();
            if(!success)
                throw new Exception("Parameter Category either already existent or null, please try again");
            else
                System.out.println("\nParameter category successfully created!");
        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            success = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            success = false;
        }
        return success;
    }
}
