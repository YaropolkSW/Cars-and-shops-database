package client.dao;

import client.dto.CarOwner;

import java.util.List;

public interface CarOwnerDAO {
    CarOwner read(final int id);

    String readByCar(final int id);

    List<CarOwner> readAll();

    void save(final String name, final String city, final int carId);

    void deleteTable();

    void delete(final int id);

    String choiceOfId();
}
