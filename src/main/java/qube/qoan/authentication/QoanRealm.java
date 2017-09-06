/*
 * Copyright 2017 Qoan Software Association. All rights reserved.
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
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import qube.qai.main.QaiConstants;
import qube.qai.user.User;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public class QoanRealm extends JdbcRealm {

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return super.doGetAuthenticationInfo(token);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return super.doGetAuthorizationInfo(principals);
    }

    @Override
    protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        return super.getRoleNamesForUser(conn, username);
    }

    @Override
    protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames) throws SQLException {
        return super.getPermissions(conn, username, roleNames);
    }

    @Override
    protected String getSaltForUser(String username) {
        return super.getSaltForUser(username);
    }

    public User findUser(String username) {

        IMap<String, User> userMap = hazelcastInstance.getMap(QaiConstants.USERS);
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("username").equal(username);

        Collection<User> employees = userMap.values(predicate);

        if (employees == null || employees.isEmpty()) {
            return null;
        }

        if (employees.size() != 1) {
            throw new IllegalStateException("there are more than one user with username: " + username);
        }

        return employees.iterator().next();
    }

    public User createUser(String username, String password) {

        User user = findUser(username);
        if (user == null) {
            user = new User(username, password);
            IMap<String, User> userMap = hazelcastInstance.getMap(QaiConstants.USERS);
            userMap.put(user.getUuid(), user);
        }

        return user;
    }
}
