package han.se.fswd.tep.module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    @Test
    void setUsername_ValidUsername_ShouldSetUsername() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test
        String validUsername = "validUser";

        // Act
        sut.setUsername(validUsername);

        // Assert
        assertEquals(validUsername, sut.getUsername());
    }

    @Test
    void setUsername_WhenNull_ShouldThrowIllegalArgumentException() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> sut.setUsername(null));
        assertEquals("Username must be between 3 and 16 characters.", thrown.getMessage());
    }

    @Test
    void setUsername_WhenTooShort_ShouldThrowIllegalArgumentException() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test
        String shortUsername = "ab"; // less than 3 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> sut.setUsername(shortUsername));
        assertEquals("Username must be between 3 and 16 characters.", thrown.getMessage());
    }

    @Test
    void setUsername_WhenTooLong_ShouldThrowIllegalArgumentException() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test
        String longUsername = "thisIsAReallyLongUsername"; // more than 16 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> sut.setUsername(longUsername));
        assertEquals("Username must be between 3 and 16 characters.", thrown.getMessage());
    }

    @Test
    void setPassword_ValidPassword_ShouldSetPassword() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test
        String validPassword = "password123";

        // Act
        sut.setPassword(validPassword);

        // Assert
        assertEquals(validPassword, sut.getPassword());
    }

    @Test
    void setPassword_WhenNull_ShouldThrowIllegalArgumentException() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> sut.setPassword(null));
        assertEquals("Password must be between 3 and 60 characters.", thrown.getMessage());
    }

    @Test
    void setPassword_WhenTooShort_ShouldThrowIllegalArgumentException() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test
        String shortPassword = "12"; // less than 3 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> sut.setPassword(shortPassword));
        assertEquals("Password must be between 3 and 60 characters.", thrown.getMessage());
    }

    @Test
    void setPassword_WhenTooLong_ShouldThrowIllegalArgumentException() {
        // Arrange
        LoginRequest sut = new LoginRequest();  // SUT: System Under Test
        String longPassword = "a".repeat(61); // more than 60 characters

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> sut.setPassword(longPassword));
        assertEquals("Password must be between 3 and 60 characters.", thrown.getMessage());
    }
}
