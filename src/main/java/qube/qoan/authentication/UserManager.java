package qube.qoan.authentication;

import qube.qai.user.User;

/**
 * Created by rainbird on 12/24/15.
 */
public class UserManager {

    /**
     * this is in order to authenticate the user with the
     * given username and password
     * @param username
     * @param password
     * @return
     */
    public User authenticateUser(String username, String password) {

        User user = new User();
        user.setUserName(username);

        return user;
    }
}
