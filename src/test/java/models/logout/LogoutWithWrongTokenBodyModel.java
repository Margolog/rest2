package models.logout;

import java.util.List;

public record LogoutWithWrongTokenBodyModel(String detail, String code) {}
