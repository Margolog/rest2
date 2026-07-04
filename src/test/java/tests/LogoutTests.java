package tests;

import models.login.LoginBodyModel;
import models.logout.LogoutBodyModel;
import models.logout.LogoutWithWrongTokenBodyModel;
import models.logout.LogoutWithoutTokenBodyModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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

        LogoutWithoutTokenBodyModel response =
                api.auth.logoutWithoutToken(logoutData);

        assertThat(response.refresh()).containsExactly(EMPTY_CREDENTIALS_ERROR);
    }

    @Test
    public void logoutWithWrongTokenTest() {
        LogoutBodyModel logoutData = new LogoutBodyModel(WRONG_TOKEN);

        LogoutWithWrongTokenBodyModel response =
                api.auth.logoutWithWrongToken(logoutData);

        assertThat(response.detail()).isEqualTo(INVALID_TOKEN_DETAIL_ERROR);
        assertThat(response.code()).isEqualTo(INVALID_TOKEN_CODE_ERROR);
    }

    //Регистрация с пустыми username и password одновременно
//Регистрация с password длиннее 128 символов
//Регистрация без username/password через null
//Logout с access token вместо refresh token
}
