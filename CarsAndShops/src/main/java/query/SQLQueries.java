package query;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SQLQueries {
    private final static String PATH_TO_PROPERTIES = "src/main/resources/databasePatterns.properties";
    private final static String FILE_NOT_FOUND_ERROR_MESSAGE = "Файл не найден!";

    public final static String SHOP_GET_ALL_PATTERN;
    public final static String SHOP_READ_BY_CLIENT_PATTERN;
    public final static String CAR_READ_ALL_BY_PRICE_PATTERN;
    public final static String SHOP_GET_ALL_CARS_PATTERN;
    public final static String SHOP_GET_ALL_CARS_BY_PRICE_PATTERN;
    public final static String SHOP_READ_ALL_ID_PATTERN;
    public final static String SHOP_SAVE_LINE_PATTERN;
    public final static String SHOP_DELETE_TABLE_PATTERN;
    public final static String SHOP_DELETE_LINE_PATTERN;

    public final static String OWNER_READ_LINE_PATTERN;
    public final static String OWNER_READ_BY_CAR_PATTERN;
    public final static String OWNER_READ_ALL_LINES_PATTERN;
    public final static String OWNER_READ_ALL_ID_PATTERN;
    public final static String OWNER_SAVE_LINE_PATTERN;
    public final static String OWNER_DELETE_TABLE_PATTERN;
    public final static String OWNER_DELETE_LINE_PATTERN;

    public final static String CAR_READ_LINE_PATTERN;
    public final static String CAR_READ_ALL_LINES_PATTERN;
    public final static String CAR_READ_ALL_ID_PATTERN;
    public final static String CAR_SAVE_LINE_PATTERN;
    public final static String CAR_UPDATE_LINE_PATTERN;
    public final static String CAR_DELETE_TABLE_PATTERN;
    public final static String CAR_DELETE_LINE_PATTERN;

    static {
        final Properties properties = new Properties();

        try (final FileInputStream inputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND_ERROR_MESSAGE);
        }

        SHOP_GET_ALL_PATTERN = properties.getProperty("shopGetAll");
        SHOP_GET_ALL_CARS_PATTERN = properties.getProperty("shopGetAllCars");
        SHOP_GET_ALL_CARS_BY_PRICE_PATTERN = properties.getProperty("shopGetAllCarsByPrice");
        SHOP_READ_BY_CLIENT_PATTERN = properties.getProperty("shopReadByClient");
        SHOP_SAVE_LINE_PATTERN = properties.getProperty("shopSaveLine");
        SHOP_DELETE_TABLE_PATTERN = properties.getProperty("shopDeleteTable");
        SHOP_DELETE_LINE_PATTERN = properties.getProperty("shopDeleteLine");
        SHOP_READ_ALL_ID_PATTERN = properties.getProperty("shopReadAllId");

        OWNER_READ_LINE_PATTERN = properties.getProperty("ownerReadLine");
        OWNER_READ_BY_CAR_PATTERN = properties.getProperty("ownerReadByCar");
        OWNER_READ_ALL_LINES_PATTERN = properties.getProperty("ownerReadAllLines");
        OWNER_SAVE_LINE_PATTERN = properties.getProperty("ownerSaveLine");
        OWNER_DELETE_TABLE_PATTERN = properties.getProperty("ownerDeleteTable");
        OWNER_DELETE_LINE_PATTERN = properties.getProperty("ownerDeleteLine");
        OWNER_READ_ALL_ID_PATTERN = properties.getProperty("ownerReadAllId");

        CAR_READ_LINE_PATTERN = properties.getProperty("carReadLine");
        CAR_READ_ALL_LINES_PATTERN = properties.getProperty("carReadAllLines");
        CAR_READ_ALL_BY_PRICE_PATTERN = properties.getProperty("carReadAllByPrice");
        CAR_SAVE_LINE_PATTERN = properties.getProperty("carSaveLine");
        CAR_UPDATE_LINE_PATTERN = properties.getProperty("carUpdateLine");
        CAR_DELETE_TABLE_PATTERN = properties.getProperty("carDeleteTable");
        CAR_DELETE_LINE_PATTERN = properties.getProperty("carDeleteLine");
        CAR_READ_ALL_ID_PATTERN = properties.getProperty("carReadAllId");
    }
}
