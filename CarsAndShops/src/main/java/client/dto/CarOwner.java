package client.dto;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarOwner {
    int id;
    String name;
    String city;
    String car;

    @Override
    public String toString() {
        return String.format("ID - %d\nName - %s\nCity - %s\nCar - %s", id, name, city, car);
    }
}
