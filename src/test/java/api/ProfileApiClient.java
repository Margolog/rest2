package api;

import io.qameta.allure.Step;
import models.profile.*;

import static io.restassured.RestAssured.given;
import static specs.profile.ProfileSpec.patchUserEmptyFieldsResponseSpec;
import static specs.profile.ProfileSpec.successfulUpdateUserResponseSpec;
import static specs.profile.ProfileSpec.updateUserAllRequiredFieldsResponseSpec;
import static specs.profile.ProfileSpec.updateUserRequestSpec;
import static specs.profile.ProfileSpec.updateUserRequiredFieldsResponseSpec;

public class ProfileApiClient {

    @Step("Успешно обновить профиль пользователя")
    public UpdateUserResponseModel updateUser(String accessToken, UpdateUserBodyModel body) {
        return given(updateUserRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .put("/users/me/")
                .then()
                .spec(successfulUpdateUserResponseSpec)
                .extract()
                .as(UpdateUserResponseModel.class);
    }

    public UpdateUserRequiredFieldsResponseModel updateUserWithoutRequiredFields(
            String accessToken,
            UpdateUserOnlyUsernameBodyModel body
    ) {
        return given(updateUserRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .put("/users/me/")
                .then()
                .spec(updateUserRequiredFieldsResponseSpec)
                .extract()
                .as(UpdateUserRequiredFieldsResponseModel.class);
    }

    public UpdateUserAllRequiredFieldsResponseModel updateUserWithoutAllRequiredFields(
            String accessToken,
            UpdateUserEmptyBodyModel body
    ) {
        return given(updateUserRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .put("/users/me/")
                .then()
                .spec(updateUserAllRequiredFieldsResponseSpec)
                .extract()
                .as(UpdateUserAllRequiredFieldsResponseModel.class);
    }

    public UpdateUserResponseModel patchUser(String accessToken, PatchUserBodyModel body) {
        return given(updateUserRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .patch("/users/me/")
                .then()
                .spec(successfulUpdateUserResponseSpec)
                .extract()
                .as(UpdateUserResponseModel.class);
    }

    public PatchUserEmptyFieldsResponseModel patchUserWithEmptyFields(String accessToken, UpdateUserBodyModel body) {
        return given(updateUserRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .patch("/users/me/")
                .then()
                .spec(patchUserEmptyFieldsResponseSpec)
                .extract()
                .as(PatchUserEmptyFieldsResponseModel.class);
    }
}
