package api;

import io.qameta.allure.Step;
import models.clubs.ClubsBodyModel;
import models.clubs.ClubResponseModel;
import models.clubs.SuccessfulGetClubsResponseModel;

import static io.restassured.RestAssured.given;
import static specs.clubs.ClubsSpec.*;

public class ClubsApiClient {

    @Step("Создание clubs")
    public ClubResponseModel createClub(String accessToken, ClubsBodyModel body) {
        return given(clubsRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulCreateClubsResponseSpec)
                .extract()
                .as(ClubResponseModel.class);
    }


    @Step("Получение clubs по поиску")
    public SuccessfulGetClubsResponseModel getClubs(String accessToken, String search) {
        return given(clubsRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("search", search)
                .when()
                .get("/clubs/")
                .then()
                .spec(successfulGetClubsResponseSpec)
                .extract()
                .as(SuccessfulGetClubsResponseModel.class);
    }

    @Step("Проверка что определенный клуб удален")
    public void checkClubNotFoundById(String accessToken, Integer id) {
        given(clubsRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/clubs/{id}/", id)
                .then()
                .spec(notFoundClubResponseSpec);
    }

    @Step("Обновление клуба")
    public ClubResponseModel patchClubs(String accessToken, Integer id, ClubsBodyModel body) {
        return given(clubsRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .body(body)
                .patch("/clubs/{id}/", id)
                .then()
                .spec(successfulUpdateClubsResponseSpec)
                .extract()
                .as(ClubResponseModel.class);
    }

    @Step("Удаление клуба")
    public void deleteClubs(String accessToken, Integer id) {
        given(clubsRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("/clubs/{id}/", id)
                .then()
                .spec(successfulDeleteClubsResponseSpec);
    }
}
