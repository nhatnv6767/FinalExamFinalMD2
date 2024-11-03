package ra.entity;

import ra.validation.Validator;

import java.util.Scanner;

public class Customer implements IHotelManager {

    private int customerId;
    private String customerName;
    private String phoneNumber;
    private String idCard;
    private String address;
    private String customerType;

    public Customer() {
    }

    public Customer(int customerId, String customerName, String phoneNumber, String idCard, String address, String customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.idCard = idCard;
        this.address = address;
        this.customerType = customerType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @Override
    public void inputData(Scanner scanner, Validator validator) {
        setCustomerName(validator.getNonEmptyStringInput(scanner, "Nhập tên khách hàng: "));
        setPhoneNumber(validator.getPhoneNumberInput(scanner, "Nhập số điện thoại: "));
        setIdCard(validator.getNonEmptyStringInput(scanner, "Nhập số CMND: "));
        setAddress(validator.getNonEmptyStringInput(scanner, "Nhập địa chỉ: "));
        setCustomerType(validator.getCustomerTypeInput(scanner, "Nhập loại khách hàng (1 - Regular, 2 - VIP): "));
    }

    @Override
    public void displayData() {
        System.out.println("Mã khách hàng: " + getCustomerId());
        System.out.println("Tên khách hàng: " + getCustomerName());
        System.out.println("Số điện thoại: " + getPhoneNumber());
        System.out.println("Số CMND: " + getIdCard());
        System.out.println("Địa chỉ: " + getAddress());
        System.out.println("Loại khách hàng: " + getCustomerType());
    }
}
