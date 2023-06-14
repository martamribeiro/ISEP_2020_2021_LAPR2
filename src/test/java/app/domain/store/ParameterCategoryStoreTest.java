package app.domain.store;

import app.domain.model.Company;
import app.domain.model.ParameterCategory;
import app.domain.model.TestType;
import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParameterCategoryStoreTest {
    private Company company;
    private ParameterCategoryStore parameterCategoryStore;

    @Before
    public void setUp() {
        company = new Company("Many Labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
        parameterCategoryStore = company.getParameterCategoryStore();
    }


    @Test
    public void createParameterCategory() {
        ParameterCategory expObject = new ParameterCategory("code1","hemogram");
        ParameterCategory obj = parameterCategoryStore.createParameterCategory("code1", "hemogram");

        Assert.assertEquals(expObject, obj);

    }

    @Test
    public void ensureDifferentParameterCategoriesAreSaved() {
        ParameterCategory pc1 = new ParameterCategory("code1", "hemogram");
        parameterCategoryStore.saveParameterCategory(pc1);
        ParameterCategory pc2 = new ParameterCategory("code2","categorie2");

        boolean result = parameterCategoryStore.saveParameterCategory(pc2);

        Assert.assertTrue(result);
    }

    @Test
    public void ensureParameterCategoryIsNotSavedExistingAlreadyTheSameObject() {
        ParameterCategory pc1 = new ParameterCategory("code1", "hemogram");
        parameterCategoryStore.saveParameterCategory(pc1);

        boolean result = parameterCategoryStore.saveParameterCategory(pc1);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureParameterCategoryIsNotSavedExistingEqualObject() {
        ParameterCategory pc1 = new ParameterCategory("code1", "hemogram");
        parameterCategoryStore.saveParameterCategory(pc1);
        ParameterCategory pc2 = new ParameterCategory("code1", "hemogram");

        boolean result = parameterCategoryStore.saveParameterCategory(pc2);

        Assert.assertFalse(result);
    }

    @Test
    public void ensureNullParameterCategoryIsNotSaved() {

        boolean result = parameterCategoryStore.saveParameterCategory(null);

        Assert.assertFalse(result);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureGetWithInvalidParameterCategoryCodeThrowsException() {
        ParameterCategory pc1 = parameterCategoryStore.createParameterCategory("code1","categorie1");
        ParameterCategory pc2 = parameterCategoryStore.createParameterCategory("code2","categorie2");
        parameterCategoryStore.saveParameterCategory(pc1);
        parameterCategoryStore.saveParameterCategory(pc2);
        List<String> codeList = new ArrayList<String>();
        codeList.add("code1");
        codeList.add("codee");

        parameterCategoryStore.getCategoriesByCode(codeList);
    }

    @Test
    public void getCategoriesByCode() {
        ParameterCategory pc1 = parameterCategoryStore.createParameterCategory("code1","categorie1");
        ParameterCategory pc2 = parameterCategoryStore.createParameterCategory("code2","categorie2");
        parameterCategoryStore.saveParameterCategory(pc1);
        parameterCategoryStore.saveParameterCategory(pc2);
        List<String> codeList = new ArrayList<String>();
        codeList.add("code1");
        codeList.add("code2");

        List<ParameterCategory> expList = new ArrayList<>();
        expList.add(pc1);
        expList.add(pc2);

        List<ParameterCategory> list = parameterCategoryStore.getCategoriesByCode(codeList);


        Assert.assertEquals(expList, list);
    }

    @Test
    public void getParameterCategoriesList() {
        ParameterCategory pc1 = parameterCategoryStore.createParameterCategory("code1","categorie1");
        ParameterCategory pc2 = parameterCategoryStore.createParameterCategory("code2","categorie2");
        parameterCategoryStore.saveParameterCategory(pc1);
        parameterCategoryStore.saveParameterCategory(pc2);

        List<ParameterCategory> expList = new ArrayList<>();
        expList.add(pc1);
        expList.add(pc2);

        List<ParameterCategory> list = parameterCategoryStore.getParameterCategoriesStore();

        Assert.assertEquals(expList, list);

    }

}