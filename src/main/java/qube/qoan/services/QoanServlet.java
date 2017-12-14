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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import qube.qoan.QoanUI;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = QoanUI.class)
public class QoanServlet extends VaadinServlet {

    private Injector injector;

    private QoanModule qoanModule;

    private QoanSecurityModule securityModule;

    /*@Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();


    }*/

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // and we load the whole configuration to create the injector
        // which will be used all over the application
        if (injector == null) {

            ServletContext context = servletConfig.getServletContext();
            qoanModule = new QoanModule();

            Injector dummy = Guice.createInjector(qoanModule);
            HazelcastInstance hazelcastInstance = dummy.getInstance(HazelcastInstance.class);

            securityModule = new QoanSecurityModule(hazelcastInstance, context);
            injector = Guice.createInjector(qoanModule, securityModule, ShiroWebModule.guiceFilterModule());
            QoanInjectorService.getInstance().setInjector(injector);

            // initialize the security manager while we're at it.
            Realm realm = securityModule.provideRealm();
            WebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
            SecurityUtils.setSecurityManager(securityManager);

        }
    }
}
