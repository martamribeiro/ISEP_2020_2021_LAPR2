package app.controller;

import app.domain.model.*;
import app.domain.shared.Constants;
import auth.AuthFacade;
import auth.UserSession;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class App {

    private Company company;
    private AuthFacade authFacade;

    private App() {
        Properties props = getProperties();
        this.company = new Company(props.getProperty(Constants.PARAMS_COMPANY_DESIGNATION));
        this.authFacade = this.company.getAuthFacade();
        bootstrap();
    }

    public Company getCompany()
    {
        return this.company;
    }


    public UserSession getCurrentUserSession()
    {
        return this.authFacade.getCurrentUserSession();
    }

    public boolean doLogin(String email, String pwd)
    {
        return this.authFacade.doLogin(email,pwd).isLoggedIn();
    }

    public void doLogout()
    {
        this.authFacade.doLogout();
    }

    private Properties getProperties()
    {
        Properties props = new Properties();

        // Add default properties and values
        props.setProperty(Constants.PARAMS_COMPANY_DESIGNATION, "Many Labs");


        // Read configured values
        try
        {
            InputStream in = new FileInputStream(Constants.PARAMS_FILENAME);
            props.load(in);
            in.close();
        }
        catch(IOException ex)
        {

        }
        return props;
    }

    //ACRESCENTEI
    public String getBarcodeClassNameConfig() {
        Properties props = getProperties();
        String className = props.getProperty("Company.ExternalAPI.Class");
        return className;
    }


    private void bootstrap() {
        this.authFacade.addUserRole(Constants.ROLE_ADMIN,Constants.ROLE_ADMIN);
        this.authFacade.addUserRole(Constants.ROLE_MED_LAB_TECHNICIAN, Constants.ROLE_MED_LAB_TECHNICIAN);
        this.authFacade.addUserRole(Constants.ROLE_RECEPTIONIST, Constants.ROLE_RECEPTIONIST);
        this.authFacade.addUserRole(Constants.ROLE_SPECIALIST_DOCTOR, Constants.ROLE_SPECIALIST_DOCTOR);
        this.authFacade.addUserRole(Constants.ROLE_CLINICAL_CHEM_TECHNOLOGIST, Constants.ROLE_CLINICAL_CHEM_TECHNOLOGIST);

        this.authFacade.addUserWithRole("Main Administrator", "admin@lei.sem2.pt", "123456",Constants.ROLE_ADMIN);
        this.authFacade.addUserWithRole("Med Lab Technician","medlabtech@gmail.com","1",Constants.ROLE_MED_LAB_TECHNICIAN);
        this.authFacade.addUserWithRole("rece","rec@gmail.com","1",Constants.ROLE_RECEPTIONIST);
        this.authFacade.addUserWithRole("Spedoc", "spdc@gmail.com", "123", Constants.ROLE_SPECIALIST_DOCTOR);
        this.authFacade.addUserWithRole("chem", "chem@gmail.com", "123", Constants.ROLE_CLINICAL_CHEM_TECHNOLOGIST);

        this.company.getParameterCategoryStore().saveParameterCategory(new ParameterCategory("CODE1","hemogram"));
        TestType t1 = new TestType("CODE1","descr","swab",this.company.getParameterCategoryStore().getParameterCategoriesStore(), Constants.COVID_EXTERNAL_ADAPTER);
        this.company.getTestTypeStore().saveTestType(t1);
        List<TestType> selectedTT = new ArrayList<>();
        selectedTT.add(t1);
        TestType t2 = new TestType("COD22","blabla","swab",this.company.getParameterCategoryStore().getParameterCategoriesStore(), Constants.COVID_EXTERNAL_ADAPTER);
        this.company.getTestTypeStore().saveTestType(t2);
        selectedTT.add(t2);
        ParameterCategory p1 = new ParameterCategory("code1","descrip");
        this.company.getParameterCategoryStore().saveParameterCategory(p1);
        this.company.getTestTypeStore().saveTestType(new TestType("CODE3","Description","swab",this.company.getParameterCategoryStore().getParameterCategoriesStore(), Constants.COVID_EXTERNAL_ADAPTER));
        Date d1 = new Date();
        Client c1 = new Client("1234567890123456","1234567890",d1,"1234567890","carlos@gmail.com","Carlos","12345678901");
        this.company.getClientStore().saveClient(c1);

        this.company.getParameterStore().saveParameter(new Parameter("code1", "name", "descrip",p1));

        ParameterCategory pc1 = this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0);

        Parameter param1 = new Parameter("code1","dsg","descr",pc1);
        Parameter param2 = new Parameter("code2","dag","descr",pc1);
        List<Parameter> listParameter = new ArrayList<>();
        listParameter.add(param1);
        listParameter.add(param2);

        this.company.getTestStore().saveTest(new Test("alphanumeric",c1,t1,listParameter));

        Test test1 = new Test("123456789012",c1,t1,listParameter);
        this.company.getTestStore().saveTest(test1);
        //RETIRAR BARCODE EXCEPTION DO CONSTRUTOR, BOOTSTRAP E SINGLETON EM BAIXO

        MyBarcode mb1 = new MyBarcode(c1, "12345678901");

        Sample s1 = new Sample(mb1);
        test1.addSample(s1);
        /*
        try{
            test1.addTestResult("code1", 23.45, "ug");
            test1.addTestResult("code2", 23.45, "ug");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

         */


        Test test2 = new Test("123456789000",c1,t1,listParameter);
        this.company.getTestStore().saveTest(test2);


        ClinicalAnalysisLaboratory cal1 = new ClinicalAnalysisLaboratory("mel23",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);
        cal1.getCalTestList().add(test2);

        this.company.getCalStore().saveClinicalAnalysisLaboratory(cal1);

        ClinicalAnalysisLaboratory cal2 = new ClinicalAnalysisLaboratory("llll1",
                "CAL","fff","91841373811","1234537890", selectedTT);
        cal2.getCalTestList().add(test1);
        this.company.getCalStore().saveClinicalAnalysisLaboratory(cal2);



    }



    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;
    public static App getInstance() {
        if(singleton == null)
        {
            synchronized(App.class)
            {
                singleton = new App();
            }
        }
        return singleton;
    }
}
