package models.profile;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PatchUserBodyModel(
        String username,
        String firstName,
        String lastName,
        String email
) {}
