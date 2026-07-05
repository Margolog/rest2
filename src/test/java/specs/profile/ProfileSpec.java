package specs.profile;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;
import static specs.BaseSpec.baseRequestSpec;

public class ProfileSpec {

    public static RequestSpecification updateUserRequestSpec = baseRequestSpec;

    public static ResponseSpecification successfulUpdateUserResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(200)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/profile/successful_update_user_response_schema.json"))
                    .expectBody("id", notNullValue())
                    .expectBody("username", notNullValue())
                    .expectBody("firstName", notNullValue())
                    .expectBody("lastName", notNullValue())
                    .expectBody("email", notNullValue())
                    .expectBody("remoteAddr", notNullValue())
                    .build();

    public static ResponseSpecification updateUserRequiredFieldsResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(400)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/profile/update_user_required_fields_response_schema.json"))
                    .expectBody("firstName", notNullValue())
                    .expectBody("lastName", notNullValue())
                    .expectBody("email", notNullValue())
                    .build();

    public static ResponseSpecification updateUserAllRequiredFieldsResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(400)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/profile/update_user_all_required_fields_response_schema.json"))
                    .expectBody("username", notNullValue())
                    .expectBody("firstName", notNullValue())
                    .expectBody("lastName", notNullValue())
                    .expectBody("email", notNullValue())
                    .build();

    public static ResponseSpecification patchUserEmptyFieldsResponseSpec =
            new ResponseSpecBuilder()
                    .log(ALL)
                    .expectStatusCode(400)
                    .expectBody(matchesJsonSchemaInClasspath(
                            "schemas/profile/patch_user_empty_fields_response_schema.json"))
                    .expectBody("username", notNullValue())
                    .build();
}
