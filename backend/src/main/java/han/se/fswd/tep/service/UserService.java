package han.se.fswd.tep.service;

import han.se.fswd.tep.dao.UserDao;
import han.se.fswd.tep.dao.UserDaoImpl;
import han.se.fswd.tep.exceptions.InvalidPasswordException;
import han.se.fswd.tep.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDaoImpl;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserDaoImpl userDaoImpl) {
        this.passwordEncoder = passwordEncoder;
        this.userDaoImpl = userDaoImpl;
    }

    public String authenticateUser(String username, String password) {

        User user = userDaoImpl.findByUsername(username);

        String dbPassword = user.getPassword();

        // If it does not match, exception will be thrown, further code execution should stop.
        validateUserPassword(password, dbPassword);

        // FIXME Temp return, update with token once JWTUtil is available
        return "JWT Issued";
    }

    public void validateUserPassword(String rawPassword, String storedHashedPassword) {
        // Check if the provided password (with pepper) matches the hashed password from the DB

        // Would be better to move to application.properties or .env file, for now putting the pepper directly here.
        String pepper = "DGmhu0H![3(]z.1u=+vlfjN&kuLJwYK6z'101S_T!m37^Py(T>";
        if (!passwordEncoder.matches(rawPassword + pepper, storedHashedPassword)) {
            throw new InvalidPasswordException("Passwords do not match");
        }
    }

}
