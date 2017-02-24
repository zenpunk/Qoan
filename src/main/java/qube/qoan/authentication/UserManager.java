package qube.qoan.authentication;

import qube.qai.user.User;

/**
 * Created by rainbird on 12/24/15.
 */
public class UserManager {

    /**
     * this is in order to authenticate the user with the
     * given username and password
     *
     * @param username
     * @param password
     * @return
     */
    public User authenticateUser(String username, String password) throws UserNotAuthenticatedException {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // save the thing somewhere perhaps?

        return user;
    }

    public boolean isUserAuthorized(String username, String password, String actionDescription)
            throws UserNotAuthenticatedException, UserNotAuthorizedException {

        User user = authenticateUser(username, password);
        if (user != null) {
            return isUserAuthorized(user, actionDescription);
        }

        return false;
    }

    public boolean isUserAuthorized(User user, String actionDescription) throws UserNotAuthorizedException {

        // here action-descriptions will be converted tand be checked-
        // no idea how that is supposed to work right now

        return true;
    }
}
