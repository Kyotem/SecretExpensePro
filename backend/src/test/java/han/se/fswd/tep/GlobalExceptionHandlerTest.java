package han.se.fswd.tep;

import han.se.fswd.tep.exceptions.DatabaseException;
import han.se.fswd.tep.exceptions.InvalidUserInputException;
import han.se.fswd.tep.exceptions.UserNotFoundException;
import han.se.fswd.tep.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler sut = new GlobalExceptionHandler();  // System Under Test

    @Test
    void handleUserNotFoundException_WhenUserNotFound_ShouldReturnUnauthorizedErrorStatus() {
        // Arrange
        UserNotFoundException unfe = new UserNotFoundException("User not found");

        // Act
        ResponseEntity<String> response = sut.handleUserNotFoundException(unfe);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void handleDatabaseException_WhenDatabaseErrorOccurs_ShouldReturnInternalServerErrorStatus() {
        // Arrange
        DatabaseException de = new DatabaseException("Database error");

        // Act
        ResponseEntity<String> response = sut.handleDatabaseException(de);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Database error", response.getBody());
    }

    @Test
    void handleInvalidUserInputException_WhenInvalidUserInput_ShouldReturnBadRequestErrorStatus() {
        // Arrange
        InvalidUserInputException iuie = new InvalidUserInputException("Username must be between 3 and 16 characters");

        // Act
        ResponseEntity<String> response = sut.handleInvalidUserInputException(iuie);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username must be between 3 and 16 characters", response.getBody());
    }

    @Test
    void handleIllegalArgumentException_WhenInvalidInputIsProvided_ShouldReturnBadRequestErrorStatus() {
        // Arrange
        IllegalArgumentException iae = new IllegalArgumentException("Invalid input");

        // Act
        ResponseEntity<String> response = sut.handleIllegalArgumentException(iae);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid input", response.getBody());
    }
}
