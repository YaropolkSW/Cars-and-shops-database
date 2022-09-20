package shop.dao;

import car.dto.Car;
import client.dto.CarOwner;
import dao.Migration;
import factory.ConnectionFactory;
import factory.StatementFactory;
import query.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopDAOImpl implements ShopDAO {
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String NEXT_LINE_PATTERN = "\n";
    private final static String SHOP_ID = "shop_id";
    private final static String CAR_ID = "car_id";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String PRICE = "price";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";
    private final static String SPACE = " ";

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final StatementFactory statementFactory = new StatementFactory();

    @Override
    public void createTable() {
        new Migration().initialMigration();
    }

    @Override
    public List<Car> getAllCars(final int shopId) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.SHOP_GET_ALL_CARS_PATTERN, shopId));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Car car = new Car();
                car.setId(resultSet.getInt(CAR_ID));
                car.setBrand(resultSet.getString(BRAND));
                car.setModel(resultSet.getString(MODEL));
                car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
                car.setPrice(resultSet.getInt(PRICE));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return cars;
    }

    @Override
    public List<Car> getCarsByPrice(final int shopId, final int price) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.SHOP_GET_ALL_CARS_BY_PRICE_PATTERN, shopId, price));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Car car = new Car();
                car.setId(resultSet.getInt(CAR_ID));
                car.setBrand(resultSet.getString(BRAND));
                car.setModel(resultSet.getString(MODEL));
                car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
                car.setPrice(resultSet.getInt(PRICE));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return cars;
    }

    @Override
    public CarOwner readByClient(final int clientId) {
        final CarOwner carOwner = new CarOwner();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.SHOP_READ_BY_CLIENT_PATTERN, clientId));

        try {
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                carOwner.setId(clientId);
                carOwner.setName(resultSet.getString(CLIENT_NAME));
                carOwner.setCity(resultSet.getString(CITY));
                carOwner.setCar(resultSet.getString(BRAND) + SPACE + resultSet.getString(MODEL));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return carOwner;
    }

    @Override
    public void save(final String name) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.SHOP_SAVE_LINE_PATTERN, name));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void deleteTable() {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                SQLQueries.SHOP_DELETE_TABLE_PATTERN);

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void delete(final int id) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.SHOP_DELETE_LINE_PATTERN, id));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    private List<Integer> readAllId() {
        List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                SQLQueries.SHOP_READ_ALL_ID_PATTERN);

        final ResultSet resultSet;
        try {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                idList.add(resultSet.getInt(SHOP_ID));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return idList;
    }

    @Override
    public void choiceOfId() {
        System.out.println(CHOICE_OF_ID_PATTERN + NEXT_LINE_PATTERN + readAllId());
    }
}
