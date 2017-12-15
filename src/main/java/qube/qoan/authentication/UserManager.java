/*
 * Copyright 2017 Qoan Wissenschaft & Software. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package qube.qoan.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import qube.qai.security.QaiRealm;
import qube.qai.user.User;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/24/15.
 */
public class UserManager implements UserManagerInterface {

    @Inject
    private QaiRealm realm;

    //@Inject
    //private SecurityManager securityManager;

    /**
     * this is in order to authenticate the user with the
     * given username and password
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public User authenticateUser(String username, String password) throws UserNotAuthenticatedException {

        User user = null;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //this is all you have to do to support 'remember me' (no config - built in!):
        token.setHost("/qoan#!");
        token.setRememberMe(true);

        subject.login(token);
        if (subject.isAuthenticated()) {
            user = (User) subject.getPrincipal();
        } else {
            throw new UserNotAuthenticatedException();
        }

        return user;
    }

    @Override
    public User findUser(String username) {
        return realm.findUser(username);
    }

    @Override
    public boolean isUserRole(String roleName) {

        Subject subject = SecurityUtils.getSubject();

        return subject.hasRole(roleName);
    }

    @Override
    public boolean isUserPermission(String permissionName) throws UserNotAuthorizedException {

        Subject subject = SecurityUtils.getSubject();

        return subject.isPermitted(permissionName);
    }

    @Override
    public User createUser(String username, String password, String rolename, String... permissions) {
        return realm.createUser(username, password, rolename, permissions);
    }

    @Override
    public void removeUser(String username) {
        realm.removeUser(username);
    }

}
