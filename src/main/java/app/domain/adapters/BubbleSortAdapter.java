package app.domain.adapters;

import app.domain.interfaces.SortAlgorithm;
import app.domain.model.Client;
import app.domain.sort.algorithms.BubbleSort;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Finds the contiguous subsequence with maximum sum of an interval, through a brute-force algorithm.
 *
 * @author Marta Ribeiro 1201592
 */
public class BubbleSortAdapter implements SortAlgorithm, Serializable {
    @Override
    public void sortClientsList(List<Client> clients, Comparator<Client> comparator) {
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSortArrayList(clients, comparator);
    }
}
