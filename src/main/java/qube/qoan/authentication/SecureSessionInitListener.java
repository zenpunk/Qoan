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

import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.user.User;

import javax.inject.Inject;

public class SecureSessionInitListener implements SessionInitListener {

    private Logger logger = LoggerFactory.getLogger("SecureSessionInitListener");

    @Inject
    SecurityManager securityManager;

    @Override
    public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {

        Subject subject = SecurityUtils.getSubject();
        if (subject == null || subject.getPrincipal() == null) {
            logger.info("Session has has been started for anonymous user");
        } else if (subject.getPrincipal() != null) {
            User user = (User) subject.getPrincipal();
            logger.info("Session has has been started for '" + user.getUsername() + "'");
        }

    }
}
