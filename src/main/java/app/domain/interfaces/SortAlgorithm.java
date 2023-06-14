package app.domain.interfaces;

import app.domain.model.Client;

import java.util.Comparator;
import java.util.List;

public interface SortAlgorithm {

    public abstract void sortClientsList(List<Client> clients, Comparator<Client> comparator);
}
