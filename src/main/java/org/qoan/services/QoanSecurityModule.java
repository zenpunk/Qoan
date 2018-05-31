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

package org.qoan.services;

import com.google.inject.Exposed;
import com.google.inject.Provides;
import com.hazelcast.core.HazelcastInstance;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.qoan.authentication.QoanRealm;
import org.qoan.authentication.UserManager;
import org.qoan.authentication.UserManagerInterface;

import javax.inject.Singleton;
import javax.servlet.ServletContext;

/**
 * Created by rainbird on 7/19/17.
 */
public class QoanSecurityModule extends ShiroWebModule {

    private QoanRealm qoanRealm;

    private UserManagerInterface userManager;

    private HazelcastInstance hazelcastInstance;

    public QoanSecurityModule(HazelcastInstance hazelcastInstance, ServletContext servletContext) {
        super(servletContext);
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    protected void configureShiroWeb() {

        try {
            bindRealm().toConstructor(IniRealm.class.getConstructor(Ini.class));
        } catch (NoSuchMethodException e) {
            addError(e);
        }

        addFilterChain("/qoan#", ANON);
        addFilterChain("/qoan#!welcome", AUTHC_BASIC);
        addFilterChain("/qoan#!components", AUTHC_BASIC);
        addFilterChain("/qoan#!wiki", AUTHC_BASIC);
        addFilterChain("/qoan#!registration", AUTHC_BASIC);
        addFilterChain("/qoan#!workspace", AUTHC_BASIC);
        addFilterChain("/qoan#!management", AUTHC_BASIC);
        addFilterChain("/**", ANON);
    }

    @Provides
    @Exposed
    Realm provideRealm() {
        if (qoanRealm == null) {
            qoanRealm = new QoanRealm(hazelcastInstance);
        }
        return qoanRealm;
    }

    @Provides
    @Exposed
    @Singleton
    UserManagerInterface provideUserManager() {

        if (userManager == null) {
            userManager = new UserManager(provideRealm());
        }

        return userManager;
    }

    @Provides
    Ini loadShiroIni() {
        return Ini.fromResourcePath("classpath:org/qoan/services/shiro.ini");
    }

}
