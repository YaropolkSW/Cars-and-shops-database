package tests;

import client.dao.CarOwnerDAOImpl;
import dao.MigrationForTest;
import factory.ConnectionFactoryForTest;
import factory.StatementFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarOwnerDAOImplTest {
    final ConnectionFactoryForTest connectionFactory = new ConnectionFactoryForTest();
    final StatementFactory statementFactory = new StatementFactory();

    final CarOwnerDAOImpl carOwnerDAO = new CarOwnerDAOImpl(connectionFactory, statementFactory);

    @BeforeEach
    public void createTable() {
        new MigrationForTest(connectionFactory, statementFactory).initialMigration();
    }

    @Test
    public void readByCarShouldReturnCorrectInformation() {
        final String expectedOwner = "ID - 1\nName - Василий Ф.И.\nCity - Москва\nCar - Subaru Outback";

        final String actualOwner = carOwnerDAO.readByCar(1);

        Assertions.assertEquals(expectedOwner, actualOwner);
    }
}
