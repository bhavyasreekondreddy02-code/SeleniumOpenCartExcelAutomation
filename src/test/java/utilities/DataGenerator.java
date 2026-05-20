package utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {

    // Random alphabetic string (letters only)
    public static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    // Random numeric string (digits only)
    public static String randomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    // Random alphanumeric string (letters + digits)
    public static String randomAlphaNumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    // Random email with guaranteed uniqueness
    public static String randomEmail() {
        return randomString(6) + randomNumber(4) + "@test.com";
    }

    // Random mobile number (10 digits)
    public static String randomMobile() {
        return randomNumber(10);
    }

    // Random password (8 characters alphanumeric)
    public static String randomPassword() {
        return randomAlphaNumeric(8);
    }
}
