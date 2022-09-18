package dao;

public class Migration {
    public void initialMigration() {
        final DAO dao = new DAO();
        dao.createShopDatabase();
        dao.createCarDatabase();
        dao.createClientDatabase();
    }
}
