package app.domain.sort.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BubbleSort {
    public <T> void bubbleSortArrayList(List<T> list, Comparator<? super T> comparator) {
        long start2 = System.nanoTime();
        T temp;
        int k = list.size();
        for (int i = 0; i < k-1; i++) {
            for (int j = 0; j < (k-i-1); j++) {
                if (comparator.compare(list.get(j),list.get(j + 1)) > 0) {
                    temp = list.get(j); // temp = arr[j]
                    list.set(j, list.get(j + 1)); // arr[j] = arr[j+1]
                    list.set(j + 1, temp); // arr[j+1] =temp = arr[j]
                }
            }
        }
        long end2 = System.nanoTime();
        System.out.println("Elapsed Time in milli seconds(Bubble sort): "+ (end2-start2) + "ns for input size:" + list.size());
    }
}
