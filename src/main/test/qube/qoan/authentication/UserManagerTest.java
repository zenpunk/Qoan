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
import qube.qai.user.User;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;

/**
 * Created by rainbird on 4/21/17.
 */
public class UserManagerTest extends QoanTestBase {

    @Inject
    private HazelcastInstance hazelcastInstance;

    public void testUserManager() throws Exception {

        UserManager manager = new UserManager();
        injector.injectMembers(manager);

        // first create the user to check the positive case
        String userName = "test_user";
        String password = "password";
        String roleName = "do_all_test_role";
        String permissionName = "do_all_test_permission";
        User user = manager.createUser(userName, password, roleName, permissionName);

        assertNotNull("user has to be created", user);
        assertTrue("there has to be roles", !user.getRoles().isEmpty());
        assertTrue("there has to be permissions", !user.getPermissions().isEmpty());

        User authUser = manager.authenticateUser(userName, password);
        assertNotNull("user must be able to log in", authUser);

        assertTrue("user has to have the role", manager.isUserRole(roleName));
        assertTrue("user has to have the permission", manager.isUserPermission(permissionName));

        manager.removeUser(userName);
    }
}
