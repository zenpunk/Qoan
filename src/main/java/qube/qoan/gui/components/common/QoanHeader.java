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

package qube.qoan.gui.components.common;

import com.vaadin.server.ClassResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import qube.qoan.QoanUI;
import qube.qoan.gui.views.*;

/**
 * Created by rainbird on 11/9/15.
 */
public class QoanHeader extends Panel {

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

        Button wikiButton = new Button("Wiki");
        wikiButton.addClickListener(clickEvent -> onWikiCLicked());
        wikiButton.setStyleName("link");
        layout.addComponent(wikiButton);

        setContent(layout);
    }

    public void onWorkspaceClicked() {
        ((QoanUI) UI.getCurrent()).setTargetViewName(WorkspaceView.NAME);
        UI.getCurrent().getNavigator().navigateTo(WorkspaceView.NAME);
    }

    public void onHomeClicked() {
        ((QoanUI) UI.getCurrent()).setTargetViewName(StartView.NAME);
        UI.getCurrent().getNavigator().navigateTo(StartView.NAME);
    }

    public void onComponenetsClicked() {
        ((QoanUI) UI.getCurrent()).setTargetViewName(ComponentsView.NAME);
        UI.getCurrent().getNavigator().navigateTo(ComponentsView.NAME);
    }

    public void onManagementClicked() {
        ((QoanUI) UI.getCurrent()).setTargetViewName(ManagementView.NAME);
        UI.getCurrent().getNavigator().navigateTo(ManagementView.NAME);
    }

    public void onWikiCLicked() {
        ((QoanUI) UI.getCurrent()).setTargetViewName(WikiView.NAME);
        UI.getCurrent().getNavigator().navigateTo(WikiView.NAME);
    }

    public void onRegistrationClicked() {
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

    private boolean authenticationRequired(String viewName) {

//        String currentPage = UI.getCurrent().getEmbedId();
//        User user = ((QoanUI) UI.getCurrent()).getUser();

        // for the time being stop this so
        // can't be bothered
//        if (ManagementView.NAME.equals(viewName)
//                || WorkspaceView.NAME.equals(viewName)
//                && user == null) {
//            return true;
//        }

        return false;
    }
}
