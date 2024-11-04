package ra.presentation;

public enum RoomMenuEnum {
    LIST(1, "List all rooms"),
    ADD(2, "Add new room"),
    UPDATE(3, "Update room"),
    DELETE(4, "Delete room"),
    SEARCH_BY_TYPE(5, "Search room by type"),
    SEARCH_BY_PRICE(6, "Search room by price"),
    SEARCH_BY_STATUS(7, "Search room by status"),
    ROOM_AVAILABILITY(8, "Get rooms availability"),
    BACK(0, "Back to main menu");

    private final int value;
    private final String description;

    RoomMenuEnum(int value, String description) {
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
