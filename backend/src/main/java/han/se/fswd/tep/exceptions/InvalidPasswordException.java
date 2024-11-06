package han.se.fswd.tep.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Exception cause) {
        super(message, cause);
    }
}

