package models.logout;

import java.util.List;

public record LogoutWithoutTokenBodyModel(List<String> refresh) {}
