import car.dao.CarDAOImpl;
import client.dao.CarOwnerDAOImpl;
import dao.Migration;
import factory.ConnectionFactory;
import factory.StatementFactory;
import lombok.Cleanup;
import shop.dao.ShopDAOImpl;
import ui.Printer;
import ui.UserInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        final StatementFactory statementFactory = new StatementFactory();

        @Cleanup
        final Scanner scanner = new Scanner(System.in);
        final CarDAOImpl carDAO = new CarDAOImpl(connectionFactory, statementFactory);
        final ShopDAOImpl shopDAO = new ShopDAOImpl(connectionFactory, statementFactory);
        final CarOwnerDAOImpl carOwnerDAO = new CarOwnerDAOImpl(connectionFactory, statementFactory);
        final Migration migration = new Migration(connectionFactory, statementFactory);
        final Printer printer = new Printer();

        final UserInterface userInterface = new UserInterface(scanner, carDAO, shopDAO, carOwnerDAO, migration, printer);

        boolean isContinue = true;

        while (isContinue) {
            isContinue = userInterface.showUserInterface();
        }
    }
}
