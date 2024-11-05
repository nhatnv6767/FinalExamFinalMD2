package ra.entity;

import ra.validation.Validator;

import java.util.Scanner;

public class Room implements IStoreManager {
    private int roomId;
    private String roomNumber;
    // Single, Double, Family, VIP
    private String roomType;
    private double price;
    // Available, Occupied
    private String status;

    public Room() {
    }

    public Room(int roomId, String roomNumber, String roomType, double price, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void inputData(Scanner scanner, Validator validator) {
        setRoomNumber(validator.getNonEmptyStringInput(scanner, "Nhập số phòng: ", 10));
        setRoomType(validator.getRoomTypeInput(scanner, "Nhập loại phòng (1 - Single, 2 - Double, 3 - Family, 4 - VIP): "));
        setPrice(validator.getPositiveDoubleInput(scanner, "Nhập giá phòng: "));
        setStatus(validator.getRoomStatusInput(scanner, "Nhập trạng thái phòng (1 - Available, 2 - Occupied): "));
    }

    @Override
    public void displayData() {
        System.out.println("Room ID: " + roomId);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: " + price);
        System.out.println("Status: " + status);
    }
}
