package dao;

import factory.ConnectionFactory;
import factory.StatementFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ShopDAOImpl implements ShopDAO {
    private final static String PATH_TO_PROPERTIES = "src/main/resources/shopDatabasePatterns.properties";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String FILE_NOT_FOUND_ERROR_MESSAGE = "Файл не найден!";
    private final static String SHOP_ID = "shop_id";
    private final static String SHOP_NAME = "shop_name";
    private final static String CAR_ID = "car_id";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String PRICE = "price";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";

    private final String READ_LINE_PATTERN;
    private final String READ_BY_CLIENT;
    private final String READ_ALL;
    private final String READ_ALL_BY_PRICE;
    private final String READ_ALL_IN_SHOP;
    private final String READ_ALL_IN_SHOP_BY_PRICE;
    private final String READ_ALL_ID_PATTERN;
    private final String SAVE_SHOP_LINE_PATTERN;
    private final String DELETE_TABLE_PATTERN;
    private final String DELETE_LINE_PATTERN;
    private final String NEXT_LINE_PATTERN;

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final StatementFactory statementFactory = new StatementFactory();
    private final CarDAOImpl carDAO = new CarDAOImpl();

    {
        final Properties properties = new Properties();

        try (final FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND_ERROR_MESSAGE);
        }

        READ_LINE_PATTERN = properties.getProperty("readLine");
        READ_BY_CLIENT = properties.getProperty("readByClient");
        READ_ALL = properties.getProperty("readAll");
        READ_ALL_BY_PRICE = properties.getProperty("readAllByPrice");
        READ_ALL_IN_SHOP = properties.getProperty("readAllInShop");
        READ_ALL_IN_SHOP_BY_PRICE = properties.getProperty("readAllInShopByPrice");
        READ_ALL_ID_PATTERN = properties.getProperty("readAllId");
        SAVE_SHOP_LINE_PATTERN = properties.getProperty("saveShopLine");
        DELETE_TABLE_PATTERN = properties.getProperty("deleteTable");
        DELETE_LINE_PATTERN = properties.getProperty("deleteShopLine");
        NEXT_LINE_PATTERN = properties.getProperty("nextLine");
    }

    @Override
    public void createTable() {
        new DAO();
    }

    @Override
    public List<Car> readAllInShop(final int shopId) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement firstStatement = statementFactory.createStatement(connection,
                String.format(READ_LINE_PATTERN, shopId));

        try {
            final ResultSet firstResultSet = firstStatement.executeQuery();
            while (firstResultSet.next()) {

                final PreparedStatement secondStatement = statementFactory.createStatement(connection, READ_ALL_IN_SHOP);
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
    public List<Car> readInShopByPrice(final int shopId, final int price) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement firstStatement = statementFactory.createStatement(connection,
                String.format(READ_LINE_PATTERN, shopId));

        try {
            final ResultSet firstResultSet = firstStatement.executeQuery();
            while (firstResultSet.next()) {

                final PreparedStatement secondStatement = statementFactory.createStatement(connection,
                        String.format(READ_ALL_IN_SHOP_BY_PRICE, price));

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
                String.format(READ_ALL_BY_PRICE, price));

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
    public Client readByClient(final int clientId) {
        final Client client = new Client();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(READ_BY_CLIENT, clientId));

        try {
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                client.setId(clientId);
                client.setName(resultSet.getString(CLIENT_NAME));
                client.setCity(resultSet.getString(CITY));
                client.setCar(carDAO.read(resultSet.getInt(CAR_ID)).toString());
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return client;
    }

    @Override
    public List<Car> readAll() {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection, READ_ALL);

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
                String.format(SAVE_SHOP_LINE_PATTERN, name));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void deleteTable() {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection, DELETE_TABLE_PATTERN);

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void delete(final int id) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(DELETE_LINE_PATTERN, id));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    private List<Integer> readAllId() {
        List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection, READ_ALL_ID_PATTERN);

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
