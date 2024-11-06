package han.se.fswd.tep.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class InvalidPasswordExceptionTest {


    @Test
    void constructorWithMessage_ShouldSetMessage() {
        // Arrange
        String message = "Invalid Password";

        // Act
        InvalidPasswordException ipe = new InvalidPasswordException(message);

        // Assert
        assertEquals(message, ipe.getMessage());
    }

    @Test
    void constructorWithMessageAndCause_ShouldSetMessageAndCause() {
        // Arrange
        String message = "Invalid Password";
        RuntimeException cause = new RuntimeException("Cause of the error");

        // Act
        InvalidPasswordException exception = new InvalidPasswordException(message, cause);

        // Assert
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}



