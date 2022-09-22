package car.dao;

import car.dto.Car;
import factory.ConnectionFactory;
import factory.StatementFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import query.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CarDAOImpl implements CarDAO {
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String NEXT_LINE_PATTERN = "\n";
    private final static String CAR_ID = "car_id";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String PRICE = "price";

    ConnectionFactory connectionFactory;
    StatementFactory statementFactory;

    @Override
    public Car read(final int id) {
        final Car car = new Car();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.CAR_READ_LINE_PATTERN, id));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                car.setId(id);
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
        final PreparedStatement statement = statementFactory.createStatement(connection,
                SQLQueries.CAR_READ_ALL_LINES_PATTERN);

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

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return cars;
    }

    @Override
    public List<Car> readAllByPrice(final int price) {
        final List<Car> cars = new ArrayList<>();
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.CAR_READ_ALL_BY_PRICE_PATTERN, price));

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
    public void save(final String brand, final String model, final int ageOfProduce, final int price) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.CAR_SAVE_LINE_PATTERN, brand, model, ageOfProduce, price));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void update(final int id, final int newPrice) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.CAR_UPDATE_LINE_PATTERN, newPrice, id));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void deleteTable() {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                SQLQueries.CAR_DELETE_TABLE_PATTERN);

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void delete(final int id) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.CAR_DELETE_LINE_PATTERN, id));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    private List<Integer> readAllId() {
        List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                SQLQueries.CAR_READ_ALL_ID_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();
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
