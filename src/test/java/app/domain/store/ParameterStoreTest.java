package app.domain.store;

import app.domain.model.Company;
import app.domain.model.Parameter;
import app.domain.model.ParameterCategory;
import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ParameterStoreTest {

    private ParameterCategory p1;
    private ParameterCategory p2;
    private Company company = new Company("Many Labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
    private List<ParameterCategory> pcList;

    @Before
    public void setUp() {
        pcList = new ArrayList<>();
        p1 = new ParameterCategory("HEM01","Hemogram");
        p2 = new ParameterCategory("COD01","Name");
        pcList.add(p1);
        pcList.add(p2);
    }

    //Test 21
    @Test
    public void ensureParameterStoreIsBeingCreatedCorrectlyWithNoElements() {
        ParameterStore ps1 = new ParameterStore();
        Parameter[] result = ps1.toArray();
        Assert.assertEquals(0, result.length);
    }

    //Test 22
    @Test
    public void ensureParameterStoreIsBeingCreatedCorrectlyWithSomeElements() {
        ParameterStore ps1 = new ParameterStore();
        Parameter parameter1 = ps1.createParameter("RBC01", "RBC", "Red Blood Cells", p1);
        ps1.saveParameter(parameter1);
        Parameter parameter2 = ps1.createParameter("WBC01", "WBC", "White Blood Cells", p1);
        ps1.saveParameter(parameter2);
        Parameter parameter3 = ps1.createParameter("PLT01", "PLT", "Platelets", p1);
        ps1.saveParameter(parameter3);
        Parameter parameter4 = ps1.createParameter("PCOD1", "NAME", "Description", p2);
        ps1.saveParameter(parameter4);
        Parameter[] result = ps1.toArray();
        Assert.assertEquals(4, result.length);
    }

    //Test 23
    @Test
    public void ensureParameterIsBeingCreatedCorrectly() {
        Parameter expected = new Parameter("RBC01", "RBC", "Red Blood Cells", p1);
        ParameterStore parameterStore = company.getParameterStore();
        Parameter actual = parameterStore.createParameter("RBC01", "RBC", "Red Blood Cells", p1);
        Assert.assertEquals(expected, actual);
    }

    //Test 24
    @Test
    public void ensureParameterIsNotSavedRepeatedWithSameObject() {
        Parameter parameter1 = company.getParameterStore().createParameter("RBC01", "RBC", "Red Blood Cells", p1);
        Parameter parameter2 = company.getParameterStore().createParameter("RBC01", "RBC", "Red Blood Cells", p1);
        ParameterStore parameterStore = company.getParameterStore();
        parameterStore.saveParameter(parameter1);
        boolean result = parameterStore.saveParameter(parameter2);
        assertFalse(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureParameterSearchThrowsNotFound() {
        ParameterStore parameterStore = company.getParameterStore();
        List<String> list = new ArrayList<>();
        list.add("CODEC");
        parameterStore.getParamsByCodes(list);
    }

    @Test
    public void ensureParametersAreFoundByCode() {
        Parameter parameter1 = company.getParameterStore().createParameter("RBC01", "RBC", "Red Blood Cells", p1);
        Parameter parameter2 = company.getParameterStore().createParameter("RBC02", "RCC", "Red clood Cells", p1);
        ParameterStore parameterStore = company.getParameterStore();
        parameterStore.saveParameter(parameter1);
        parameterStore.saveParameter(parameter2);

        List<String> list = new ArrayList<>();
        list.add(parameter1.getPrmCode());
        list.add(parameter2.getPrmCode());
        List<Parameter> actual = parameterStore.getParamsByCodes(list);

        Assert.assertEquals(actual, parameterStore.getPrmList());
    }

    @Test
    public void ensureParametersAreFoundByCategories() {
        Parameter parameter1 = company.getParameterStore().createParameter("RBC01", "RBC", "Red Blood Cells", p1);
        Parameter parameter2 = company.getParameterStore().createParameter("RBC02", "RCC", "Red clood Cells", p1);
        ParameterStore parameterStore = company.getParameterStore();
        parameterStore.saveParameter(parameter1);
        parameterStore.saveParameter(parameter2);

        List<String> list = new ArrayList<>();
        list.add(parameter1.getPc().getCode());
        List<Parameter> actual = parameterStore.getParamsByCategories(list);

        Assert.assertEquals(actual, parameterStore.getPrmList());
    }

    //Test 25
    @Test
    public void ensureParameterIsNotSavedIfNull() {
        ParameterStore parameterStore = company.getParameterStore();
        assertFalse(parameterStore.saveParameter(null));
    }

}