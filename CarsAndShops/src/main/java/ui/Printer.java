package ui;

public class Printer {
    private final static String FIRST_MESSAGE = "1 - просмотр списка машин которые продаются в магазине\n" +
                                                "2 - получение машин из магазина ниже введенной цены\n" +
                                                "3 - список людей, которые владеют машиной\n" +
                                                "4 - выход\n" +
                                                "Введите запрос: ";
    private final static String SEPARATOR = "------------------------------";

    public void printSeparator() {
        System.out.println(SEPARATOR);
    }

    public void printMessage() {
        System.out.print(FIRST_MESSAGE);
    }
}
