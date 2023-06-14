package app.domain.sort.comparators;

import app.domain.model.Client;

import java.util.Comparator;

public class alphabeticalNameClient implements Comparator<Client> {
    @Override
    public int compare(Client o1, Client o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
