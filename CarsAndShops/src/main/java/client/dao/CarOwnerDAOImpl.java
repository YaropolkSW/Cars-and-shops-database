package client.dao;

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

public class CarOwnerDAOImpl implements CarOwnerDAO {
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String NEXT_LINE_PATTERN = "\n";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final StatementFactory statementFactory = new StatementFactory();

    @Override
    public void createTable() {
        new Migration().initialMigration();
    }

    @Override
    public CarOwner read(final int id) {
        final CarOwner carOwner = new CarOwner();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.OWNER_READ_LINE_PATTERN, id));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carOwner.setId(id);
                carOwner.setName(resultSet.getString(CLIENT_NAME));
                carOwner.setCity(resultSet.getString(CITY));
                carOwner.setCar(resultSet.getString(BRAND) + " " + resultSet.getString(MODEL));
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
                SQLQueries.OWNER_READ_ALL_LINES_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final CarOwner carOwner = new CarOwner();
                carOwner.setId(resultSet.getInt(CLIENT_ID));
                carOwner.setName(resultSet.getString(CLIENT_NAME));
                carOwner.setCity(resultSet.getString(CITY));
                carOwner.setCar(resultSet.getString(BRAND) + " " + resultSet.getString(MODEL));
                carOwners.add(carOwner);
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
                String.format(SQLQueries.OWNER_SAVE_LINE_PATTERN, name, city, carId));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void deleteTable() {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                SQLQueries.OWNER_DELETE_TABLE_PATTERN);

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    @Override
    public void delete(final int id) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.OWNER_DELETE_LINE_PATTERN, id));

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }

    private List<Integer> readAllId() {
        List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                SQLQueries.OWNER_READ_ALL_ID_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                idList.add(resultSet.getInt(CLIENT_ID));
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
