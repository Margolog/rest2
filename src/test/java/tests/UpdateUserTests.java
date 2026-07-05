package tests;

import io.qameta.allure.*;
import models.login.LoginBodyModel;
import models.profile.*;
import models.registration.RegistrationBodyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.TestData;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static tests.testData.TestData.*;

@Feature("Профиль пользователя")
public class UpdateUserTests extends TestBase {

    TestData registeredUserData;
    TestData updatedUserData;

    @BeforeEach
    public void prepareTestData() {
        registeredUserData = new TestData();
        updatedUserData = new TestData();
    }


    @Test
    @Story("Успешное обновление профиля")
    @DisplayName("Пользователь может успешно обновить профиль")
    public void successfulUpdateUserTest() {
        step("Зарегистрировать нового пользователя", () -> {
            RegistrationBodyModel registrationData =
                    new RegistrationBodyModel(registeredUserData.username, registeredUserData.password);

            api.users.register(registrationData);
        });

        String accessToken = step("Авторизоваться и получить access token", () -> {
            LoginBodyModel loginData =
                    new LoginBodyModel(registeredUserData.username, registeredUserData.password);

            return api.auth.loginAndGetAccessToken(loginData);
        });

        UpdateUserBodyModel updateData = step("Подготовить данные для обновления профиля", () ->
                new UpdateUserBodyModel(
                        updatedUserData.username,
                        updatedUserData.firstName,
                        updatedUserData.lastName,
                        updatedUserData.email
                )
        );

        UpdateUserResponseModel response = step("Отправить PUT-запрос на обновление профиля", () ->
                api.profile.updateUser(accessToken, updateData)
        );

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
        step("Зарегистрировать нового пользователя", () -> {
            RegistrationBodyModel registrationData =
                    new RegistrationBodyModel(registeredUserData.username, registeredUserData.password);

            api.users.register(registrationData);
        });

        String accessToken = step("Авторизоваться и получить access token", () -> {
            LoginBodyModel loginData =
                    new LoginBodyModel(registeredUserData.username, registeredUserData.password);

            return api.auth.loginAndGetAccessToken(loginData);
        });

        UpdateUserOnlyUsernameBodyModel updateData = step("Подготовить данные только с username", () ->
                new UpdateUserOnlyUsernameBodyModel(updatedUserData.username)
        );

        UpdateUserRequiredFieldsResponseModel response =
                step("Отправить PUT-запрос без firstName, lastName и email", () ->
                        api.profile.updateUserWithoutRequiredFields(accessToken, updateData)
                );

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
        step("Зарегистрировать нового пользователя", () -> {
            RegistrationBodyModel registrationData =
                    new RegistrationBodyModel(registeredUserData.username, registeredUserData.password);

            api.users.register(registrationData);
        });

        String accessToken = step("Авторизоваться и получить access token", () -> {
            LoginBodyModel loginData =
                    new LoginBodyModel(registeredUserData.username, registeredUserData.password);

            return api.auth.loginAndGetAccessToken(loginData);
        });

        UpdateUserEmptyBodyModel updateData = step("Подготовить пустой JSON для обновления профиля",
                UpdateUserEmptyBodyModel::new
        );

        UpdateUserAllRequiredFieldsResponseModel response =
                step("Отправить PUT-запрос без username, firstName, lastName и email", () ->
                        api.profile.updateUserWithoutAllRequiredFields(accessToken, updateData)
                );

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
        step("Зарегистрировать нового пользователя", () -> {
            RegistrationBodyModel registrationData =
                    new RegistrationBodyModel(registeredUserData.username, registeredUserData.password);

            api.users.register(registrationData);
        });

        String accessToken = step("Авторизоваться и получить access token", () -> {
            LoginBodyModel loginData =
                    new LoginBodyModel(registeredUserData.username, registeredUserData.password);

            return api.auth.loginAndGetAccessToken(loginData);
        });

        PatchUserBodyModel patchData = step("Подготовить новый username", () ->
                new PatchUserBodyModel(updatedUserData.username, null, null, null)
        );

        UpdateUserResponseModel response = step("Отправить PATCH-запрос на обновление username", () ->
                api.profile.patchUser(accessToken, patchData)
        );

        step("Проверить, что username обновился", () ->
                assertThat(response.username()).isEqualTo(updatedUserData.username)
        );
    }


    @Test
    @Story("Валидация пустых значений")
    @DisplayName("PATCH профиля с пустым username возвращает ошибку")
    public void patchUserWithEmptyFieldsTest() {
        step("Зарегистрировать нового пользователя", () -> {
            RegistrationBodyModel registrationData =
                    new RegistrationBodyModel(registeredUserData.username, registeredUserData.password);

            api.users.register(registrationData);
        });

        String accessToken = step("Авторизоваться и получить access token", () -> {
            LoginBodyModel loginData =
                    new LoginBodyModel(registeredUserData.username, registeredUserData.password);

            return api.auth.loginAndGetAccessToken(loginData);
        });

        UpdateUserBodyModel updateData = step("Подготовить данные с пустыми значениями", () ->
                new UpdateUserBodyModel("", "", "", "")
        );

        PatchUserEmptyFieldsResponseModel response =
                step("Отправить PATCH-запрос с пустым username", () ->
                        api.profile.patchUserWithEmptyFields(accessToken, updateData)
                );

        step("Проверить ошибку в поле username", () ->
                assertThat(response.username()).containsExactly(EMPTY_CREDENTIALS_ERROR)
        );
    }
}
