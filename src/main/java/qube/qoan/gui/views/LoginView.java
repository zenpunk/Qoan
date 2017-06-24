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

package qube.qoan.gui.views;

import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qube.qai.user.User;
import qube.qoan.QoanUI;
import qube.qoan.authentication.UserManager;

import javax.inject.Inject;

/**
 * Created by rainbird on 12/24/15.
 */
public class LoginView extends QoanView {

    private Logger logger = LoggerFactory.getLogger("LoginView");

    public static String NAME = "LoginView";

    @Inject
    private UserManager userManager;

    public LoginView() {
        this.viewTitle = "Qoan Login";
    }

    //    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//        UI.getCurrent().getPage().setContext("Qoan Login");
//
//        Injector injector = ((QoanUI) UI.getCurrent()).getInjector();
//        injector.injectMembers(this);
//
//        QoanHeader header = new QoanHeader();
//        addComponent(header);
//
//
//    }

    @Override
    protected void initialize() {
        FormLayout layout = new FormLayout();
        TextField userField = new TextField("Username");
        layout.addComponent(userField);

        PasswordField passwordField = new PasswordField("Password");
        layout.addComponent(passwordField);

        Button loginButton = new Button("Login");
        loginButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                User user = null;
//                try {
//                    user = userManager.authenticateUser(userField.getValue(), passwordField.getValue());
//                } catch (UserNotAuthenticatedException e) {
//                    logger.error("User: '" + userField.getValue() + "' with password: '" + passwordField.getValue() + "' cannot be authenticated", e);
//                }
//                ((QoanUI) UI.getCurrent()).setUser(user);
                String targetPage = ((QoanUI) UI.getCurrent()).getTargetViewName();
                if (targetPage == null) {
                    targetPage = StartView.NAME;
                }
                UI.getCurrent().getNavigator().navigateTo(targetPage);
            }
        });
        layout.addComponent(loginButton);

        addComponent(layout);
        setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
    }
}
