package app.ui.console;

import app.controller.SelectCalController;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SpecDocUI implements Runnable {

    private SelectCalController ctrl;
    private String laboratoryID;

    public SpecDocUI(){
        this.ctrl = new SelectCalController();
    }

    @Override
    public void run() {

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Make a diagnosis and write a report for a given test", new WriteReportUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nSpecialist Doctor Menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        }
        while (option != -1 );

    }

}
