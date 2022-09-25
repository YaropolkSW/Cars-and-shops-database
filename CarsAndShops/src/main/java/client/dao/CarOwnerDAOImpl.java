package client.dao;

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
public class CarOwnerDAOImpl implements CarOwnerDAO {
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String EMPTY_OWNER = "Машиной %s никто не владеет!";
    private final static String NEXT_LINE_PATTERN = "\n";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String SPACE = " ";

    private final ConnectionFactory connectionFactory;
    private final StatementFactory statementFactory;

    @Override
    public CarOwner read(final int id) {
        CarOwner carOwner = null;

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.OWNER_READ_LINE_PATTERN, id));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final String clientName = resultSet.getString(CLIENT_NAME);
                final String clientCity = resultSet.getString(CITY);
                final String car = resultSet.getString(BRAND) + SPACE + resultSet.getString(MODEL);
                carOwner = new CarOwner(id, clientName, clientCity, car);
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return carOwner;
    }

    @Override
    public String readByCar(final int id) {
        CarOwner carOwner = null;

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SQLQueries.OWNER_READ_BY_CAR_PATTERN, id));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final int clientId = resultSet.getInt(CLIENT_ID);
                final String car = resultSet.getString(BRAND) + SPACE + resultSet.getString(MODEL);

                if (clientId > 0) {
                    final String clientName = resultSet.getString(CLIENT_NAME);
                    final String clientCity = resultSet.getString(CITY);
                    carOwner = new CarOwner(clientId, clientName, clientCity, car);
                } else {
                    return String.format(EMPTY_OWNER, car);
                }
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        return carOwner.toString();
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
                final int clientId = resultSet.getInt(CLIENT_ID);
                final String clientName = resultSet.getString(CLIENT_NAME);
                final String clientCity = resultSet.getString(CITY);
                final String car = resultSet.getString(BRAND) + SPACE + resultSet.getString(MODEL);
                carOwners.add(new CarOwner(clientId, clientName, clientCity, car));
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
    public String choiceOfId() {
        return (CHOICE_OF_ID_PATTERN + NEXT_LINE_PATTERN + readAllId());
    }
}
