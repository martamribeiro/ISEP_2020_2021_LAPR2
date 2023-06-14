/*
package app.controller;

import app.domain.model.Client;
import app.domain.sort.algorithms.BubbleSort;
import app.domain.sort.algorithms.InsertionSort;
import app.domain.sort.comparators.ascendTinClient;
import app.domain.store.TestStore;
import app.mappers.dto.ClientDTO;
import app.ui.console.utils.TestFileUtils;
import app.mappers.dto.TestFileDTO;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConsultTestByClientTest {

     static ImportTestController importTestController;
     static List<Client> clients;

    @BeforeClass
    public static void setUp() throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException, BarcodeException, OutputException, IOException {
        importTestController = new ImportTestController();
        TestFileUtils testFileUtils = new TestFileUtils();
        List<TestFileDTO> procedData = testFileUtils.getTestsDataToDto("./inputs_mdisc/TESTS_6060INPUTS.CSV");
        for (TestFileDTO testData : procedData) {
            try{
                importTestController.importTestFromFile(testData);
            }catch (Exception e){ }

        }
        clients = App.getInstance().getCompany().getClientStore().getClients();
        Collections.shuffle(clients);
        TestStore testStore = App.getInstance().getCompany().getTestStore();
        CreateTestController createTestController = new CreateTestController("001WA");
        List<String> params = new ArrayList<>();
        params.add("WBC00");
        params.add("RBC00");
        createTestController.createTest("123456789012", "2100000004", "Blood", params);
        createTestController.saveTest();
        App.getInstance().getCompany().getTestStore().getTestsByClient(App.getInstance().getCompany().getClientStore().getClientByTinNumber("2100000004")).get(1).setDateOfValidation(new Date());

}

    @Test
    public void runtimeTestAForInsertion30Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            input.add(clients.get(i));
        }
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestBForInsertion60Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 60; i++){
            input.add(clients.get(i));
        }
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestCForInsertion240Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 240; i++){
            input.add(clients.get(i));
        }
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestDForInsertion480Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 480; i++){
            input.add(clients.get(i));
        }
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestEForInsertion900Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 900; i++){
            input.add(clients.get(i));
        }
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestFForInsertion1800Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 1800; i++){
            input.add(clients.get(i));
        }
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestGForInsertion3000Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 3000; i++){
            input.add(clients.get(i));
        }
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestHForInsertion6000Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(clients, new ascendTinClient());
    }

    @Test
    public void runtimeTestIForBubble30Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            input.add(clients.get(i));
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestJForBubble60Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 60; i++){
            input.add(clients.get(i));
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestKForBubble240Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 240; i++){
            input.add(clients.get(i));
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestLForBubble480Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 480; i++){
            input.add(clients.get(i));
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestMForBubble900Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 900; i++){
            input.add(clients.get(i));
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestNForBubble1800Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 1800; i++){
            input.add(clients.get(i));
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestOForBubble3000Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        List<Client> input = new ArrayList<>();
        for(int i = 0; i < 3000; i++){
            input.add(clients.get(i));
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(input, new ascendTinClient());
    }
    @Test
    public void runtimeTestPForBubble6000Inputs() throws ClassNotFoundException, InstantiationException, BarcodeException, IllegalAccessException {
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(clients, new ascendTinClient());
    }

}
*/
