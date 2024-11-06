package han.se.fswd.tep;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import han.se.fswd.tep.dao.UserDaoImpl;
import han.se.fswd.tep.exceptions.DatabaseException;
import han.se.fswd.tep.exceptions.InvalidUserInputException;
import han.se.fswd.tep.exceptions.UserNotFoundException;
import han.se.fswd.tep.mapper.UserRowMapper;
import han.se.fswd.tep.module.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDaoImpl sut;  // System Under Test

    private final String testUsername = "testUser";
    private final int validUserId = 1;
    private final int invalidUserId = -1;  // Invalid ID

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(validUserId, testUsername, "password123");
    }

    // Tests for doesUserExist
    @Test
    void doesUserExist_WhenUserExists_ShouldReturnTrue() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(validUserId)))
                .thenReturn(1);  // Simulate that one user is found

        // Act
        Boolean result = sut.doesUserExist(validUserId);

        // Assert
        assertTrue(result);  // Expecting true because the user exists
    }

    @Test
    void doesUserExist_WhenQueryReturnsNull_ShouldReturnFalse() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(validUserId)))
                .thenReturn(null);  // Simulate that no result is found (null is returned)

        // Act
        Boolean result = sut.doesUserExist(validUserId);

        // Assert
        assertFalse(result);  // Expecting false because null indicates no user found
    }


    @Test
    void doesUserExist_WhenUserDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(validUserId)))
                .thenReturn(0);  // Simulate that no user is found

        // Act
        Boolean result = sut.doesUserExist(validUserId);

        // Assert
        assertFalse(result);  // Expecting false because the user doesn't exist
    }

    @Test
    void doesUserExist_WhenDatabaseErrorOccurs_ShouldThrowDatabaseException() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(validUserId)))
                .thenThrow(new DataAccessException("Database error") {});

        // Act & Assert
        DatabaseException de = assertThrows(DatabaseException.class,
                () -> sut.doesUserExist(validUserId));
        assertEquals("Database access error", de.getMessage());
    }


    @Test
    void findByUsername_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(testUsername)))
                .thenReturn(mockUser);

        // Act
        User result = sut.findByUsername(testUsername);

        // Assert
        assertNotNull(result);
        assertEquals(mockUser.getUsername(), result.getUsername());
        assertEquals(mockUser.getPassword(), result.getPassword());
    }

    @Test
    void findByUsername_WhenUserNotFound_ShouldThrowUserNotFoundException() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(testUsername)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        UserNotFoundException unfe = assertThrows(UserNotFoundException.class,
                () -> sut.findByUsername(testUsername));
        assertEquals("User not found with username: " + testUsername, unfe.getMessage());
    }

    @Test
    void findByUsername_WhenDatabaseErrorOccurs_ShouldThrowDatabaseException() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(testUsername)))
                .thenThrow(new DataAccessException("Database error") {});

        // Act & Assert
        DatabaseException de = assertThrows(DatabaseException.class,
                () -> sut.findByUsername(testUsername));
        assertEquals("Database access error", de.getMessage());
    }

    @Test
    void findByUsername_WhenInvalidUsername_ShouldThrowInvalidUserInputException() {
        // Arrange
        String invalidUsernameEmpty = "";  // Invalid because it's empty
        String invalidUsernameTooShort = "ab"; // Invalid because it's too short
        String invalidUsernameTooLong = "thisIsAVeryLongUsername"; // Invalid because it's too long

        // Act & Assert for empty username
        InvalidUserInputException emptyEx = assertThrows(InvalidUserInputException.class,
                () -> sut.findByUsername(invalidUsernameEmpty));
        assertEquals("Username must be between 3 and 16 characters", emptyEx.getMessage());

        // Act & Assert for too short username
        InvalidUserInputException shortEx = assertThrows(InvalidUserInputException.class,
                () -> sut.findByUsername(invalidUsernameTooShort));
        assertEquals("Username must be between 3 and 16 characters", shortEx.getMessage());

        // Act & Assert for too long username
        InvalidUserInputException longEx = assertThrows(InvalidUserInputException.class,
                () -> sut.findByUsername(invalidUsernameTooLong));
        assertEquals("Username must be between 3 and 16 characters", longEx.getMessage());
    }

    // Tests for findById
    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(validUserId)))
                .thenReturn(mockUser);

        // Act
        User result = sut.findById(validUserId);

        // Assert
        assertNotNull(result);
        assertEquals(mockUser.getUsername(), result.getUsername());
        assertEquals(mockUser.getPassword(), result.getPassword());
    }

    @Test
    void findById_WhenUserNotFound_ShouldThrowUserNotFoundException() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(validUserId)))
                .thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        UserNotFoundException unfe = assertThrows(UserNotFoundException.class,
                () -> sut.findById(validUserId));
        assertEquals("User not found with ID: " + validUserId, unfe.getMessage());
    }

    @Test
    void findById_WhenDatabaseErrorOccurs_ShouldThrowDatabaseException() {
        // Arrange
        when(jdbcTemplate.queryForObject(anyString(), any(UserRowMapper.class), eq(validUserId)))
                .thenThrow(new DataAccessException("Database error") {});

        // Act & Assert
        DatabaseException de = assertThrows(DatabaseException.class,
                () -> sut.findById(validUserId));
        assertEquals("Database access error", de.getMessage());
    }

    @Test
    void findById_WhenInvalidUserId_ShouldThrowInvalidUserInputException() {
        // Arrange & Act & Assert for invalid userId (negative value)
        InvalidUserInputException invalidIdEx = assertThrows(InvalidUserInputException.class,
                () -> sut.findById(invalidUserId));
        assertEquals("User ID must be a positive integer", invalidIdEx.getMessage());
    }
}
