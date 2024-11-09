package han.se.fswd.tep.module;

public class LoginRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 16) {
            throw new IllegalArgumentException("Username must be between 3 and 16 characters.");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Max of 60 is based on the database max length, not the case description itself.
        if (password == null || password.length() < 3 || password.length() > 60) {
            throw new IllegalArgumentException("Password must be between 3 and 60 characters.");
        }
        this.password = password;
    }
}
