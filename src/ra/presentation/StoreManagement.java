package ra.presentation;

import ra.DAO.CategoriesBusiness;
import ra.DAO.RoomBusiness;
import ra.entity.Categories;
import ra.entity.CategoryStatistics;
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
    private static CategoriesBusiness categoriesBusiness = new CategoriesBusiness();
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
                    System.err.println("Goodbye!");
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
            System.out.println("*************CATEGORIES MANAGEMENT*************");
            for (CategoriesMenuEnum menu : CategoriesMenuEnum.values()) {
                System.out.println(menu.getValue() + ". " + menu.getDescription());
            }
            System.out.print("Please choose: ");
            choice = validator.getIntInput(scanner);

            switch (choice) {
                case 1:
                    displayAllCategories();
                    break;
                case 2:
                    addCategory(scanner);
                    break;
                case 3:
                    updateCategory(scanner);
                    break;
                case 4:
                    deleteCategory(scanner);
                    break;
                case 5:
                    displayProductsByCategory();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Invalid choice. Please choose again");
            }
        } while (choice != 0);
    }

    private static void displayAllCategories() {
        Categories[] categories = categoriesBusiness.getAll();
        System.out.println("Category list:");
        for (Categories category : categories) {
            category.displayData();
            System.out.println("====================================");
        }
    }

    private static void addCategory(Scanner scanner) {
        Categories category = new Categories();
        category.inputData(scanner, validator);
        categoriesBusiness.insert(category);
    }


    private static void updateCategory(Scanner scanner) {
        System.out.print("Enter Category ID: ");
        int categoryId = validator.getIntInput(scanner);
        Categories category = categoriesBusiness.get(categoryId);
        if (category == null) {
            System.err.println("Category not found");
            return;
        }

        System.out.println("Category information:");
        category.displayData();

        Map<Integer, UpdateOption<Categories>> updateOptions = new HashMap<>();
        updateOptions.put(1, new UpdateOption<>("Update category name", (c, s) -> c.setCategoryName(validator.getNonEmptyStringInput(s, "Enter new category name: ", 50))));


        updateEntity(category, scanner, categoriesBusiness::update, updateOptions);
    }

    private static void deleteCategory(Scanner scanner) {
        int categoryId = validator.getPositiveIntInput(scanner, "Enter category ID: ");
        Categories category = categoriesBusiness.get(categoryId);
        if (category == null) {
            System.err.println("Category not found");
            return;
        }
        categoriesBusiness.delete(category);
    }

    private static void displayProductsByCategory() {
        List<CategoryStatistics> statistics = categoriesBusiness.getProductsByCategory();

        if (statistics.isEmpty()) {
            System.err.println("No products found");
            return;
        }

        System.out.println("Products by category:");
        for (CategoryStatistics stat : statistics) {
            if (stat.getProductCount() > 0) {
                System.out.println("Category: " + stat.getCategoryName() + ", Number of products: " + stat.getProductCount());
            }
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
