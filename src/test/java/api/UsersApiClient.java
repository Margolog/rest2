package api;

import models.registration.*;

import static io.restassured.RestAssured.given;
import static specs.registration.RegistrationSpec.*;

public class UsersApiClient {

    public SuccessfulRegistrationResponseModel register(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
    }

    public ExistingUserResponseModel registerExistingUser(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(existingUserRegistrationResponseSpec)
                .extract()
                .as(ExistingUserResponseModel.class);
    }

    public RegistrationWithoutPasswordResponseModel registrationWithoutPassword(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(registrationWithoutPasswordResponseSpec)
                .extract()
                .as(RegistrationWithoutPasswordResponseModel.class);
    }

    public RegistrationWithoutUserNameResponseModel registrationWithoutUserName(RegistrationBodyModel body) {
        return given(registrationRequestSpec)
                .body(body)
                .when()
                .post("/users/register/")
                .then()
                .spec(registrationWithoutUserNameResponseSpec)
                .extract()
                .as(RegistrationWithoutUserNameResponseModel.class);
    }
}