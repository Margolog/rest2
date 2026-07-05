package models.profile;

public record UpdateUserBodyModel(
        String username,
        String firstName,
        String lastName,
        String email
) {}
