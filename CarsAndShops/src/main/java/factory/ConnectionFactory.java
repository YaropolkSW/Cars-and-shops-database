package factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private final static String PATH_OF_CONNECTION_PROPERTIES = "src/main/resources/connection.properties";
    private final static String FILE_NOT_FOUND_ERROR_MESSAGE = "Файл не найден!";
    private final static String GET_CONNECTION_ERROR_MESSAGE = "Ошибка при создании подключения!";
    private final static String CLOSE_CONNECTION_ERROR_MESSAGE = "Ошибка при закрытии подключения!";
    private final static String URL = "url";
    private final static String USER = "user";
    private final static String PASSWORD = "password";


    public Connection createConnection() {
        Connection connection = null;
        final Properties properties = new Properties();

        try (final FileInputStream inputStream = new FileInputStream(PATH_OF_CONNECTION_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND_ERROR_MESSAGE);
        }

        final String url = properties.getProperty(URL);
        final String user = properties.getProperty(USER);
        final String password = properties.getProperty(PASSWORD);

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(GET_CONNECTION_ERROR_MESSAGE);
        }

        return connection;
    }

    public void closeConnection(final Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(CLOSE_CONNECTION_ERROR_MESSAGE);
        }
    }
}
