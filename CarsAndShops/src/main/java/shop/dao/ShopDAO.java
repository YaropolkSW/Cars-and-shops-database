package shop.dao;

import car.dto.Car;
import client.dto.CarOwner;

import java.util.List;

public interface ShopDAO {
    void createTable();

    List<Car> getAllCars(final int shopId);

    List<Car> getCarsByPrice(final int shopId, final int price);

    CarOwner readByClient(final int clientId);

    void save(final String name);

    void deleteTable();

    void delete(final int id);

    void choiceOfId();
}
