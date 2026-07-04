package tests;

import models.registration.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static tests.TestData.*;

public class RegistrationTests extends TestBase {

    TestData testData;

    @BeforeEach
    public void prepareTestData() {
        testData = new TestData();
    }

    @Test
    public void successfulRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(testData.username, testData.password);

        SuccessfulRegistrationResponseModel registrationResponse =
                api.users.register(registrationData);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(testData.username);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
    }

    @Test
    public void existingUserWrongRegistrationTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(testData.username, testData.password);

        SuccessfulRegistrationResponseModel firstRegistrationResponse =
                api.users.register(registrationData);

        assertThat(firstRegistrationResponse.username()).isEqualTo(testData.username);

        ExistingUserResponseModel secondRegistrationResponse =
                api.users.registerExistingUser(registrationData);

        String expectedError = REGISTRATION_EXISTING_USER_ERROR;
        String actualError = secondRegistrationResponse.username().get(0);
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    public void registrationWithoutPasswordTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(testData.username, "");

        RegistrationWithoutPasswordResponseModel registrationWithoutPassword =
                api.users.registrationWithoutPassword(registrationData);

        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = registrationWithoutPassword.password().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void registrationWithoutUsernameTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel("", testData.password);

        RegistrationWithoutUserNameResponseModel registrationWithoutUserName =
                api.users.registrationWithoutUserName(registrationData);
        String expectedDetailError = EMPTY_CREDENTIALS_ERROR;
        String actualDetailError = registrationWithoutUserName.username().get(0);
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }
}


