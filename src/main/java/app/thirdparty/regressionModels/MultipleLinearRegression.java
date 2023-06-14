package app.thirdparty.regressionModels;

import java.io.Serializable;

/**
 *  The code MultipleLinearRegression class performs a multiple linear regression
 *  on an set of n data points (x1_i, x2_i, y_i).
 *  That is, it fits a straight line y = b0 + b1 * x1 + b2 * x2,
 *  (where y is the response variable, x1 and x2 are the predictor variables,
 *  b0 is the y-intercept, and b1 and b2 the coefficients of the
 *  indepedent variables x1 and x2).
 *
 *  It  computes associated statistics, including the coefficient of
 *  determination R^2 and the R^2 adjusted.
 *
 * @author Ana Albergaria
 */
public class MultipleLinearRegression implements Serializable {
    /**
     * The first regression coefficient.
     */
    private double b0;
    /**
     * The second regression coefficient.
     */
    private double b1;
    /**
     * The third regression coefficient.
     */
    private double b2;
    /**
     * The vector containing the regression coefficients.
     */
    private double[] regressionCoefficients;
    /**
     * The number of observations.
     */
    private int n;
    /**
     * The coefficient of determination.
     */
    private double r2;
    /**
     * The r2 adjusted.
     */
    private double r2Adjusted;
    /**
     * The regression sum of squares.
     */
    private double sr;
    /**
     * The residual sum of squares.
     */
    private double se;
    /**
     * The variance.
     */
    private double svar;
    /**
     * The inverse of the xTx matrix.
     */
    private double[][] xTxInverse;
    /**
     * The number of the Regression Coefficients of the Multiple Linear Regression.
     * (it doesn't include B0)
     *
     */
    private static final int NUM_REG_COEFFICIENTS = 2;

    /**
     * Performs a linear regression on the data points (x1_i, x2_i, y_i).
     *
     * @param x1 the values of the first independent variable
     * @param x2 the values of the second independent variable
     * @param y the values of the dependent variable
     * @throws IllegalArgumentException if the array lengths aren't equal
     */
    public MultipleLinearRegression(double[] x1, double[] x2, double[] y) {
        if (x1.length != x2.length || x1.length != y.length) {
            throw new IllegalArgumentException("Array lengths are not equal!");
        }
        n = x1.length;

        //determine X
        double[][] x = new double[x1.length][3];
        for (int i = 0; i < x.length; i++) {
            x[i][0] = 1;
            x[i][1] = x1[i];
            x[i][2] = x2[i];
        }

        //determine xT (transpose of the matrix X)
        double[][] xT = transposeMatrix(x);

        //determine xTx
        double[][] xTx = matrixMultiplication(xT, x);

        //determine inverse of xTx
        xTxInverse = invert(xTx);

        //determine xTy
        double[] xTy = matrixWithVectorMultiplication(xT, y);

        //determine the vector containing the regression coefficients
        regressionCoefficients = matrixWithVectorMultiplication(xTxInverse, xTy);

        b0 = regressionCoefficients[0];
        b1 = regressionCoefficients[1];
        b2 = regressionCoefficients[2];

        double ybar = mean(y);

        //regressionCoefficientsT = regressionCoefficients (in Code!)
        //determine ^BTxTy
        double regressionsCoefficientsTxTy = vectorWithVectorMultiplication(regressionCoefficients, xTy);

        //y = yT in Code!
        //determine yTy
        double yTy = vectorWithVectorMultiplication(y, y);

        //determine SQr
        sr = regressionsCoefficientsTxTy - (n * Math.pow(ybar, 2));
        //determine SQe
        se = yTy - regressionsCoefficientsTxTy;
        //determine SQt
        double st = sr + se;

        //determine r2 and r2Adjusted
        r2 = sr / st;
        r2Adjusted = 1 - ((n-1.0) / (n-(NUM_REG_COEFFICIENTS+1)) * (1-r2));

        //determine svar == MQe!
        svar = se / (n - (NUM_REG_COEFFICIENTS + 1));

    }

    /**
     * Returns the first regression coefficient.
     *
     * @return the first regression coefficient
     */
    public double getB0() {
        return b0;
    }

    /**
     * Returns the second regression coefficient.
     *
     * @return the second regression coefficient
     */
    public double getB1() {
        return b1;
    }

    /**
     * Returns the third regression coefficient.
     *
     * @return the third regression coefficient
     */
    public double getB2() {
        return b2;
    }

    /**
     * Returns the coefficient of determination.
     *
     * @return the coefficient of determination
     */
    public double getR2() {
        return r2;
    }

    /**
     * Returns the R2 adjusted.
     *
     * @return the R2 adjusted
     */
    public double getR2Adjusted() {
        return r2Adjusted;
    }

    /**
     * Returns the regression sum of squares.
     *
     * @return the regression sum of squares
     */
    public double getSR() {
        return sr;
    }

    /**
     * Returns the residual sum of squares.
     *
     * @return the residual sum of squares
     */
    public double getSE() {
        return se;
    }

    /**
     * Returns the number of observations.
     *
     * @return the number of observations
     */
    public int getN() {
        return n;
    }

    /**
     * Returns the expected response y given the value of the predictors
     * variables x1 and x2.
     *
     * @param x1 first independent variable
     * @param x2 second independent variable
     *
     * @return the expected response y given the value of the predictors variables x1 and x2
     */
    public double predict(double x1, double x2) {
        return b0 + (b1*x1) + (b2*x2);
    }

    /**
     * Returns the observed value regarding a certain regression coefficient,
     * defined by its index (indexB)
     *
     * @param indexB the index to determine which is the regression coefficient
     *
     * @return the observed value regarding a certain regression coefficient
     */
    public double calculateTObsBj(int indexB) {
        double cjj = xTxInverse[indexB][indexB];
        return regressionCoefficients[indexB] / Math.sqrt(svar * cjj);
    }

    /**
     * Returns the partial value of the error (without the critical value)
     * of a Confidence Interval.
     *
     * @param x0T the x0T vector to obtain the predicted value y0
     *
     * @return the partial value of the error (without the critical value) of a Confidence Interval.
     */
    public double calculateAuxDelta(double[] x0T) {
        //determine x0T * C
        double[] x0TC = vectorWithMatrixMultiplication(x0T, xTxInverse);
        //determine x0T * C * x0
        double x0TCx0 = vectorWithVectorMultiplication(x0TC, x0T);

        return Math.sqrt(svar * (1.0 + x0TCx0));
    }

    /**
     * Returns the tranpose of a matrix received by parameter.
     *
     * @param x the matrix to be transposed
     *
     * @return the transpose of the matrix x
     */
    public double[][] transposeMatrix(double[][] x) {
        int rowX = x.length, columnX = x[0].length;
        double[][] xT = new double[columnX][rowX];

        for (int i = 0; i < rowX; i++) {
            for (int j = 0; j < columnX; j++) {
                xT[j][i] = x[i][j];
            }
        }
        return xT;
    }

    /**
     * Returns the result of a multiplication between two matrices - a matrix.
     *
     * @param matrix first matrix
     * @param otherMatrix second matrix
     *
     * @return result of a multiplication between two matrices - a matrix
     */
    public double[][] matrixMultiplication(double[][] matrix, double[][] otherMatrix) {
        if(matrix[0].length != otherMatrix.length)
            throw new IllegalArgumentException("The multiplication is not possible with " + matrix[0].length + " columns from the" +
                    " first matrix and " + otherMatrix.length + " lines from the second matrix!");

        double[][] result = new double[matrix.length][otherMatrix[0].length];

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < otherMatrix[0].length; column++) {
                for (int k = 0; k < matrix[0].length; k++) {
                    result[row][column] += matrix[row][k] * otherMatrix[k][column];
                }
            }
        }
        return result;
    }

    /**
     * Returns the result of a multiplication between matrix and a vector - a vector.
     *
     * @param matrix the matrix
     * @param vector the vector
     *
     * @return result of a multiplication between matrix and a vector - a vector
     */
    public double[] matrixWithVectorMultiplication(double[][] matrix, double[] vector) {
        if(matrix[0].length != vector.length)
            throw new IllegalArgumentException("The multiplication is not possible with " + matrix[0].length + " columns from the " +
                    "matrix and " + vector.length + " lines from the vector!");

        int rows = matrix.length;
        int columns = matrix[0].length;

        double[] result = new double[rows];

        for (int row = 0; row < rows; row++) {
            double sum = 0;
            for (int column = 0; column < columns; column++) {
                sum += matrix[row][column] * vector[column];
            }
            result[row] = sum;
        }
        return result;
    }

    /**
     * Returns the result of a multiplication between two vectors - a value.
     *
     * @param vector first vector
     * @param otherVector second vector
     *
     * @return result of a multiplication between two vectors - a value
     */
    public double vectorWithVectorMultiplication(double[] vector, double[] otherVector) {
        if(vector.length != otherVector.length)
            throw new IllegalArgumentException("The multiplication is not possible with " + vector.length + " columns from the " +
                    "first vector and " + otherVector.length + " lines from the second vector!");

        double result = 0;
        for (int i = 0; i < vector.length; i++) {
            result += vector[i] * otherVector[i];
        }

        return result;
    }

    /**
     * Returns the result of a multiplication between vector and a matrix - a vector.
     *
     * @param vector the vector
     * @param matrix the matrix
     *
     * @return the result of a multiplication between vector and a matrix - a vector
     */
    public double[] vectorWithMatrixMultiplication(double[] vector, double[][] matrix) {
        if(vector.length != matrix.length)
            throw new IllegalArgumentException("The multiplication is not possible with " + vector.length + " columns from the " +
                    "vector and " + matrix.length + " lines from the matrix!");

        int columns = matrix[0].length;

        double[] result = new double[columns];

        for (int column = 0; column < columns; column++) {
            double sum = 0;
            for (int i = 0; i < columns; i++) {
                sum += vector[i] * matrix[i][column];
            }
            result[column] = sum;
        }

        return result;
    }

    /**
     * Returns the inverse of a matrix a, received by parameter
     *
     * @param a matrix to be inverted
     *
     * @return inverse of the matrix a
     */
    private double[][] invert(double a[][]) {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i) {
            x[n-1][i] = b[index[n-1]][i] / a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    /**
     * Method to carry out the partial-pivoting Gaussian
     * elimination.  Here index[] stores pivoting order.
     *
     * @param a matrix
     * @param index vector to store pivoting order
     */
    private void gaussian(double[][] a, int[] index) {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) {
            double c1 = 0;
            for (int j=0; j<n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n-1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j+1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];
                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
                // Modify other elements accordingly
                for (int l = j+1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }

    /**
     * Returns the mean of the values of the vector x,
     * received by parameter.
     *
     * @param x values of the vector
     *
     * @return the mean of the values of the vector x
     */
    public double mean(double[] x) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i];
        }
        return sum / x.length;
    }

    /**
     * Returns a string representation of the multiple linear regression model.
     *
     * @return a string representation of the multiple linear regression model,
     *         including the regression equation and R^2
     */
    @Override
    public String toString() {
        return String.format("^y=%f + %fx1 + %fx2%nR2 = %s", b0, b1, b2, r2);
    }

}
