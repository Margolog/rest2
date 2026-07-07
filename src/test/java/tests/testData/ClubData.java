package tests.testData;

import net.datafaker.Faker;

public class ClubData {

    public static Faker faker = new Faker();

    public String
            bookTitle = faker.book().title() + " " + System.currentTimeMillis(),
            bookAuthors = faker.book().author(),
            bookDescription = faker.lorem().sentence(),
            telegramChatLink = "https://t.me/" + faker.regexify("[a-zA-Z][a-zA-Z0-9_]{7,20}"),
            updatedBookTitle = faker.book().title() + " updated " + System.currentTimeMillis()        ;


    public Integer publicationYear = faker.number().numberBetween(1900, 2026);
}
