package app.domain.adapters;

import app.domain.interfaces.SubMaxSumAlgorithms;
import com.isep.mdis.Sum;

import java.io.Serializable;

/**
 * Finds the contiguous subsequence with maximum sum of an interval, through a benchmark algorithm.
 *
 * @author Marta Ribeiro 1201592
 */
public class BenchmarkAlgorithmAdapter implements SubMaxSumAlgorithms, Serializable {

    /**
     * Method for getting the contiguous subsequence with maximum sum of an interval, through the benchmark algorithm
     * @param interval the interval to be analysed
     * @return the contiguous subsequence with maximum sum
     */
    @Override
    public int[] findSubMaxSum(int[] interval){
        int[] result = Sum.Max(interval);
        return result;
    }

}