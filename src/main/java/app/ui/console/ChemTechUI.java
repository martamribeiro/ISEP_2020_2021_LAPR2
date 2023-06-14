package app.ui.console;

import app.controller.SelectCalController;
import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChemTechUI implements Runnable{

    public ChemTechUI(){}

    @Override
    public void run() {

        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Add chemical analysis results to a test", new RecordResultsUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nClinical chemistry technologist menu:");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        }
        while (option != -1 );

    }

}
