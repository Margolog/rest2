package tests;

import io.qameta.allure.Feature;
import models.clubs.ClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;
import models.clubs.SuccessfulGetClubsResponseModel;
import models.login.LoginBodyModel;
import models.registration.RegistrationBodyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.testData.ClubData;
import tests.testData.UserData;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("Книжные клубы")
public class ClubsTests extends TestBase {

    ClubData clubData;
    UserData userData;

    @BeforeEach
    public void prepareTestData() {
        clubData = new ClubData();
        userData = new UserData();
    }

    @Test
    @DisplayName("Успешное создание клуба возвращает данные созданного клуба")
    public void successfulCreateClubTest() {
        step("Зарегистрировать нового пользователя", () -> {
            RegistrationBodyModel registrationData =
                    new RegistrationBodyModel(userData.username, userData.password);

            api.users.register(registrationData);
        });

        String accessToken = step("Авторизоваться новым пользователем", () -> {
            LoginBodyModel loginData = new LoginBodyModel(userData.username, userData.password);

            return api.auth.loginAndGetAccessToken(loginData);
        });

        SuccessfulCreateClubResponseModel clubResponse = step("Создать книжный клуб", () -> {
            ClubsBodyModel clubData = new ClubsBodyModel(
                    this.clubData.bookTitle,
                    this.clubData.bookAuthors,
                    this.clubData.publicationYear,
                    this.clubData.bookDescription,
                    this.clubData.telegramChatLink
            );

            return api.clubs.createClub(accessToken, clubData);
        });

        step("Проверить данные созданного клуба", () -> {
            assertThat(clubResponse.bookTitle()).isEqualTo(clubData.bookTitle);
            assertThat(clubResponse.bookAuthors()).isEqualTo(clubData.bookAuthors);
            assertThat(clubResponse.publicationYear()).isEqualTo(clubData.publicationYear);
            assertThat(clubResponse.description()).isEqualTo(clubData.bookDescription);
            assertThat(clubResponse.telegramChatLink()).isEqualTo(clubData.telegramChatLink);
            assertThat(clubResponse.owner()).isPositive();
            assertThat(clubResponse.members()).contains(clubResponse.owner());
            assertThat(clubResponse.created()).isNotBlank();
            assertThat(clubResponse.modified()).isNull();
        });
    }

    @Test
    @DisplayName("Получение списка клубов возвращает созданный клуб")
    public void successfulGetClubsTest() {
        step("Зарегистрировать нового пользователя", () -> {
            RegistrationBodyModel registrationData =
                    new RegistrationBodyModel(userData.username, userData.password);

            api.users.register(registrationData);
        });

        String accessToken = step("Авторизоваться новым пользователем", () -> {
            LoginBodyModel loginData = new LoginBodyModel(userData.username, userData.password);

            return api.auth.loginAndGetAccessToken(loginData);
        });

        SuccessfulCreateClubResponseModel createdClub = step("Создать книжный клуб", () -> {
            ClubsBodyModel clubBody = new ClubsBodyModel(
                    clubData.bookTitle,
                    clubData.bookAuthors,
                    clubData.publicationYear,
                    clubData.bookDescription,
                    clubData.telegramChatLink
            );

            return api.clubs.createClub(accessToken, clubBody);
        });

        SuccessfulGetClubsResponseModel clubsResponse =
                step("Получить список книжных клубов", () ->
                        api.clubs.getClubs(accessToken, clubData.bookTitle));

        step("Проверить, что созданный клуб есть в списке", () -> {
            assertThat(clubsResponse.count()).isPositive();
            assertThat(clubsResponse.results())
                    .anySatisfy(club -> {
                        assertThat(club.id()).isEqualTo(createdClub.id());
                        assertThat(club.bookTitle()).isEqualTo(clubData.bookTitle);
                        assertThat(club.bookAuthors()).isEqualTo(clubData.bookAuthors);
                        assertThat(club.publicationYear()).isEqualTo(clubData.publicationYear);
                        assertThat(club.description()).isEqualTo(clubData.bookDescription);
                        assertThat(club.telegramChatLink()).isEqualTo(clubData.telegramChatLink);
                        assertThat(club.owner()).isEqualTo(createdClub.owner());
                        assertThat(club.members()).contains(createdClub.owner());
                    });
        });
    }
}
