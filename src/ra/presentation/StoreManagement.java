package ra.presentation;

import ra.DAO.CustomerBusiness;
import ra.DAO.RoomBusiness;
import ra.entity.Customer;
import ra.entity.Room;
import ra.util.UpdateOption;
import ra.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;


public class StoreManagement {
    private static CustomerBusiness customerBusiness = new CustomerBusiness();
    private static RoomBusiness roomBusiness = new RoomBusiness();
    private static final Validator validator = new Validator();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("*************STORE MANAGEMENT*************");
            System.out.println("1. Products management");
            System.out.println("2. Categories management");
            System.out.println("0. Exit");
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);
            switch (choice) {
                case 1:
                    productsMenu(scanner);
                    break;
                case 2:
                    categoriesMenu(scanner);

                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }


    private static void productsMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************PRODUCT MANAGEMENT*************");
            for (ProductMenuEnum menu : ProductMenuEnum.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            switch (choice) {
                case 1:
//                    displayAllRooms();
                    break;
                case 2:
//                    addRoom(scanner);
                    break;
                case 3:
//                    updateRoom(scanner);
                    break;
                case 4:
//                    deleteRoom(scanner);
                    break;
                case 5:
//                    searchRoomByType(scanner);
                    break;
                case 6:
//                    searchRoomByPrice(scanner);
                    break;
                case 7:
//                    searchRoomByStatus(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void addRoom(Scanner scanner) {
        Room room = new Room();
        room.inputData(scanner, validator);
        roomBusiness.insert(room);
    }

    private static void displayAllRooms() {
        Room[] rooms = roomBusiness.getAll();
        System.out.println("Room list:");
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void updateRoom(Scanner scanner) {
        System.out.print("Enter room ID: ");
        int roomId = validator.getIntInput(scanner);
        Room room = roomBusiness.get(roomId);
        if (room == null) {
            System.err.println("Room not found");
            return;
        }

        System.out.println("Room information:");
        room.displayData();

        Map<Integer, UpdateOption<Room>> updateOptions = new HashMap<>();
        updateOptions.put(1, new UpdateOption<>("Update room number", (r, s) -> r.setRoomNumber(validator.getNonEmptyStringInput(s, "Enter new room number: ", 10))));
        updateOptions.put(2, new UpdateOption<>("Update room type", (r, s) -> r.setRoomType(validator.getRoomTypeInput(s, "Enter new room type (1 - Single, 2 - Double, 3 - Family, 4 - VIP): "))));
        updateOptions.put(3, new UpdateOption<>("Update price", (r, s) -> r.setPrice(validator.getPositiveDoubleInput(s, "Enter new price: "))));
        updateOptions.put(4, new UpdateOption<>("Update status", (r, s) -> r.setStatus(validator.getRoomStatusInput(s, "Enter new status (1 - Available, 2 - Occupied): "))));
        updateEntity(room, scanner, roomBusiness::update, updateOptions);

    }

    private static void deleteRoom(Scanner scanner) {
        int roomId = validator.getPositiveIntInput(scanner, "Enter room ID: ");
        Room room = roomBusiness.get(roomId);
        if (room == null) {
            System.err.println("Room not found");
            return;
        }
        roomBusiness.delete(room);
    }

    private static void searchRoomByType(Scanner scanner) {
        String roomType = validator.getRoomTypeInput(scanner, "Enter room type (1 - Single, 2 - Double, 3 - Family, 4 - VIP): ");
        List<Room> rooms = roomBusiness.searchRoomByType(roomType);
        if (rooms.isEmpty()) {
            System.err.println("Room not found");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void searchRoomByPrice(Scanner scanner) {
        double minPrice = validator.getPositiveDoubleInput(scanner, "Enter minimum price: ");
        double maxPrice = validator.getPositiveDoubleInput(scanner, "Enter maximum price: ");
        if (minPrice > maxPrice) {
            System.err.println("Invalid price range");
            return;
        }
        List<Room> rooms = roomBusiness.searchRoomByPrice(minPrice, maxPrice);
        if (rooms.isEmpty()) {
            System.err.println("Room not found");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void searchRoomByStatus(Scanner scanner) {
        String status = validator.getRoomStatusInput(scanner, "Enter room status (1 - Available, 2 - Occupied): ");
        List<Room> rooms = roomBusiness.searchRoomByStatus(status);
        if (rooms.isEmpty()) {
            System.err.println("Room not found");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }

    private static void getRoomAvailability() {
        List<Room> rooms = roomBusiness.getAvailableRoom();
        if (rooms.isEmpty()) {
            System.err.println("No available room");
            return;
        }
        for (Room room : rooms) {
            room.displayData();
            System.out.println("====================================");
        }
    }


    private static void categoriesMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("*************CUSTOMER MANAGEMENT*************");
            for (CategoriesMenuEnum menu : CategoriesMenuEnum.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            switch (choice) {
                case 1:
//                    displayAllCustomers();
                    break;
                case 2:
//                    addCustomer(scanner);
                    break;
                case 3:
//                    updateCustomer(scanner);
                    break;
                case 4:
//                    deleteCustomer(scanner);
                    break;
                case 5:
//                    searchCustomerByCondition(scanner);
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
        int customerId = validator.getIntInput(scanner);
        Customer customer = customerBusiness.get(customerId);
        if (customer == null) {
            System.err.println("Customer not found");
            return;
        }

        System.out.println("Customer information:");
        customer.displayData();

        Map<Integer, UpdateOption<Customer>> updateOptions = new HashMap<>();
        updateOptions.put(1, new UpdateOption<>("Update customer name", (c, s) -> c.setCustomerName(validator.getNonEmptyStringInput(s, "Enter new customer name: "))));
        updateOptions.put(2, new UpdateOption<>("Update phone number", (c, s) -> c.setPhoneNumber(validator.getPhoneNumberInput(s, "Enter new phone number: "))));
        updateOptions.put(3, new UpdateOption<>("Update id card", (c, s) -> c.setIdCard(validator.getNonEmptyStringInput(s, "Enter new id card: "))));
        updateOptions.put(4, new UpdateOption<>("Update address", (c, s) -> c.setAddress(validator.getNonEmptyStringInput(s, "Enter new address: "))));
        updateOptions.put(5, new UpdateOption<>("Update customer type", (c, s) -> c.setCustomerType(validator.getCustomerTypeInput(s, "Enter new customer type (1 - Regular, 2 - VIP): "))));


        updateEntity(customer, scanner, customerBusiness::update, updateOptions);
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


    private static <T> void updateEntity(T entity, Scanner scanner, Consumer<T> updateFunction, Map<Integer, UpdateOption<T>> updateOptions) {
        int choice;
        do {
            System.out.println("Choose field to update:");
            updateOptions.forEach((key, value) -> System.out.println(key + ". " + value.getDescription()));
            System.out.println("0. Cancel");
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            if (choice != 0 && updateOptions.containsKey(choice)) {
                updateOptions.get(choice).getAction().accept(entity, scanner);
                System.out.println("Field updated successfully");
            } else if (choice != 0) {
                System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);

        updateFunction.accept(entity);
    }


}
