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

package qube.qoan;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import qube.qai.services.SearchServiceInterface;
import qube.qai.user.User;
import qube.qoan.authentication.SecureViewChangeListener;
import qube.qoan.gui.views.*;
import qube.qoan.services.QoanModule;

import java.util.Set;

/**
 *
 */
//@WebServlet(value = "/*", asyncSupported = true)
//@VaadinServletConfiguration(productionMode = false, ui = QoanUI.class)
@Theme("mytheme")
@Widgetset("qube.qoan.MyWidgetset")
public class QoanUI extends UI {

    protected Navigator navigator;

    protected SecureViewChangeListener changeListener;

    protected WorkspaceView workspaceView;

    protected ManagementView managementView;

    protected Injector injector;

    protected QoanModule qoanModule;

    protected HazelcastInstance hazelcastInstance;

    protected SearchServiceInterface wikipediaSearchService;

    protected SearchServiceInterface wiktionarySearchService;

    protected User user;

    protected String targetViewName;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        // this way we have a different injector for each thread
        qoanModule = new QoanModule();
        injector = Guice.createInjector(qoanModule); // , new QaiModule() do i really need this here???

        hazelcastInstance = injector.getInstance(HazelcastInstance.class);
        //injector.injectMembers(this);
        wikipediaSearchService = qoanModule.provideWikipediaSearchService();
        wiktionarySearchService = qoanModule.provideWiktionarySearchService();

        getPage().setTitle("Qoan");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);
        changeListener = new SecureViewChangeListener();
        navigator.addViewChangeListener(changeListener);

        // instantiate the workspace-view
        if (workspaceView == null) {
            workspaceView = new WorkspaceView();
        }

        // instantiate the management-view,
        // so that the thing has time to create its listeners and things
        if (managementView == null) {
            managementView = new ManagementView();
            injector.injectMembers(managementView);
        }

        // Create and register the views
        navigator.addView("", StartView.class);
        navigator.addView(LoginView.NAME, LoginView.class);
        navigator.addView(ComponentsView.NAME, ComponentsView.class);
        navigator.addView(WorkspaceView.NAME, workspaceView);
        navigator.addView(ManagementView.NAME, managementView);
        navigator.addView(WikiView.NAME, WikiView.class);
    }

    public SearchServiceInterface getNamedService(String name) {
        return qoanModule.getNamedService(name);
    }

    public Set<String> getSearchServiceNames() {
        return qoanModule.getSearchServiceNames();
    }

    public Injector getInjector() {
        return injector;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTargetViewName() {
        return targetViewName;
    }

    public void setTargetViewName(String targetViewName) {
        this.targetViewName = targetViewName;
    }
}
