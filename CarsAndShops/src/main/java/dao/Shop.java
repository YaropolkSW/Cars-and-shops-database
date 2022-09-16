package dao;

public class Shop {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("ID - %d\nName - %s", id, name);
    }
}
