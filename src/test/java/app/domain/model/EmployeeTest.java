package app.domain.model;

import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {
    private Company company;
    private OrgRole r1;
    private OrgRole r2;

    @Before
    public void setUp() {
        company = new Company("Many Labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
        r1 = new OrgRole("Spec Doctor");
        r2 = new OrgRole("Med Lab Tech");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC8NameNotNull() {
        Employee instance = new Employee(r1, null, "Morada",
                "1234567890","joana@gmail.com","1234");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC8NameNotEmpty() {
        Employee instance = new Employee(r1, "", "Morada",
                "1234567890","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC8NameNotWhiteSpace() {
        Employee instance = new Employee(r1, " ", "Morada",
                "1234567890","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC8NameHasTheRightLength() {
        Employee instance = new Employee(r1, "abcdefghijklmnopqrstvuxzasdfghjklqejfjgkgnkfgnfkngkfngknkfn",
                "Morada", "1234567890","joana@gmail.com","1234");
    }

    //MUTATION
    @Test
    public void ensureAC8NameWithLengthOf35() {

        Employee instance = new Employee(r1, "EmployeeComNomeTrintaECincoLetrasss",
                "Morada", "1234567890","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC9PhoneNumberNotNull() {
        Employee instance = new Employee(r1, "emp", "Morada",
                null,"joana@gmail.com","1234");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC9PhoneNumberNotEmpty() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC9PhoneNumberNotWhiteSpace() {
        Employee instance = new Employee(r1, "emp", "Morada",
                " ","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC9PhoneNumberHasTheRightLength() {
        Employee instance = new Employee(r1, "employee", "Morada",
                "123","joana@gmail.com","1234");
    }
    //MUTATION
    @Test
    public void ensurePhoneNumberHasLengthOf10() {
        Employee instance = new Employee(r1, "employee", "Morada",
                "1234567890","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC9PhoneNumberHasOnlyDigits() {
        Employee instance = new Employee(r1, "employee", "Morada",
                "12a3456789","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC10EmailNotNull() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890",null,"1234");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC10EmailNotEmpty() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC10EmailNotWhiteSpace() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890"," ","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC10EmailHasTheRightFormat() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","analamas","1234");
    }
    //MUTATION
    @Test
    public void checkEmailHasTheRightFormat() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","analamas@hotmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSocCodeNotNull() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","joana@gmail.com",null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureSocCodeNotEmpty() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","joana@gmail.com","");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSocCodeNotWhiteSpace() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","joana@gmail.com"," ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSocCodeWithRightLength() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","joana@gmail.com","123457");
    }

    @Test
    public void ensureSocCodeWithLengthOf4() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSocCodeOnlyHasDigits() {
        Employee instance = new Employee(r1, "emp", "Morada",
                "1234567890","joana@gmail.com","123a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAddressNotNull() {
        Employee instance = new Employee(r1, "employee", null,
                "1234567890","joana@gmail.com","1234");
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureAddressNotEmpty() {
        Employee instance = new Employee(r1, "employee", "",
                "1234567890","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAddressNotWhiteSpace() {
        Employee instance = new Employee(r1, "employee", " ",
                "1234567890","joana@gmail.com","1234");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAddressHasTheRightLength() {
        Employee instance = new Employee(r1, "employee", "Moradaasfsgdthfyjgukilihfeadgdhjklkhfsdfgvbjmjhmgfsddfhgjh",
                "1234567890","joana@gmail.com","1234");
    }

    //MUTATION
    @Test
    public void ensureAddressWithLengthOf30() {
        Employee instance = new Employee(r1, "employee", "qwertyuiopasdfghjkl zxcvbnmqwe",
                "1234567890","joana@gmail.com","1234");
    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentName() {
        Employee object = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");

        Employee objectOnlyWithDifferentEmployeeID = new Employee(r1,
                "Jessica","Lisboa","9184137881","manuel@gmail.com", "1234");

        boolean resultDifferentAddresses = object.equals(objectOnlyWithDifferentEmployeeID);

        Assert.assertFalse(resultDifferentAddresses);
    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentAddress() {
        Employee object = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");

        Employee objectOnlyWithDifferentEmployeeID = new Employee(r1,
                "Manuel","Porto","9184137881","manuel@gmail.com", "1234");

        boolean resultDifferentAddresses = object.equals(objectOnlyWithDifferentEmployeeID);

        Assert.assertFalse(resultDifferentAddresses);
    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentPhoneNumber() {
        Employee object = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");

        Employee objectOnlyWithDifferentEmployeeID = new Employee(r1,
                "Manuel","Lisboa","9184137882","manuel@gmail.com", "1234");

        boolean resultDifferentAddresses = object.equals(objectOnlyWithDifferentEmployeeID);

        Assert.assertFalse(resultDifferentAddresses);
    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentEmail() {
        Employee object = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");

        Employee objectOnlyWithDifferentEmployeeID = new Employee(r1,
                "Manuel","Lisboa","9184137881","manu@gmail.com", "1234");

        boolean resultDifferentAddresses = object.equals(objectOnlyWithDifferentEmployeeID);

        Assert.assertFalse(resultDifferentAddresses);
    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentSocCode() {
        Employee object = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");

        Employee objectOnlyWithDifferentEmployeeID = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1233");

        boolean resultDifferentAddresses = object.equals(objectOnlyWithDifferentEmployeeID);

        Assert.assertFalse(resultDifferentAddresses);
    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentRole() {
        Employee object = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");

        Employee objectOnlyWithDifferentEmployeeID = new Employee(r2,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");

        boolean resultDifferentAddresses = object.equals(objectOnlyWithDifferentEmployeeID);

        Assert.assertFalse(resultDifferentAddresses);
    }

    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {
        //Arrange
        Employee emp = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");
        ParameterCategory p = new ParameterCategory("code1","hemogram");
        //Act
        boolean resultDifferentClasses = emp.equals(p);
        //Assert
        Assert.assertFalse(resultDifferentClasses);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {
        //Arrange
        Employee emp1 = new Employee(r1,
                "Manuel","Lisboa","9184137881","manuel@gmail.com", "1234");
        Employee emp2 = null;
        //Act
        boolean resultWithNull = emp1.equals(emp2);

        //Assert
        Assert.assertFalse(resultWithNull);
    }






}