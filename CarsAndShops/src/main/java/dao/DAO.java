package dao;

import factory.ConnectionFactory;
import factory.StatementFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DAO {
    private final static String PATH_TO_SHOP_FILE = "src/main/resources/createShopDatabase.sql";
    private final static String PATH_TO_CAR_FILE = "src/main/resources/createCarDatabase.sql";
    private final static String PATH_TO_CLIENT_FILE = "src/main/resources/createClientDatabase.sql";
    private final static String ERROR_WHILE_READING_FILE_MESSAGE = "Ошибка во время чтения файла!";

    public DAO() {
        createShopDatabase();
        createCarDatabase();
        createClientDatabase();
    }

    private void createShopDatabase() {
        createExecuteClose(PATH_TO_SHOP_FILE);
    }

    private void createCarDatabase() {
        createExecuteClose(PATH_TO_CAR_FILE);
    }

    private void createClientDatabase() {
        createExecuteClose(PATH_TO_CLIENT_FILE);
    }

    private void createExecuteClose(final String pathOfFile) {
        final StringBuilder builder = new StringBuilder();

        try (final Scanner scanner = new Scanner(new File(pathOfFile))) {
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            System.out.println(ERROR_WHILE_READING_FILE_MESSAGE);
        }

        final ConnectionFactory connectionFactory = new ConnectionFactory();
        final Connection connection = connectionFactory.createConnection();

        final StatementFactory statementFactory = new StatementFactory();
        final PreparedStatement statement = statementFactory.createStatement(connection, builder.toString());

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }
}
