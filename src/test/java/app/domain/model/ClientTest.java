package app.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientTest {
    public Date d1;

    @Before
    public void setUp() throws ParseException {
        d1 = new SimpleDateFormat("dd/MM/yyyy").parse("08/08/2001");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNullEntries() {
        new Client(null, null, null, null, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithCitizenCardNumberEmpty() {

        new Client("", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithCitizenCardNumberWith17Digits() {

        new Client("11111111111111111", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithCitizenCardNumberWith15Digits() {

        new Client("111111111111111", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithCitizenCardNumberIsFullOfSpaces() {

        new Client(" ", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void citizenCardWithLetters() {

        new Client("A123456789101112", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }


    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNhsNumberEmpty() {

        new Client("1234567890123456", "", d1, "Male", "1111111111", "alex@gmail.com", "Alex", "12345678901");
    }


    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNhsNumberNumberWith11Digits() {

        new Client("1234567890123456", "12345678901", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNhsNumberNumberWith9Digits() {

        new Client("1234567890123456", "123456789", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNhsNumberFullOfSpaces() {

        new Client("1234567890123456", "  ", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void clientWithNhsNumberWithLetters() {

        new Client("1234567890123456", "A123456789", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithSexEmpty() {

        new Client("1234567890123456", "1234567890", d1, "", "1111111111", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithSexWithJustDigits() {

        new Client("1234567890123456", "1234567890", d1, "123124", "1111111111", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithSexOtherThanFemaleOrMaleJustLeters() {

        new Client("1234567890123456", "1234567890", d1, "awodkwq", "1111111111", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createSexFullOfSpaces() {

        new Client("1234567890123456", "1234567890", d1, "   ", "1111111111", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithTinNumberEmpty() {

        new Client("1234567890123456", "1234567890", d1, "Male", "", "alex@gmail.com", "Alex", "12345678901");
    }


    @Test(expected = IllegalArgumentException.class)
    public void createClientWithTinNumberWith11Digits() {

        new Client("1234567890123456", "1234567890", d1, "Male", "12345678901", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithTinNumberWith9Digits() {

        new Client("1234567890123456", "1234567890", d1, "Male", "123456789", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithTinNumberWithLetters() {

        new Client("1234567890123456", "1234567890", d1, "Male", "AA12345678", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithTinNumberFullOfSpaces() {

        new Client("1234567890123456", "1234567890", d1, "Male", "   ", "alex@gmail.com", "Alex", "12345678901");
    }


    @Test(expected = IllegalArgumentException.class)
    public void createClientWithEmailEmpty() {
        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "", "Alex", "12345678901");
    }


    @Test(expected = IllegalArgumentException.class)
    public void createClientWithEmailWrong() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alexgmail.com", "Alex", "12345678901");

    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithEmailFullOfSpaces() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "  ", "Alex", "12345678901");
    }


    @Test(expected = IllegalArgumentException.class)
    public void createClientWithEmptyName() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNameBiggerThan35Char() {
        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "reallyreallybignamewithmorethan35char", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNameFullOfSpaces() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "  ", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithPhoneNumberEmpty() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "");
    }


    @Test(expected = IllegalArgumentException.class)
    public void createClientWithPhoneNumberWith12Digits() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "123456789012");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithPhoneNumberWith10Digits() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "1234567890");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithPhoneNumberFullOfSpaces() {

        new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", " ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClient150yearsOld() throws ParseException {
        Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse("07/05/1870");
        new Client("1234567891222222", "1234567890", d2, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClientWithNullDate() {

        new Client("1234567891222222", "1234567890", null, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");

    }

    @Test
    public void equalsTrue() {
        Client c1 = new Client("1234567890123457", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        Client c2 = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        boolean result = c1.equals(c2);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsFalse() {
        Client c1 = new Client("1234567890123457", "1234567891", d1, "Male", "1234567891", "alex2@gmail.com", "Alex", "12345678901");
        Client c2 = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex Dias", "12345678902");
        boolean result = c1.equals(c2);
        Assert.assertFalse(result);
    }


    @Test
    public void equalsTrueWithJustCitizenCardDifferent() {
        Client c1 = new Client("1234567890123457", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        Client c2 = new Client("1234567890123456", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        boolean result = c1.equals(c2);
        Assert.assertTrue(result);
    }

    @Test
    public void equalsTrueToItself() {
        Client c1 = new Client("1234567890123457", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        Assert.assertEquals(c1, c1);
    }

    @Test
    public void equalsFalseDueToNull() {
        Client c1 = new Client("1234567890123457", "1234567890", d1, "Male", "1234567890", "alex@gmail.com", "Alex", "12345678901");
        boolean result = c1.equals(null);
        Assert.assertFalse(result);
    }


}