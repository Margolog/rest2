package models.profile;

import java.util.List;

public record UpdateUserAllRequiredFieldsResponseModel(
        List<String> username,
        List<String> firstName,
        List<String> lastName,
        List<String> email
) {}
