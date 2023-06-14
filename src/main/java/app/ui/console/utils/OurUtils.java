package app.ui.console.utils;

import app.mappers.dto.TestDTO;

import java.util.ArrayList;
import java.util.List;

public class OurUtils {

    public static List<String> menuToContinueOrCancel() {
        List<String> menu = new ArrayList<>();
        menu.add("Insert the data");

        return menu;
    }

    public static void showTestsListWithAllData(List<TestDTO> list, String header)
    {
        System.out.println(header);

        int index = 0;
        for (TestDTO testDto : list)
        {
            index++;

            System.out.println(index + ". " + testDto.toStringWithAllData());
        }
        System.out.println("");
        System.out.println("0 - Cancel");
    }
}
