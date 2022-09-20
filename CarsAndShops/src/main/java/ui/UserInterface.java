package ui;

import car.dto.Car;
import choice.ChoiceOfOperation;
import client.dao.CarOwnerDAOImpl;
import shop.dao.ShopDAOImpl;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final static String INCORRECT_REQUEST = "Неверный запрос!";
    private final static String ENTER_MAX_PRICE_MESSAGE = "Введите максимальную цену: ";
    private final static String REQUEST_CAR_ID = "Введите id машины: ";
    private final static String CHOICE_OF_SHOP = "1 - Subaru Official Dealer\n" +
                                                 "2 - Chevrolet Official Dealer\n" +
                                                 "3 - Kia Official Dealer\n" +
                                                 "4 - Trade-In Dealer\n" +
                                                 "Введите номер магазина для просмотра машин: ";

    private final Scanner scanner = new Scanner(System.in);
    private final ShopDAOImpl shopDAO = new ShopDAOImpl();
    private final CarOwnerDAOImpl carOwnerDAO = new CarOwnerDAOImpl();
    private final Printer printer = new Printer();

    private int shopNumber = 0;
    private List<Car> cars;

    public boolean showUserInterface() {
        shopDAO.createTable();
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
                return false;

            default:
                System.out.println(INCORRECT_REQUEST);
                return true;
        }
    }

    private void readInShop() {
        System.out.print(CHOICE_OF_SHOP);

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
        int maxPrice = 0;

        System.out.print(CHOICE_OF_SHOP);

        try {
            shopNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        System.out.print(ENTER_MAX_PRICE_MESSAGE);

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
        int carId = 0;

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
}
