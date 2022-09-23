package tests;

import car.dto.Car;
import dao.MigrationForTest;
import factory.ConnectionFactoryForTest;
import factory.StatementFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.dao.ShopDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class ShopDAOImplTest {
    final ConnectionFactoryForTest connectionFactory = new ConnectionFactoryForTest();
    final StatementFactory statementFactory = new StatementFactory();

    final ShopDAOImpl shopDAO = new ShopDAOImpl(connectionFactory, statementFactory);

    @BeforeEach
    public void createTable() {
        new MigrationForTest(connectionFactory, statementFactory).initialMigration();
    }

    @Test
    public void getAllCarsShouldReturnCorrectInformation() {
        final List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(new Car(1, "Subaru", "Outback", 2022, 6000000));
        expectedCars.add(new Car(2, "Subaru", "Forester", 2021, 4000000));
        expectedCars.add(new Car(8, "Nissan", "Juke", 2014, 1000000));

        final List<Car> actualCars = shopDAO.getAllCars(1);

        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    public void getCarsByByPriceShouldReturnCorrectInformation() {
        final List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(new Car(2, "Subaru", "Forester", 2021, 4000000));
        expectedCars.add(new Car(8, "Nissan", "Juke", 2014, 1000000));

        final List<Car> actualCars = shopDAO.getCarsByPrice(1, 5000000);

        Assertions.assertEquals(expectedCars, actualCars);
    }
}
