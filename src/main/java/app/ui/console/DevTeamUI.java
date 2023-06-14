package app.ui.console;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class DevTeamUI implements Runnable{

    public DevTeamUI()
    {

    }
    public void run()
    {
        System.out.println("\n");
        System.out.printf("Development Team:\n");
        System.out.printf("\t Alexandre Dias - 1200805@isep.ipp.pt \n");
        System.out.printf("\t Ana Albergaria - 1201518@isep.ipp.pt \n");
        System.out.printf("\t João Lucas Wolff - 1200049@isep.ipp.pt \n");
        System.out.printf("\t Marta Ribeiro - 1201592@isep.ipp.pt \n");
        System.out.println("\n");
    }
}
