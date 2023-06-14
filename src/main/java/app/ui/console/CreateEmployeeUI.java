package app.ui.console;

import app.controller.RegisterEmployeeController;
import app.domain.shared.Constants;
import app.mappers.dto.EmployeeDTO;
import app.mappers.dto.SpecialistDoctorDTO;
import app.ui.console.utils.OurUtils;
import app.ui.console.utils.Utils;

import java.util.List;

public class CreateEmployeeUI implements Runnable{

    private RegisterEmployeeController ctrl;

    public CreateEmployeeUI (){
        ctrl = new RegisterEmployeeController();
    }

    @Override
    public void run() {
        boolean success;
        List<String> menu = OurUtils.menuToContinueOrCancel();

        System.out.println("To register a new Employee, please insert the requested data.");
        do{
            int index = Utils.showAndSelectIndex(menu, "");
            success = (index == -1) ? true : createEmployee();
        }while (!success);
        System.out.println("\nOperation finished!");
    }

    private boolean createEmployee(){
        boolean success = false;
        boolean confirm;
        try {
            System.out.println("Available organization roles: ");
            ctrl.getRoles().forEach(role -> System.out.println(role));
            String roleDesignation = Utils.readLineFromConsole("Please type in the name of the organization role: ");
            if(roleDesignation.equals("0"))
                success = true;

            String name = Utils.readLineFromConsole("Enter name: ");
            String address = Utils.readLineFromConsole("Enter address: ");
            String phoneNumber = Utils.readLineFromConsole("Enter phoneNumber: ");
            String email = Utils.readLineFromConsole("Enter email: ");
            String socNumber = Utils.readLineFromConsole("Enter the SOC number: ");

            if(roleDesignation.equalsIgnoreCase(Constants.ROLE_SPECIALIST_DOCTOR)) {
                String doctorIndexNumber = Utils.readLineFromConsole("Enter your doctorIndexNumber: ");
                SpecialistDoctorDTO specDto = new SpecialistDoctorDTO(roleDesignation, name, address, phoneNumber, email, socNumber, doctorIndexNumber);
                ctrl.createEmployee(specDto);
                confirm = Utils.confirm(String.format("Please confirm the data (type `s` if its correct, `n` if it is not):" +
                                "%nOrganization role: %s%nName: %s%nAddress: %s%nPhone Number: %s%nEmail: %s%n" +
                                "SOC number: %s%nDoctor index number: %s%n",
                                roleDesignation, name, address, phoneNumber, email, socNumber, doctorIndexNumber));
            }else{
                EmployeeDTO empDto = new EmployeeDTO(roleDesignation, name, address, phoneNumber, email, socNumber);
                ctrl.createEmployee(empDto);
                confirm = Utils.confirm(String.format("Please confirm the data (type `s` if its correct, `n` if it is not):" +
                                "%nOrganization role: %s%nName: %s%nAddress: %s%nPhone Number: %s%nEmail: %s%nSOC number: %s%n",
                        roleDesignation, name, address, phoneNumber, email, socNumber));
            }
            if(!confirm) throw new IllegalArgumentException("Please, insert again the data you wish.");
            success = ctrl.saveEmployee();
            if(!success) throw new IllegalArgumentException("Error: Employee either already existent or null, please try again");
            success = ctrl.makeEmployeeAnUserAndSendPassword();
            if(!success) throw new UnsupportedOperationException("Error: Employee is already an user of the system");

        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            success = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            success = false;
        }
        if(success)
            System.out.println("\nEmployee successfully created!");
        return success;
    }
}
