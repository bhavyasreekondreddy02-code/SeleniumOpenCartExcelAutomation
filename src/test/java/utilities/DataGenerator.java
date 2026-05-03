package utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {

    // Random alphabetic string
    public static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    // Random numeric string
    public static String randomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    // Random alphanumeric string
    public static String randomAlphaNumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    // Random email with valid domain
    public static String randomEmail() {
        return randomString(6) + "@gmail.com";
    }
}
