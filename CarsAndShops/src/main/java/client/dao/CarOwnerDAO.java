package client.dao;

import client.dto.CarOwner;

import java.util.List;

public interface CarOwnerDAO {
    void createTable();

    CarOwner read(final int id);

    List<CarOwner> readAll();

    void save(final String name, final String city, final int carId);

    void deleteTable();

    void delete(final int id);

    void choiceOfId();
}
