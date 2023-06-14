package app.domain.adapters;

import app.domain.interfaces.SortAlgorithm;
import app.domain.model.Client;
import app.domain.sort.algorithms.InsertionSort;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class InsertionSortAdapter implements SortAlgorithm, Serializable {
    @Override
    public void sortClientsList(List<Client> clients, Comparator<Client> comparator) {
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(clients, comparator);
    }
}

