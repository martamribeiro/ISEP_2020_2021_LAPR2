package app.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParameterTest {

    private ParameterCategory p1;

    @Before
    public void setUp() {
        p1 = new ParameterCategory("HEM01","Hemogram");
    }

    //Test 1
    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed(){
        Parameter prm = new Parameter(null, null, null, null);
    }

    //Test 2
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithNullParameterCode() {
        Parameter parameter = new Parameter(null, "RBC", "Red Blood Cells", p1);
    }

    //Test 3
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithParameterCodeEmpty() {
        Parameter parameter = new Parameter("", "RBC", "Red Blood Cells", p1);
    }

    //Test 4
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithParameterCodeWithLessThan5Chars() {
        Parameter parameter = new Parameter("RBC1", "RBC", "Red Blood Cells", p1);
    }

    //Test 5
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithParameterCodeWithMoreThan5Chars() {
        Parameter parameter = new Parameter("RBCCOUNT01", "RBC", "Red Blood Cells", p1);
    }

    //Test 6
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithParameterCodeFullOfSpaces() {
        Parameter parameter = new Parameter("     ", "RBC", "Red Blood Cells", p1);
    }

    //Test 7
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithNullShortName() {
        Parameter parameter = new Parameter("RBC01", null, "Red Blood Cells", p1);
    }

    //Test 8
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithShortNameEmpty() {
        Parameter parameter = new Parameter("RBC01", "", "Red Blood Cells", p1);
    }

    //Test 9
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithShortNameWithMoreThan8Chars() {
        Parameter parameter = new Parameter("RBC01", "RBC Count", "RedBloodC", p1);
    }

    //Test 10
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithShortNameFullOfSpaces() {
        Parameter parameter = new Parameter("RBC01", "   ", "Red Blood Cells", p1);
    }

    //Test 11
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithNullDescription() {
        Parameter parameter = new Parameter("RBC01", "RBC", null, p1);
    }

    //Test 12
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithDescriptionEmpty() {
        Parameter parameter = new Parameter("RBC01", "RBC", "", p1);
    }

    //Test 13
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithDescriptionWithMoreThan20Chars() {
        Parameter parameter = new Parameter("RBC01", "RBC",
                "Counting of RedBloodC", p1);
    }

    //Test 14
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithDescriptionFullOfSpaces() {
        Parameter parameter = new Parameter("RBC01", "RBC", "               ", p1);
    }

    //Test 15
    @Test(expected = IllegalArgumentException.class)
    public void createParameterWithParameterCategoryNull() {
        Parameter parameter = new Parameter("RBC01", "RBC", "Red Blood Cells", null);
    }

    //Test 16
    @Test
    public void equalsTrue() {
        Parameter parameter1 = new Parameter("RBC01", "RBC", "Red Blood Cells", p1);
        Parameter parameter2 = new Parameter("RBC01", "RBC", "Red Blood Cells", p1);
        boolean result = parameter1.equals(parameter2);
        Assert.assertTrue(result);
    }

    //Test 17
    @Test
    public void equalsFalse() {
        Parameter parameter1 = new Parameter("RBC01", "RBC", "Red Blood Cells", p1);
        Parameter parameter2 = new Parameter("WBC01", "WBC", "White Blood Cells", p1);
        boolean result = parameter1.equals(parameter2);
        Assert.assertFalse(result);
    }

    //Test 18
    @Test
    public void equalsTrueDueToSameCode(){
        Parameter parameter1 = new Parameter("RBC01", "RBC", "Red Blood Cells", p1);
        Parameter parameter2 = new Parameter("RBC01", "CRB", "Cells Red Blood", p1);
        boolean result = parameter1.equals(parameter2);
        Assert.assertTrue(result);
    }

    //Test 19
    @Test
    public void equalsTrueToItself() {
        Parameter parameter1 = new Parameter("RBC01", "RBC", "Red Blood Cells", p1);
        boolean result = parameter1.equals(parameter1);
        Assert.assertTrue(result);
    }

    //Test 20
    @Test
    public void equalsFalseDueToNull() {
        Parameter parameter1 = new Parameter("RBC01", "RBC", "Red Blood Cells", p1);
        boolean result = parameter1.equals(null);
        Assert.assertFalse(result);
    }

}