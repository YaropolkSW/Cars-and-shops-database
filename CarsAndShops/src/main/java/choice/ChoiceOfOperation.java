package choice;

public enum ChoiceOfOperation {
    READ_IN_SHOP,
    READ_IN_SHOP_BY_PRICE,
    READ_CAR_BY_OWNER,
    EXIT;

    public static ChoiceOfOperation getOperation(final int operation) {
        switch (operation) {
            case 1:
                return ChoiceOfOperation.READ_IN_SHOP;
            case 2:
                return ChoiceOfOperation.READ_IN_SHOP_BY_PRICE;
            case 3:
                return ChoiceOfOperation.READ_CAR_BY_OWNER;
            case 4:
                return ChoiceOfOperation.EXIT;
            default:
                throw new NumberFormatException();
        }
    }
}
