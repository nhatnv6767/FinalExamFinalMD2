package ra.presentation;

import ra.DAO.CustomerBusiness;
import ra.entity.Customer;
import ra.validation.Validator;

import java.util.Scanner;

enum CustomerMenu {
    LIST(1, "List all customers"),
    ADD(2, "Add new customer"),
    UPDATE(3, "Update customer"),
    DELETE(4, "Delete customer"),
    SEARCH_BY_CONDITION(5, "Search customer by name or phone number or id card"),
    BACK(0, "Back to main menu");

    private final int value;
    private final String description;

    CustomerMenu(int value, String description) {
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

public class HotelManagement {
    private static CustomerBusiness customerBusiness = new CustomerBusiness();
    private static final Validator validator = new Validator();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("*************HOTEL MANAGEMENT*************");
            System.out.println("1. Customer Management");
            System.out.println("0. Exit");
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);
            switch (choice) {
                case 1:
                    customerMenu(scanner);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void customerMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************CUSTOMER MANAGEMENT*************");
            for (CustomerMenu menu : CustomerMenu.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    displayAllCustomers();
                    break;
                case 2:
                    addCustomer(scanner);
                    break;
                case 3:
                    updateCustomer(scanner);
                    break;
                case 4:
                    deleteCustomer(scanner);
                    break;
                case 5:
                    searchCustomerByCondition(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void addCustomer(Scanner scanner) {
        Customer customer = new Customer();
        customer.inputData(scanner, validator);
        customerBusiness.insert(customer);
    }

    private static void updateCustomer(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int customerId = getIntInput(scanner);
        Customer customer = customerBusiness.get(customerId);
        if (customer == null) {
            System.err.println("Customer not found");
            return;
        }

        System.out.println("Customer information:");
        customer.displayData();

        int choice;
        do {
            System.out.println("Choose field to update:");
            System.out.println("1. Update customer name");
            System.out.println("2. Update phone number");
            System.out.println("3. Update id card");
            System.out.println("4. Update address");
            System.out.println("5. Update customer type");
            System.out.println("0. Cancel");
            System.out.print("Please choose: ");
            choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    String newCustomerName = validator.getNonEmptyStringInput(scanner, "Enter new customer name: ");
                    if (!newCustomerName.isEmpty()) {
                        customer.setCustomerName(newCustomerName);
                    }
                    break;
                case 2:
                    String newPhoneNumber = validator.getPhoneNumberInput(scanner, "Enter new phone number: ");
                    if (!newPhoneNumber.isEmpty()) {
                        customer.setPhoneNumber(newPhoneNumber);
                    }
                    break;
                case 3:
                    String newIdCard = validator.getNonEmptyStringInput(scanner, "Enter new id card: ");
                    if (!newIdCard.isEmpty()) {
                        customer.setIdCard(newIdCard);
                    }
                    break;
                case 4:
                    String newAddress = validator.getNonEmptyStringInput(scanner, "Enter new address: ");
                    if (!newAddress.isEmpty()) {
                        customer.setAddress(newAddress);
                    }
                    break;
                case 5:
                    String newCustomerType = validator.getCustomerTypeInput(scanner, "Enter new customer type (1 - Regular, 2 - VIP): ");
                    if (!newCustomerType.isEmpty()) {
                        customer.setCustomerType(newCustomerType);
                    }
                    break;
                case 0:
                    System.out.println("Cancel");
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }

        } while (choice != 0);

        customerBusiness.update(customer);
    }

    private static void deleteCustomer(Scanner scanner) {
        int customerId = validator.getPositiveIntInput(scanner, "Enter customer ID: ");
        Customer customer = customerBusiness.get(customerId);
        if (customer == null) {
            System.err.println("Customer not found");
            return;
        }
        customerBusiness.delete(customer);
    }

    private static void searchCustomerByCondition(Scanner scanner) {
        String condition = validator.getNonEmptyStringInput(scanner, "Enter customer name or phone number or id card: ");
        Customer[] customers = customerBusiness.searchCustomerByCondition(condition);
        if (customers.length == 0) {
            System.err.println("Customer not found");
            return;
        }
        for (Customer customer : customers) {
            customer.displayData();
        }
    }

    private static void displayAllCustomers() {
        Customer[] customers = customerBusiness.getAll();
        for (Customer customer : customers) {
            customer.displayData();
            System.out.println("====================================");
        }
    }


    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number");
            }
        }
    }
}
