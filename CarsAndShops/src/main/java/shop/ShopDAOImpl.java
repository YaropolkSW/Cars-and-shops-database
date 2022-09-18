package shop;

import car.Car;
import car.CarDAOImpl;
import client.CarOwner;
import dao.Migration;
import factory.ConnectionFactory;
import factory.StatementFactory;
import pattern.InitialPatterns;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopDAOImpl implements ShopDAO {
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String SHOP_ID = "shop_id";
    private final static String SHOP_NAME = "shop_name";
    private final static String CAR_ID = "car_id";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String PRICE = "price";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final StatementFactory statementFactory = new StatementFactory();
    private final CarDAOImpl carDAO = new CarDAOImpl();



    @Override
    public void createTable() {
        new Migration().initialMigration();
    }

    @Override
    public List<Car> getAllCars(final int shopId) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement firstStatement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.SHOP_READ_LINE_PATTERN, shopId));

        try {
            final ResultSet firstResultSet = firstStatement.executeQuery();
            while (firstResultSet.next()) {

                final String shopName = firstResultSet.getString(SHOP_NAME);

                final PreparedStatement secondStatement = statementFactory.createStatement(connection,
                        String.format(InitialPatterns.SHOP_GET_ALL_CARS_PATTERN, shopName));

                final ResultSet secondResultSet = secondStatement.executeQuery();

                while (secondResultSet.next()) {
                    final Car car = new Car();
                    car.setShop(secondResultSet.getString(SHOP_NAME));
                    car.setId(secondResultSet.getInt(CAR_ID));
                    car.setBrand(secondResultSet.getString(BRAND));
                    car.setModel(secondResultSet.getString(MODEL));
                    car.setAgeOfProduce(secondResultSet.getInt(AGE_OF_PRODUCE));
                    car.setPrice(secondResultSet.getInt(PRICE));
                    cars.add(car);
                }
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
        final PreparedStatement firstStatement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.SHOP_READ_LINE_PATTERN, shopId));

        try {
            final ResultSet firstResultSet = firstStatement.executeQuery();
            while (firstResultSet.next()) {

                final PreparedStatement secondStatement = statementFactory.createStatement(connection,
                        String.format(InitialPatterns.SHOP_GET_ALL_CARS_BY_PRICE_PATTERN, price));

                final ResultSet secondResultSet = secondStatement.executeQuery();

                while (secondResultSet.next()) {
                    final Car car = new Car();
                    car.setShop(secondResultSet.getString(SHOP_NAME));
                    car.setId(secondResultSet.getInt(CAR_ID));
                    car.setBrand(secondResultSet.getString(BRAND));
                    car.setModel(secondResultSet.getString(MODEL));
                    car.setAgeOfProduce(secondResultSet.getInt(AGE_OF_PRODUCE));
                    car.setPrice(secondResultSet.getInt(PRICE));
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return cars;
    }

    @Override
    public List<Car> readAllByPrice(final int price) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.SHOP_READ_ALL_BY_PRICE_PATTERN, price));

        try {
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final Car car = new Car();
                car.setShop(resultSet.getString(SHOP_NAME));
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
                String.format(InitialPatterns.SHOP_READ_BY_CLIENT_PATTERN, clientId));

        try {
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                carOwner.setId(clientId);
                carOwner.setName(resultSet.getString(CLIENT_NAME));
                carOwner.setCity(resultSet.getString(CITY));
                carOwner.setCar(carDAO.read(resultSet.getInt(CAR_ID)).toString());
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return carOwner;
    }

    @Override
    public List<Car> readAll() {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                InitialPatterns.SHOP_READ_ALL_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final Car car = new Car();
                car.setShop(resultSet.getString(SHOP_NAME));
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
    public void save(final String name) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.SHOP_SAVE_LINE_PATTERN, name));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void deleteTable() {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                InitialPatterns.SHOP_DELETE_TABLE_PATTERN);

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void delete(final int id) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.SHOP_DELETE_LINE_PATTERN, id));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    private List<Integer> readAllId() {
        List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                InitialPatterns.SHOP_READ_ALL_ID_PATTERN);

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
        System.out.println(CHOICE_OF_ID_PATTERN + InitialPatterns.NEXT_LINE_PATTERN + readAllId());
    }
}
