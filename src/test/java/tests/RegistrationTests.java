package tests;

import io.qameta.allure.Feature;
import models.registration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.UserData;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.testData.UserData.*;

@Feature("Регистрация")
public class RegistrationTests extends TestBase {

    UserData userData;

    @BeforeEach
    public void prepareTestData() {
        userData = new UserData();
    }

    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    public void successfulRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(userData.username, userData.password);

        SuccessfulRegistrationResponseModel registrationResponse =
                api.users.register(registrationData);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(userData.username);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
    }

    @Test
    @DisplayName("Повторная регистрация существующего пользователя возвращает ошибку")
    public void existingUserWrongRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(userData.username, userData.password);

        SuccessfulRegistrationResponseModel firstRegistrationResponse =
                api.users.register(registrationData);

        assertThat(firstRegistrationResponse.username()).isEqualTo(userData.username);

        ExistingUserResponseModel secondRegistrationResponse =
                api.users.registerExistingUser(registrationData);

        String expectedError = REGISTRATION_EXISTING_USER_ERROR;
        String actualError = secondRegistrationResponse.username().get(0);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Регистрация без password возвращает ошибку в поле password")
    public void registrationWithoutPasswordTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(userData.username, "");

        RegistrationPasswordErrorResponseModel registrationWithoutPassword =
                api.users.registrationWithoutPassword(registrationData);

        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = registrationWithoutPassword.password().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    @DisplayName("Регистрация без username возвращает ошибку в поле username")
    public void registrationWithoutUsernameTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel("", userData.password);

        RegistrationWithoutUserNameResponseModel registrationWithoutUserName =
                api.users.registrationWithoutUserName(registrationData);
        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = registrationWithoutUserName.username().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    @DisplayName("Регистрация без username и password возвращает две ошибки")
    public void registrationWithoutUsernameAndPasswordTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel("", "");

        RegistrationWithoutUsernameAndPasswordResponseModel response =
                api.users.registrationWithoutUsernameAndPassword(registrationData);

        assertThat(response.username()).containsExactly(EMPTY_CREDENTIALS_ERROR);
        assertThat(response.password()).containsExactly(EMPTY_CREDENTIALS_ERROR);
    }

    @Test
    @DisplayName("Регистрация с password длиннее 128 символов возвращает ошибку")
    public void registrationWithLongPasswordTest() {
        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(userData.username, userData.longPassword);

        RegistrationPasswordErrorResponseModel response =
                api.users.registrationWithLongPassword(registrationData);

        assertThat(response.password()).containsExactly(LONG_PASSWORD_ERROR);
    }

    @Test
    @DisplayName("Регистрация с null в username и password возвращает две ошибки")
    public void registrationWithNullUsernameAndPasswordTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(null, null);

        RegistrationWithoutUsernameAndPasswordResponseModel response =
                api.users.registrationWithoutUsernameAndPassword(registrationData);

        assertThat(response.username()).containsExactly(NULL_CREDENTIALS_ERROR);
        assertThat(response.password()).containsExactly(NULL_CREDENTIALS_ERROR);
    }
}
