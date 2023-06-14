package app.domain.sort.comparators;

import app.domain.model.Client;

import java.util.Comparator;

public class ascendTinClient implements Comparator<Client> {

    @Override
    public int compare(Client o1, Client o2) {
        int tin1 = Integer.parseInt(o1.getTinNumber());
        int tin2 = Integer.parseInt(o2.getTinNumber());

        return Integer.compare(tin1, tin2);
    }
}
