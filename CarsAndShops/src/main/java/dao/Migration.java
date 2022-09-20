package dao;

import factory.ConnectionFactory;
import factory.StatementFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Migration {
    private final static String PATH_TO_SQL_FILE = "src/main/resources/initialMigration.sql";
    private final static String ERROR_WHILE_READING_FILE_MESSAGE = "Ошибка во время чтения файла!";

    public void initialMigration() {
        final StringBuilder builder = new StringBuilder();

        try (final Scanner scanner = new Scanner(new File(PATH_TO_SQL_FILE))) {
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
