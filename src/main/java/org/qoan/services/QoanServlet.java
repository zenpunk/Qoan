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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.server.VaadinServlet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class QoanServlet extends VaadinServlet {

    private Injector injector;

    private QoanModule qoanModule;

    private QoanSearchServicesModule searchServices;

    private QoanSecurityModule securityModule;

    private Properties properties;

    private ClientConfig config;

    // development properties- dev-grid
    private String PROPERTIES_FILE = "org/qoan/services/config_dev.properties";
    // deployent properties- qai-grid
    //private String PROPERTIES_FILE = "org/qoan/services/config_deploy.properties";

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // and we load the whole configuration to create the injector
        // which will be used all over the application
        if (injector == null) {

            loadProperties();

            config = new ClientConfig();
            ServletContext context = servletConfig.getServletContext();
            qoanModule = new QoanModule(properties, config);
            searchServices = new QoanSearchServicesModule(properties);

            Injector dummy = Guice.createInjector(qoanModule);
            dummy.injectMembers(searchServices);
            HazelcastInstance hazelcastInstance = dummy.getInstance(HazelcastInstance.class);

            securityModule = new QoanSecurityModule(hazelcastInstance, context);
            injector = Guice.createInjector(qoanModule,
                    securityModule,
                    searchServices,
                    ShiroWebModule.guiceFilterModule());
            QoanInjectorService.getInstance().setInjector(injector);

            // initialize the security manager while we're at it.
            Realm realm = securityModule.provideRealm();
            WebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
            SecurityUtils.setSecurityManager(securityManager);

        }
    }

    private Properties loadProperties() {
        try {

            properties = new Properties();

            ClassLoader loader = QoanServlet.class.getClassLoader();
            URL url = loader.getResource(PROPERTIES_FILE);
            properties.load(url.openStream());
        } catch (IOException e) {
            throw new IllegalStateException("Properties file " + PROPERTIES_FILE + " could not be loaded!");
        }

        return properties;
    }
}
