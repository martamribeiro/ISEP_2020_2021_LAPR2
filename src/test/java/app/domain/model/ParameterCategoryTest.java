package app.domain.model;

import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParameterCategoryTest {
    Company company;

    @Before
    public void setUp(){
        company = new Company("many labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNullArgsNotAllowed() {
        System.out.println("ensureNullArgsNotAllowed");
        ParameterCategory pc = new ParameterCategory(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCodeIsNotEmpty() {
        System.out.println("ensureCodeIsNotEmpty");

        ParameterCategory pc = new ParameterCategory("", "TESTE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNameIsNotEmpty() {
        System.out.println("ensureNameIsNotEmpty");

        ParameterCategory pc = new ParameterCategory("CODE", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCodeIsNotMoreThan5Chars() {
        System.out.println("ensureCodeIsNotMoreThan5Chars");

        ParameterCategory pc = new ParameterCategory("MORETHEN5", "TESTE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCodeIsNotLessThan5Chars() {
        System.out.println("ensureCodeIsNotLessThan5Chars");

        ParameterCategory pc = new ParameterCategory("LESS", "TESTE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCodeIsAlphanumeric() {
        System.out.println("ensureCodeIsAlphanumeric");

        ParameterCategory pc = new ParameterCategory("@!$%#", "TESTE");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNameIsNotMoreThan10Chars() {
        System.out.println("ensureNameIsNotMoreThan10Chars");

        ParameterCategory pc = new ParameterCategory("CODE1", "MORETHANTEN");
    }

    @Test
    public void ensureNameCanBe10char() {
        System.out.println("ensureNameCanBe10char");

        ParameterCategory pc = new ParameterCategory("CODE1", "EQUALSTEN");
    }

    @Test
    public void equalsTrue(){
        ParameterCategory pc1 = new ParameterCategory("HEM02", "Hemogram");
        ParameterCategory pc2 = new ParameterCategory("HEM02", "Covid19");
        boolean result = pc1.equals(pc2);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsFalse(){
        ParameterCategory pc1 = new ParameterCategory("HEM02", "Hemogram");
        ParameterCategory pc2 = new ParameterCategory("CODE1", "Name");
        boolean result = pc1.equals(pc2);
        Assert.assertFalse(result);
    }

    @Test
    public void equalsTrueToItself(){
        ParameterCategory pc1 = new ParameterCategory("HEM02", "Hemogram");
        boolean result = pc1.equals(pc1);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsFalseDueToNull(){
        ParameterCategory pc1 = new ParameterCategory("HEM02", "Hemogram");
        boolean result = pc1.equals(null);
        Assert.assertFalse(result);
    }

    @Test
    public void equalsDueToSameCode(){
        ParameterCategory pc1 = new ParameterCategory("HEM02", "Hemogram");
        ParameterCategory pc2 = new ParameterCategory("HEM02", "Name");
        boolean result = pc1.equals(pc2);
        Assert.assertTrue(result);
    }

}