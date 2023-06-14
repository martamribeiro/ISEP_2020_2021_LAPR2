package app.domain.model;

import app.domain.shared.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpecialistDoctorTest {
    private Company company;
    private OrgRole r1;

    @Before
    public void setUp(){
        company = new Company("Many Labs", Constants.CLASS_BARCODE_API,Constants.CLASS_SORT_ALGORITHM, Constants.CLASS_SIMPLE_REGRESSION_MODEL, Constants.DATE_INTERVAL, Constants.HISTORICAL_POINTS, Constants.CONFIDENCE_LEVEL, Constants.SIGNIFICANCE_LEVEL);
        r1 = new OrgRole("Spec Doctor");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureNullIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(null, null, null,
                null, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNullRoleIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(null, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNullNameIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, null, "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNullAddressIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", null,
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNullPhoneNumberIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                null,"joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNullEmailIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890",null,"1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNullSocCodeIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com",null, "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNullDoctorIndexNumberIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmptyNameIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmptyAddressIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmptyPhoneNumberIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmptyEmailIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmptySocCodeIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmptyDoctorIndexNumberIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNameFullOfSpacesIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "          ", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithAddressFullOfSpacesIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "      ",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithPhoneNumberFullOfSpacesIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "          ","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmailFullOfSpacesIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","              ","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithSocCodeFullOfSpacesIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","    ", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithDoctorIndexNumberFullOfSpacesIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "      ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithPhoneNumberWithOtherCharsBesideNumbersIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "i2E456789O","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithEmailWithInvalidFormatIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","@joao.@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithSocCodeWithOtherCharsBesideNumbersIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","i23A", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithNameWithMoreThan35CharsIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Joao Joao Joao Matos Matos Mato", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithAddressWithMoreThan30CharsIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Moradaaaaa Moradaaaaa Moradaaaa",
                "1234567890","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithPhoneNumberWithLessThan10CharsIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "123456789","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithPhoneNumberWithMoreThan10CharsIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "12345678910","joao@gmail.com","1234", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithSocCodeWithLessThan4CharsIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","123", "123456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureSpecDocWithSocCodeWithMoreThan4CharsIsNotAllowed(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","12345", "123456");
    }

    @Test
    public void equalsTrue(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
        SpecialistDoctor sd2 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");

        boolean result = sd1.equals(sd2);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsFalse(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
        SpecialistDoctor sd2 = new SpecialistDoctor(r1, "Joana Matos", "Morada",
                "1234567891","joana@gmail.com","1234", "123457");
        boolean result = sd1.equals(sd2);
        Assert.assertFalse(result);
    }

    @Test
    public void equalsTrueToItself(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
        boolean result = sd1.equals(sd1);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsFalseDueToNull(){
        SpecialistDoctor sd1 = new SpecialistDoctor(r1, "Joao Matos", "Morada",
                "1234567890","joao@gmail.com","1234", "123456");
        boolean result = sd1.equals(null);
        Assert.assertFalse(result);
    }

}