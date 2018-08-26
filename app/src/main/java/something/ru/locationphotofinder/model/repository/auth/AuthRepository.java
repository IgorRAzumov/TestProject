package something.ru.locationphotofinder.model.repository.auth;

public class AuthRepository implements IAuthRepository {
    private String accessToken;

    @Override
    public void setToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
