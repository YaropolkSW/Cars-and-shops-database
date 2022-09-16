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

public class ClientDAOImpl implements ClientDAO {
    private final static String PATH_TO_PROPERTIES = "src/main/resources/clientDatabasePatterns.properties";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String FILE_NOT_FOUND_ERROR_MESSAGE = "Файл не найден!";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_NAME = "client_name";
    private final static String CITY = "city";
    private final static String CAR_ID = "car_id";

    private final String READ_LINE_PATTERN;
    private final String READ_ALL_LINES_PATTERN;
    private final String READ_ALL_ID_PATTERN;
    private final String SAVE_CLIENT_LINE_PATTERN;
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
        READ_ALL_LINES_PATTERN = properties.getProperty("readAllLines");
        READ_ALL_ID_PATTERN = properties.getProperty("readAllId");
        SAVE_CLIENT_LINE_PATTERN = properties.getProperty("saveClientLine");
        DELETE_TABLE_PATTERN = properties.getProperty("deleteTable");
        DELETE_LINE_PATTERN = properties.getProperty("deleteClientLine");
        NEXT_LINE_PATTERN = properties.getProperty("nextLine");
    }

    @Override
    public void createTable() {
        new DAO();
    }

    @Override
    public Client read(final int id) {
        final Client client = new Client();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(READ_LINE_PATTERN, id));

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                client.setId(id);
                client.setName(resultSet.getString(CLIENT_NAME));
                client.setCity(resultSet.getString(CITY));
                client.setCar(carDAO.read(resultSet.getInt(CAR_ID)).toString());
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return client;
    }

    @Override
    public List<Client> readAll() {
        final List<Client> clients = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection, READ_ALL_LINES_PATTERN);

        try {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Client client = new Client();
                client.setId(resultSet.getInt(CLIENT_ID));
                client.setName(resultSet.getString(CLIENT_NAME));
                client.setCity(resultSet.getString(CITY));
                client.setCar(carDAO.read(resultSet.getInt(CAR_ID)).toString());
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);

        return clients;
    }

    @Override
    public void save(final String name, final String city, final int carId) {
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.createStatement(connection,
                String.format(SAVE_CLIENT_LINE_PATTERN, name, city, carId));

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
