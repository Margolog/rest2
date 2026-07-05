package models.profile;

import java.util.List;

public record UpdateUserRequiredFieldsResponseModel(
        List<String> firstName,
        List<String> lastName,
        List<String> email
) {}
