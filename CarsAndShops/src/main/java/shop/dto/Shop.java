package shop.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Shop {
    private int id;
    private String name;
    public String toString() {
        return String.format("ID - %d\nName - %s", id, name);
    }
}
