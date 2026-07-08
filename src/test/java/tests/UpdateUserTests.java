package tests;

import io.qameta.allure.*;
import models.login.LoginBodyModel;
import models.profile.*;
import models.registration.RegistrationBodyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.UserData;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.testData.UserData.*;

@Feature("Профиль пользователя")
public class UpdateUserTests extends TestBase {

    UserData registeredUserData;
    UserData updatedUserData;

    @BeforeEach
    public void prepareTestData() {
        registeredUserData = new UserData();
        updatedUserData = new UserData();
    }


    @Test
    @Story("Успешное обновление профиля")
    @DisplayName("Пользователь может успешно обновить профиль")
    public void successfulUpdateUserTest() {
        api.users.register(new RegistrationBodyModel(
                registeredUserData.username,
                registeredUserData.password
        ));

        LoginBodyModel loginData =
                new LoginBodyModel(registeredUserData.username, registeredUserData.password);

        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        UpdateUserBodyModel updateData = new UpdateUserBodyModel(
                updatedUserData.username,
                updatedUserData.firstName,
                updatedUserData.lastName,
                updatedUserData.email
        );

        UpdateUserResponseModel response = api.profile.updateUser(accessToken, updateData);

        step("Проверить, что профиль обновился", () -> {
            assertThat(response.id()).isGreaterThan(0);
            assertThat(response.username()).isEqualTo(updatedUserData.username);
            assertThat(response.firstName()).isEqualTo(updatedUserData.firstName);
            assertThat(response.lastName()).isEqualTo(updatedUserData.lastName);
            assertThat(response.email()).isEqualTo(updatedUserData.email);
            assertThat(response.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });
    }

    @Test
    @Story("Валидация обязательных полей")
    @DisplayName("Обновление профиля только с username возвращает ошибки firstName, lastName и email")
    public void updateUserOnlyWithUsernameTest() {
        api.users.register(new RegistrationBodyModel(
                registeredUserData.username,
                registeredUserData.password
        ));

        LoginBodyModel loginData =
                new LoginBodyModel(registeredUserData.username, registeredUserData.password);

        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        UpdateUserOnlyUsernameBodyModel updateData =
                new UpdateUserOnlyUsernameBodyModel(updatedUserData.username);

        UpdateUserRequiredFieldsResponseModel response =
                api.profile.updateUserWithoutRequiredFields(accessToken, updateData);

        step("Проверить ошибки обязательных полей", () -> {
            assertThat(response.firstName()).containsExactly(REQUIRED_FIELD_ERROR);
            assertThat(response.lastName()).containsExactly(REQUIRED_FIELD_ERROR);
            assertThat(response.email()).containsExactly(REQUIRED_FIELD_ERROR);
        });
    }

    @Test
    @Story("Валидация обязательных полей")
    @DisplayName("Обновление профиля с пустым JSON возвращает ошибки всех обязательных полей")
    public void updateUserWithoutAllRequiredFieldsTest() {
        api.users.register(new RegistrationBodyModel(
                registeredUserData.username,
                registeredUserData.password
        ));

        LoginBodyModel loginData =
                new LoginBodyModel(registeredUserData.username, registeredUserData.password);

        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        UpdateUserEmptyBodyModel updateData = new UpdateUserEmptyBodyModel();

        UpdateUserAllRequiredFieldsResponseModel response =
                api.profile.updateUserWithoutAllRequiredFields(accessToken, updateData);

        step("Проверить ошибки всех обязательных полей", () -> {
            assertThat(response.username()).containsExactly(REQUIRED_FIELD_ERROR);
            assertThat(response.firstName()).containsExactly(REQUIRED_FIELD_ERROR);
            assertThat(response.lastName()).containsExactly(REQUIRED_FIELD_ERROR);
            assertThat(response.email()).containsExactly(REQUIRED_FIELD_ERROR);
        });
    }

    @Test
    @Story("Успешное частичное обновление профиля")
    @DisplayName("PATCH обновляет username пользователя")
    public void patchUserNameTest() {
        api.users.register(new RegistrationBodyModel(
                registeredUserData.username,
                registeredUserData.password
        ));

        LoginBodyModel loginData =
                new LoginBodyModel(registeredUserData.username, registeredUserData.password);

        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        PatchUserBodyModel patchData =
                new PatchUserBodyModel(updatedUserData.username, null, null, null);

        UpdateUserResponseModel response = api.profile.patchUser(accessToken, patchData);

        step("Проверить, что username обновился", () ->
                assertThat(response.username()).isEqualTo(updatedUserData.username)
        );
    }


    @Test
    @Story("Валидация пустых значений")
    @DisplayName("PATCH профиля с пустым username возвращает ошибку")
    public void patchUserWithEmptyFieldsTest() {
        api.users.register(new RegistrationBodyModel(
                registeredUserData.username,
                registeredUserData.password
        ));

        LoginBodyModel loginData =
                new LoginBodyModel(registeredUserData.username, registeredUserData.password);

        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        UpdateUserBodyModel updateData = new UpdateUserBodyModel("", "", "", "");

        PatchUserEmptyFieldsResponseModel response =
                api.profile.patchUserWithEmptyFields(accessToken, updateData);

        step("Проверить ошибку в поле username", () ->
                assertThat(response.username()).containsExactly(EMPTY_CREDENTIALS_ERROR)
        );
    }
}
