package han.se.fswd.tep.module;

public class User {

    private int id;
    private String username;
    private String password;

    // Empty Constructor
    public User() {
    }

    // Parameterized Constructor
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    // For future use, might be better to use the Bean Validation, for now sticking with throwing Exceptions manually.
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}



