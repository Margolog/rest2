package api;

import io.qameta.allure.Step;
import models.registration.*;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.registration.RegistrationSpec.*;

public class UsersApiClient {

    @Step("Зарегистрировать нового пользователя")
    public SuccessfulRegistrationResponseModel register(RegistrationBodyModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
    }

    public ExistingUserResponseModel registerExistingUser(RegistrationBodyModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(existingUserRegistrationResponseSpec)
                .extract()
                .as(ExistingUserResponseModel.class);
    }

    public RegistrationPasswordErrorResponseModel registrationWithoutPassword(RegistrationBodyModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(registrationPasswordErrorResponseSpec)
                .extract()
                .as(RegistrationPasswordErrorResponseModel.class);
    }

    public RegistrationWithoutUserNameResponseModel registrationWithoutUserName(RegistrationBodyModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(registrationWithoutUserNameResponseSpec)
                .extract()
                .as(RegistrationWithoutUserNameResponseModel.class);
    }

    public RegistrationWithoutUsernameAndPasswordResponseModel registrationWithoutUsernameAndPassword(
            RegistrationBodyModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(registrationWithoutUsernameAndPasswordResponseSpec)
                .extract()
                .as(RegistrationWithoutUsernameAndPasswordResponseModel.class);
    }

    public RegistrationPasswordErrorResponseModel registrationWithLongPassword(RegistrationBodyModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(registrationPasswordErrorResponseSpec)
                .extract()
                .as(RegistrationPasswordErrorResponseModel.class);
    }
}
