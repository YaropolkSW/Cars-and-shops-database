import client.dao.CarOwnerDAOImpl;
import dao.Migration;
import factory.ConnectionFactory;
import factory.StatementFactory;
import lombok.Cleanup;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import shop.dao.ShopDAOImpl;
import ui.UserInterface;

import java.util.Scanner;

@FieldDefaults(makeFinal = true)
public class Main {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        StatementFactory statementFactory = new StatementFactory();

        @Cleanup
        Scanner scanner = new Scanner(System.in);
        ShopDAOImpl shopDAO = new ShopDAOImpl(connectionFactory, statementFactory);
        CarOwnerDAOImpl carOwnerDAO = new CarOwnerDAOImpl(connectionFactory, statementFactory);
        Migration migration = new Migration(connectionFactory, statementFactory);

        UserInterface userInterface = new UserInterface(scanner, shopDAO, carOwnerDAO, migration);

        @NonFinal boolean isContinue = true;

        while (isContinue) {
            isContinue = userInterface.showUserInterface();
        }
    }
}
