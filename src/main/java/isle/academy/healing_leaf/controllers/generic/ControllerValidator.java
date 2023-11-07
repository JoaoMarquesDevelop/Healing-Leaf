package isle.academy.healing_leaf.controllers.generic;

public class ControllerValidator {

    public static long validateLong(String value) {
        long x;

        try {
            x = Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        return x;
    }
}
