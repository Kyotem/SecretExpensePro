package han.se.fswd.tep.module;

public class LoginResponse {

    private String token;

    public LoginResponse() {}

    public LoginResponse(String message) {
        this.token = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
