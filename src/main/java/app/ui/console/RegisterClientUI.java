package app.ui.console;

import app.controller.RegisterClientController;
import app.ui.console.utils.OurUtils;
import app.ui.console.utils.Utils;

import java.util.Date;
import java.util.List;

public class RegisterClientUI implements Runnable{

    private RegisterClientController ctrl;

    public RegisterClientUI(){
        ctrl = new RegisterClientController();
    }

    @Override
    public void run() {
        boolean success;
        List<String> menu = OurUtils.menuToContinueOrCancel();

        System.out.println("To register a new Client, please insert the requested data.");
        do{
            int index = Utils.showAndSelectIndex(menu, "");
            success = (index == -1) ? true : createClient();
        }while (!success);
    }

    private boolean createClient(){
        boolean success = false;
        boolean confirm;
        try {
            String citizenCard = Utils.readLineFromConsole("Enter your citizen card number: ");
            String nhsNumber = Utils.readLineFromConsole("Enter your nhs number: ");
            Date birthDate = Utils.readDateFromConsole("Enter your birth date in the format dd-mm-yyyy: ");
            String tinNumber = Utils.readLineFromConsole("Enter your tin number: ");
            String email = Utils.readLineFromConsole("Enter your email: ");
            String name = Utils.readLineFromConsole("Enter your name: ");
            String phoneNumber = Utils.readLineFromConsole("Enter your phoneNumber: ");
            if(Utils.confirm("Want to add client's sex? (type `s` if its correct, `n` if it is not)")) {
                String sex = Utils.readLineFromConsole("Enter your sex (Male or Female): ");
                ctrl.registerClient(citizenCard, nhsNumber, birthDate,sex, tinNumber, email, name, phoneNumber);
                confirm = Utils.confirm(String.format("Please confirm the data (type `s` if its correct, `n` if it is not):" +
                                "%n Citizen card: %s%n Nhs number: %s%n Birth date: %s%n Tin number: %s%n Email: %s%n " +
                                "Name: %s%n Phone number: %s%n Sex: %s%n",
                        citizenCard, nhsNumber, birthDate.toString(), tinNumber, email, name, phoneNumber, sex));
            }else{
                ctrl.registerClient(citizenCard, nhsNumber, birthDate, tinNumber, email, name, phoneNumber);
                confirm = Utils.confirm(String.format("Please confirm the data (type `s` if its correct, `n` if it is not):" +
                                "%nCitizen card: %s%nNhs number: %s%nBirth date: %s%nTin number: %s%nEmail: %s%nName: %s%nPhone number: %s%n",
                        citizenCard, nhsNumber, birthDate.toString(), tinNumber, email, name, phoneNumber));
            }
            if(!confirm) throw new Exception("Please, insert again the data you wish.");
            success = ctrl.saveClient();
            if(!success)
                throw new Exception("Error: Client either already existent or null, please try again");
            else
                System.out.println("\nClient successfully created!");
            success = ctrl.makeClientAnUserAndSendPassword();
            if(!success) throw new Exception("Error: Client is already an user of the system");
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
