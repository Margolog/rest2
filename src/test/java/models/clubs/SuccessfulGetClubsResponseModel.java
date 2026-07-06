package models.clubs;

import java.util.List;

public record SuccessfulGetClubsResponseModel(
        Integer count,
        String next,
        String previous,
        List<ResultsModel> results
) {
    public record ResultsModel(
            Integer id,
            String bookTitle,
            String bookAuthors,
            Integer publicationYear,
            String description,
            String telegramChatLink,
            Integer owner,
            List<Integer> members,
            List<ReviewsModel> reviews,
            String created,
            String modified
    ) {}

    public record ReviewsModel(
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
