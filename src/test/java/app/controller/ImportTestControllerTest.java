package app.controller;

import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.domain.shared.Constants;
import app.ui.console.utils.TestFileUtils;
import app.domain.store.ParameterStore;
import app.domain.store.TestTypeStore;
import app.mappers.dto.TestFileDTO;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportTestControllerTest {
    private List<Parameter> parametersBlood;
    private List<Parameter> parametersCovid;
    private List<ParameterCategory> pcListBlood;
    private List<ParameterCategory> pcList;
    private ParameterCategory p1;
    private ParameterCategory p2;
    private ParameterCategory p3;
    private TestType t1;
    private TestType t2;
    private Date d1;
    ParameterStore parameterStore = new ParameterStore();

    @Before
    public void setUp() throws ParseException {
        TestTypeStore testTypeStore = App.getInstance().getCompany().getTestTypeStore();
        ParameterStore parameterStore = App.getInstance().getCompany().getParameterStore();

        parametersBlood = new ArrayList<>();
        parametersCovid = new ArrayList<>();

        d1 = new SimpleDateFormat("dd/MM/yyyy").parse("08/08/2001");

        pcListBlood = new ArrayList<>();
        p1 = new ParameterCategory("CODE1","Hemogram");
        pcListBlood.add(p1);
        Parameter rbc = parameterStore.createParameter("RBC00", "rbc", "redbloodcells", p1);
        parameterStore.saveParameter(rbc);
        Parameter wbc =parameterStore.createParameter("WBC00", "wbc", "whitebloodcells", p1);
        parameterStore.saveParameter(wbc);
        Parameter plt = parameterStore.createParameter("PLT00", "plt", "plt", p1);
        parameterStore.saveParameter(plt);
        Parameter hb = parameterStore.createParameter("HB000", "hb", "hb", p1);
        parameterStore.saveParameter(hb);
        Parameter hdl = parameterStore.createParameter("HDL00", "hdl", "cholest", p1);
        parameterStore.saveParameter(hdl);

        pcList = new ArrayList<>();
        p2 = new ParameterCategory("CODE1","covid");
        pcList.add(p2);
        Parameter igg = new Parameter("IgGAN", "iga", "covidParam", p2);
        parameterStore.saveParameter(igg);
        parametersBlood.add(rbc);
        parametersBlood.add(wbc);
        parametersCovid.add(igg);

        t1 = new TestType("Blood","blood test","blood",pcListBlood, Constants.BLOOD_EXTERNAL_ADAPTER_3);
        t2 = new TestType("Covid","covid","swab",pcList, Constants.COVID_EXTERNAL_ADAPTER);

    }

    /*@Test
    public void createCovidTestsFromFile() throws ClassNotFoundException, InstantiationException, IllegalAccessException, BarcodeException, OutputException, IOException {
        ImportTestController ctrl = new ImportTestController();
        TestFileUtils testFileUtils = new TestFileUtils();
        List<TestFileDTO> procedData = testFileUtils.getTestsDataToDto("tests_CovidMATCPCSV.csv");
        for (TestFileDTO testData : procedData) {
            ctrl.importTestFromFile(testData);
        }
        *//*for (app.domain.model.Test s : App.getInstance().getCompany().getTestStore().getTests()) {
            System.out.println(s);
            System.out.println(s.getSamples().get(0).getMyBarcode().getBarcodeNumber());
            System.out.println("++++++++++++++++++++++++++++++");
        }*//*
    }*/

}