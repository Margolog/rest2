package models.login;

import java.util.List;

public record EmptyPasswordAndLoginResponseModel(List<String> username,List<String> password ) {}