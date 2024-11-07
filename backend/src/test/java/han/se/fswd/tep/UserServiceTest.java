package han.se.fswd.tep;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import han.se.fswd.tep.dao.UserDaoImpl;
import han.se.fswd.tep.exceptions.InvalidPasswordException;
import han.se.fswd.tep.module.User;
import han.se.fswd.tep.service.UserService;
import han.se.fswd.tep.service.JwtUtil;  // Import JwtUtil
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
    private JwtUtil jwtUtil;  // Mock JwtUtil to simulate JWT generation

    @InjectMocks
    private UserService sut;  // System Under Test

    private final String testUsername = "testUser";
    private final String testPassword = "password123";
    private final String pepperValue = "DGmhu0H![3(]z.1u=+vlfjN&kuLJwYK6z'101S_T!m37^Py(T>";

    private User mockUser;

    @BeforeEach
    void setUp() {
        // Use BCryptPasswordEncoder to hash the password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        sut = new UserService(passwordEncoder, userDaoImpl, jwtUtil);

        String rawPasswordWithPepper = testPassword + pepperValue;
        String hashedPassword = passwordEncoder.encode(rawPasswordWithPepper);
        mockUser = new User(1, testUsername, hashedPassword);
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
        when(userDaoImpl.findByUsername(testUsername)).thenReturn(mockUser);
        String expectedToken = "someJWTToken"; // Simulate JWT string
        when(jwtUtil.generateToken(mockUser.getId())).thenReturn(expectedToken);

        // Act
        String result = sut.authenticateUser(testUsername, testPassword);

        // Assert
        assertEquals(expectedToken, result); // Ensure the token returned is the same as the mocked token
    }

    @Test
    void authenticateUser_WhenPasswordsDoNotMatch_ShouldThrowInvalidPasswordException() {
        // Arrange
        when(userDaoImpl.findByUsername(testUsername)).thenReturn(mockUser);

        // Act & Assert
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class,
                () -> sut.authenticateUser(testUsername, "wrongPassword"));
        assertEquals("Passwords do not match", exception.getMessage());
    }
}
