package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class DataGenerator {

    public static String generateRandomAlphaNumericUpperCaseString(int length) {
        return RandomStringUtils.random(length, true, true).toUpperCase();
    }

    public static String generateRandomNumericString(int length) {
        return RandomStringUtils.random(length, false, true);
    }

    public static String generateRandomAlphabeticString(int length) {
        return RandomStringUtils.random(length, true, false);
    }

    public static boolean generateRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
