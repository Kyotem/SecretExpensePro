package han.se.fswd.tep.dao;

import han.se.fswd.tep.module.User;
import han.se.fswd.tep.mapper.UserRowMapper;
import han.se.fswd.tep.exceptions.DatabaseException;
import han.se.fswd.tep.exceptions.InvalidUserInputException;
import han.se.fswd.tep.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findByUsername(String username) {

        // Doing a basic check here, the login data being passed through should already have been validated before it reaches this point
        if (username == null || username.length() < 3 || username.length() > 16) {
            throw new InvalidUserInputException("Username must be between 3 and 16 characters");
        }

        String sql = "SELECT userID, username, password FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
        } catch (EmptyResultDataAccessException e) {
            // Handle User not existing in DB
            throw new UserNotFoundException("User not found with username: " + username);
        } catch (DataAccessException e) {
            // Handle SQL-Related errors
            throw new DatabaseException("Database access error", e);
        }
    }

    @Override
    public User findById(int userId) {
        // Validate the user ID input
        if (userId <= 0) {
            throw new InvalidUserInputException("User ID must be a positive integer");
        }

        String sql = "SELECT userID, username, password FROM users WHERE id = ?";
        try {
            // Query the database for the user with the given userId
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            // Handle case where no user is found with the given ID
            throw new UserNotFoundException("User not found with ID: " + userId);
        } catch (DataAccessException e) {
            // Handle SQL-related errors
            throw new DatabaseException("Database access error", e);
        }
    }


    @Override
    public Boolean doesUserExist(int id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
            return count != null && count > 0; // Return true if the count is greater than 0
        } catch (EmptyResultDataAccessException e) {
            // Handle case where no results are returned (should not happen with COUNT)
            return false;
        } catch (DataAccessException e) {
            // Handle SQL-related errors (e.g., log the error)
            throw new DatabaseException("Database access error", e);
        }
    }
}
