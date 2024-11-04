package ra.presentation;

public enum CustomerMenuEnum {
    LIST(1, "List all customers"),
    ADD(2, "Add new customer"),
    UPDATE(3, "Update customer"),
    DELETE(4, "Delete customer"),
    SEARCH_BY_CONDITION(5, "Search customer by name or phone number or id card"),
    BACK(0, "Back to main menu");

    private final int value;
    private final String description;

    CustomerMenuEnum(int value, String description) {
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
