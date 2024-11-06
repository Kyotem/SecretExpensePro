package han.se.fswd.tep.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void constructorWithMessage_ShouldSetMessage() {
        // Arrange
        String message = "User not found";

        // Act
        UserNotFoundException unfe = new UserNotFoundException(message);

        // Assert
        assertEquals(message, unfe.getMessage());
    }

    @Test
    void constructorWithMessageAndCause_ShouldSetMessageAndCause() {
        // Arrange
        String message = "User not found";
        RuntimeException cause = new RuntimeException("Cause of the error");

        // Act
        UserNotFoundException exception = new UserNotFoundException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
