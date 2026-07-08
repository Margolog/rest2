package api;

import io.qameta.allure.Step;
import models.login.*;
import models.logout.LogoutBodyModel;
import models.logout.LogoutWithWrongTokenBodyModel;
import models.logout.LogoutWithoutTokenBodyModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.login.LoginSpec.*;
import static specs.logout.LogoutSpec.*;

public class AuthApiClient {

    @Step("Авторизоваться с валидными данными")
    public SuccessfulLoginResponseModel login(LoginBodyModel loginBody) {
        return given(baseRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .as(SuccessfulLoginResponseModel.class);
    }

    @Step("Авторизация и получение токена")
    public String loginAndGetRefreshToken(LoginBodyModel loginBody) {
        return given(baseRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .path("refresh");
    }

    @Step("Авторизация и получение access токена")
    public String loginAndGetAccessToken(LoginBodyModel loginBody) {
        return given(baseRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .path("access");
    }

    @Step("Авторизоваться с невалидными данными")
    public WrongLoginResponseModel loginWrongCredentials(LoginBodyModel loginBody) {
        return given(baseRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongCredentialsLoginResponseSpec)
                .extract()
                .as(WrongLoginResponseModel.class);
    }

    @Step("Авторизоваться без username")
    public EmptyLoginResponseModel emptyLoginCredentials(LoginBodyModel loginBody) {
        return given(baseRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyCredentialsLoginResponseSpec)
                .extract()
                .as(EmptyLoginResponseModel.class);
    }

    @Step("Авторизоваться без password")
    public EmptyPasswordResponseModel emptyPasswordResponseModel(LoginBodyModel loginBody) {
        return given(baseRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyCredentialsPasswordResponseSpec)
                .extract()
                .as(EmptyPasswordResponseModel.class);
    }

    @Step("Авторизоваться без username и password")
    public EmptyPasswordAndLoginResponseModel emptyPasswordAndLogin(LoginBodyModel loginBody) {
        return given(baseRequestSpec)
                .body(loginBody)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyCredentialsResponseSpec)
                .extract()
                .as(EmptyPasswordAndLoginResponseModel.class);
    }

    @Step("Отправка запроса logout")
    public void logout(LogoutBodyModel logoutBody) {
        given(baseRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
    }

    @Step("Отправить logout без refresh token")
    public LogoutWithoutTokenBodyModel logoutWithoutToken(LogoutBodyModel logoutBody) {
        return given(baseRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(logoutWithoutTokenRequestSpec)
                .extract()
                .as(LogoutWithoutTokenBodyModel.class);
    }

    @Step("Отправить logout с невалидным token")
    public LogoutWithWrongTokenBodyModel logoutWithWrongToken(LogoutBodyModel logoutBody) {
        return given(baseRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(logoutWithWrongTokenRequestSpec)
                .extract()
                .as(LogoutWithWrongTokenBodyModel.class);
    }
}
