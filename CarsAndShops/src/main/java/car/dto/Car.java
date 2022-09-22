package car.dto;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {
    int id;
    String brand;
    String model;
    int ageOfProduce;
    int price;

    @Override
    public String toString() {
        return String.format("ID - %d\nBrand - %s\nModel - %s\nAge of produce - %d\nPrice - %d",
                id, brand, model, ageOfProduce, price);
    }
}
