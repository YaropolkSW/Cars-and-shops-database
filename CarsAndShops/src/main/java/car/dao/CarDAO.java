package car.dao;

import car.dto.Car;

import java.util.List;

public interface CarDAO {
    void createTable();

    Car read(final int id);

    List<Car> readAll();

    List<Car> readAllByPrice(final int price);

    void save(final String brand, final String model, final int ageOfProduce, final int price);

    void update(final int id, final int newPrice);

    void deleteTable();

    void delete(final int id);

    void choiceOfId();
}
