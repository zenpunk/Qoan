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

package qube.qoan.gui.components.common;

import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.apache.shiro.SecurityUtils;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.gui.views.*;

/**
 * Created by rainbird on 11/9/15.
 */
public class QoanHeader extends Panel {

    private Button logoutButton;

    public QoanHeader() {
        super();

        initialize();
    }

    private void initialize() {

        HorizontalLayout layout = new HorizontalLayout();

        // add some space before the icon
        Label space = new Label("&nbsp;");
        space.setContentMode(ContentMode.HTML);
        layout.addComponent(space);
        // Image as a file resource
        ClassResource resource = new ClassResource("gui/images/kokoline.gif");
        Image image = new Image(null, resource);
        image.setWidth("20px");
        image.setHeight("30px");
        layout.addComponent(image);

        // button for navigating to Welcome page
        Button homeButton = new Button("Home");
        homeButton.addClickListener(clickEvent -> onHomeClicked());
        homeButton.setStyleName("link");
        layout.addComponent(homeButton);

        // button for navigating to the Workspace
        Button workspaceButton = new Button("Workspace");
        workspaceButton.addClickListener(clickEvent -> onWorkspaceClicked());
        workspaceButton.setStyleName("link");
        layout.addComponent(workspaceButton);

        // button for navigating to the Components view
        Button componentsButton = new Button("Components");
        componentsButton.addClickListener(clickEvent -> onComponenetsClicked());
        componentsButton.setStyleName("link");
        layout.addComponent(componentsButton);

        // button for navigating to the Components view
        Button managementButton = new Button("Management");
        managementButton.addClickListener(clickEvent -> onManagementClicked());
        managementButton.setStyleName("link");
        layout.addComponent(managementButton);

        Button registrationButton = new Button("Registration");
        registrationButton.addClickListener(clickEvent -> onRegistrationClicked());
        registrationButton.setStyleName("link");
        layout.addComponent(registrationButton);

        // With the current web-site configuration this has become rather useless.
//        Button wikiButton = new Button("Wiki");
//        wikiButton.addClickListener(clickEvent -> onWikiCLicked());
//        wikiButton.setStyleName("link");
//        layout.addComponent(wikiButton);

        logoutButton = new Button("Logout");
        logoutButton.addClickListener(clickEvent -> onLogoutClicked());
        logoutButton.setStyleName("link");
        // check whether there is a registered user
        checkLogoutState();
        layout.addComponent(logoutButton);

        setContent(layout);
    }

    private void checkLogoutState() {
        User user = ((QoanUI) UI.getCurrent()).getUser();
        if (user == null) {
            logoutButton.setVisible(false);
        } else {
            logoutButton.setCaption("Logout [" + user.getUsername() + "]");
            logoutButton.setVisible(true);
        }
    }

    public void onLogoutClicked() {
        ((QoanUI) UI.getCurrent()).setUser(null);
        SecurityUtils.getSubject().logout();
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        vaadinSession.getSession().invalidate();
        checkLogoutState();
        onHomeClicked();
    }

    public void onWorkspaceClicked() {
        checkLogoutState();
        ((QoanUI) UI.getCurrent()).setTargetViewName(WorkspaceView.NAME);
        UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);
    }

    public void onHomeClicked() {
        checkLogoutState();
        ((QoanUI) UI.getCurrent()).setTargetViewName(StartView.NAME);
        UI.getCurrent().getNavigator().navigateTo(StartView.NAME);
    }

    public void onComponenetsClicked() {
        checkLogoutState();
        ((QoanUI) UI.getCurrent()).setTargetViewName(ComponentsView.NAME);
        UI.getCurrent().getNavigator().navigateTo(ComponentsView.NAME);
    }

    public void onManagementClicked() {
        checkLogoutState();
        ((QoanUI) UI.getCurrent()).setTargetViewName(ManagementView.NAME);
        UI.getCurrent().getNavigator().navigateTo(ManagementView.NAME);
    }

    public void onWikiCLicked() {
        checkLogoutState();
        ((QoanUI) UI.getCurrent()).setTargetViewName(WikiView.NAME);
        UI.getCurrent().getNavigator().navigateTo(WikiView.NAME);
    }

    public void onRegistrationClicked() {
        checkLogoutState();
        ((QoanUI) UI.getCurrent()).setTargetViewName(RegistrationView.NAME);
        UI.getCurrent().getNavigator().navigateTo(RegistrationView.NAME);
    }

    private boolean isOnPage(String viewName) {

        String currentPage = UI.getCurrent().getPage().getWindowName();
        if (currentPage.equals(viewName)) {
            return false;
        }
        return true;
    }

}
