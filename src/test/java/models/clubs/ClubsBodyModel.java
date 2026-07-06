package models.clubs;

public record ClubsBodyModel(String bookTitle, String bookAuthors, Integer publicationYear,
                             String description, String telegramChatLink) {}