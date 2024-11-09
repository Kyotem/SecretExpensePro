package han.se.fswd.tep.mapper;

import han.se.fswd.tep.module.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRowMapperTest {

    private UserRowMapper userRowMapper;

    @BeforeEach
    void setUp() {
        userRowMapper = new UserRowMapper();
    }

    @Test
    void mapRow_ValidResultSet_ReturnsUser() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("username")).thenReturn("testUser");
        when(resultSet.getString("password")).thenReturn("password123");

        // Act
        User user = userRowMapper.mapRow(resultSet, 1);

        // Assert
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void mapRow_ThrowsSQLException() throws SQLException {
        // Arrange
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenThrow(new SQLException("SQL error"));

        // Act & Assert
        SQLException thrown = assertThrows(SQLException.class,
                () -> userRowMapper.mapRow(resultSet, 1));
        assertEquals("SQL error", thrown.getMessage());
    }
}
