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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.user.User;
import qube.qoan.services.QoanTestBase;

public class TestQoanRealm extends QoanTestBase {

    private static Logger logger = LoggerFactory.getLogger("TestQoanRealm");

    public void testQoanRealm() throws Exception {

        QoanRealm realm = new QoanRealm();
        injector.injectMembers(realm);

        String username = createUsername();
        User user = realm.createUser(username, "password");
        assertNotNull(user);
        logger.info("Created user with username: " + user.getUsername());

        User foundUser = realm.findUser(username);
        assertNotNull("there has to be a found user", foundUser);
        assertTrue("users must be same", user.equals(foundUser));
        logger.info("found user " + username + " to have " + foundUser.getUuid());
    }

    private String createUsername() {
        return "user_" + System.currentTimeMillis();
    }
}
