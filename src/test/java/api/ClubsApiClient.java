package api;

import io.qameta.allure.Step;
import models.clubs.ClubsBodyModel;
import models.clubs.SuccessfulCreateClubResponseModel;

import static io.restassured.RestAssured.given;
import static specs.clubs.ClubsSpec.clubsRequestSpec;
import static specs.clubs.ClubsSpec.successfulCreateClubsResponseSpec;

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


}
