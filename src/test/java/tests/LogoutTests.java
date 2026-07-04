package tests;

import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.logout.LogoutBodyModel;
import org.junit.jupiter.api.Test;

import static tests.TestData.*;

public class LogoutTests extends TestBase {

    @Test
    public void successfulLogoutTest() {
        LoginBodyModel loginData = new LoginBodyModel(LOGIN_USERNAME, LOGIN_PASSWORD);
        String refreshToken = api.auth.loginAndGetRefreshToken(loginData);

        LogoutBodyModel logoutData = new LogoutBodyModel(refreshToken);
        api.auth.logout(logoutData);
    }

    @Test
    public void logoutWithoutTokenTest() {
        LogoutBodyModel logoutData = new LogoutBodyModel("");
        api.auth.logoutWithoutToken(logoutData);
    }

    @Test
    public void logoutWithWrongTokenTest() {
        LogoutBodyModel logoutData = new LogoutBodyModel(WRONG_TOKEN);
        api.auth.logoutWithWrongToken(logoutData);

    }

    //Регистрация с пустыми username и password одновременно
//Регистрация с password длиннее 128 символов
//Регистрация без username/password через null
//Logout с access token вместо refresh token
}
