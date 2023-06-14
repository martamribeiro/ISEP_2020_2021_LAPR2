package app.ui.console;

import app.controller.SelectCalController;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class RecepcionistUI implements Runnable{
    private SelectCalController ctrl;
    private String laboratoryID;

    public RecepcionistUI() {
        this.ctrl = new SelectCalController();
    }

    @Override
    public void run() {
        try {
            laboratoryID = getSelectedLaboratoryID();
            List<MenuItem> options = new ArrayList<MenuItem>();
            options.add(new MenuItem("Register a client", new RegisterClientUI()));
            options.add(new MenuItem("Register a test to a registered client", new CreateTestUI(laboratoryID)));
            int option = 0;
            do {
                option = Utils.showAndSelectIndex(options, "\n\nRecepcionist Menu:");

                if ((option >= 0) && (option < options.size())) {
                    options.get(option).run();
                }
            }
            while (option != -1);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private String getSelectedLaboratoryID() {
        ClinicalAnalysisLaboratoryDTO selectedCalDto =
                (ClinicalAnalysisLaboratoryDTO) Utils.showAndSelectOne(ctrl.getCalListDTO(),
                        "Please selected in which Clinical Analysis Laboratory you are working: ");
        if(selectedCalDto == null) throw new IllegalArgumentException("Operation canceled!");
        return selectedCalDto.getLaboratoryID();
    }
}
