package factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementFactory {
    private final static String CREATE_STATEMENT_ERROR_MESSAGE = "Ошибка при подготовке утверждения!";
    private final static String EXECUTE_STATEMENT_ERROR_MESSAGE = "Ошибка при выполнении утверждения!";
    private final static String CLOSE_STATEMENT_ERROR_MESSAGE = "Ошибка при закрытии утверждения!";

    public PreparedStatement createStatement(final Connection connection, final String stringStatement) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(stringStatement);
        } catch (SQLException e) {
            System.out.println(CREATE_STATEMENT_ERROR_MESSAGE);
        }

        return statement;
    }

    public void executeStatement(final PreparedStatement statement) {
        try {
            statement.execute();
        } catch (SQLException e) {
            System.out.println(EXECUTE_STATEMENT_ERROR_MESSAGE);
        }
    }

    public void closeStatement(final PreparedStatement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println(CLOSE_STATEMENT_ERROR_MESSAGE);
        }
    }
}
