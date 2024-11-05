package ra.entity;

import ra.validation.Validator;

import java.util.Scanner;

public interface IStoreManager {
    void inputData(Scanner scanner, Validator validator);

    void displayData();
}
