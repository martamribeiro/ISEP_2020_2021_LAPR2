package app.domain.adapters;

import app.domain.interfaces.SubMaxSumAlgorithms;
import app.thirdparty.BruteForceAlgorithm;

import java.io.Serializable;

/**
 * Finds the contiguous subsequence with maximum sum of an interval, through a brute-force algorithm.
 *
 * @author Marta Ribeiro 1201592
 */
public class BruteForceAlgorithmAdapter implements SubMaxSumAlgorithms, Serializable {

    /**
     * Method for getting the contiguous subsequence with maximum sum of an interval, through the brute-force algorithm
     * @param interval the interval to be analysed
     * @return the contiguous subsequence with maximum sum
     */
    @Override
    public int[] findSubMaxSum(int[] interval){
        int[] result = BruteForceAlgorithm.Max(interval);
        return result;
    }

}
