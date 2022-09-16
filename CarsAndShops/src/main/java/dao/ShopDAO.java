package dao;

import java.util.List;

public interface ShopDAO {
    void createTable();

    List<Car> readAllInShop(final int shopId);

    List<Car> readInShopByPrice(final int shopId, final int price);

    List<Car> readAllByPrice(final int price);

    Client readByClient(final int clientId);

    List<Car> readAll();

    void save(final String name);

    void deleteTable();

    void delete(final int id);

    void choiceOfId();
}
