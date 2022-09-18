package shop;

import car.Car;
import client.CarOwner;

import java.util.List;

public interface ShopDAO {
    void createTable();

    List<Car> getAllCars(final int shopId);

    List<Car> getCarsByPrice(final int shopId, final int price);

    List<Car> readAllByPrice(final int price);

    CarOwner readByClient(final int clientId);

    List<Car> readAll();

    void save(final String name);

    void deleteTable();

    void delete(final int id);

    void choiceOfId();
}
