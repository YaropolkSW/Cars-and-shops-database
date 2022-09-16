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

public class CarDAOImpl implements CarDAO {
    private final static String PATH_TO_PROPERTIES = "src/main/resources/carDatabasePatterns.properties";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String FILE_NOT_FOUND_ERROR_MESSAGE = "Файл не найден!";
    private final static String CAR_ID = "car_id";
    private final static String SHOP_NAME = "shop_name";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String PRICE = "price";

    private final String READ_LINE_PATTERN;
    private final String READ_ALL_LINES_PATTERN;
    private final String READ_ALL_ID_PATTERN;
    private final String SAVE_CAR_LINE_PATTERN;
    private final String UPDATE_LINE_PATTERN;
    private final String DELETE_TABLE_PATTERN;
    private final String DELETE_LINE_PATTERN;
    private final String NEXT_LINE_PATTERN;

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final StatementFactory statementFactory = new StatementFactory();

    {
        final Properties properties = new Properties();

        try (final FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND_ERROR_MESSAGE);
        }

        READ_LINE_PATTERN = properties.getProperty("readLine");
        READ_ALL_LINES_PATTERN = properties.getProperty("readAllLines");
        READ_ALL_ID_PATTERN = properties.getProperty("readAllId");
        SAVE_CAR_LINE_PATTERN = properties.getProperty("saveCarLine");
        UPDATE_LINE_PATTERN = properties.getProperty("updateCarLine");
        DELETE_TABLE_PATTERN = properties.getProperty("deleteTable");
        DELETE_LINE_PATTERN = properties.getProperty("deleteCarLine");
        NEXT_LINE_PATTERN = properties.getProperty("nextLine");
    }

    @Override
    public void createTable() {
        new DAO();
    }

    @Override
    public Car read(final int id) {
        final Car car = new Car();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(READ_LINE_PATTERN, id));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                car.setId(id);
                car.setShop(resultSet.getString(SHOP_NAME));
                car.setBrand(resultSet.getString(BRAND));
                car.setModel(resultSet.getString(MODEL));
                car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
                car.setPrice(resultSet.getInt(PRICE));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return car;
    }

    @Override
    public List<Car> readAll() {
        final List<Car> cars = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection, READ_ALL_LINES_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Car car = new Car();
                car.setId(resultSet.getInt(CAR_ID));
                car.setShop(resultSet.getString(SHOP_NAME));
                car.setBrand(resultSet.getString(BRAND));
                car.setModel(resultSet.getString(MODEL));
                car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
                car.setPrice(resultSet.getInt(PRICE));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return cars;
    }

    @Override
    public void save(final String shopName,
                     final String brand,
                     final String model,
                     final int ageOfProduce,
                     final int price) {

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SAVE_CAR_LINE_PATTERN, shopName, brand, model, ageOfProduce, price));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void update(final int id, final int newPrice) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(UPDATE_LINE_PATTERN, newPrice, id));

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
                idList.add(resultSet.getInt(CAR_ID));
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
