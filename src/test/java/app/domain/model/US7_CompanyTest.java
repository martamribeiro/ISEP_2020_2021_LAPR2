package app.domain.model;

import app.domain.shared.Constants;
import app.mappers.dto.EmployeeDTO;
import app.mappers.dto.SpecialistDoctorDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class US7_CompanyTest {
    private Company company;
    private OrgRole r1;
    private OrgRole r2;

    @Before
    public void setUp() {
        company = new Company("Many Labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
        r1 = new OrgRole("Spec Doctor");
        r2 = new OrgRole("Med Lab Tech");
    }

   @Test
    public void createSpecialistDoctor() {

        //Arrange
        SpecialistDoctor expObj = new SpecialistDoctor(r1,
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234","123456");

        SpecialistDoctorDTO empDTO = new SpecialistDoctorDTO("Spec Doctor",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234","123456");

        //Act
        Employee obj = company.createSpecialistDoctor(empDTO);

        //Assert
        Assert.assertEquals(expObj, obj);
    }

    @Test
    public void createEmployee() {

        //Arrange
        Employee expObj = new Employee(r2,
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234");

        EmployeeDTO empDTO = new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234");

        //Act
        Employee obj = company.createEmployee(empDTO);

        //Assert
        Assert.assertEquals(expObj, obj);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void ensureCreateEmpWithInexistentRole() {

        EmployeeDTO empDTO = new EmployeeDTO("Jenitor",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234");

        company.createEmployee(empDTO);
    }

    @Test
    public void saveEmployee() {

        //Arrange
        Employee expObj = new Employee(r2,
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234");

        EmployeeDTO empDTO = new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234");

        //Act
        Employee obj = company.createEmployee(empDTO);
        company.saveEmployee(obj);

        Employee savedObj = company.getEmpList().get(0);
        //Assert
        Assert.assertEquals(expObj, savedObj);
    }

    @Test
    public void ensureSaveNullObjectNotAllowed() {

        boolean save = company.saveEmployee(null);
        //Assert
        Assert.assertFalse(save);
    }

    @Test
    public void ensureSaveRepeatedEmpNotAllowed() {

        //Act
        Employee obj = company.createEmployee(new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234"));
        company.saveEmployee(obj);
        boolean save = company.saveEmployee(obj);
        //Assert
        Assert.assertFalse(save);
    }

    @Test
    public void ensureSaveEmpWithRepeatedAttributesNotAllowed() {

        //Act
        Employee obj = company.createEmployee(new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234"));
        company.saveEmployee(obj);
        Employee obj2 = company.createEmployee(new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234"));
        boolean save = company.saveEmployee(obj2);
        //Assert
        Assert.assertFalse(save);
    }

    @Test
    public void makeEmployeeAnUserWithNewRoleToTheSystem() {

        //Act
        Employee obj = company.createEmployee(new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234"));
        Employee obj2 = company.createEmployee(new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","0987654321","afonso@gmail.com",
                "1234"));

        boolean save = company.makeEmployeeAUser(obj, "1234567890");
        //Assert
        Assert.assertTrue(save);
    }

    @Test
    public void ensureItsNotPossibleToHaveTwoUsersWithSameEmail() {

        //Act
        Employee obj = company.createEmployee(new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234"));
        Employee obj2 = company.createEmployee(new EmployeeDTO("Med Lab Tech",
                "Afonso","Lisboa","0987654321","afonso@gmail.com",
                "1234"));

        company.makeEmployeeAUser(obj, "1234567890");
        boolean save = company.makeEmployeeAUser(obj2, "1234567890");
        //Assert
        Assert.assertFalse(save);
    }

    @Test
    public void makeClientAnUserWithExistentRole() {

        //Act
        Employee obj = company.createEmployee(new EmployeeDTO("Administrator",
                "Afonso","Lisboa","1234567890","afonso@gmail.com",
                "1234"));

        boolean save = company.makeEmployeeAUser(obj, "1234567890");
        //Assert
        Assert.assertTrue(save);
    }

}
