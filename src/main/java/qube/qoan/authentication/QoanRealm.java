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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import qube.qai.main.QaiConstants;
import qube.qai.user.Permission;
import qube.qai.user.Role;
import qube.qai.user.User;

import javax.inject.Inject;
import java.util.Collection;

public class QoanRealm implements Realm {

    private String name = "QoanRealm";

    @Inject
    private HazelcastInstance hazelcastInstance;

    public QoanRealm() {
    }

    public QoanRealm(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }


    public User createUser(String username, String password, String rolename, String... permissions) {

        User user = findUser(username);
        if (user == null) {
            user = new User(username, password);
            user.addRole(new Role(rolename));
            for (String permission : permissions) {
                user.addPermission(new Permission(permission));
            }
            IMap<String, User> userMap = hazelcastInstance.getMap(QaiConstants.USERS);
            userMap.put(user.getUuid(), user);
        }

        return user;
    }

    public void removeUser(String username) {

        User user = findUser(username);
        if (user != null) {
            IMap<String, User> userMap = hazelcastInstance.getMap(QaiConstants.USERS);
            userMap.remove(user.getUuid(), user);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        User user = findUser(username);
        if (user == null) {
            throw new AuthenticationException("Username or password not right for '" + username + "'");
        } else if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Username or password not right for '" + username + "'");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());

        return info;
    }

    public User findUser(String username) {

        IMap<String, User> userMap = hazelcastInstance.getMap(QaiConstants.USERS);
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("username").equal(username);

        Collection<User> users = userMap.values(predicate);

        if (users == null || users.isEmpty()) {
            return null;
        }

        if (users.size() != 1) {
            throw new IllegalStateException("there are more than one user with username: " + username);
        }

        return users.iterator().next();
    }

    /**
     * this function loads user authorization data from "userManager" data source (database)
     * User, Role are custom POJOs (beans) and are loaded from database.
     * WildcardPermission implements shiros Permission interface, so my permissions in database gets accepted by shiro security
     **/
   /* @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {

        Set<String> roles = new HashSet<>();
        Set<org.apache.shiro.authz.Permission> permissions = new HashSet<>();
        Collection<User> principalsList = principals.byType(User.class);

        if (principalsList.isEmpty()) {
            throw new AuthorizationException("Empty principals list!");
        }
        //LOADING STUFF FOR PRINCIPAL
        for (User userPrincipal : principalsList) {

            User user = findUser(userPrincipal.getUsername());

            Set<Role> userRoles = user.getRoles();
            for (Role r : userRoles) {
                roles.add(r.getName());
            }
            for (Permission p : user.getPermissions()) {
                permissions.add(p);
            }
        }
        //THIS IS THE MAIN CODE YOU NEED TO DO !!!!
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setRoles(roles); //fill in roles
        info.setObjectPermissions(permissions); //add permisions (MUST IMPLEMENT SHIRO PERMISSION INTERFACE)

        return info;
    }*/

   /* @Override
    protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        User user = findUser(username);
        if (user == null) {
            return null;
        }
        Set<String> rolenames = new HashSet<>();
        for (Role role : user.getRoles()) {
            rolenames.add(role.getName());
        }
        return rolenames;
    }*/

    /*@Override
    protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames) throws SQLException {
        User user = findUser(username);
        if (user == null) {
            return null;
        }
        Set<String> permissions = new HashSet<>();
        for (Permission permission : user.getPermissions()) {
            permissions.add(permission.getName());
        }

        return permissions;
    }*/

    /*@Override
    protected String getSaltForUser(String username) {
        User user = findUser(username);
        if (user != null) {
            return user.getUuid();
        }
        return UUIDService.uuidString();
    }*/

}
