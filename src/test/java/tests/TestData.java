package tests;

import net.datafaker.Faker;

public class TestData {

    public static Faker faker = new Faker();

    public String
            username = "user_" + faker.regexify("[A-Za-z0-9]{10}") + "_" + System.currentTimeMillis(),
    firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            email = faker.internet().emailAddress(),
            password = faker.regexify("[A-Za-z0-9]{8}");


    public static final String LOGIN_USERNAME = "user_1783169597082";
    public static final String LOGIN_PASSWORD = "pass_1783169597082";
    public static final String LOGIN_WRONG_PASSWORD = "qaguru1234";
    public static final String WRONG_TOKEN = "wrong token";

    public static final String LOGIN_TOKEN_PREFIX = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    public static final String LOGIN_WRONG_CREDENTIALS_ERROR = "Invalid username or password.";
    public static final String EMPTY_CREDENTIALS_ERROR = "This field may not be blank.";
    public static final String INVALID_TOKEN_DETAIL_ERROR ="Token is invalid";
    public static final String INVALID_TOKEN_CODE_ERROR ="token_not_valid";

    public static final String REGISTRATION_EXISTING_USER_ERROR =
            "A user with that username already exists.";

    public static final String REGISTRATION_IP_REGEXP =
            "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                    + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
}