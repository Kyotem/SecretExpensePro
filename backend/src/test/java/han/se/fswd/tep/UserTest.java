package han.se.fswd.tep;

import han.se.fswd.tep.module.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void setUsername_ValidUsername_ShouldSetUsername() {
        // Arrange
        User user = new User();
        String validUsername = "validUser";

        // Act
        user.setUsername(validUsername);

        // Assert
        assertEquals(validUsername, user.getUsername());
    }

    @Test
    void setUsername_WhenNull_ShouldThrowIllegalArgumentException() {
        // Arrange
        User user = new User();

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> user.setUsername(null));
        assertEquals("Username must be between 3 and 16 characters.", thrown.getMessage());
    }

    @Test
    void setUsername_WhenTooShort_ShouldThrowIllegalArgumentException() {
        // Arrange
        User user = new User();
        String shortUsername = "ab"; // less than 3 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> user.setUsername(shortUsername));
        assertEquals("Username must be between 3 and 16 characters.", thrown.getMessage());
    }

    @Test
    void setUsername_WhenTooLong_ShouldThrowIllegalArgumentException() {
        // Arrange
        User user = new User();
        String longUsername = "thisIsAReallyLongUsername"; // more than 16 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> user.setUsername(longUsername));
        assertEquals("Username must be between 3 and 16 characters.", thrown.getMessage());
    }

    @Test
    void setPassword_ValidPassword_ShouldSetPassword() {
        // Arrange
        User user = new User();
        String validPassword = "password123";

        // Act
        user.setPassword(validPassword);

        // Assert
        assertEquals(validPassword, user.getPassword());
    }

    @Test
    void setPassword_WhenNull_ShouldThrowIllegalArgumentException() {
        // Arrange
        User user = new User();

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> user.setPassword(null));
        assertEquals("Password must be between 3 and 60 characters.", thrown.getMessage());
    }

    @Test
    void setPassword_WhenTooShort_ShouldThrowIllegalArgumentException() {
        // Arrange
        User user = new User();
        String shortPassword = "12"; // less than 3 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> user.setPassword(shortPassword));
        assertEquals("Password must be between 3 and 60 characters.", thrown.getMessage());
    }

    @Test
    void setPassword_WhenTooLong_ShouldThrowIllegalArgumentException() {
        // Arrange
        User user = new User();
        String longPassword = "a".repeat(73); // more than 72 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> user.setPassword(longPassword));
        assertEquals("Password must be between 3 and 60 characters.", thrown.getMessage());
    }

    @Test
    void testToString_ShouldReturnCorrectStringRepresentation() {
        // Arrange
        User user = new User(1, "testUser", "password123");

        // Act
        String result = user.toString();

        // Assert
        assertEquals("User{id=1, username='testUser', password='password123'}", result);
    }
}
