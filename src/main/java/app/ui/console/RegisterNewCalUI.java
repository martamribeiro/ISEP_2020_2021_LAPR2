package app.ui.console;

import app.controller.RegisterNewCalController;
import app.domain.model.TestType;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;
import app.mappers.dto.TestTypeDTO;
import app.ui.console.utils.OurUtils;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RegisterNewCalUI implements Runnable {
    private RegisterNewCalController ctrl;

    public RegisterNewCalUI() {
        ctrl = new RegisterNewCalController();
    }

    public void run() {
        boolean success = true;
        List<String> menu = OurUtils.menuToContinueOrCancel();

        System.out.println("To register a new Clinical Analysis Laboratory, please insert the requested data.");
        do {
            int index = Utils.showAndSelectIndex(menu, "");
            success = (index == -1) ? true : registerClinicalAnalysisLaboratory();
        } while (!success);


    }
    private boolean registerClinicalAnalysisLaboratory() {
        boolean success = false;

        try {
            String laboratoryID = Utils.readLineFromConsole("Laboratory ID: ");
            String name = Utils.readLineFromConsole("Name: ");
            String address = Utils.readLineFromConsole("Address: ");
            String phoneNumber = Utils.readLineFromConsole("Phone Number: ");
            String numTIN = Utils.readLineFromConsole("TIN Number: ");
            List<String> selectedTT = getTypesOfTestToCode();

            ClinicalAnalysisLaboratoryDTO calDto = new ClinicalAnalysisLaboratoryDTO(laboratoryID,
                        name, address, phoneNumber, numTIN, selectedTT);

            ctrl.createClinicalAnalysisLaboratory(calDto);

            List<String> copy = new ArrayList<>(calDto.getTestTypeCodes());

            StringBuilder codes = new StringBuilder();
            for (String code : copy) {
                codes.append("\n- ");
                codes.append("Code: ");
                codes.append(code);
            }

            boolean confirm = Utils.confirm(String.format("Please, confirm the data (type `s` if its correct, `n` if it is not):" +
                            "%n%nCLINICAL ANALYSIS LABORATORY%nLaboratory ID: %s%nName: %s%nAddress: %s%nPhone Number: %s%nTIN Number: %s%nCodes Of The Types Of Test: %s%n",
                    calDto.getLaboratoryID(), calDto.getName(), calDto.getAddress(),
                    calDto.getPhoneNumber(), calDto.getNumTIN(), codes));

            if (!confirm) {
                System.out.print("Please, insert again the data you wish.");
            } else {
                boolean save = ctrl.saveClinicalAnalysisLaboratory();
                if (save) {
                    success = true;
                    System.out.println("\nClinical Analysis Laboratory successfully created!");
                } else
                    throw new IllegalArgumentException("\nERROR: Clinical Analysis Laboratory Null or Already Registered in the System!");
                }
            }
            catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                success = false;
        }


        return success;



    }
    private List<TestTypeDTO> showTestTypeListAndSelectingThem(List<TestTypeDTO> listTestType) {
        List<TestTypeDTO> listTestTypeDto = ctrl.getTestTypes();
        TestTypeDTO testTypeToAdd = (TestTypeDTO) Utils.showAndSelectOne(listTestTypeDto,
                "Please, select the types of test from the list this laboratory will operate:");
        if(testTypeToAdd != null)
            listTestType.add(testTypeToAdd);
        else
            throw new IllegalArgumentException("Operation canceled!");
        boolean addMore = Utils.confirm("If you want to add more types of test please type 's', otherwise type 'n'");
        return addMore ? showTestTypeListAndSelectingThem(listTestType) : listTestType;

    }

    private List<String> getTypesOfTestToCode() {
        List<TestTypeDTO> listTestType = new ArrayList<>();
        listTestType = showTestTypeListAndSelectingThem(listTestType);
        List<String> testTypesCodes = new ArrayList<>();
        for (TestTypeDTO tt : listTestType) {
            if(!testTypesCodes.contains(tt.getCode()))
                testTypesCodes.add(tt.getCode());
        }
        return testTypesCodes;
    }
}
