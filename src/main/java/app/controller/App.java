package app.controller;

import app.domain.model.*;
import app.domain.shared.Constants;
import app.mappers.dto.TestFileDTO;
import app.ui.console.utils.TestFileUtils;
import auth.AuthFacade;
import auth.UserSession;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class App implements Serializable {

    private Company company;
    private AuthFacade authFacade;

    private App() {
        Properties props = getProperties();
        this.company = new Company(props.getProperty(Constants.PARAMS_COMPANY_DESIGNATION),
                props.getProperty("Company.ExternalAPI.Class"),
                props.getProperty("Company.SortAlgorithm.Class"),
                props.getProperty("Company.RegressionModel.Class"),
                props.getProperty("Company.DateInterval"),
                props.getProperty("Company.NumberOfHistoricalPoints"),
                props.getProperty("Company.ConfidenceLevel"),
                props.getProperty("Company.SignificanceLevel"));
        this.authFacade = this.company.getAuthFacade();
        bootstrap();
        Date delay = this.company.getDateForNHSReportTask();
        this.company.scheduleNHSReportTask(delay, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
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

    private void bootstrap() {
        this.authFacade.addUserRole(Constants.ROLE_MED_LAB_TECHNICIAN, Constants.ROLE_MED_LAB_TECHNICIAN);
        this.authFacade.addUserRole(Constants.ROLE_ADMIN, Constants.ROLE_ADMIN);
        this.authFacade.addUserRole(Constants.ROLE_RECEPTIONIST, Constants.ROLE_RECEPTIONIST);
        this.authFacade.addUserRole(Constants.ROLE_SPECIALIST_DOCTOR, Constants.ROLE_SPECIALIST_DOCTOR);
        this.authFacade.addUserRole(Constants.ROLE_CLINICAL_CHEM_TECHNOLOGIST, Constants.ROLE_CLINICAL_CHEM_TECHNOLOGIST);
        this.authFacade.addUserRole(Constants.ROLE_LAB_COORDINATOR, Constants.ROLE_LAB_COORDINATOR);
        this.authFacade.addUserRole("CLIENT", "CLIENT");

        this.authFacade.addUserWithRole("Main Administrator", "admin@lei.sem2.pt", "123456",Constants.ROLE_ADMIN);
        this.authFacade.addUserWithRole("Med Lab Technician","medlabtech@gmail.com","1",Constants.ROLE_MED_LAB_TECHNICIAN);
        this.authFacade.addUserWithRole("rece","rec@gmail.com","1",Constants.ROLE_RECEPTIONIST);
        this.authFacade.addUserWithRole("Spedoc", "spdc@gmail.com", "123", Constants.ROLE_SPECIALIST_DOCTOR);
        this.authFacade.addUserWithRole("chem", "chem@gmail.com", "123", Constants.ROLE_CLINICAL_CHEM_TECHNOLOGIST);
        this.authFacade.addUserWithRole("labCord", "lc@gmail.com","1",Constants.ROLE_LAB_COORDINATOR);
        this.company.getParameterCategoryStore().saveParameterCategory(new ParameterCategory("Blood","hemogram"));
        this.company.getParameterCategoryStore().saveParameterCategory(new ParameterCategory("CODE2","choleste"));
        this.company.getParameterCategoryStore().saveParameterCategory(new ParameterCategory("Covid","covid"));

        List<ParameterCategory> pcsCovid = new ArrayList<>();
        pcsCovid.add(this.company.getParameterCategoryStore().getParameterCategoriesStore().get(2));

        TestType t1 = new TestType("covid","descr","swab",pcsCovid, Constants.COVID_EXTERNAL_ADAPTER);
        this.company.getTestTypeStore().saveTestType(t1);
        List<TestType> selectedTT = new ArrayList<>();
        selectedTT.add(t1);

        List<ParameterCategory> pcsBlood = new ArrayList<>();
        pcsBlood.add(this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0));
        pcsBlood.add(this.company.getParameterCategoryStore().getParameterCategoriesStore().get(1));

        TestType t2 = new TestType("blood","blabla","blood",pcsBlood, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        this.company.getTestTypeStore().saveTestType(t2);
        selectedTT.add(t2);

        ParameterCategory p1 = new ParameterCategory("covid","descrip");
        this.company.getParameterCategoryStore().saveParameterCategory(p1);
        this.company.getTestTypeStore().saveTestType(new TestType("Covid","Description",
                "swab",this.company.getParameterCategoryStore().getParameterCategoriesStore(), Constants.COVID_EXTERNAL_ADAPTER));
        Date d1 = new Date();
        Client c1 = new Client("1234567890123456","1234567890",d1,"1234567890","maria@gmail.com","Maria","12345678901");
        this.company.getClientStore().saveClient(c1);

        Client c2 = new Client("1234567890123333","1233367890",d1,"1234555890","pedro@gmail.com","Pedro","11115678901");
        this.company.getClientStore().saveClient(c2);

        Parameter parameter = new Parameter("IgGAN", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(2));
        this.company.getParameterStore().saveParameter(parameter);
        Parameter parameter1 = new Parameter("WBC00", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0));
        this.company.getParameterStore().saveParameter(parameter1);
        Parameter parameter2 = new Parameter("RBC00", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0));
        this.company.getParameterStore().saveParameter(parameter2);
        Parameter parameter3 = new Parameter("HDL00", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(1));
        this.company.getParameterStore().saveParameter(parameter3);
        Parameter parameter4 = new Parameter("PLT00", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0));
        this.company.getParameterStore().saveParameter(parameter4);
        Parameter parameter5 = new Parameter("HB000", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0));
        this.company.getParameterStore().saveParameter(parameter5);

        List<Parameter> listParameter = new ArrayList<>();
        listParameter.add(parameter);


        ClinicalAnalysisLaboratory cal1 = new ClinicalAnalysisLaboratory("001DO",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);


        this.company.getCalStore().saveClinicalAnalysisLaboratory(cal1);

        ClinicalAnalysisLaboratory cal2 = new ClinicalAnalysisLaboratory("001LN",
                "CAL","fff","91841373811","1234537898", selectedTT);

        this.company.getCalStore().saveClinicalAnalysisLaboratory(cal2);
        this.company.getCalStore().saveClinicalAnalysisLaboratory(new ClinicalAnalysisLaboratory("001LR",
                "CAL3","SSS","91841373551","1231537890", selectedTT));
        this.company.getCalStore().saveClinicalAnalysisLaboratory(new ClinicalAnalysisLaboratory("001MA",
                "CAL4","GGG","91841373441","1232537890", selectedTT));
        this.company.getCalStore().saveClinicalAnalysisLaboratory(new ClinicalAnalysisLaboratory("001SO",
                "CAL5","GG","91841373331","1234537890", selectedTT));
        this.company.getCalStore().saveClinicalAnalysisLaboratory(new ClinicalAnalysisLaboratory("001WA",
                "CAL6","AAA","91841373221","1234437890", selectedTT));


        MyBarcode mb1 = new MyBarcode(c1, "12345678901");

        Sample s1 = new Sample(mb1);
        this.company.getParameterStore().saveParameter(new Parameter("WBC00", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0)));
        this.company.getParameterStore().saveParameter(new Parameter("RBC00", "name", "descrip",this.company.getParameterCategoryStore().getParameterCategoriesStore().get(0)));

        Test testWithResult = new Test("123456789067",c1,t2,listParameter,cal1);
        this.company.getTestStore().saveTest(testWithResult);
        Date date1reg = new Date(2020, Calendar.JANUARY, 14,18,0,0);
        testWithResult.setDateOfTestRegistration(date1reg);
        Sample sample1 = null;
        try {
            sample1 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678901"), "12345678901"));
        } catch (BarcodeException e) {
            e.printStackTrace();
        }
        testWithResult.addSample(sample1);
        Date date1s = new Date(2020, Calendar.JANUARY, 15, 8, 0, 0);
        testWithResult.setDateOfSamplesCollection(date1s);
        try {
            testWithResult.addTestResult("IgGAN", 23.45, "ug");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Date date1 = new Date(2020, Calendar.JANUARY, 16, 8, 0, 0);
        testWithResult.setDateOfChemicalAnalysis(date1);
        Report report1 = new Report("Everything is well.");
        testWithResult.addReport(report1);
        Date date1r = new Date(2020,Calendar.JANUARY,18,8,0,0);
        testWithResult.setDateOfDiagnosis(date1r);
        testWithResult.setDateOfValidation(date1r);

        Test test4 = new Test("000099998888",c1,t1,listParameter,cal1);
        this.company.getTestStore().saveTest(test4);
        Date date4reg = new Date(2020,Calendar.JANUARY, 14,8,0,0);
        test4.setDateOfTestRegistration(date4reg);
        Sample sample4 = null;
        try {
            sample4 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678904"), "12345678904"));
        } catch (BarcodeException e) {
            e.printStackTrace();
        }
        test4.addSample(sample4);
        Date date4s = new Date(2020, Calendar.JANUARY, 15, 19, 59, 58);
        test4.setDateOfSamplesCollection(date4s);
        try {
            test4.addTestResult("IgGAN", 23.45, "ug");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        test4.addChemicalAnalysisDate();
        Date date4 = new Date(2020, Calendar.JANUARY, 15, 19, 59, 59);
        test4.setDateOfChemicalAnalysis(date4);
        Report report4 = new Report("Everything is well.");
        test4.addReport(report4);
        Date date4r = new Date(2020,Calendar.JANUARY,16,19,59,59);
        test4.setDateOfDiagnosis(date4r);
        test4.setDateOfValidation(date4r);

        Test test5 = new Test("000099898888",c2,t1,listParameter,cal1);
        this.company.getTestStore().saveTest(test5);
        Date date5reg = new Date(2020,Calendar.JANUARY, 14,8,0,0);
        test5.setDateOfTestRegistration(date5reg);
        Sample sample5 = null;
        try {
            sample5 = new Sample(new MyBarcode(BarcodeFactory.createUPCA("12345678904"), "12345678904"));
        } catch (BarcodeException e) {
            e.printStackTrace();
        }
        test5.addSample(sample5);
        Date date5s = new Date(2020, Calendar.JANUARY, 15, 19, 59, 58);
        test5.setDateOfSamplesCollection(date5s);
        try {
            test5.addTestResult("IgGAN", 23.45, "ug");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        test4.addChemicalAnalysisDate();
        Date date5 = new Date(2020, Calendar.JANUARY, 15, 19, 59, 59);
        test5.setDateOfChemicalAnalysis(date5);
        Report report5 = new Report("Everything is well.");
        test5.addReport(report5);
        Date date5r = new Date(2020,Calendar.JANUARY,16,19,59,59);
        test5.setDateOfDiagnosis(date5r);
        test5.setDateOfValidation(date5r);
    }

    public boolean storeUserSection(){
        try {
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(Constants.SERILIAZTION_FILE_NAME))) {
                out.writeObject(this);
                System.out.println("success here write");
            }
            return true;
        } catch (IOException ex) {
            System.out.println("falhou wirte");
            ex.printStackTrace();
            return false;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static App getStoredAppInstance(){
        File file = new File(Constants.SERILIAZTION_FILE_NAME);
        App app;
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(file));
            try {
                app = (App) in.readObject();
                Date delay = app.getCompany().getDateForNHSReportTask();
                app.getCompany().scheduleNHSReportTask(delay, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
                System.out.println("success here");
            } finally {
                in.close();
            }
            return app;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("File Not Found");
            return new App();
        }
    }


    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;
    public static App getInstance() {
        if(singleton == null)
        {
            synchronized(App.class)
            {
                singleton = getStoredAppInstance();
            }
        }
        return singleton;
    }
}
