package dao;

import factory.ConnectionFactory;
import factory.StatementFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Migration {
    private final static String PATH_TO_SQL_FILE = "src/main/resources/initialMigration.sql";
    private final static String ERROR_WHILE_READING_FILE_MESSAGE = "Ошибка во время чтения файла!";
    private final static String NEXT_LINE_PATTERN = "\n";

    ConnectionFactory connectionFactory;
    StatementFactory statementFactory;

    public void initialMigration() {
        final StringBuilder builder = new StringBuilder();

        try (final Scanner scanner = new Scanner(new File(PATH_TO_SQL_FILE))) {
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine()).append(NEXT_LINE_PATTERN);
            }
        } catch (IOException e) {
            System.out.println(ERROR_WHILE_READING_FILE_MESSAGE);
        }

        final Connection connection = connectionFactory.createConnection();

        final PreparedStatement statement = statementFactory.createStatement(connection, builder.toString());

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }
}
