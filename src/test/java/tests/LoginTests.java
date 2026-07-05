package tests;

import io.qameta.allure.Feature;
import models.login.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.TestData;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.testData.TestData.*;

@Feature("Авторизация")
public class LoginTests extends TestBase {

    TestData testData = new TestData();

    @Test
    @DisplayName("Успешный логин возвращает access и refresh токены")
    public void successfulLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);

        SuccessfulLoginResponseModel loginResponse = api.auth.login(loginData);

        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();
        assertThat(actualAccess).startsWith(LOGIN_TOKEN_PREFIX);
        assertThat(actualRefresh).startsWith(LOGIN_TOKEN_PREFIX);
        assertThat(actualAccess).isNotEqualTo(actualRefresh);
    }

    @Test
    @DisplayName("Логин с неверным паролем возвращает ошибку")
    public void wrongPasswordLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, WRONG_PASSWORD);

        WrongLoginResponseModel loginResponse = api.auth.loginWrongCredentials(loginData);

        String expectedDetailError = LOGIN_WRONG_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.detail();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    @DisplayName("Логин с несуществующим username возвращает ошибку")
    public void wrongUserNameLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(testData.username, LOGIN_PASSWORD);

        WrongLoginResponseModel loginResponse = api.auth.loginWrongCredentials(loginData);

        String expectedDetailError = LOGIN_WRONG_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.detail();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    @DisplayName("Логин без username возвращает ошибку в поле username")
    public void withoutUserNameTest() {
        LoginBodyModel loginData = new LoginBodyModel("", LOGIN_PASSWORD);

        EmptyLoginResponseModel loginResponse = api.auth.emptyLoginCredentials(loginData);

        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.username().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    @DisplayName("Логин без password возвращает ошибку в поле password")
    public void withoutPasswordTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, "");

        EmptyPasswordResponseModel loginResponse = api.auth.emptyPasswordResponseModel(loginData);

        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.password().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    @DisplayName("Логин без username и password возвращает две ошибки")
    public void withoutPasswordAndLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel("", "");

        EmptyPasswordAndLoginResponseModel emptyPasswordAndLogin = api.auth.emptyPasswordAndLogin(loginData);

        assertThat(emptyPasswordAndLogin.username()).containsExactly(EMPTY_CREDENTIALS_ERROR);
        assertThat(emptyPasswordAndLogin.password()).containsExactly(EMPTY_CREDENTIALS_ERROR);
    }
}
