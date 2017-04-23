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
import qube.qai.services.SearchServiceInterface;
import qube.qai.user.User;
import qube.qoan.services.QoanTestBase;

import javax.inject.Inject;

/**
 * Created by rainbird on 4/21/17.
 */
public class TestUserManager extends QoanTestBase {

    @Inject
    private HazelcastInstance hazelcastInstance;

    public void testUserManager() throws Exception {

        UserManager manager = new UserManager();
        injector.injectMembers(manager);
//        ModelStore modelStore = new ModelStore("./test/dummy.model.directory");
//        modelStore.init();
//        manager.setUserSearchService(modelStore);

        SearchServiceInterface modelStore = manager.getUserSearchService();

        // first create the user to check the positive case
        String userName = "test_user";
        String password = "password";
        User user = new User(userName, password);
        IMap<String, User> userMap = hazelcastInstance.getMap("USERS");
        userMap.put(user.getUuid(), user);

        User authUser = manager.authenticateUser(user.getUsername(), user.getPassword());
        assertNotNull("there has to be a user", authUser);
        assertTrue("the users must be equal", user.equals(authUser));

        userMap.remove(user.getUuid());

        authUser = manager.authenticateUser(user.getUsername(), user.getPassword());
        assertTrue("the user is removed", authUser == null);
    }
}
