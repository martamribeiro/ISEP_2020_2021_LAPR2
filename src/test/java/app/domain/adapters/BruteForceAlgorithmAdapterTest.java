package app.domain.adapters;

import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BruteForceAlgorithmAdapterTest {

    private static app.domain.adapters.BruteForceAlgorithmAdapter bfa;
    private static int min;
    private static int max;

    @BeforeClass
    public static void setUp() {
        bfa = new BruteForceAlgorithmAdapter();
        min = -99;
        max = 99;
    }

    //Test 3
    @Test
    public void checkIfSubMaxSumIsFound(){
        int[] example1 = new int[]{29, -32, -9, -25, 44, 12, -61, 51, -9, 44, 74, 4};
        int[] example1Result = new int[]{51, -9, 44, 74, 4};
        int[] obtainedResult = bfa.findSubMaxSum(example1);
        Assert.assertArrayEquals(example1Result, obtainedResult);
    }

    //Test 4
    @Test
    public void checkIfSubMaxSumIsFound2(){
        int[] example2 = new int[]{17, -2, 4, 20, -44, 30};
        int[] example2Result = new int[]{17, -2, 4, 20};
        int[] obtainedResult = bfa.findSubMaxSum(example2);
        Assert.assertArrayEquals(example2Result, obtainedResult);
    }

    @Test
    public void runtimeTestAForBenchmark1Ints(){
        int num = 1;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test A1
    @Test
    public void runtimeTestAForBruteForce24Ints(){
        int num = 24;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test B1
    @Test
    public void runtimeTestBForBruteForce72Ints(){
        int num = 72;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test C1
    @Test
    public void runtimeTestCForBruteForce240Ints(){
        int num = 240;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test D1
    @Test
    public void runtimeTestDForBruteForce480Ints(){
        int num = 480;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test E1
    @Test
    public void runtimeTestEForBruteForce912Ints(){
        int num = 912;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test F1
    @Test
    public void runtimeTestFForBruteForce1800Ints(){
        int num = 1800;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test G1
    @Test
    public void runtimeTestGForBruteForce3000Ints(){
        int num = 3000;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

    //Test H1
    @Test
    public void runtimeTestHForBruteForce6000Ints(){
        int num = 6000;
        int[] input = new int[num];
        Random rand = new Random();
        int rand_int;
        for (int i = 0; i < num; i++) {
            rand_int = rand.nextInt(max+1-min)+min;
            input[i]=rand_int;
        }
        int[] output = bfa.findSubMaxSum(input);
    }

}