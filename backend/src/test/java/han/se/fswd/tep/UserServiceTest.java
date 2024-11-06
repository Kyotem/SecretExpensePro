package han.se.fswd.tep;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import han.se.fswd.tep.dao.UserDaoImpl;
import han.se.fswd.tep.exceptions.InvalidPasswordException;
import han.se.fswd.tep.module.User;
import han.se.fswd.tep.service.UserService;
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
        sut = new UserService(passwordEncoder, userDaoImpl);

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

        // Act
        String result = sut.authenticateUser(testUsername, testPassword);

        // Assert
        // FIXME Temp, Update when JWTUtil is properly created
        assertEquals("JWT Issued", result);
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
