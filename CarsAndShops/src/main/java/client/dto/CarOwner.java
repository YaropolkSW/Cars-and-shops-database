package client.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CarOwner {
    private int id;
    private String name;
    private String city;
    private String car;

    @Override
    public String toString() {
        return String.format("ID - %d\nName - %s\nCity - %s\nCar - %s", id, name, city, car);
    }
}
