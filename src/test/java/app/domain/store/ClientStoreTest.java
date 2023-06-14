package app.domain.store;

import app.domain.model.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientStoreTest {
    public Date d1;

    @Before
    public void setUp() throws ParseException {
        d1 = new SimpleDateFormat("dd/MM/yyyy").parse("08/08/2001");
    }


    @Test
    public void createClientStoreWithSomeElements() {

        ClientStore cs1 = new ClientStore();

        Client cl1 = cs1.registerClient("1234567890123457","1234567890",d1,"Male","1234567891","alex1@gmail.com","Alex", "12345678901");
        cs1.saveClient(cl1);

        Client cl2 = cs1.registerClient("1234567890123458","1234567891",d1,"Female","1234567892","alex2@gmail.com","Alex", "12345678902");
        cs1.saveClient(cl2);

        Client cl3 =cs1.registerClient("1234567890123459","1234567892",d1,"Male","1234567893","alex3@gmail.com","Alex", "12345678903");
        cs1.saveClient(cl3);

        Assert.assertEquals(3, cs1.getClients().size());

    }

    @Test
    public void createClientStoreWithSomeElementsButTwoAreTheSame() throws ParseException {

        Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse("08/08/2001");

        ClientStore cs1 = new ClientStore();

        Client cl1 = cs1.registerClient("1234567890123457","1234567890",d1,"Male","1234567891","alex1@gmail.com","Alex", "12345678901");
        cs1.saveClient(cl1);

        Client cl2 = cs1.registerClient("1234567890123457","1234567891",d1,"Female","1234567892","alex2@gmail.com","Alex", "12345678901");
        cs1.saveClient(cl2);

        Client cl3 =cs1.registerClient("1234567890123459","1234567892",d1,"Male","1234567893","alex3@gmail.com","Alex", "12345678900");
        cs1.saveClient(cl3);

        Assert.assertEquals(2, cs1.getClients().size());

    }

    @Test
    public void createClientStoreWithNullParameter() throws ParseException {

        Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse("08/08/2001");

        ClientStore cs1 = new ClientStore();

        Client cl1 = cs1.registerClient("1234567890123457","1234567890",d1,"Male","1234567891","alex1@gmail.com","Alex", "12345678901");
        cs1.saveClient(cl1);

        boolean c  = cs1.saveClient(null);

        Assert.assertFalse(c);

    }

}