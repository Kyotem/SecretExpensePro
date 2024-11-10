package han.se.fswd.tep.module;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseTest {

    @Test
    void constructor_WithToken_ShouldSetToken() {
        // Arrange
        String token = "testToken";
        LoginResponse sut = new LoginResponse(token);  // SUT: System Under Test

        // Act & Assert
        assertEquals(token, sut.getToken());
    }

    @Test
    void setToken_ValidToken_ShouldSetToken() {
        // Arrange
        LoginResponse sut = new LoginResponse();  // SUT: System Under Test
        String token = "newToken";

        // Act
        sut.setToken(token);

        // Assert
        assertEquals(token, sut.getToken());
    }

    @Test
    void getToken_ShouldReturnCorrectToken() {
        // Arrange
        LoginResponse sut = new LoginResponse();  // SUT: System Under Test
        String token = "anotherToken";
        sut.setToken(token);

        // Act
        String retrievedToken = sut.getToken();

        // Assert
        assertEquals(token, retrievedToken);
    }
}
