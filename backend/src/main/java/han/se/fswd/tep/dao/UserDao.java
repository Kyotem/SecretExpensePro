package han.se.fswd.tep.dao;

import han.se.fswd.tep.module.User;

// Interface isn't really necessary in this case as far I'm aware, still implementing this for scalability-reasons.
public interface UserDao {
    User findByUsername(String username);

    User findById(int id);

    Boolean doesUserExist(int id);

}
