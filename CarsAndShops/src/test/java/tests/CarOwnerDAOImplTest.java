package tests;

import client.dao.CarOwnerDAOImpl;
import dao.Migration;
import factory.ConnectionFactory;
import factory.StatementFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarOwnerDAOImplTest {
    private final static String PATH_OF_CONNECTION_PROPERTIES = "src/test/resources/connectionForTest.properties";
    private final static String PATH_TO_SQL_FILE = "src/test/resources/initialMigrationForTest.sql";

    private final ConnectionFactory connectionFactory = new ConnectionFactory(PATH_OF_CONNECTION_PROPERTIES);
    private final StatementFactory statementFactory = new StatementFactory();

    private final CarOwnerDAOImpl carOwnerDAO = new CarOwnerDAOImpl(connectionFactory, statementFactory);
    private final Migration migration = new Migration(PATH_TO_SQL_FILE, connectionFactory, statementFactory);

    @BeforeEach
    public void createTable() {
        migration.initialMigration();
    }

    @Test
    public void readByCarShouldReturnCorrectInformation() {
        final String expectedOwner = "ID - 1\nName - Василий Ф.И.\nCity - Москва\nCar - Subaru Outback";

        final String actualOwner = carOwnerDAO.readByCar(1);

        Assertions.assertEquals(expectedOwner, actualOwner);
    }
}
