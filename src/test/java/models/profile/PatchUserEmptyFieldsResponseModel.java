package models.profile;

import java.util.List;

public record PatchUserEmptyFieldsResponseModel(
        List<String> username
) {}
