package han.se.fswd.tep.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}
