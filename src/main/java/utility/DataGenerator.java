package utility;

import net.datafaker.Faker;

public class DataGenerator {
    private static Faker faker = new Faker();

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getPassword() {
        return faker.name().firstName() + "123";
    }

    public static int getPrice(int digits){
        return Integer.valueOf(faker.number().digits(digits));
    }

    public static boolean getBoolean(){
        return faker.bool().bool();
    }

    public static String getAdditionalNeeds(){
        return faker.food().fruit();
    }
}
