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

package qube.qoan.services;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.vaadin.ui.UI;
import qube.qoan.QoanUI;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Created by rainbird on 11/26/15.
 */
public class QoanServletConfig extends GuiceServletContextListener {

    private boolean debug = true;

    private ServletContext servletContext;

    private Injector injector;



    @Override
    public Injector getInjector() {

        if (injector == null) {
            ServletModule module = new ServletModule() {
                @Override
                protected void configureServlets() {
                    log("Starting to filter servlets...");
                    //serve("/*").with(VaadinServlet.class);
                    serve("/*").with(QoanServlet.class);

                    bind(UI.class).to(QoanUI.class).in(ServletScopes.SESSION);
                    //bind(Realm.class).to(QoanRealm.class).asEagerSingleton();

                }
            };
            injector = QoanInjectorService.getInstance().getInjector();
        }

        return injector;
    }

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        this.servletContext = servletContextEvent.getServletContext();
        super.contextInitialized(servletContextEvent);
    }

    private void log(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }
}
