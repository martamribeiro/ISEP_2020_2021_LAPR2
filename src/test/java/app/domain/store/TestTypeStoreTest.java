package app.domain.store;

import app.domain.model.Company;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TestTypeStoreTest {
    private List<ParameterCategory> pcList;
    private ParameterCategory p1;
    private ParameterCategory p2;
    private Company company = new Company("many labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);

    @Before
    public void setUp() {
        pcList = new ArrayList<>();
        p1 = new ParameterCategory("CODE1","Name");
        p2 = new ParameterCategory("CODE2","Name");
        pcList.add(p1);
        pcList.add(p2);
    }

    //Test 10: Check if createTestType method returns TestType correctly
    @Test
    public void createTestType() {
        TestType expected = new TestType("AAA23", "blood analysis", "needle", pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        TestType actual = company.getTestTypeStore().createTestType("AAA23", "blood analysis", "needle", pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        Assert.assertEquals(expected,actual);
    }

    //Test 11: Check that it is not possible to save a repeated Test Type in the store
    @Test
    public void ensureTestTypeIsNotSavedRepeatedWithSameObject() {
        TestType t1 = company.getTestTypeStore().createTestType("AAA23", "blood analysis", "needle", pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        company.getTestTypeStore().saveTestType(t1);
        boolean actual = company.getTestTypeStore().saveTestType(t1);
        assertFalse(actual);
    }

    //Test 11(with different objects with same attributes): Check that it is not possible to save a repeated Test Type in the store
    @Test
    public void ensureTestTypeIsNotSavedRepeatedWithAlikeObject() {
        TestType t1 = company.getTestTypeStore().createTestType("AAA23", "blood analysis", "needle", pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        TestType t2 = company.getTestTypeStore().createTestType("AAA23", "blood analysis", "needle", pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        company.getTestTypeStore().saveTestType(t1);
        boolean actual = company.getTestTypeStore().saveTestType(t2);
        assertFalse(actual);
    }

    //Test 12: Check that it is not possible to save a null Test Type in the store
    @Test
    public void ensureTestTypeIsNotSavedIfNull() {
        System.out.println("ensureTestTypeIsNotSavedIfNull");
        assertFalse(company.getTestTypeStore().saveTestType(null));
    }

    //Test 13: Check that it is not possible to get the test types by code with not assigned codes
    @Test(expected = UnsupportedOperationException.class)
    public void ensureGetWithInvalidTestTypeCodeThrowsException() {
        System.out.println("ensureGetWithInvalidTestTypeCodeThrowsException");
        TestTypeStore testTypeStore = company.getTestTypeStore();
        TestType t1 = testTypeStore.createTestType("AAAA1", "blood analysis", "needle", pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        TestType t2 = testTypeStore.createTestType("AAAA2", "blood analysis", "needle", pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        testTypeStore.saveTestType(t1);
        testTypeStore.saveTestType(t2);
        List<String> codeList = new ArrayList<String>();
        codeList.add("AAAA1");
        codeList.add("AAAAA");

        testTypeStore.getTestTypesByCode(codeList);
    }



}