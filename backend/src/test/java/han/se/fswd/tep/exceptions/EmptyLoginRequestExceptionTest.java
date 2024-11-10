package han.se.fswd.tep.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyLoginRequestExceptionTest {

    @Test
    void constructorWithMessage_ShouldSetMessage() {
        // Arrange
        String message = "Missing Login Request";

        // Act
        EmptyLoginRequestException elre = new EmptyLoginRequestException(message);

        // Assert
        assertEquals(message, elre.getMessage());
    }

    @Test
    void constructorWithMessageAndCause_ShouldSetMessageAndCause() {
        // Arrange
        String message = "Missing Login Request";
        RuntimeException cause = new RuntimeException("Cause of the error");

        // Act
        EmptyLoginRequestException elre = new EmptyLoginRequestException(message, cause);

        // Assert
        assertEquals(message, elre.getMessage());
        assertEquals(cause, elre.getCause());
    }
}
