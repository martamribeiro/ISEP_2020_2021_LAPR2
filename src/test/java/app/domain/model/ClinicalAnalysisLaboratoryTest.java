package app.domain.model;

import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ClinicalAnalysisLaboratoryTest {
    private List<ParameterCategory> pcList;
    private ParameterCategory p1;
    private ParameterCategory p2;
    private List<TestType> selectedTT;
    private TestType t1;
    private TestType t2;

    @Before
    public void setUp() {
        pcList = new ArrayList<>();
        p1 = new ParameterCategory("CODE1","Name");
        p2 = new ParameterCategory("CODE2","Name");
        pcList.add(p1);
        pcList.add(p2);

        t1 = new TestType("CODE3","Description","swab",pcList, Constants.COVID_EXTERNAL_ADAPTER);
        t2 = new TestType("CODE4","Description","swab",pcList, Constants.COVID_EXTERNAL_ADAPTER);

        selectedTT = new ArrayList<>();
        selectedTT.add(t1);
        selectedTT.add(t2);
    }

    //Test 1: Check that it is not possible to create an instance of the ClinicalAnalysisLaboratory
    // class with null values.
    @Test(expected = IllegalArgumentException.class)
        public void ensureNullIsNotAllowed() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory(null,
                null,null,null,null,null);
    }


    //AC2 - The Laboratory ID must have five alphanumeric characters.
    //Test 2, 3, 4: Check that it is not possible to create an instance of the
    //ClinicalAnalysisLaboratory class with a blank (null, empty (""), or whitespace) laboratoryID.
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC2LaboratoryIDNotNull() {

        //- Null laboratoryID
        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory(null,
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC2LaboratoryIDNotEmpty() {

        //- Empty laboratoryID
        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC2LaboratoryIDNotWhiteSpace() {

        //- Whitespace laboratoryID
        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory(" ",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);
    }

    //Test 5: Check that it is not possible to create an instance of the
    //ClinicalAnalysisLaboratory class with a laboratoryID length different than 5.
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC2LaboratoryIDWithRightLength() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL123456",
                "CAL","Lisboa","91841378811","1234567890",selectedTT);
    }



    //Test 6: Check that it is not possible to create an instance of the
    //ClinicalAnalysisLaboratory class with a laboratoryID that
    //doesn't contain alphanumeric characters.
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC2LaboratoryIDIsAlphanumeric() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("C.L1@",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);
    }

    //MUTATION - laboratoryID IS alphanumeric
    @Test
    public void LaboratoryIDAlreadyIsAlphanumeric() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);
    }

    //AC3 - The name is a string with no more than 20 characters.
    //Test 7, 8, 9: Check that it is not possible to create an instance of the
    //ClinicalAnalysisLaboratory class with a with a blank
    // (null, empty (""), or whitespace) name.

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC3NameNotNull() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                null,"Lisboa","91841378811","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC3NameNotEmpty() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "","Lisboa","91841378811","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC3NameNotWhiteSpace() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                " ","Lisboa","91841378811","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC3NameWithRightLength() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "Clinical Laboratory ManyLabs ","Lisboa","91841378811","1234567890", selectedTT);
    }

    //CONFIRM - MUTATION - if name.length = 20
    @Test
    public void ensureAC3NameWithLengthOf20() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "Laboratorio de Santa","Lisboa","91841378811","1234567890", selectedTT);
    }

    //AC4 - Address: A string with no more than 30 characters.

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC4AddressNotNull() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL",null,"91841378811","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC4AddressNotEmpty() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","","91841378811","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC4AddressNotWhiteSpace() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL"," ","91841378811","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC4AddressWithRightLength() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Av. 25 de Abril, Ed. Sao Joao, 127-1º Fte, Dir e Esq.","91841378811","1234567890",selectedTT);
    }

    //CONFIRM - MUTATION - address.length() <= 30
    @Test
    public void ensureAC4AddressWithLengthLesserThan30() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Av. 25 de Abril","91841378811","1234567890",selectedTT);
    }

    //CONFIRM - MUTATION - address.length() == 30
    @Test
    public void ensureAC4AddressWithLengthEqualsTo30() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","thisAddressHasLengthofThirty01","91841378811","1234567890",selectedTT);
    }

    //AC5 - The Phone Number is a 11 digit number.
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC5PhoneNumberNotNull() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa",null,"1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC5PhoneNumberNotEmpty() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC5PhoneNumberWhiteSpace() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa"," ","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC5PhoneNumberOnlyDigits() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","918413a7881","1234567890", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC5PhoneNumberWithRightLength() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","918413788112","1234567890", selectedTT);
    }

    //AC6 - The TIN Number is a 10 digit number.
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC6TINNumberNotNull() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811",null, selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC6TINNumberNotEmpty() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC6TINNumberWhiteSpace() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811"," ", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC6TINNumberOnlyDigits() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","123456789!", selectedTT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC6TINNumberWithRightLength() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","123456789011", selectedTT);
    }

    //AC7 - Type of tests must be an attribute of the Clinical Analysis Laboratory.
    @Test(expected = IllegalArgumentException.class)
    public void ensureAC7ListOfTestTypesNotNull() {

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureAC7ListOfTestTypesNotEmpty() {

        List<TestType> emptyList = new ArrayList<>();

        ClinicalAnalysisLaboratory instance = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", emptyList);
    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentLabID() {
        ClinicalAnalysisLaboratory object = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        ClinicalAnalysisLaboratory objectOnlyWithDifferentLabID = new ClinicalAnalysisLaboratory("CAL13",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        boolean resultDifferentAddresses = object.equals(objectOnlyWithDifferentLabID);

        Assert.assertFalse(resultDifferentAddresses);

    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentName() {
        ClinicalAnalysisLaboratory object = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        ClinicalAnalysisLaboratory objectOnlyWithDifferentName = new ClinicalAnalysisLaboratory("CAL12",
                "LAB","Lisboa","91841378811","1234567890", selectedTT);


        boolean resultDifferentName = object.equals(objectOnlyWithDifferentName);

        Assert.assertFalse(resultDifferentName);

    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentAddress() {
        ClinicalAnalysisLaboratory object = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        ClinicalAnalysisLaboratory objectOnlyWithDifferentAddress = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Porto","91841378811","1234567890", selectedTT);

        boolean resultDifferentAddress = object.equals(objectOnlyWithDifferentAddress);

        Assert.assertFalse(resultDifferentAddress);

    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentPhoneNumber() {
        ClinicalAnalysisLaboratory object = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        ClinicalAnalysisLaboratory objectOnlyWithDifferentPhoneNum = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378810","1234567890", selectedTT);

        boolean resultDifferentPhoneNum = object.equals(objectOnlyWithDifferentPhoneNum);

        Assert.assertFalse(resultDifferentPhoneNum);

    }

    @Test
    public void ensureNotEqualsObjectsWithDifferentTINNumber() {
        ClinicalAnalysisLaboratory object = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        ClinicalAnalysisLaboratory objectOnlyWithDifferentTINNum = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567892", selectedTT);

        boolean resultDifferentTINNum = object.equals(objectOnlyWithDifferentTINNum);

        Assert.assertFalse(resultDifferentTINNum);

    }
    @Test
    public void ensureNotEqualsObjectsWithDifferentTestTypeList() {
        ClinicalAnalysisLaboratory object = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);

        List<TestType> differentSelectedTT = new ArrayList<>();

        TestType t3 = new TestType("TEST3","Description","blood",pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);
        TestType t4 = new TestType("TEST4","Description","blood",pcList, Constants.BLOOD_EXTERNAL_ADAPTER_2);

        differentSelectedTT.add(t3);
        differentSelectedTT.add(t4);

        ClinicalAnalysisLaboratory objectOnlyWithDifferentTestTypeList = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", differentSelectedTT);

        boolean resultDifferentTestTypeList = object.equals(objectOnlyWithDifferentTestTypeList);

        Assert.assertFalse(resultDifferentTestTypeList);
    }

    @Test
    public void ensureEqualsMethodObjectsFromDifferentClasses() {
        //Arrange
        ClinicalAnalysisLaboratory c1 = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);
        //Act
        boolean resultDifferentClasses = c1.equals(t1);
        //Assert
        Assert.assertFalse(resultDifferentClasses);
    }

    @Test
    public void ensureEqualsMethodNullObjectNotEqualToExistingObject() {
        //Arrange
        ClinicalAnalysisLaboratory c1 = new ClinicalAnalysisLaboratory("CAL12",
                "CAL","Lisboa","91841378811","1234567890", selectedTT);
        ClinicalAnalysisLaboratory c2 = null;
        //Act
        boolean resultWithNull = c1.equals(c2);
        //Assert
        Assert.assertFalse(resultWithNull);
    }



}