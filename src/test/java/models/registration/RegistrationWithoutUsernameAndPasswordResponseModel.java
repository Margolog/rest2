package models.registration;

import java.util.List;

public record RegistrationWithoutUsernameAndPasswordResponseModel(
        List<String> username,
        List<String> password
) {}