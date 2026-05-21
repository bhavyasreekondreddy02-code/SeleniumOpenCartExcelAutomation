package utilities;

import java.util.UUID;

public class DataGenerator {

    // Generate unique email based on base email from Excel
    public static String generateUniqueEmail(String baseEmail) {
        String uniquePart = UUID.randomUUID().toString().substring(0, 6);
        return baseEmail.replace("@", "+" + uniquePart + "@");
    }

    // Random string generator
    public static String randomString(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    // Random number generator (e.g., mobile numbers)
    public static String randomNumber(int digits) {
        return String.valueOf((long)(Math.pow(10, digits-1) + Math.random() * Math.pow(10, digits-1)));
    }
}
