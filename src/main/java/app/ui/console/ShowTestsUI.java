package app.ui.console;

import app.controller.ShowAllTestsController;
import app.ui.console.utils.OurUtils;
import app.ui.console.utils.Utils;


/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class ShowTestsUI implements Runnable{

    ShowAllTestsController ctrl;

    public ShowTestsUI()
    {
        this.ctrl = new ShowAllTestsController();
    }
    public void run()
    {
        OurUtils.showTestsListWithAllData(ctrl.getAllTests(), "ALL REGISTERED TESTS\n");
        //Utils.showList(ctrl.getAllTests(), "All registered tests");
    }
}
