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

import qube.qai.services.DataServiceInterface;
import qube.qai.user.User;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rainbird on 12/24/15.
 */
public class UserManager {


    @Inject
    @Named("USER")
    private DataServiceInterface userSearchService;

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

        //Collection<SearchResult> results = userSearchService.searchInputString(username, "USER", 1);


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
