package han.se.fswd.tep.exceptions;

public class EmptyLoginRequestException extends RuntimeException {
    public EmptyLoginRequestException(String message) {
        super(message);
    }

    public EmptyLoginRequestException(String message, Exception cause) {
        super(message, cause);
    }
}
