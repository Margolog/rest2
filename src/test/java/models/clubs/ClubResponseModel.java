package models.clubs;

import java.util.List;

public record ClubResponseModel(
        Integer id,
        String bookTitle,
        String bookAuthors,
        Integer publicationYear,
        String description,
        String telegramChatLink,
        Integer owner,
        List<Integer> members,
        List<ReviewModel> reviews,
        String created,
        String modified
) {
    public record ReviewModel(
            Integer id,
            Integer club,
            UserModel user,
            String review,
            Integer assessment,
            Integer readPages,
            String created,
            String modified
    ) {}

    public record UserModel(
            Integer id,
            String username
    ) {}
}
