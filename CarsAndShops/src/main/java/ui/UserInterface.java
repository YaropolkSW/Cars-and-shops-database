package ui;

import car.dto.Car;
import choice.ChoiceOfOperation;
import client.dao.CarOwnerDAOImpl;
import dao.Migration;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import shop.dao.ShopDAOImpl;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserInterface {
    private final static String INCORRECT_REQUEST = "Неверный запрос!";
    private final static String REQUEST_NUMBER_OF_SHOP = "Введите номер магазина для просмотра машин: ";
    private final static String REQUEST_MAX_PRICE_MESSAGE = "Введите максимальную цену: ";
    private final static String REQUEST_CAR_ID = "Введите id машины: ";
    private final static String DASH_PATTERN = " - ";
    private final static String NEXT_LINE_PATTERN = "\n";

    Scanner scanner;
    ShopDAOImpl shopDAO;
    CarOwnerDAOImpl carOwnerDAO;
    Migration migration;
    Printer printer = new Printer();

    public boolean showUserInterface() {
        migration.initialMigration();
        printer.printMessage();

        final ChoiceOfOperation operation;
        try {
            operation = ChoiceOfOperation.getOperation(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return true;
        }

        printer.printSeparator();

        switch (operation) {
            case READ_IN_SHOP:
                readInShop();
                return true;

            case READ_IN_SHOP_BY_PRICE:
                readInShopByPrice();
                return true;

            case READ_CAR_BY_OWNER:
                readOwnerByCar();
                return true;

            case EXIT:
                scanner.close();
                return false;

            default:
                System.out.println(INCORRECT_REQUEST);
                return true;
        }
    }

    private void readInShop() {
        final List<Car> cars;
        final int shopNumber;
        System.out.print(choiceOfShop());

        try {
            shopNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        cars = shopDAO.getAllCars(shopNumber);

        printer.printSeparator();
        for (Car car : cars) {
            System.out.println(car);
            printer.printSeparator();
        }
    }

    private void readInShopByPrice() {
        final List<Car> cars;
        final int shopNumber;
        int maxPrice;

        System.out.print(choiceOfShop());

        try {
            shopNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        System.out.print(REQUEST_MAX_PRICE_MESSAGE);

        try {
            maxPrice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        cars = shopDAO.getCarsByPrice(shopNumber, maxPrice);

        printer.printSeparator();
        for (Car car : cars) {
            System.out.println(car);
            printer.printSeparator();
        }
    }

    private void readOwnerByCar() {
        int carId;

        System.out.print(REQUEST_CAR_ID);

        try {
            carId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        final String carOwner = carOwnerDAO.readByCar(carId);

        printer.printSeparator();
        System.out.println(carOwner);
        printer.printSeparator();
    }

    private String choiceOfShop() {
        int numberOfChoice = 1;
        final StringBuilder builderOfMessage = new StringBuilder();
        List<String> shops = shopDAO.getAllShops();

        for (String shop : shops) {
            if (numberOfChoice != shops.size()) {
                builderOfMessage.append(numberOfChoice + DASH_PATTERN + shop + NEXT_LINE_PATTERN);
            } else {
                builderOfMessage.append(numberOfChoice + DASH_PATTERN + shop + NEXT_LINE_PATTERN + REQUEST_NUMBER_OF_SHOP);
            }
            numberOfChoice++;
        }

        return builderOfMessage.toString();
    }
}
