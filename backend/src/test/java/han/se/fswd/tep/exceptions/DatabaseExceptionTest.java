package han.se.fswd.tep.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseExceptionTest {

    @Test
    void constructorWithMessage_ShouldSetMessage() {
        // Arrange
        String message = "Database error";

        // Act
        DatabaseException de = new DatabaseException(message);

        // Assert
        assertEquals(message, de.getMessage());
    }

    @Test
    void constructorWithMessageAndCause_ShouldSetMessageAndCause() {
        // Arrange
        String message = "Database error";
        RuntimeException cause = new RuntimeException("Cause of the error");

        // Act
        DatabaseException de = new DatabaseException(message, cause);

        // Assert
        assertEquals(message, de.getMessage());
        assertEquals(cause, de.getCause());
    }
}
