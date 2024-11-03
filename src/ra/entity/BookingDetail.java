package ra.entity;

import ra.validation.Validator;

import java.util.Scanner;

public class BookingDetail implements IHotelManager {
    private int bookingId;
    private int serviceId;
    private int quantity;

    public BookingDetail() {
    }

    public BookingDetail(int bookingId, int serviceId, int quantity) {
        this.bookingId = bookingId;
        this.serviceId = serviceId;
        this.quantity = quantity;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void inputData(Scanner scanner, Validator validator) {

    }

    @Override
    public void displayData() {

    }
}
