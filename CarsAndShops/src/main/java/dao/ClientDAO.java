package dao;

import java.util.List;

public interface ClientDAO {
    void createTable();

    Client read(final int id);

    List<Client> readAll();

    void save(final String name, final String city, final int carId);

    void deleteTable();

    void delete(final int id);

    void choiceOfId();
}
