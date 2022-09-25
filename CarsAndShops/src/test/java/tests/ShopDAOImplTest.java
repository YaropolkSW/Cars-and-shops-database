package tests;

import car.dto.Car;
import dao.Migration;
import factory.ConnectionFactory;
import factory.StatementFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.dao.ShopDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class ShopDAOImplTest {
    private final static String PATH_OF_CONNECTION_PROPERTIES = "src/test/resources/connectionForTest.properties";
    private final static String PATH_TO_SQL_FILE = "src/test/resources/initialMigrationForTest.sql";

    private final ConnectionFactory connectionFactory = new ConnectionFactory(PATH_OF_CONNECTION_PROPERTIES);
    private final StatementFactory statementFactory = new StatementFactory();

    private final ShopDAOImpl shopDAO = new ShopDAOImpl(connectionFactory, statementFactory);
    private final Migration migration = new Migration(PATH_TO_SQL_FILE, connectionFactory, statementFactory);

    @BeforeEach
    public void createTable() {
        migration.initialMigration();
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
