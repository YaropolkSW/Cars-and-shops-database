package shop.dao;

import car.dto.Car;
import client.dto.CarOwner;
import factory.ConnectionFactory;
import factory.StatementFactory;
import lombok.RequiredArgsConstructor;
import query.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ShopDAOImpl implements ShopDAO {
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String NEXT_LINE_PATTERN = "\n";
    private final static String SHOP_NAME = "shop_name";
    private final static String SHOP_ID = "shop_id";
    private final static String CAR_ID = "car_id";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String PRICE = "price";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";
    private final static String SPACE = " ";

    private final ConnectionFactory connectionFactory;
    private final StatementFactory statementFactory;

    @Override
    public List<String> getAllShops() {
        final List<String> shops = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection, SQLQueries.SHOP_GET_ALL_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                shops.add(resultSet.getString(SHOP_NAME));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return shops;
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
                final int carId = resultSet.getInt(CAR_ID);
                final String brand = resultSet.getString(BRAND);
                final String model = resultSet.getString(MODEL);
                final int ageOfProduce = resultSet.getInt(AGE_OF_PRODUCE);
                final int price = resultSet.getInt(PRICE);
                cars.add(new Car(carId, brand, model, ageOfProduce, price));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return cars;
    }

    @Override
    public List<Car> getCarsByPrice(final int shopId, final int maxPrice) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.SHOP_GET_ALL_CARS_BY_PRICE_PATTERN, shopId, maxPrice));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final int carId = resultSet.getInt(CAR_ID);
                final String brand = resultSet.getString(BRAND);
                final String model = resultSet.getString(MODEL);
                final int ageOfProduce = resultSet.getInt(AGE_OF_PRODUCE);
                final int price = resultSet.getInt(PRICE);
                cars.add(new Car(carId, brand, model, ageOfProduce, price));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return cars;
    }

    @Override
    public CarOwner readByClient(final int clientId) {
        CarOwner carOwner = null;
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.SHOP_READ_BY_CLIENT_PATTERN, clientId));

        try {
            final ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final String clientName = resultSet.getString(CLIENT_NAME);
                final String clientCity = resultSet.getString(CITY);
                final String car = resultSet.getString(BRAND) + SPACE + resultSet.getString(MODEL);
                carOwner = new CarOwner(clientId, clientName, clientCity, car);
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
    public String choiceOfId() {
        return (CHOICE_OF_ID_PATTERN + NEXT_LINE_PATTERN + readAllId());
    }
}
