package tests;

import io.qameta.allure.Feature;
import models.clubs.ClubsBodyModel;
import models.clubs.ClubResponseModel;
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
        api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        LoginBodyModel loginData = new LoginBodyModel(userData.username, userData.password);
        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        ClubsBodyModel clubBody = new ClubsBodyModel(
                clubData.bookTitle,
                clubData.bookAuthors,
                clubData.publicationYear,
                clubData.bookDescription,
                clubData.telegramChatLink
        );

        ClubResponseModel clubResponse = api.clubs.createClub(accessToken, clubBody);

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
        api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        LoginBodyModel loginData = new LoginBodyModel(userData.username, userData.password);
        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        ClubsBodyModel clubBody = new ClubsBodyModel(
                clubData.bookTitle,
                clubData.bookAuthors,
                clubData.publicationYear,
                clubData.bookDescription,
                clubData.telegramChatLink
        );

        ClubResponseModel createdClub = api.clubs.createClub(accessToken, clubBody);

        SuccessfulGetClubsResponseModel clubsResponse =
                api.clubs.getClubs(accessToken, clubData.bookTitle);

        step("Проверить, что созданный клуб есть в списке", () -> {
            assertThat(clubsResponse.count()).isPositive();
            assertThat(clubsResponse.results())
                    .anySatisfy(club -> {
                        assertThat(club.id()).isEqualTo(createdClub.id());
                        assertThat(club.bookTitle()).isEqualTo(clubData.bookTitle);
                    });
        });
    }

    @Test
    @DisplayName("Обновление названия книги")
    public void changeBookTitleTest() {
        api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        LoginBodyModel loginData = new LoginBodyModel(userData.username, userData.password);
        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        ClubsBodyModel clubBody = new ClubsBodyModel(
                clubData.bookTitle,
                clubData.bookAuthors,
                clubData.publicationYear,
                clubData.bookDescription,
                clubData.telegramChatLink
        );

        ClubResponseModel createdClub = api.clubs.createClub(accessToken, clubBody);

        ClubsBodyModel updatedClubBody = new ClubsBodyModel(
                clubData.updatedBookTitle,
                null,
                null,
                null,
                null
        );

        ClubResponseModel updatedClub = api.clubs.patchClubs(accessToken, createdClub.id(), updatedClubBody);

        step("Проверить, что название книги обновилось", () -> {
            assertThat(updatedClub.id()).isEqualTo(createdClub.id());
            assertThat(updatedClub.bookTitle()).isEqualTo(clubData.updatedBookTitle);
            assertThat(updatedClub.modified()).isNotBlank();
        });
    }

    @Test
    @DisplayName("Удаление клуба")
    public void deleteClubTest() {
        api.users.register(new RegistrationBodyModel(userData.username, userData.password));

        LoginBodyModel loginData = new LoginBodyModel(userData.username, userData.password);
        String accessToken = api.auth.loginAndGetAccessToken(loginData);

        ClubsBodyModel clubBody = new ClubsBodyModel(
                clubData.bookTitle,
                clubData.bookAuthors,
                clubData.publicationYear,
                clubData.bookDescription,
                clubData.telegramChatLink
        );

        ClubResponseModel createdClub = api.clubs.createClub(accessToken, clubBody);

        api.clubs.deleteClubs(accessToken, createdClub.id());
        api.clubs.checkClubNotFoundById(accessToken, createdClub.id());
    }
}
