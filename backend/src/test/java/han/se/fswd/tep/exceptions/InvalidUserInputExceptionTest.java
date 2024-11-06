package han.se.fswd.tep.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidUserInputExceptionTest {

    @Test
    void constructorWithMessage_ShouldSetMessage() {
        // Arrange
        String message = "Invalid input";

        // Act
        InvalidUserInputException iuie = new InvalidUserInputException(message);

        // Assert
        assertEquals(message, iuie.getMessage());
    }

    @Test
    void constructorWithMessageAndCause_ShouldSetMessageAndCause() {
        // Arrange
        String message = "Invalid input";
        RuntimeException cause = new RuntimeException("Cause of the error");

        // Act
        InvalidUserInputException exception = new InvalidUserInputException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
