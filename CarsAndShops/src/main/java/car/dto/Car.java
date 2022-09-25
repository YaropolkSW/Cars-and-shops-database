package car.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Car {
    private int id;
    private String brand;
    private String model;
    private int ageOfProduce;
    private int price;

    @Override
    public String toString() {
        return String.format("ID - %d\nBrand - %s\nModel - %s\nAge of produce - %d\nPrice - %d",
                id, brand, model, ageOfProduce, price);
    }
}
