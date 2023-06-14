package app.domain.store;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class NHSReportStoreTest {

    NHSReportStore nhsReportStore = new NHSReportStore();

    @Test
    public void getWeeksColumnToTableOfValuesTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("16/06/2021", "15/06/2021", "14/06/2021", "12/06/2021", "11/06/2021", "10/06/2021", "09/06/2021", "08/06/2021", "07/06/2021", "05/06/2021", "04/06/2021", "03/06/2021"));
        List<String> actual = nhsReportStore.getDatesColumnToTableOfValues(12, sdf.parse("16/6/2021"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getWeekTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String expected = "07/06/2021-12/06/2021";
        String actual = nhsReportStore.getWeek(sdf.parse("07/06/2021"), sdf.parse("12/06/2021"));
        Assert.assertEquals(expected, actual);
    }

}