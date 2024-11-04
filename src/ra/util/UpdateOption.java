package ra.util;


import java.util.Scanner;
import java.util.function.BiConsumer;

public class UpdateOption<T> {
    private final String description;
    private final BiConsumer<T, Scanner> action;

    public UpdateOption(String description, BiConsumer<T, Scanner> action) {
        this.description = description;
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public BiConsumer<T, Scanner> getAction() {
        return action;
    }

    @Override
    public String toString() {
        return description;
    }
}