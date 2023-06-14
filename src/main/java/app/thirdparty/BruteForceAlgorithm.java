package app.thirdparty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Brute-force algorithm implementation, used to find the contiguous subsequence with maximum sum of an interval.
 *
 * @author Marta Ribeiro 1201592
 */
public class BruteForceAlgorithm implements Serializable {

    /**
     * Searches for the contiguous subsequence with maximum sum of a sequence
     * @param seq the sequence to be analysed
     * @return the contiguous subsequence with maximum sum
     */
    public static int[] Max(int[] seq){
        ArrayList<Integer> subMaxSum = new ArrayList<>();
        int sumValue = 0;
        int num = 0;
        for (int i = 0; i < seq.length; i++) {
            for (int j = 0; j < seq.length; j++) {
                if (i<j) {
                    for (int k = i; k <= j; k++) {
                        num=num+seq[k];
                    }
                    if (num > sumValue) {
                        subMaxSum.clear();
                        for (int l = i; l <= j; l++) {
                            subMaxSum.add(seq[l]);
                        }
                        sumValue=num;
                    }
                    num=0;
                }
            }
        }
        int[] finalSubMaxSum = new int[subMaxSum.size()];
        for (int i = 0; i < finalSubMaxSum.length; i++) {
            finalSubMaxSum[i] = subMaxSum.get(i).intValue();
        }
        return finalSubMaxSum;
    }
}
