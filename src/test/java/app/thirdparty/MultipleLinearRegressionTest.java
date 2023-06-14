package app.thirdparty;

import app.thirdparty.regressionModels.MultipleLinearRegression;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MultipleLinearRegressionTest {
    private MultipleLinearRegression multipleLR;

    @Before
    public void setUp() {
        //MATCP - Teórica Regressão Linear Múltipla
        double[] x1 = {80.0, 93.0, 100.0, 82.0, 90.0, 99.0, 81.0, 96.0, 94.0, 93.0, 97.0, 95.0};
        double[] x2 = {8.0, 9.0, 10.0, 12.0, 11.0, 8.0, 8.0, 10.0, 12.0, 11.0, 13.0, 11.0};
        double[] y1 = {2256.0, 2340.0, 2426.0, 2293.0, 2330.0, 2368.0, 2250.0, 2409.0, 2364.0, 2379.0, 2440.0, 2364.0};
        multipleLR = new MultipleLinearRegression(x1, x2, y1);
    }

    @Test
    public void transposeMatrix() {
        double[][] x = {{1,2,3,1}, {4,5,6,2}, {7,8,9,0}, {1,1,1,1}};
        double[][] expected = {{1,4,7,1}, {2,5,8,1}, {3,6,9,1}, {1,2,0,1}};

        double[][] actual = multipleLR.transposeMatrix(x);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void matrixMultiplication() {
        double[][] matrix1 = {{1,2,3,1,1}, {4,5,6,2,1}, {7,8,9,0,1}, {1,1,1,1,1}, {2,3,4,1,1}};
        double[][] matrix2 = {{1,2,3}, {2,3,4}, {1,2,1}, {2,3,4}, {2,3,4}};

        double[][] expected = {{12,20,22}, {26,44,50}, {34,59,66}, {8,13,16}, {16,27,30}};

        double[][] actual = multipleLR.matrixMultiplication(matrix1, matrix2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixMultiplicationEnsureNotPossible() {
        double[][] matrix1 = {{1,2,3,1,1}, {4,5,6,2,1}, {7,8,9,0,1}, {1,1,1,1,1}, {2,3,4,1,1}};
        double[][] matrix2 = {{1,2,3}, {2,3,4}, {1,2,1}, {2,3,4}, {2,3,4}, {2,3,4}};

        double[][] result = multipleLR.matrixMultiplication(matrix1, matrix2);
    }

    @Test
    public void matrixWithVectorMultiplication() {
        double[][] matrix = {{1,2,3,1,1}, {4,5,6,2,1}, {7,8,9,0,1}, {1,1,1,1,1}, {2,3,4,1,1}};
        double[] vector = {1,2,1,2,2};

        double[] expected = {12,26,34,8,16};

        double[] actual = multipleLR.matrixWithVectorMultiplication(matrix, vector);

        Assert.assertArrayEquals(expected, actual, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void matrixWithVectorMultiplicationNotPossible() {
        double[][] matrix = {{1,2,3,1}, {4,5,6,2}, {7,8,9,0}, {1,1,1,1}};
        double[] vector = {1,2,1,2,2};


        double[] result = multipleLR.matrixWithVectorMultiplication(matrix, vector);
    }

    @Test
    public void vectorWithVectorMultiplication() {
        double[] vector1 = {1,2,3,4,5,6};
        double[] vector2 = {1,2,1,2,2,5};

        double expected = 56;
        double actual = multipleLR.vectorWithVectorMultiplication(vector1, vector2);

        Assert.assertEquals(expected, actual, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void vectorWithVectorMultiplicationNotPossible() {
        double[] vector1 = {1,2,3,4,5,6};
        double[] vector2 = {1,2,1,2,2};


        double result = multipleLR.vectorWithVectorMultiplication(vector1, vector2);
    }

    //método a confirmar!
    @Test
    public void vectorWithMatrixMultiplication() {
        double[] vector = {21,23,56,3,0};
        double[][] matrix = {{23,1,0,20,3}, {5,6,9,13,0}, {1,2,3,4,5}, {40,8,9,76,7}, {1,2,3,4,5}};

        double[] expected = {774,295,402,1171,364};

        double[] actual = multipleLR.vectorWithMatrixMultiplication(vector, matrix);

        Assert.assertArrayEquals(expected, actual, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void vectorWithMatrixMultiplicationNotPossible() {
        double[] vector = {21,23,56,3};
        double[][] matrix = {{23,1,0,20,3}, {5,6,9,13,0}, {1,2,3,4,5}, {40,8,9,76,7}, {1,2,3,4,5}};

        double[] actual = multipleLR.vectorWithMatrixMultiplication(vector, matrix);
    }

    @Test
    public void mean() {
        double[] values = {20,3,4,5,7,4};
        double expected = 7.16666666;
        double actual = multipleLR.mean(values);

        Assert.assertEquals(expected, actual, 0.0001);

    }
}