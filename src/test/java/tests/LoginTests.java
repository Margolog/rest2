package tests;

import models.login.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class LoginTests extends TestBase {

    TestData testData = new TestData();

    @Test
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
    public void wrongPasswordLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_WRONG_PASSWORD);

        WrongLoginResponseModel loginResponse = api.auth.loginWrongCredentials(loginData);

        String expectedDetailError = LOGIN_WRONG_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.detail();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void wrongUserNameLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel(testData.username, LOGIN_PASSWORD);

        WrongLoginResponseModel loginResponse = api.auth.loginWrongCredentials(loginData);

        String expectedDetailError = LOGIN_WRONG_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.detail();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void withoutUserNameTest() {
        LoginBodyModel loginData = new LoginBodyModel("", LOGIN_PASSWORD);

        EmptyLoginResponseModel loginResponse = api.auth.emptyLoginCredentials(loginData);

        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.username().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void withoutPasswordTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, "");

        EmptyPasswordResponseModel loginResponse = api.auth.emptyPasswordResponseModel(loginData);

        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = loginResponse.password().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void withoutPasswordAndLoginTest() {
        LoginBodyModel loginData = new LoginBodyModel("", "");

        EmptyPasswordAndLoginResponseModel emptyPasswordAndLogin = api.auth.emptyPasswordAndLogin(loginData);

        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = emptyPasswordAndLogin.password().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }
}
