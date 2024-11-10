package han.se.fswd.tep.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Exception cause) {
        super(message, cause);
    }
}
