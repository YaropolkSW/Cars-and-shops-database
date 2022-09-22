package shop.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shop {
    int id;
    String name;
    public String toString() {
        return String.format("ID - %d\nName - %s", id, name);
    }
}
