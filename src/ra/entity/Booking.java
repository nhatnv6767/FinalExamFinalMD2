package ra.entity;

import ra.validation.Validator;

import java.time.LocalDate;
import java.util.Scanner;

public class Booking implements IHotelManager {
    private int bookingId;
    private int customerId;
    private int roomId;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private int numberOfGuests;
    private double totalPrice;

    public Booking() {
    }

    public Booking(int bookingId, int customerId, int roomId, LocalDate arrivalDate, LocalDate departureDate, int numberOfGuests, double totalPrice) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public void inputData(Scanner scanner, Validator validator) {
        setCustomerId(validator.getValidCustomerId(scanner, "Enter customer ID: "));
        setRoomId(validator.getValidRoomId(scanner, "Enter room ID: "));
        setArrivalDate(validator.getDateInput(scanner, "Enter arrival date (yyyy-MM-dd): "));
        setDepartureDate(validator.getDateInput(scanner, "Enter departure date (yyyy-MM-dd): "));
        setNumberOfGuests(validator.getPositiveIntInput(scanner, "Enter number of guests: "));
        setTotalPrice(0.0);
    }

    @Override
    public void displayData() {

    }
}
