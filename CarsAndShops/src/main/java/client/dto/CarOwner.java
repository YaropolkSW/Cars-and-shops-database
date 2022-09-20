package client.dto;

public class CarOwner {
    private int id;
    private String name;
    private String city;
    private String car;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCar(String car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return String.format("ID - %d\nName - %s\nCity - %s\nCar - %s", id, name, city, car);
    }
}
