package api;

import io.qameta.allure.Step;
import models.clubs.ClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;
import models.clubs.SuccessfulGetClubsResponseModel;

import static io.restassured.RestAssured.given;
import static specs.clubs.ClubsSpec.*;

public class ClubsApiClient {

    @Step("Создание clubs")
    public SuccessfulCreateClubResponseModel createClub(String accessToken, ClubsBodyModel body) {
        return given(clubsRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulCreateClubsResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
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
}
