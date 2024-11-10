package han.se.fswd.tep.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import han.se.fswd.tep.dao.UserDaoImpl;
import han.se.fswd.tep.exceptions.InvalidPasswordException;
import han.se.fswd.tep.module.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDaoImpl userDaoImpl;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService sut;

    private final String testUsername = "testUser";
    private final String testPassword = "password123";
    private final String pepperValue = "DGmhu0H![3(]z.1u=+vlfjN&kuLJwYK6z'101S_T!m37^Py(T>";

    private User mockUser;

    @BeforeEach
    void setUp() {
        // Use BCryptPasswordEncoder to hash the password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        sut = new UserService(passwordEncoder, userDaoImpl, jwtUtil);  // Injecting the mocked userDaoImpl and jwtUtil

        String rawPasswordWithPepper = testPassword + pepperValue;
        String hashedPassword = passwordEncoder.encode(rawPasswordWithPepper);
        mockUser = new User(1, testUsername, hashedPassword);  // Creating a mocked User with hashed password
    }

    @Test
    void validateUserPassword_WhenPasswordsMatch_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> sut.validateUserPassword(testPassword, mockUser.getPassword()));
    }

    @Test
    void validateUserPassword_WhenPasswordsDoNotMatch_ShouldThrowInvalidPasswordException() {
        // Act & Assert
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                () -> sut.validateUserPassword("wrongPassword", mockUser.getPassword()));
        assertEquals("Passwords do not match", exception.getMessage());
    }

    @Test
    void authenticateUser_WhenUserExistsAndPasswordIsCorrect_ShouldReturnJwt() {
        // Arrange
        when(userDaoImpl.findByUsername(testUsername)).thenReturn(mockUser);  // Mocking UserDaoImpl response
        String expectedToken = "someJWTToken";  // Simulate JWT string
        when(jwtUtil.generateToken(mockUser.getId())).thenReturn(expectedToken);  // Mocking JWT generation

        // Act
        String result = sut.authenticateUser(testUsername, testPassword);

        // Assert
        assertEquals(expectedToken, result);  // Ensure the token returned is the same as the mocked token
    }

    @Test
    void authenticateUser_WhenPasswordsDoNotMatch_ShouldThrowInvalidPasswordException() {
        // Arrange
        when(userDaoImpl.findByUsername(testUsername)).thenReturn(mockUser);  // Mocking UserDaoImpl response

        // Act & Assert
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                () -> sut.authenticateUser(testUsername, "wrongPassword"));
        assertEquals("Passwords do not match", exception.getMessage());
    }

    @Test
    void blacklistToken_WhenValidToken_ShouldBlacklistToken() {
        // Arrange
        String validToken = "validToken";
        when(jwtUtil.validateToken(validToken)).thenReturn(true);  // Mock the token validation to return true

        // Act
        sut.blacklistToken(validToken);  // Call the method to blacklist the token

        // Assert
        verify(jwtUtil).blacklistToken(validToken);  // Verify that blacklistToken was called with the correct argument
    }

    @Test
    void blacklistToken_WhenInvalidToken_ShouldNotBlacklistToken() {
        // Arrange
        String invalidToken = "invalidToken";
        when(jwtUtil.validateToken(invalidToken)).thenReturn(false);  // Mock the token validation to return false

        // Act
        sut.blacklistToken(invalidToken);  // Call the method to blacklist the token

        // Assert
        verify(jwtUtil, never()).blacklistToken(invalidToken);  // Verify that blacklistToken was not called
    }
}
