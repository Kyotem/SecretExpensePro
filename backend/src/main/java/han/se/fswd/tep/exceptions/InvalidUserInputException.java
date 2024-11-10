package han.se.fswd.tep.exceptions;

public class InvalidUserInputException extends RuntimeException {
    public InvalidUserInputException(String message) {
        super(message);
    }

    public InvalidUserInputException(String message, Exception cause) {
        super(message, cause);
    }
}
