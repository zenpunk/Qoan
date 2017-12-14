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

package qube.qoan.services;

import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;

public class QoanSecurityFilter extends ShiroFilter {

    static class SecurityManagerFactory extends WebIniSecurityManagerFactory {

        private final WebSecurityManager securityManager;

        public SecurityManagerFactory(WebSecurityManager securityManager) {
            this.securityManager = securityManager;
        }

        public SecurityManagerFactory(WebSecurityManager securityManager, Ini ini) {
            super(ini);
            this.securityManager = securityManager;
        }

        @Override
        protected SecurityManager createDefaultInstance() {
            return securityManager;
        }
    }

    private final Provider<WebSecurityManager> securityManager;

    @Inject
    QoanSecurityFilter(Provider<WebSecurityManager> securityManager) {
        super();
        this.securityManager = securityManager;
    }


    protected Map<String, ?> applySecurityManager(Ini ini) {
        SecurityManagerFactory factory;
        if (ini == null || ini.isEmpty()) {
            factory = new SecurityManagerFactory(securityManager.get());
        } else {
            factory = new SecurityManagerFactory(securityManager.get(), ini);
        }
        setSecurityManager((WebSecurityManager) factory.getInstance());
        return factory.getBeans();
    }
}
