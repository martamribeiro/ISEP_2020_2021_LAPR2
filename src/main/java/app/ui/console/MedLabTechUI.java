package app.ui.console;

import app.controller.SelectCalController;
import app.mappers.dto.ClinicalAnalysisLaboratoryDTO;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MedLabTechUI implements Runnable {
    private SelectCalController ctrl;
    private String laboratoryID;

    public MedLabTechUI() {
        this.ctrl = new SelectCalController();
    }

    @Override
    public void run() {

        try {
            laboratoryID = getSelectedCalID();
            List<MenuItem> options = new ArrayList<MenuItem>();
            options.add(new MenuItem("Record the Samples collected of a Test", new RecordSamplesUI(laboratoryID)));


            int option = 0;
            do
            {
                option = Utils.showAndSelectIndex(options, "\n\nMedical Lab Technician Menu:\n");

                if ( (option >= 0) && (option < options.size()))
                {
                    options.get(option).run();
                }
            }
            while (option != -1 );
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private String getSelectedCalID() {

        ClinicalAnalysisLaboratoryDTO selectedCalDto = (ClinicalAnalysisLaboratoryDTO) Utils.showAndSelectOne(ctrl.getCalListDTO(),
                "Please select in which Clinical Analysis Laboratory you are working: \n");

        if(selectedCalDto == null) throw new IllegalArgumentException("Operation canceled!");
        return selectedCalDto.getLaboratoryID();
    }
}


