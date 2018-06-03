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

package org.qoan;

import com.google.inject.Injector;
import com.hazelcast.core.HazelcastInstance;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;
import org.qai.services.SearchServiceInterface;
import org.qai.user.User;
import org.qoan.authentication.SecureSessionInitListener;
import org.qoan.authentication.SecureViewChangeListener;
import org.qoan.authentication.UserManagerInterface;
import org.qoan.gui.views.*;
import org.qoan.services.QoanInjectorService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Set;

/**
 *
 */
//@Push
@Theme("mytheme")
@Widgetset("org.qoan.MyWidgetset")
public class QoanUI extends UI {

    protected Navigator navigator;

    protected SecureViewChangeListener changeListener;

    protected SecureSessionInitListener sessionListener;

    protected WorkspaceView workspaceView;

    protected ManagementView managementView;

    protected RegistrationView registrationView;

    protected Injector injector;

    @Inject
    protected HazelcastInstance hazelcastInstance;

    @Inject
    protected UserManagerInterface userManager;

    @Inject
    @Named("ServicesMap")
    Map<String, SearchServiceInterface> searchServiceMap;

    protected User user;

    protected String targetViewName;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        injector = QoanInjectorService.getInstance().getInjector();
        injector.injectMembers(this);

        getPage().setTitle("Qoan");

        // Create a navigator to control the views
        navigator = new Navigator(this, this);
        changeListener = new SecureViewChangeListener();
        navigator.addViewChangeListener(changeListener);
        sessionListener = new SecureSessionInitListener();
        VaadinService.getCurrent().addSessionInitListener(sessionListener);

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

        // initialize the registration-view as well
        if (registrationView == null) {
            registrationView = new RegistrationView();
            injector.injectMembers(registrationView);
        }

        // Create and register the views
        navigator.addView("", StartView.class);
        navigator.addView(LoginView.NAME, LoginView.class);
        navigator.addView(ComponentsView.NAME, ComponentsView.class);
        navigator.addView(WorkspaceView.NAME, workspaceView);
        navigator.addView(ManagementView.NAME, managementView);
        navigator.addView(RegistrationView.NAME, registrationView);
        // with the new web page design, this is no longer really relevant
        //navigator.addView(WikiView.NAME, WikiView.class);
    }

    public SearchServiceInterface getNamedService(String name) {
        return searchServiceMap.get(name);
    }

    public Set<String> getSearchServiceNames() {
        return searchServiceMap.keySet();
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
