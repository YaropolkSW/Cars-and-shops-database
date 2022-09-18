package car;

public class Car {
    private String shop;
    private int id;
    private String brand;
    private String model;
    private int ageOfProduce;
    private int price;

    public void setShop(final String shop) {
        this.shop = shop;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public void setAgeOfProduce(final int ageOfProduce) {
        this.ageOfProduce = ageOfProduce;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Shop - %s\nID - %d\nBrand - %s\nModel - %s\nAge of produce - %d\nPrice - %d\n",
                shop, id, brand, model, ageOfProduce, price);
    }
}
