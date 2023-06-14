package app.ui.console;

import app.ui.console.utils.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */

public class AdminUI implements Runnable{
    public AdminUI()
    {
    }

    public void run()
    {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create Parameter Category", new ParameterCategoryUI()));
        options.add(new MenuItem("Create Test Type", new CreateTestTypeUI()));
        options.add(new MenuItem("Register a Clinical Analysis Laboratory", new RegisterNewCalUI()));
        options.add(new MenuItem("Register a new Employee ", new CreateEmployeeUI()));
        options.add(new MenuItem("Specify a new Parameter and categorize it", new CreateParameterUI()));
        options.add(new MenuItem("View all tests", new ShowTestsUI()));

        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nAdmin Menu:");

            if ( (option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        }
        while (option != -1 );
    }
}
