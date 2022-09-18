package car;

import java.util.List;

public interface CarDAO {
    void createTable();

    Car read(final int id);

    List<Car> readAll();

    void save(final String shopName, final String brand, final String model, final int ageOfProduce, final int price);

    void update(final int id, final int newPrice);

    void deleteTable();

    void delete(final int id);

    void choiceOfId();
}
