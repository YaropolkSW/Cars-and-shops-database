package client;

import car.CarDAOImpl;
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

public class CarOwnerDAOImpl implements CarOwnerDAO {
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";
    private final static String CAR_ID = "car_id";

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final StatementFactory statementFactory = new StatementFactory();
    private final CarDAOImpl carDAO = new CarDAOImpl();

    @Override
    public void createTable() {
        new Migration().initialMigration();
    }

    @Override
    public CarOwner read(final int id) {
        final CarOwner carOwner = new CarOwner();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.OWNER_READ_LINE_PATTERN, id));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carOwner.setId(id);
                carOwner.setName(resultSet.getString(CLIENT_NAME));
                carOwner.setCity(resultSet.getString(CITY));
                carOwner.setCar(carDAO.read(resultSet.getInt(CAR_ID)).toString());
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return carOwner;
    }

    @Override
    public List<CarOwner> readAll() {
        final List<CarOwner> carOwners = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                InitialPatterns.OWNER_READ_ALL_LINES_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final CarOwner carOwner = new CarOwner();
                carOwner.setId(resultSet.getInt(CLIENT_ID));
                carOwner.setName(resultSet.getString(CLIENT_NAME));
                carOwner.setCity(resultSet.getString(CITY));
                carOwner.setCar(carDAO.read(resultSet.getInt(CAR_ID)).toString());
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return carOwners;
    }

    @Override
    public void save(final String name, final String city, final int carId) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.OWNER_SAVE_LINE_PATTERN, name, city, carId));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void deleteTable() {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                InitialPatterns.OWNER_DELETE_TABLE_PATTERN);

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void delete(final int id) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(InitialPatterns.OWNER_DELETE_LINE_PATTERN, id));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    private List<Integer> readAllId() {
        List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                InitialPatterns.OWNER_READ_ALL_ID_PATTERN);

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
        System.out.println(CHOICE_OF_ID_PATTERN + InitialPatterns.NEXT_LINE_PATTERN + readAllId());
    }
}
