package ra.presentation;

public enum ProductMenuEnum {
    LIST(1, "List all products"),
    ADD(2, "Add new product"),
    UPDATE(3, "Update product"),
    DELETE(4, "Delete product"),
    DISPLAY_BY_CREATION_DATE(5, "Display product list by creation date in descending order"),
    SEARCH(6, "Search products by price range"),
    SHOW_TOP_3(7, "Show top 3 products with highest profit (profit = selling price - import price)"),
    BACK(0, "Back to main menu");

    private final int value;
    private final String description;

    ProductMenuEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
