package models.clubs;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClubsBodyModel(String bookTitle, String bookAuthors, Integer publicationYear,
                             String description, String telegramChatLink) {}